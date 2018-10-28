package com.example.repository.implementations;

import com.example.model.Candidate;
import com.example.repository.generic.AbstractGenericRepository;
import com.example.repository.interfaces.CandidateRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CandidateRepositoryImpl extends AbstractGenericRepository<Candidate> implements CandidateRepository {
}
