package com.example.brainutrain.service;

import com.example.brainutrain.config.utils.PermissionChecker;
import com.example.brainutrain.dto.ReportDto;
import com.example.brainutrain.exception.ResourceNotFoundException;
import com.example.brainutrain.mapper.ReportMapper;
import com.example.brainutrain.model.Report;
import com.example.brainutrain.model.User;
import com.example.brainutrain.repository.ReportRepository;
import com.example.brainutrain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class ReportService {

    private final ReportRepository reportRepository;
    private final PermissionChecker permissionChecker;
    private final UserRepository userRepository;


    public void createReport(ReportDto reportDto){
        User user = permissionChecker.checkPermission(reportDto.getIdUser());
        Report report = ReportMapper.INSTANCE.fromDto(reportDto);
        if(report.getEmail().equals("")){
            report.setEmail(user.getEmail());
        }
        report.setUser(user);
        reportRepository.save(report);
        log.info("New report created for user with idUser: "+user.getIdUser());
    }

    public ReportDto getUserReport(String username,Long idReport){
        Report report = reportRepository.findReportByUserLoginAndAndIdReport(username,idReport)
                .orElseThrow(()->new ResourceNotFoundException(
                        "Report not found for username"+username+" and idReport"+ idReport));
        return ReportMapper.INSTANCE.toDto(report);
    }

    public List<ReportDto> getAllUserReports(String username) {
        if (userRepository.existsByLogin(username)) {
            List<Report> reports = reportRepository.findAllByUserLogin(username);
            return ReportMapper.INSTANCE.toDto(reports);
        }
        throw new ResourceNotFoundException("No user with login:" + username);
    }
}