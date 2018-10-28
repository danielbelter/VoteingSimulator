package com.example.model.dto;

import com.example.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateDTO {
    private Long id;
    private String name;
    private String surname;
    private Gender gender;
    private Integer votes = 0;
    private ConstituencyDTO constituency;
    private MultipartFile multipartFile;
    private String filename;
}
