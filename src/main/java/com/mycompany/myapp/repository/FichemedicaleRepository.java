package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Fichemedicale;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Fichemedicale entity.
 */
@SuppressWarnings("unused")
public interface FichemedicaleRepository extends MongoRepository<Fichemedicale,String> {

}
