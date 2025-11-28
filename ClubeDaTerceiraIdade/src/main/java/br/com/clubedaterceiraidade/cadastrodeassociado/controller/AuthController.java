package br.com.clubedaterceiraidade.cadastrodeassociado.controller;

import br.com.clubedaterceiraidade.cadastrodeassociado.model.User;
import br.com.clubedaterceiraidade.cadastrodeassociado.model.Role;
import br.com.clubedaterceiraidade.cadastrodeassociado.util.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private CustomUserDetailsService userService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/registar")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/registar")
    public String processRegistration(@ModelAttribute User user) {
        user.setRole(Role.USER); // Todos os novos utilizadores são do tipo USER
        userService.save(user);
        return "redirect:/login?success";
    }
}