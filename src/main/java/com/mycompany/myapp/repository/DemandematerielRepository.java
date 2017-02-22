package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Demandemateriel;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Demandemateriel entity.
 */
@SuppressWarnings("unused")
public interface DemandematerielRepository extends MongoRepository<Demandemateriel,String> {

}
