package com.example.brainutrain.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ReportDto {

    private Long idReport;

    @NotBlank
    @Size(max=45,message = "Title size should be max 45 letters")
    private String title;

    @NotBlank
    @Size(min = 0,message = "Text should not be empty!")
    private String text;

    @Size(max=45,message = "Email should have max 45 letters")
    @Email(message = "Should be email type given")
    private String email;

    @NotNull
    private LocalDateTime date;
}
