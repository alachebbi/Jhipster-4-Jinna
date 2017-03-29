package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Traitement;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Traitement entity.
 */
@SuppressWarnings("unused")
public interface TraitementRepository extends MongoRepository<Traitement,String> {

}
