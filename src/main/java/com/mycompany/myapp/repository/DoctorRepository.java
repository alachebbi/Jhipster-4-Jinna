package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Doctor;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Doctor entity.
 */
@SuppressWarnings("unused")
public interface DoctorRepository extends MongoRepository<Doctor,String> {

}
