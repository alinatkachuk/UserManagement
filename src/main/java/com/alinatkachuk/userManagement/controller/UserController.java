package com.alinatkachuk.userManagement.controller;

import com.alinatkachuk.userManagement.entity.Role;
import com.alinatkachuk.userManagement.entity.User;
import com.alinatkachuk.userManagement.service.UserServiceDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;

@Controller
public class UserController {

    private final UserServiceDetailsImpl userServiceDetailsImpl;

    @Autowired
    public UserController(UserServiceDetailsImpl userServiceDetailsImpl) {
        this.userServiceDetailsImpl = userServiceDetailsImpl;
    }

    //login

    @GetMapping("/user")
    public String userList(Model model) {
        model.addAttribute("allUsers", userServiceDetailsImpl.allUsers());
        model.addAttribute("allUsersByOrderByUserNameAsc", userServiceDetailsImpl.allUsersByOrderByUserNameAsc());
        model.addAttribute("usersByRoleUser", userServiceDetailsImpl.
                usersByRole(Collections.singleton(new Role (1L, "ROLE_USER"))));
        model.addAttribute("usersByRoleAdmin", userServiceDetailsImpl.
                usersByRole(Collections.singleton(new Role (2L, "ROLE_ADMIN"))));
        return "list";
    }

    @GetMapping("/user/{id}")
    public String userView (@PathVariable("id") Long id, Model model) {
        User user = userServiceDetailsImpl.findUserById (id);
        model.addAttribute("user", user);
        return "view";
    }

    @GetMapping("/user/{id}/edit")
    public String userEditView (@PathVariable("id") Long id, Model model) {
        model.addAttribute("userForm", new User());
        return "view";
    }

    //?edit + validation
    @PostMapping("/user/{id}/edit")
    public String userEdit (@PathVariable("id") Long id,
                            @ModelAttribute("userForm") @Valid User userForm,
                            @ModelAttribute("role") String roleString,
                            BindingResult bindingResult,
                            Model model) {
        User user = userServiceDetailsImpl.findUserById (id);
        user.setUserName (userForm.getUserName ());
        user.setFirstName (userForm.getFirstName ());
        user.setLastName (userForm.getLastName ());
        userServiceDetailsImpl.saveUser (user);
        return "redirect:/";
    }

}
