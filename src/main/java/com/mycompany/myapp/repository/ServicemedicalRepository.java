package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Servicemedical;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Servicemedical entity.
 */
@SuppressWarnings("unused")
public interface ServicemedicalRepository extends MongoRepository<Servicemedical,String> {

}
