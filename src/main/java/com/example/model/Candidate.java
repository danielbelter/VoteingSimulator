package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "candidates")
public class Candidate {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String surname;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Integer votes;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "constituencyId")
    private Constituency constituency;
    private String filename;
}
