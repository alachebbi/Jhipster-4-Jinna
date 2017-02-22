package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Materiel;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Materiel entity.
 */
@SuppressWarnings("unused")
public interface MaterielRepository extends MongoRepository<Materiel,String> {

}
