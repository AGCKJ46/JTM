package net.ckj46.JTM.projects.repository;

import net.ckj46.JTM.projects.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectsCrudRepository extends JpaRepository<Project, Long>{
    Optional<Project> findByTitle(String title);
}
