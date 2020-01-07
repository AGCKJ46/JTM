package net.ckj46.JTM.tags.boundary;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateTagRequest {
    private String name;

    void CreateTagRequest(String name){
        this.name = name;
    }
}
