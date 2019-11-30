package net.ckj46.JTM.tasks.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Task {
    private long id;
    private String title;
    private String description;
    private String project;
    private int prio;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;
}
