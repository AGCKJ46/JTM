package net.ckj46.JTM.tasks.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
class TaskResponse {
    long id;
    String title;
    String description;
    String project;
    int prio;
    LocalDateTime createdAt;
    LocalDateTime editedAt;
}
