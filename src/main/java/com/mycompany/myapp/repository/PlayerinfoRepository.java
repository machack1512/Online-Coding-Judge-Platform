package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Playerinfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Playerinfo entity.
 */
@Repository
public interface PlayerinfoRepository extends MongoRepository<Playerinfo, String> {}
