package nus.iss.practicetest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    // for filtering (WIP)
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
        task.setId(taskService.generateId());
        task.setCreatedAt(taskService.generateCurrentDate());
        task.setUpdatedAt(taskService.generateCurrentDate());
        // add object to database
        taskService.createTask(task);
        // redirect to listing page
        mav.setViewName("redirect:/list");
        return mav;
    }

    // for updating tasks (beginning)
    @GetMapping(path = "/update/{id}")
    public ModelAndView showTaskToUpdate(HttpSession sess, @PathVariable("id") String id) {
        ModelAndView mav = new ModelAndView("update");
        Task task = taskService.retrieveTask(id);
        mav.addObject("task", task);
        System.out.println("RETRIEVED TASK: " + task.toString() + "\n" + task.getDueDate()+ "\n" + task.getCreatedAt()+ "\n" + task.getUpdatedAt());
        return mav;
    }

    // for updating tasks (end)
    @PostMapping(path = "/update")
    public ModelAndView updateTask(HttpSession sess, @ModelAttribute @Valid Task task, BindingResult bindings) {
        System.out.println("PASSED IN TASK: " + task.toString() + "\n" + task.getDueDate()+ "\n" + task.getCreatedAt()+ "\n" + task.getUpdatedAt());

        ModelAndView mav = new ModelAndView();
        // check for errors. if yes, return task object
        if (bindings.hasErrors()) {
            System.out.println("TASK TO BE UPDATED HAS ERRORS: " + task.toString());
            mav.addObject("task", task);
            return mav;
        }
        // if no errors, update UpdatedAt date
        System.out.println("TASK TO BE UPDATED: " + task.toString());
        task.setUpdatedAt(taskService.generateCurrentDate());
        System.out.println("TASK TO BE UPDATED AFTER CHANGING DATE: " + task.toString());
        taskService.updateTask(task);
        mav.setViewName("redirect:/list");
        return mav;
    }


}
