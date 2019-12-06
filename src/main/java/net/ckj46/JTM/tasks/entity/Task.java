package net.ckj46.JTM.tasks.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class Task {
    private long id;
    private String title;
    private String description;
    private String project;
    // private String owner;
    // private String assignee;
    private int prio;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;
    private List attachmentsList;
}
