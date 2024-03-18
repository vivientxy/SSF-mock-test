package nus.iss.practicetest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import nus.iss.practicetest.model.Task;
import nus.iss.practicetest.service.TaskService;

@Controller
@RequestMapping
public class TaskController {
    
    @Autowired
    TaskService taskService;

    @GetMapping(path = {"/", "/list"})
    public ModelAndView showTaskList(HttpSession sess) {
        ModelAndView mav = new ModelAndView("listing");
        List<Task> taskList = taskService.retrieveTasks();
        mav.addObject("tasks", taskList);
        return mav;
    }

    @PostMapping
    public ModelAndView showFilteredTaskList(HttpSession sess, @RequestParam String filter) {
        ModelAndView mav = new ModelAndView("listing");
        List<Task> taskList = taskService.retrieveFilteredTasks(filter);
        mav.addObject("tasks", taskList);
        return mav;
    }
}
