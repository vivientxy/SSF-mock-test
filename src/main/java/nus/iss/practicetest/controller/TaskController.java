package nus.iss.practicetest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import nus.iss.practicetest.model.Task;
import nus.iss.practicetest.service.TaskService;

@Controller
@RequestMapping
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping(path = { "/", "/list" })
    public ModelAndView showTaskList(HttpSession sess) {
        ModelAndView mav = new ModelAndView("listing");
        List<Task> taskList = taskService.retrieveTasks();
        mav.addObject("tasks", taskList);
        return mav;
    }

    // for filtering
    @PostMapping
    public ModelAndView showFilteredTaskList(HttpSession sess, @RequestParam String filter) {
        ModelAndView mav = new ModelAndView("listing");
        List<Task> taskList = taskService.retrieveFilteredTasks(filter);
        mav.addObject("tasks", taskList);
        return mav;
    }

    // for adding new tasks (beginning)
    @GetMapping(path = "/add")
    public ModelAndView showAddTask(HttpSession sess) {
        ModelAndView mav = new ModelAndView("add");
        mav.addObject("task", new Task());
        return mav;
    }

    // for adding new tasks (end)
    @PostMapping(path = "/add")
    public ModelAndView addTask(HttpSession sess, @ModelAttribute @Valid Task task, BindingResult bindings) {
        ModelAndView mav = new ModelAndView();
        // check for errors. if yes, return task object
        if (bindings.hasErrors()) {
            mav.addObject("task", task);
            return mav;
        }

        // if no errors, add Id and Date stamps to the object
        task.setId(task.generateId());
        task.setCreatedAt(task.generateCurrentDate());
        task.setUpdatedAt(task.generateCurrentDate());
        // add object to database
        taskService.createTask(task);

        // redirect to listing page
        mav.setViewName("redirect:/list");
        return mav;
    }
}
