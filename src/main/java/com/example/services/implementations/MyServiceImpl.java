package com.example.services.implementations;

import com.example.exceptions.MyException;
import com.example.model.Candidate;
import com.example.model.Constituency;
import com.example.model.Voter;
import com.example.model.dto.CandidateDTO;
import com.example.model.dto.ConstituencyDTO;
import com.example.model.dto.VoterDTO;
import com.example.model.dto.mappers.ModelMapper;
import com.example.repository.interfaces.CandidateRepository;
import com.example.repository.interfaces.ConstituencyRepository;
import com.example.repository.interfaces.VoterRepository;
import com.example.services.interfaces.MyService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MyServiceImpl implements MyService {

    private CandidateRepository candidateRepository;
    private VoterRepository voterRepository;
    private ConstituencyRepository constituencyRepository;
    private ModelMapper modelMapper;
    private ServiceHelpers serviceHelpers;

    public MyServiceImpl(CandidateRepository candidateRepository,
                         VoterRepository voterRepository,
                         ConstituencyRepository constituencyRepository,
                         ModelMapper modelMapper,
                         ServiceHelpers serviceHelpers) {
        this.candidateRepository = candidateRepository;
        this.voterRepository = voterRepository;
        this.constituencyRepository = constituencyRepository;
        this.modelMapper = modelMapper;
        this.serviceHelpers = serviceHelpers;
    }

    @Override
    public List<ConstituencyDTO> getAllConstituencies() {
        try {
            return constituencyRepository
                    .findAll()
                    .stream()
                    .map(modelMapper::fromConstituencyToConstituencyDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new MyException("SERVICE, GET ALL CONSTITUENCIES", LocalDateTime.now());
        }
    }

    @Override
    public void addCandidate(CandidateDTO candidateDTO) {
        try {
            if (serviceHelpers.doesCandidateWithNameAndSurnameExists(candidateDTO.getName(), candidateDTO.getSurname())) {
                throw new IllegalArgumentException("CANDIDATE WITH GIVEN NAME AND SURNAME ALREADY EXISTS");
            }

            if (candidateDTO.getConstituency() == null) {
                throw new IllegalArgumentException("YOU HAVE TO ENTER CANDIDATE CONSTITUENCY");
            }

            Constituency constituency = constituencyRepository
                    .findById(candidateDTO.getConstituency().getId())
                    .orElseThrow(NullPointerException::new);
            Candidate candidate = modelMapper.fromCandidateDTOToCandidate(candidateDTO);
            candidate.setConstituency(constituency);
            candidateRepository.addOrUpdate(candidate);
        } catch (Exception e) {
            throw new MyException("SERVICE, ADD CANDIDATE", LocalDateTime.now());
        }
    }

    @Override
    public List<CandidateDTO> getAllCandidates() {
        return candidateRepository
                .findAll()
                .stream()
                .map(modelMapper::fromCandidateTocandidateDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void editCandidate(CandidateDTO candidateDTO) {
        Candidate candidate = modelMapper.fromCandidateDTOToCandidate(candidateDTO);
        candidateRepository.addOrUpdate(candidate);
    }

    @Override
    public void editVoter(VoterDTO voterDTO) {
        Voter voter = modelMapper.fromVoterDTOToVoter(voterDTO);
        voterRepository.addOrUpdate(voter);
    }

    @Override
    public CandidateDTO findByIdCandidate(Long id) {
        return candidateRepository
                .findById(id)
                .map(modelMapper::fromCandidateTocandidateDTO)
                .orElseThrow(NullPointerException::new);
    }

    @Override
    public void addVoter(VoterDTO voterDTO) {
        try {
            if (serviceHelpers.doesVoterWithNameAndSurnameExists(voterDTO.getName(), voterDTO.getSurname())) {
                throw new IllegalArgumentException("VOTER WITH GIVEN NAME AND SURNAME ALREADY EXISTS");
            }

            if (voterDTO.getConstituency() == null) {
                throw new IllegalArgumentException("YOU HAVE TO ENTER CANDIDATE CONSTITUENCY");
            }

            Constituency constituency = constituencyRepository
                    .findById(voterDTO.getConstituency().getId())
                    .orElseThrow(NullPointerException::new);
            Voter voter = modelMapper.fromVoterDTOToVoter(voterDTO);
            voter.setConstituency(constituency);
            voterRepository.addOrUpdate(voter);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("SERVICE, ADD VOTER", LocalDateTime.now());
        }
    }

    @Override
    public List<VoterDTO> getAllVoters() {
        return voterRepository
                .findAll()
                .stream()
                .map(modelMapper::fromVoterToVoterDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CandidateDTO> canditatesForVoter(Long id) {
        Voter voter = voterRepository.findById(id).orElseThrow(() -> new NullPointerException());
        List candidateList = candidateRepository.findAll().stream().filter(candidate -> candidate.getConstituency().equals(voter.getConstituency())).map(modelMapper::fromCandidateTocandidateDTO).collect(Collectors.toList());
        return candidateList;

    }

    @Override
    public VoterDTO getNthVoter(long n) {
        try {
            return voterRepository.findAll()
                    .stream()
                    .skip(n - 1)
                    .limit(1)
                    .findFirst()
                    .map(modelMapper::fromVoterToVoterDTO)
                    .orElseThrow(NullPointerException::new);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("SERVICE, GET NTH VOTER", LocalDateTime.now());
        }
    }

    @Override
    public CandidateDTO addVoteForCandidate(long id) {
        try {

            Candidate candidate = candidateRepository.findById(id).orElseThrow(() -> new NullPointerException());
            candidate.setVotes(candidate.getVotes() + 1);
            CandidateDTO candidateDTO = modelMapper.fromCandidateTocandidateDTO(candidate);


            return candidateDTO;
        } catch (Exception e) {
            throw new MyException("SERVICE, INCREMENT VOTES", LocalDateTime.now());
        }
    }

    @Override
    public List<CandidateDTO> electionResult() {
        List resultList = candidateRepository.findAll().stream().limit(1).sorted(Comparator.comparing(Candidate::getVotes).reversed()).map(modelMapper::fromCandidateTocandidateDTO).collect(Collectors.toList());
        return resultList;
    }

    @Override
    public List<CandidateDTO> electionResultForConstituency(Long id) {
        Constituency constituency = constituencyRepository.findById(id).orElseThrow(() -> new NullPointerException());
        List resultList = candidateRepository.findAll().stream().filter(candidate -> candidate.getConstituency().getId().equals(id)).sorted(Comparator.comparing(Candidate::getVotes).reversed()).map(modelMapper::fromCandidateTocandidateDTO).collect(Collectors.toList());
        return resultList;
    }
}

