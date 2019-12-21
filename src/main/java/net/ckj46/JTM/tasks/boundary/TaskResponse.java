package net.ckj46.JTM.tasks.boundary;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
    private Set attachments;
}
