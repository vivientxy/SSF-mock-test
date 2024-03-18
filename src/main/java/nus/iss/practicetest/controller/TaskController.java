package nus.iss.practicetest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
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

    @GetMapping(path = { "/", "/login" })
    public ModelAndView showLogin(HttpSession sess) {
        ModelAndView mav = new ModelAndView("login");
        return mav;
    }

    @PostMapping(path = "/login")
    public ModelAndView processLogin(HttpSession sess, @RequestParam MultiValueMap<String,String> loginMap) {
        ModelAndView mav = new ModelAndView();
        if (loginMap.getFirst("fullname").isEmpty() || loginMap.getFirst("age").isEmpty()) {
            mav.setViewName("refused");
            System.out.println("return refused");
            return mav;
        }
        mav.setViewName("listing");
        System.out.println("return listing");
        return mav;
    }

    @GetMapping(path = "/list")
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
        mav.setViewName("redirect:/list");
        return mav;
    }

    // for updating tasks (beginning)
    @GetMapping(path = "/update/{id}")
    public ModelAndView showTaskToUpdate(HttpSession sess, @PathVariable("id") String id) {
        ModelAndView mav = new ModelAndView("update");
        Task task = taskService.retrieveTask(id);
        mav.addObject("task", task);
        return mav;
    }

    // for updating tasks (end)
    @PostMapping(path = "/update")
    public ModelAndView updateTask(HttpSession sess, @ModelAttribute @Valid Task task, BindingResult bindings) {
        ModelAndView mav = new ModelAndView();
        if (bindings.hasErrors()) {
            mav.addObject("task", task);
            return mav;
        }
        // if no errors, update UpdatedAt date
        task.setUpdatedAt(taskService.generateCurrentDate());
        taskService.updateTask(task);
        mav.setViewName("redirect:/list");
        return mav;
    }

        // for updating tasks (beginning)
        @GetMapping(path = "/delete/{id}")
        public ModelAndView deleteTask(HttpSession sess, @PathVariable("id") String id) {
            ModelAndView mav = new ModelAndView("redirect:/list");
            taskService.deleteTask(id);
            return mav;
        }


}
