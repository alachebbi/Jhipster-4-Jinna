package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Fichepatient;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Fichepatient entity.
 */
@SuppressWarnings("unused")
public interface FichepatientRepository extends MongoRepository<Fichepatient,String> {

}
