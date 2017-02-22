package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Reclamation;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Reclamation entity.
 */
@SuppressWarnings("unused")
public interface ReclamationRepository extends MongoRepository<Reclamation,String> {

}
