package br.com.clubedaterceiraidade.cadastrodeassociado.associado;

import br.com.clubedaterceiraidade.cadastrodeassociado.carteira.CarteirinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/associados")
public class AssociadoController {

    @Autowired
    private AssociadoRepository associadoRepository;

    @Autowired
    private CarteirinhaService carteirinhaService;

    @GetMapping
    public String listarAssociados(@RequestParam(required = false) String nome, Model model) {
        List<Associado> associados;
        if (nome != null && !nome.isBlank()) {
            associados = associadoRepository.findByNomeContainingIgnoreCase(nome);
        } else {
            associados = associadoRepository.findAll();
        }
        model.addAttribute("associados", associados);
        return "associado/lista";
    }

    @GetMapping("/novo")
    public String mostrarFormularioDeCadastro(Model model) {
        model.addAttribute("associado", new Associado());
        return "associado/formulario";
    }

    @PostMapping("/salvar")
    public String salvarAssociado(@ModelAttribute("associado") Associado associado, RedirectAttributes ra) {
        carteirinhaService.gerenciarCriacaoCarteirinha(associado);

        associadoRepository.save(associado);
        ra.addFlashAttribute("mensagem", "Associado salvo com sucesso!");
        return "redirect:/associados";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioDeEdicao(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {
        Optional<Associado> associadoOpt = associadoRepository.findById(id);
        if (associadoOpt.isEmpty()) {
            ra.addFlashAttribute("mensagem_erro", "Associado não encontrado!");
            return "redirect:/associados";
        }
        model.addAttribute("associado", associadoOpt.get());
        return "associado/formulario";
    }

    @PostMapping("/apagar/{id}")
    public String apagarAssociado(@PathVariable("id") Long id, RedirectAttributes ra) {
        if (!associadoRepository.existsById(id)) {
            ra.addFlashAttribute("mensagem_erro", "Associado não encontrado!");
            return "redirect:/associados";
        }
        try {
            associadoRepository.deleteById(id);
            ra.addFlashAttribute("mensagem", "Associado apagado com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("mensagem_erro", "Não foi possível apagar o associado. Verifique dependências.");
        }
        return "redirect:/associados";
    }

    @PostMapping("/renovar-carteirinha/{id}")
    public String renovarCarteirinha (
            @PathVariable Long id,
            @RequestParam LocalDate novaValidade,
            RedirectAttributes ra) {
        try {
            carteirinhaService.renovarCarteirinha(id, novaValidade);
            ra.addFlashAttribute("mensagem", "Carteirinha renovada com sucesso!");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("mensagem_erro", e.getMessage());
        }
        return "redirect:/associados/editar/" + id;
    }
}