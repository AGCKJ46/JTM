package net.ckj46.JTM.tasks.repository;

import net.ckj46.JTM.tasks.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface TasksCrudRepository extends JpaRepository<Task, Long> {
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
