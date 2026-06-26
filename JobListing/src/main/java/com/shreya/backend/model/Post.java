package com.shreya.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Arrays;

@Document(collection="JobPost")
public class Post {
    @Field("id")
    private int JobId;
    private String profile;
    private String description;
    private int experience;
    private String techstack[];

    public Post() {
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public int getJobId() {
        return JobId;
    }

    public void setJobId(int jobId) {
        JobId = jobId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String[] getTechstack() {
        return techstack;
    }

    public void setTechstack(String[] techstack) {
        this.techstack = techstack;
    }

    @Override
    public String toString() {
        return "Post{" +
                "JobId=" + JobId +
                ", profile='" + profile + '\'' +
                ", description='" + description + '\'' +
                ", experience=" + experience +
                ", techstack=" + Arrays.toString(techstack) +
                '}';
    }
}
