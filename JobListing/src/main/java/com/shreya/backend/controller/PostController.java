package com.shreya.backend.controller;

import com.shreya.backend.model.Post;
import com.shreya.backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class PostController {
    @Autowired
    PostService ob;
    @GetMapping("/jobs")
    public List<Post> getAllJobs(){
        return ob.getAllJobsService();

    }

    @PostMapping("/addJobs")
    public Post addJobs(@RequestBody Post newPost){
        return ob.addJobsService(newPost);
    }

}
