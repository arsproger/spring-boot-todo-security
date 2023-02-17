package com.arsen.controllers;

import com.arsen.models.Task;
import com.arsen.models.User;
import com.arsen.repositories.TaskRepository;
import com.arsen.security.DetailsUser;
import com.arsen.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
    private final TaskService taskService;
    private final TaskRepository taskRepository;
    
    @Autowired
    public UserController(TaskService taskService,
                          TaskRepository taskRepository) {
        this.taskService = taskService;
        this.taskRepository = taskRepository;
    }

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        DetailsUser detailsUser = (DetailsUser) authentication.getPrincipal();
        return detailsUser.getUser();
    }

    @GetMapping
    public String show(Model model) {
        User user = getUser();
        model.addAttribute("user", user);
        model.addAttribute("tasks", taskRepository.findByOwner(user));

        return "show";
    }

    @GetMapping("/task/new")
    public String newTask(@ModelAttribute("task") Task task) {
        return "task-new";
    }

    @PostMapping("task/new")
    public String saveTask(@ModelAttribute("task") @Valid Task task, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "task-new";

        taskService.newTask(getUser().getId(), task);

        return "redirect:/user";
    }

    @GetMapping("/task/update/{id}")
    public String updateTask(Model model, @PathVariable("id") Long id) {
        model.addAttribute("task", taskService.getById(id));

        return "task-edit";
    }

    @PatchMapping("/task/{id}")
    public String updateTask(@ModelAttribute("task") @Valid Task task,
                             BindingResult bindingResult, @PathVariable Long id) {
        if(bindingResult.hasErrors())
            return "task-edit";

        taskService.updateTaskById(id, task);
        return "redirect:/user";
    }

    @GetMapping("/task/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        taskService.deleteTaskById(id);

        return "redirect:/user";
    }
}
