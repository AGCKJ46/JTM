package net.ckj46.JTM.tags.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.ckj46.JTM.app.entity.BaseEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Table(name = "tags")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
public class Tag extends BaseEntity {
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;

    public Tag(String name){
        this.name = name;
        this.createdAt = LocalDateTime.now();
        this.editedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", editedAt=" + editedAt +
                "} " + super.toString();
    }
}

