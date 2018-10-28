package com.example.repository.implementations;

import com.example.model.Voter;
import com.example.repository.generic.AbstractGenericRepository;
import com.example.repository.interfaces.VoterRepository;
import org.springframework.stereotype.Repository;

@Repository
public class VoterRepositoryImpl extends AbstractGenericRepository<Voter> implements VoterRepository {

}
