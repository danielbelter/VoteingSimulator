package com.example.services.implementations;

import com.example.repository.interfaces.CandidateRepository;
import com.example.repository.interfaces.VoterRepository;
import org.springframework.stereotype.Component;

@Component
public class ServiceHelpers {

    private CandidateRepository candidateRepository;
    private VoterRepository voterRepository;

    public ServiceHelpers(CandidateRepository candidateRepository, VoterRepository voterRepository) {
        this.candidateRepository = candidateRepository;
        this.voterRepository = voterRepository;
    }

    protected boolean doesConstituencyWithNameExists(String name) {
        return false;
    }

    protected boolean doesCandidateWithNameAndSurnameExists(String candidateName, String candidateSurname) {
        return candidateRepository
                .findAll()
                .stream()
                .filter(c -> c.getSurname().equals(candidateSurname) && c.getName().equals(candidateName))
                .findFirst().isPresent();
    }

    protected boolean doesVoterWithNameAndSurnameExists(String voterName, String voterSurname) {
        return voterRepository
                .findAll()
                .stream()
                .filter(v -> v.getName().equals(voterName) && v.getSurname().equals(voterSurname))
                .findFirst().isPresent();
    }

}
