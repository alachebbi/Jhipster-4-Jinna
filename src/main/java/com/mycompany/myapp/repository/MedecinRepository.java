package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Medecin;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Medecin entity.
 */
@SuppressWarnings("unused")
public interface MedecinRepository extends MongoRepository<Medecin,String> {

}
