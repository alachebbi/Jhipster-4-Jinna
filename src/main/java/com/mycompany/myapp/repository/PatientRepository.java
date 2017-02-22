package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Patient;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Patient entity.
 */
@SuppressWarnings("unused")
public interface PatientRepository extends MongoRepository<Patient,String> {

}
