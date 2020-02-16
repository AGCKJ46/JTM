package net.ckj46.JTM.tasks.control;

import net.ckj46.JTM.app.sys.Clock;
import net.ckj46.JTM.app.sys.SystemClock;
import net.ckj46.JTM.attachments.repository.StorageService;
import net.ckj46.JTM.projects.entity.Project;
import net.ckj46.JTM.tags.repository.TagsRepository;
import net.ckj46.JTM.tasks.entity.Task;
import net.ckj46.JTM.tasks.repository.TasksRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

// @SpringBootTest
class TasksServiceTest {
    private TasksService tasksService;

    private TasksRepository tasksRepository = Mockito.mock(TasksRepository.class);
    private StorageService storageService = Mockito.mock(StorageService.class);
    private TagsRepository tagsRepository = Mockito.mock(TagsRepository.class);

    private Clock clock = new SystemClock();

    @BeforeEach
    public void setupBeforeAll() {
        tasksService = new TasksService(
                tasksRepository,
                storageService,
                tagsRepository,
                clock
        );
    }

    @Test
    public void shouldReturnTasksWithPrio(){
        // given
        Project project = new Project("Test project 1", "Description for test project 1");
        Task t1 = new Task("Test task 1", "", 1, LocalDateTime.now(), LocalDateTime.now(), project);
        Task t2 = new Task("Test task 2", "", 2, LocalDateTime.now(), LocalDateTime.now(), project);
        Task t3 = new Task("Test task 3", "", 1, LocalDateTime.now(), LocalDateTime.now(), project);
        Task t4 = new Task("Test task 4", "", 2, LocalDateTime.now(), LocalDateTime.now(), project);
        Task t5 = new Task("Test task 5", "", 1, LocalDateTime.now(), LocalDateTime.now(), project);

        Mockito.when(tasksRepository.fetchAll())
                .thenReturn(Arrays.asList(t1,t3,t5));

        //when
        List<Task> tasks = tasksService.getTaskWithPrio(1);

        //then
        Assertions.assertThat(tasks)
                .containsExactly(t1,t3,t5);
    }

    @Test
    public void shouldReturnTaskWithId(){
        // given
        Project project = new Project("Test project 1", "Description for test project 1");
        Task t1 = new Task("Test task 1", "", 1, LocalDateTime.now(), LocalDateTime.now(), project);
        Task t2 = new Task("Test task 2", "", 2, LocalDateTime.now(), LocalDateTime.now(), project);
        Task t3 = new Task("Test task 3", "", 1, LocalDateTime.now(), LocalDateTime.now(), project);
        Task t4 = new Task("Test task 4", "", 2, LocalDateTime.now(), LocalDateTime.now(), project);
        Task t5 = new Task("Test task 5", "", 1, LocalDateTime.now(), LocalDateTime.now(), project);

        Mockito.when(tasksRepository.fetchById(1L))
                .thenReturn(t1);

        //when
        Task task = tasksService.fetchTaskById(1L);

        //then
        Assertions.assertThat(task.getTitle()).isEqualToIgnoringCase("Test task 1");
    }
}