package com.shreya.backend.repo;

import com.shreya.backend.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface postRepo extends MongoRepository<Post,String> {
}
