package com.example.services.implementations;

import com.example.model.dto.mappers.ModelMapper;
import com.example.repository.interfaces.CandidateRepository;
import com.example.repository.interfaces.ConstituencyRepository;
import com.example.repository.interfaces.VoterRepository;
import com.example.services.interfaces.DataInitializerService;
import org.springframework.stereotype.Service;

@Service
public class DataInitializerServiceImpl implements DataInitializerService {

    private CandidateRepository candidateRepository;
    private VoterRepository voterRepository;
    private ConstituencyRepository constituencyRepository;
    private ModelMapper modelMapper;

    public DataInitializerServiceImpl(
            CandidateRepository candidateRepository,
            VoterRepository voterRepository,
            ConstituencyRepository constituencyRepository,
            ModelMapper modelMapper) {
        this.candidateRepository = candidateRepository;
        this.voterRepository = voterRepository;
        this.constituencyRepository = constituencyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initializeData() {
        deleteAll();


    }


    private void deleteAll() {

    }
}
