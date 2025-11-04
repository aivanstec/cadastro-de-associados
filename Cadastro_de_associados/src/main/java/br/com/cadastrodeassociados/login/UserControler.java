package br.com.cadastrodeassociados.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;


@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class UserControler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin/user/list";
    }

    @GetMapping("/novo")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/user/form";
    }

    @PostMapping("/salvar")
    public String saveOrUpdateUser(@ModelAttribute("user") User user, RedirectAttributes ra) {

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else if (user.getId() != null) {
            userRepository.findById(user.getId()).ifPresent(existingUser -> user.setPassword(existingUser.getPassword()));
        }

        userRepository.save(user);
        ra.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");
        return "redirect:/admin/users";
    }

    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            ra.addFlashAttribute("mensagem_erro", "Usuário não encontrado!");
            return "redirect:/admin/users";
        }
        model.addAttribute("user", userOpt.get());

        userOpt.get().setPassword(null);
        return "admin/user/form";
    }

    @PostMapping("/apagar/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes ra) {
        if (!userRepository.existsById(id)) {
            ra.addFlashAttribute("mensagem_erro", "Usuário não encontrado!");
            return "redirect:/admin/users";
        }
        userRepository.deleteById(id);
        ra.addFlashAttribute("mensagem", "Usuário apagado com sucesso!");
        return "redirect:/admin/users";
    }
}