package net.ckj46.JTM.tasks.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Data
@Table("tasks")
public class Task {
    @Id
    private Long id;

    private String title;
    private String description;
    private String project;
    // private String owner;
    // private String assignee;
    private int prio;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;

    public Task(String title, String description, String project, int prio, LocalDateTime createdAt, LocalDateTime editedAt) {
        this.title = title;
        this.description = description;
        this.project = project;
        this.prio = prio;
        this.createdAt = createdAt;
        this.editedAt = editedAt;
    }


    // private List attachmentsList; // TODO List => Set
    public List<Task> getAttachmentsList(){
        return new LinkedList<>();
    }

    public void setAttachmentsList(List<String> attachmentsList){
    }
}
