package net.ckj46.JTM.projects.boundary;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.projects.entity.Project;
import net.ckj46.JTM.tasks.boundary.TaskResponse;
import net.ckj46.JTM.tasks.entity.Task;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Getter
public class ProjectResponse {
    private Long id;
    private String title;
    private String description;
    private Set<TaskResponse> tasks = new HashSet<>();
    private int tasksCount;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;


    private ProjectResponse(Long id, String title, String description, Set<TaskResponse> tasks, LocalDateTime createdAt, LocalDateTime editedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.tasks = tasks;
        this.tasksCount = tasks.size();
        this.createdAt = createdAt;
        this.editedAt = editedAt;
    }

    public static ProjectResponse from(Project project){
        log.info("Creating ProjectResponsefrom: {}", project.toString());
        return new ProjectResponse(
                project.getId(),
                project.getTitle(),
                project.getDescription(),
                project.getTasks()
                        .stream()
                        .map((task)->TaskResponse.from(task, task.getTags(), task.getAttachments()))
                        .collect(Collectors.toSet()),
                project.getCreatedAt(),
                project.getEditedAt());
    }
}
