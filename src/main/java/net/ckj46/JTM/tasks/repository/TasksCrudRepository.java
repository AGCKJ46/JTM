package net.ckj46.JTM.tasks.repository;

import net.ckj46.JTM.tasks.entity.Task;
import org.springframework.data.repository.CrudRepository;

interface TasksCrudRepository extends CrudRepository<Task, Long> {

}
