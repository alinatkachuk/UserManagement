package com.alinatkachuk.userManagement.controller;

import com.alinatkachuk.userManagement.entity.Role;
import com.alinatkachuk.userManagement.entity.User;
import com.alinatkachuk.userManagement.repository.RoleRepository;
import com.alinatkachuk.userManagement.service.UserServiceDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.*;

@Controller
public class RegistrationController {

    private final UserServiceDetailsImpl userServiceDetailsImpl;
    private final RoleRepository roleRepository;

    @Autowired
    public RegistrationController(UserServiceDetailsImpl userServiceDetailsImpl, RoleRepository roleRepository) {
        this.userServiceDetailsImpl = userServiceDetailsImpl;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/user/new")
    public String registration(Model model) {
        model.addAttribute("userForm", new User ());
        model.addAttribute("allRoles", roleRepository.findAll ());
        return "new";
    }

    @PostMapping("/user/new")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm,
                          @ModelAttribute("role") String roleString,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            return "new";
        }
        if (!userForm.getPassword().equals(userForm.getPassword())){
            model.addAttribute("passwordError", "Invalid password");
            return "new";
        }
        if (!userServiceDetailsImpl.saveUser(userForm)){
            model.addAttribute("userNameError", "A user with the same name already exists");
            return "new";
        }
        Iterator <Role> iterator = roleRepository.findAll ().iterator ();
        while (iterator.hasNext ()) {
            if (iterator.next ().getName().equals(roleString)) {
                userForm.setRoles(Collections.singleton(new Role (iterator.next ().getId (), roleString)));
            }
        }
        userForm.setStatus (true);
        Calendar calendar = Calendar.getInstance ();
        userForm.setCreatedAt (calendar.getTime ());
        userServiceDetailsImpl.saveUser (userForm);
        return "redirect:/";
    }
}
