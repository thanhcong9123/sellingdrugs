package com.example.websellingdrugs.controllers;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;
import com.example.websellingdrugs.models.users;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class UserController {
    private com.example.websellingdrugs.data.services.impl.userService _userService;

    public UserController(com.example.websellingdrugs.data.services.impl.userService userService) {
        this._userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new users());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute users user) {
        _userService.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/home")
    public String showHomePage() {
        // Đăng nhập thành công, chuyển hướng người dùng đến trang /products/
        return "redirect:/products/";
    }
     @GetMapping("/api/check-login")
    public ResponseEntity<Map<String, Object>> checkLoginStatus(HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        String remoteUser = request.getRemoteUser();
        if (remoteUser != null) {
            response.put("isLoggedIn", true);
            response.put("username", remoteUser);
        } else {
            response.put("isLoggedIn", false);
        }
        return ResponseEntity.ok(response);
    }
}
