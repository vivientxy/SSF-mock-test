package nus.iss.practicetest.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nus.iss.practicetest.model.Task;
import nus.iss.practicetest.repo.TaskRepo;

@Service
public class TaskService {

    @Autowired
    TaskRepo taskRepo;

    // CRUD
    public void createTask(Task task) {
        taskRepo.createTask(task.getId(), task.toString());
    }

    public Task retrieveTask(String taskId) {
        String taskStr = taskRepo.retrieveTask(taskId);
        return strToTask(taskStr);
    }

    public List<Task> retrieveTasks() {
        List<String> taskListStr = taskRepo.retrieveTasks();
        List<Task> taskList = new ArrayList<>();
        for (String taskStr : taskListStr) {
            Task task = strToTask(taskStr);
            taskList.add(task);
        }
        return taskList;
    }

    public List<Task> retrieveFilteredTasks(String status) {
        List<String> taskListStr = taskRepo.retrieveTasks();
        List<Task> taskList = new ArrayList<>();
        for (String taskStr : taskListStr) {
            Task task = strToTask(taskStr);
            if (task.getStatus().equals(status)) {
                taskList.add(task);
            }
        }
        return taskList;
    }

    public void updateTask(Task task) {
        taskRepo.updateTask(task.getId(), task.toString());
    }

    public void deleteTask(String taskId) {
        taskRepo.deleteTask(taskId);
    }

    public Task strToTask(String taskString) {
        String[] taskFields = taskString.split(",");
        Task task = new Task();
        task.setId(taskFields[0]);
        task.setName(taskFields[1]);
        task.setDescription(taskFields[2]);
        task.setPriority(taskFields[4]);
        task.setStatus(taskFields[5]);
        Date dDate = new Date(Long.parseLong(taskFields[3]));
        Date cDate = new Date(Long.parseLong(taskFields[6]));
        Date uDate = new Date(Long.parseLong(taskFields[7]));
        task.setDueDate(dDate);
        task.setCreatedAt(cDate);
        task.setUpdatedAt(uDate);
        return task;
    }

}
