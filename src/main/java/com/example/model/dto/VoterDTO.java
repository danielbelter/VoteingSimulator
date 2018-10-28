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
public class VoterDTO {
    private Long id;
    private String name;
    private String surname;
    private Integer age;
    private Gender gender;
    private ConstituencyDTO constituency;
    private MultipartFile multipartFile;
    private String filename;
}
