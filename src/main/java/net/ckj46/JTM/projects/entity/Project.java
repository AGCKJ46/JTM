package net.ckj46.JTM.projects.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.ckj46.JTM.app.entity.BaseEntity;
import net.ckj46.JTM.tasks.entity.Task;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Slf4j
@NoArgsConstructor
@Table(name = "projects")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
public class Project extends BaseEntity {
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<Task> tasks = new HashSet<>();

    public Project(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Project{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", editedAt=" + editedAt +
                // ", tasks=" + tasks +
                "} " + super.toString();
    }
}

