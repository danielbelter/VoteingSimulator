package com.example.services.interfaces;

import com.example.model.dto.CandidateDTO;
import com.example.model.dto.ConstituencyDTO;
import com.example.model.dto.VoterDTO;

import java.util.List;

public interface MyService {
    List<ConstituencyDTO> getAllConstituencies();

    void editVoter(VoterDTO voterDTO);

    void addCandidate(CandidateDTO candidateDTO);

    CandidateDTO findByIdCandidate(Long id);

    List<CandidateDTO> getAllCandidates();

    void editCandidate(CandidateDTO candidateDTO);

    void addVoter(VoterDTO voterDTO);

    List<VoterDTO> getAllVoters();

    List<CandidateDTO> canditatesForVoter(Long id);

    VoterDTO getNthVoter(long n);

    CandidateDTO addVoteForCandidate(long id);

    List<CandidateDTO> electionResult();

    List<CandidateDTO> electionResultForConstituency(Long id);
}
