package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PlayerActivityLogs;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the PlayerActivityLogs entity.
 */
@Repository
public interface PlayerActivityLogsRepository extends MongoRepository<PlayerActivityLogs, String> {}
