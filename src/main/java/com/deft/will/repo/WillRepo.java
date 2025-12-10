package com.deft.will.repo;

import com.deft.will.models.WillFormModel;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WillRepo extends ReactiveMongoRepository<WillFormModel, String> {
}
