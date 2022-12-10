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
 *
 */
@AllArgsConstructor
@Service
@Slf4j
public class ReportService {

    private final ReportRepository reportRepository;
    private final AuthenticationUtils authenticationUtils;
    private final UserRepository userRepository;


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

    public List<ReportDto> getAllUserReports(String username) {
        if (!userRepository.existsByLogin(username)) {
            throw new ResourceNotFoundException("Nie odnaleziono użytkownika dla:" + username);
        }
        List<Report> reports = reportRepository.findAllByUserLoginAndActiveTrue(username);
        List<Report> sortedReports = sortReports(reports);
        return ReportMapper.INSTANCE.toDto(sortedReports);
    }

    public List<ReportDto> getAllReports(){
        List<Report> reports = reportRepository.findAllByActiveTrue();
        List<Report> sortedReports = sortReports(reports);
        return ReportMapper.INSTANCE.toDto(sortedReports);
    }

    private List<Report> sortReports(List<Report> reports){
        return reports.stream().sorted(Comparator.comparing(Report::getDate)).toList();
    }

    public void deactivateReportById(Long idReport){
        Report report = reportRepository.findByIdReportAndActiveTrue(idReport).orElseThrow(
                ()->new ResourceNotFoundException("Nie odnaleziono raportu dla id: "+idReport)
        );
        report.setActive(false);
        reportRepository.save(report);
    }
}
