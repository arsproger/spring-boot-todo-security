package com.arsen.controllers;

import com.arsen.models.Task;
import com.arsen.models.User;
import com.arsen.security.DetailsUser;
import com.arsen.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("user")
public class UserController {
    private final TaskService taskService;

    @Autowired
    public UserController(TaskService taskService) {
        this.taskService = taskService;
    }

    public DetailsUser getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        DetailsUser detailsUser = (DetailsUser) authentication.getPrincipal();
        return detailsUser;
    }

    @GetMapping
    public String show(Model model) {
        UserDetails userDetails = (UserDetails) new User(getUser().getUsername(), getUser().getPassword(), getUser().getAuthorities());
        model.addAttribute("user", getUser().getUser());
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

        taskService.newTask(getUser().getUser().getId(), task);

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
