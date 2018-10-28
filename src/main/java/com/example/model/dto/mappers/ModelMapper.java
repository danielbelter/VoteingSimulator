package com.example.model.dto.mappers;

import com.example.model.Candidate;
import com.example.model.Constituency;
import com.example.model.Voter;
import com.example.model.dto.CandidateDTO;
import com.example.model.dto.ConstituencyDTO;
import com.example.model.dto.VoterDTO;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class ModelMapper {
    public CandidateDTO fromCandidateTocandidateDTO(Candidate candidate) {
        return candidate == null ? null :
                CandidateDTO.builder()
                        .id(candidate.getId())
                        .name(candidate.getName())
                        .surname(candidate.getSurname())
                        .gender(candidate.getGender())
                        .votes(candidate.getVotes())
                        .constituency(fromConstituencyToConstituencyDTO(candidate.getConstituency()))
                        .filename(candidate.getFilename())
                        .build();
    }

    public Candidate fromCandidateDTOToCandidate(CandidateDTO candidateDTO) {
        return candidateDTO == null ? null : Candidate.builder()
                .id(candidateDTO.getId())
                .name(candidateDTO.getName())
                .surname(candidateDTO.getSurname())
                .gender(candidateDTO.getGender())
                .votes(candidateDTO.getVotes())
                .constituency(fromConstituencyDTOToConstituency(candidateDTO.getConstituency()))
                .filename(candidateDTO.getFilename())
                .build();
    }

    public VoterDTO fromVoterToVoterDTO(Voter voter) {
        return voter == null ? null : VoterDTO.builder()
                .id(voter.getId())
                .name(voter.getName())
                .surname(voter.getSurname())
                .age(voter.getAge())
                .gender(voter.getGender())
                .constituency(fromConstituencyToConstituencyDTO(voter.getConstituency()))
                .filename(voter.getFilename())
                .build();

    }

    public Voter fromVoterDTOToVoter(VoterDTO voterDTO) {
        return voterDTO == null ? null : Voter.builder()
                .id(voterDTO.getId())
                .name(voterDTO.getName())
                .surname(voterDTO.getSurname())
                .age(voterDTO.getAge())
                .gender(voterDTO.getGender())
                .constituency(fromConstituencyDTOToConstituency(voterDTO.getConstituency()))
                .filename(voterDTO.getFilename())
                .build();
    }

    public ConstituencyDTO fromConstituencyToConstituencyDTO(Constituency constituency) {
        return constituency == null ? null :
                ConstituencyDTO.builder()
                        .id(constituency.getId())
                        .name(constituency.getName())
                        .build();
    }

    public Constituency fromConstituencyDTOToConstituency(ConstituencyDTO constituencyDTO) {
        return constituencyDTO == null ? null :
                Constituency.builder()
                        .id(constituencyDTO.getId())
                        .name(constituencyDTO.getName())
                        .candidates(new HashSet<>())
                        .voters(new HashSet<>())
                        .build();
    }
}
