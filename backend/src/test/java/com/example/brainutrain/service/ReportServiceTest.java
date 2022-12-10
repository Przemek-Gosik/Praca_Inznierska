package com.example.brainutrain.service;

import com.example.brainutrain.constants.RoleName;
import com.example.brainutrain.dto.ReportDto;
import com.example.brainutrain.exception.ResourceNotFoundException;
import com.example.brainutrain.model.Report;
import com.example.brainutrain.model.Role;
import com.example.brainutrain.model.User;
import com.example.brainutrain.repository.ReportRepository;
import com.example.brainutrain.repository.UserRepository;
import com.example.brainutrain.utils.AuthenticationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private AuthenticationUtils utils;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReportService reportService;

    private User user;

    private Report report1;
    private Role roleUser;
    private Role roleAdmin;
    private Set<Role> roles = new HashSet<>();

    private ReportDto reportDto1;

    @BeforeEach
    public void init(){
        roleUser = new Role(1L, RoleName.USER);
        roleAdmin = new Role(2L,RoleName.ADMIN);
        roles.add(roleUser);
        user = new User(1L,"login","email@com.pl","pass",false,true,roles);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        LocalDateTime dateTime = LocalDateTime.now();
        report1 = new Report(1L,"Nie działa","Nic w tej aplikacji nie dziala",user.getEmail(),timestamp,true,user);
        reportDto1 = new ReportDto(1L,"Nie działa","Nic w tej aplikacji nie dziala","",dateTime);
    }

    @Test
    public void createReport_GivenValidData_ExistsInRepository(){
        when(utils.getUserFromAuthentication()).thenReturn(user);
        when(reportRepository.save(argThat(report ->
        report.getIdReport() == 1L))).thenReturn(report1);
        reportService.createReport(reportDto1);

        verify(reportRepository,times(1)).save(argThat(report ->
                report.getIdReport() == 1L));
    }

    @Test
    public void getUserReport_GivenValidId_ThenGetReportDto(){
        Long idReport=1L;
        when(utils.getUserFromAuthentication()).thenReturn(user);
        when(reportRepository.findByIdReportAndActiveTrue(idReport)).thenReturn(Optional.of(report1));
        ReportDto reportDto = reportService.getUserReport(idReport);
        assertEquals(idReport,reportDto.getIdReport());
    }

    @Test
    public void getUserReport_GivenInvalidId_ResourceNotFoundExceptionThrown(){
        Long idReport=1L;
        when(utils.getUserFromAuthentication()).thenReturn(user);
        when(reportRepository.findByIdReportAndActiveTrue(idReport)).thenReturn(Optional.ofNullable(null));
        assertThrows(ResourceNotFoundException.class,()->reportService.getUserReport(idReport));
    }

    @Test
    public void getUserReport_GivenIdForNotUsersReport_AccessDeniedExceptionThrown(){
        Long idReport=1L;
        User user1 = new User(2L,"login2","email2@com.pl","pass",false,true,roles);
        when(utils.getUserFromAuthentication()).thenReturn(user1);
        when(reportRepository.findByIdReportAndActiveTrue(idReport)).thenReturn(Optional.ofNullable(report1));
        assertThrows(AccessDeniedException.class,()->reportService.getUserReport(idReport));
    }

    @Test
    public void getAllUserReports_GivenValidUsername_GetListOfReports(){
        Report report2 = new Report(2L,"Nie działa","Nic w tej aplikacji nie dziala",user.getEmail(),new Timestamp(System.currentTimeMillis()),true,user);
        List<Report> reports = List.of(report1,report2);
        String username = user.getLogin();
        when(userRepository.existsByLogin(username)).thenReturn(true);
        when(reportRepository.findAllByUserLoginAndActiveTrue(username)).thenReturn(reports);
        List<ReportDto> reportDtoList = reportService.getAllUserReports(username);
        assertAll(
                ()->assertEquals(report1.getIdReport(),reportDtoList.get(0).getIdReport()),
                ()->assertEquals(2,reportDtoList.size())
        );
    }









}
