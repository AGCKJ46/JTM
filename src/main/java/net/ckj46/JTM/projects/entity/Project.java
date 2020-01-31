package net.ckj46.JTM.projects.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.tasks.entity.Task;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Slf4j
@NoArgsConstructor
@Table(name = "projects")
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "projectId")
    private Set<Task> tasks = new HashSet<>();

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", editedAt=" + editedAt +
                ", tasks=" + tasks +
                '}';
    }
}
