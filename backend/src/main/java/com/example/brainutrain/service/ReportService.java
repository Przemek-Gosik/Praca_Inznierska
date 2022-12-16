package com.example.brainutrain.service;

import com.example.brainutrain.utils.AuthenticationUtils;
import com.example.brainutrain.dto.ReportDto;
import com.example.brainutrain.exception.ResourceNotFoundException;
import com.example.brainutrain.mapper.ReportMapper;
import com.example.brainutrain.model.Report;
import com.example.brainutrain.model.User;
import com.example.brainutrain.repository.ReportRepository;
import com.example.brainutrain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * Service for reports
 */
@AllArgsConstructor
@Service
@Slf4j
public class ReportService {

    private final ReportRepository reportRepository;
    private final AuthenticationUtils authenticationUtils;
    private final UserRepository userRepository;

    /**
     * Method to save new report
     *
     * @param reportDto is ReportDto
     */
    public void createReport(ReportDto reportDto){
        User user = authenticationUtils.getUserFromAuthentication();
        Report report = ReportMapper.INSTANCE.fromDto(reportDto);
        if(report.getEmail().equals("")){
            report.setEmail(user.getEmail());
        }
        report.setUser(user);
        report.setActive(true);
        reportRepository.save(report);
        log.info("Nowy raport utworzony dla użytkownika o id: "+user.getIdUser());
    }

    /**
     * Method to get report if user has access
     *
     * @param idReport is Long
     * @return is ReportDto
     */
    public ReportDto getUserReport(Long idReport){
        User user = authenticationUtils.getUserFromAuthentication();
        Report report = reportRepository.findByIdReportAndActiveTrue(idReport).orElseThrow(
                ()->new ResourceNotFoundException("Nie odnaleziono raportu dla id: "+idReport)
        );
        if(!report.getUser().equals(user)){
            throw new AccessDeniedException("Brak dostępu do raportu o id: "+idReport);
        }
        return ReportMapper.INSTANCE.toDto(report);
    }

    /**
     * Method to get reports send by user
     *
     * @param username is String
     * @return is List of ReportDto
     */
    public List<ReportDto> getAllUserReports(String username) {
        if (!userRepository.existsByLogin(username)) {
            throw new ResourceNotFoundException("Nie odnaleziono użytkownika dla:" + username);
        }
        List<Report> reports = reportRepository.findAllByUserLoginAndActiveTrue(username);
        List<Report> sortedReports = sortReports(reports);
        return ReportMapper.INSTANCE.toDto(sortedReports);
    }

    /**
     * Method to get all reports
     *
     * @return is list of ReportDto
     */
    public List<ReportDto> getAllReports(){
        List<Report> reports = reportRepository.findAllByActiveTrue();
        List<Report> sortedReports = sortReports(reports);
        return ReportMapper.INSTANCE.toDto(sortedReports);
    }

    /**
     * Method to sort reports by date
     *
     * @param reports is list of Report
     * @return is list of Report
     */
    private List<Report> sortReports(List<Report> reports){
        return reports.stream().sorted(Comparator.comparing(Report::getDate)).toList();
    }

    /**
     * Method to delete report
     *
     * @param idReport is Long
     */
    public void deactivateReportById(Long idReport){
        Report report = reportRepository.findByIdReportAndActiveTrue(idReport).orElseThrow(
                ()->new ResourceNotFoundException("Nie odnaleziono raportu dla id: "+idReport)
        );
        report.setActive(false);
        reportRepository.save(report);
    }
}
