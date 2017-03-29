package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Infirmier;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Infirmier entity.
 */
@SuppressWarnings("unused")
public interface InfirmierRepository extends MongoRepository<Infirmier,String> {

}
