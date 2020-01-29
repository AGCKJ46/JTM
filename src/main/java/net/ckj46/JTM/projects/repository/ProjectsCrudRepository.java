package net.ckj46.JTM.projects.repository;

import net.ckj46.JTM.projects.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectsCrudRepository extends JpaRepository<Project, Long>{

}
