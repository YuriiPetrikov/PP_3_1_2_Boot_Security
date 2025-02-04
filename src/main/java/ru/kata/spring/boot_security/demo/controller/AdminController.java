package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import javax.validation.Valid;

@Controller
public class AdminController {

    private final UserServiceImp userService;

    public AdminController(UserServiceImp userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String getUsers(Model model) {
        model.addAttribute("usersList", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/admin/addNewUser")
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        return "/admin/addNewUser";
    }

    @PostMapping("/admin/addNewUser")
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/admin/addNewUser";
        }

        if (!user.getPassword().equals(user.getPasswordConfirm())) {
           return "/admin/addNewUser";
        }

        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/edit")
    public String editUser(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "/admin/userEditor";
    }

    @PostMapping("/admin/edit")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }
}
