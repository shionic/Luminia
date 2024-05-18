package luminia.backend.services;

import lombok.AllArgsConstructor;
import luminia.backend.models.TaskAssign;
import luminia.backend.models.TaskAssignRule;
import luminia.backend.repositories.TaskAssignRuleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TaskAssignRuleService {
    private TaskAssignRuleRepository repository;
    private TaskAssignService taskAssignService;
    private TaskService taskService;
    private UserService userService;

    public void autoAssignAll() {
        var list = repository.findAllMatchTime(LocalDateTime.now());
        List<TaskAssign> tas = new ArrayList<>();
        for(var e : list) {
            TaskAssign ta = new TaskAssign();
            ta.setTarget(userService.getReferenceById(e.getUserId()));
            ta.setTask(taskService.getReferenceById(e.getTaskId()));
            ta.setVariant(""); // TODO
            ta.setDeadlineDate(e.getEndDate());
            ta.setImportantDate(e.getEndDate().minusHours(16));
            tas.add(ta);
        }
        taskAssignService.saveAll(tas);
    }


    public TaskAssignRule getReferenceById(Long aLong) {
        return repository.getReferenceById(aLong);
    }

    public <S extends TaskAssignRule> S save(S entity) {
        return repository.save(entity);
    }

    public Optional<TaskAssignRule> findById(Long aLong) {
        return repository.findById(aLong);
    }
}
