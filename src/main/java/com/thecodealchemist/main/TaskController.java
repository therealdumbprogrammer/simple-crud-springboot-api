package com.thecodealchemist.main;

import com.thecodealchemist.main.model.TaskDetail;
import com.thecodealchemist.main.model.TaskPostRequest;
import com.thecodealchemist.main.model.TaskPostResponse;
import com.thecodealchemist.main.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public TaskPostResponse createTask(@RequestBody List<TaskPostRequest> taskPostRequest) {
        //create/insert tasks
        int insertedTasks = taskService.addAll(taskPostRequest);
        TaskPostResponse resp = new TaskPostResponse();
        resp.setInsertedRecords(insertedTasks);

        return resp;
    }

    @GetMapping
    public List<TaskDetail> tasks() {
        return taskService.fetchAll();
    }

    @GetMapping("/{user}")
    public List<TaskDetail> tasks(@PathVariable String user) {
        return taskService.fetchByCreatedBy(user);
    }

    @GetMapping("/subset")
    public List<TaskDetail> subset(@RequestParam(defaultValue = "title") String orderBy,
                                   @RequestParam(defaultValue = "asc") String direction,
                                   @RequestParam(defaultValue = "0") int page) {
        return taskService.subset(orderBy, direction, page);
    }
}
