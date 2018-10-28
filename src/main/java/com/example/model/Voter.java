package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name = "voters")
public class Voter {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String surname;
    private Integer age;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "constituencyId")
    private Constituency constituency;
    private String filename;

}
