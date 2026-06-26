package com.shreya.backend.service;

import com.shreya.backend.model.Post;
import com.shreya.backend.repo.postRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    postRepo obj;
    public List<Post> getAllJobsService(){
        return obj.findAll();
    }

    public Post addJobsService(Post newPost){
        return obj.save(newPost);

    }

}
