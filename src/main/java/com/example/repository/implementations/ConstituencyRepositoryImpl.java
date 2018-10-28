package com.example.repository.implementations;

import com.example.model.Constituency;
import com.example.repository.generic.AbstractGenericRepository;
import com.example.repository.interfaces.ConstituencyRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ConstituencyRepositoryImpl extends AbstractGenericRepository<Constituency> implements ConstituencyRepository {

}
