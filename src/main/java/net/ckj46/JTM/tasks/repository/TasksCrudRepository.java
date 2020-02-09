package net.ckj46.JTM.tasks.repository;

import net.ckj46.JTM.tasks.entity.Task;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

interface TasksCrudRepository extends JpaRepository<Task, Long> {

    @EntityGraph(value = "Task.details", type = EntityGraph.EntityGraphType.LOAD)
    List<Task> findAll();

    @EntityGraph(value = "Task.details", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Task> findById(Long id);

    List<TaskView> findAllBy();

    /*
        @Modifying
        @Query("UPDATE Task SET title = :title, description = :description WHERE id = :taskId")
        void updateTitleDescription(@Param("taskId") Long taskId, @Param("title") String title, @Param("description") String description);
    */
    @Query
    List<Task> findByTitle(String title);

    /*
        @Query("SELECT * FROM Tasks t WHERE upper(t.title) LIKE '%' || upper(:title) || '%'")
        List<Task> findByTitle(@Param("title") String title);
    */
}