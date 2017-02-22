package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Demandemedicament;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Demandemedicament entity.
 */
@SuppressWarnings("unused")
public interface DemandemedicamentRepository extends MongoRepository<Demandemedicament,String> {

}
