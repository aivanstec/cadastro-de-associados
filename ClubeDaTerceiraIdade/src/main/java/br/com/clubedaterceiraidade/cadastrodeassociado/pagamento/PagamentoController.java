package br.com.clubedaterceiraidade.cadastrodeassociado.pagamento;

import br.com.clubedaterceiraidade.cadastrodeassociado.associado.Associado;
import br.com.clubedaterceiraidade.cadastrodeassociado.associado.AssociadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/pagamentos")
public class PagamentoController {
    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private AssociadoRepository associadoRepository;

    // UC10 - Consultar histórico de pagamentos de um associado
    @GetMapping("/associado/{associadoId}")
    public String listarPagamentosPorAssociado(@PathVariable Long associadoId, Model model, RedirectAttributes ra) {
        Associado associado = associadoRepository.findById(associadoId)
                .orElseThrow(() -> new RuntimeException("Associado não encontrado!"));

        List<Pagamento> pagamentos = pagamentoRepository.findByAssociadoIdOrderByDataPagamentoDesc(associadoId);

        model.addAttribute("associado", associado);
        model.addAttribute("pagamentos", pagamentos);
        model.addAttribute("novoPagamento", new Pagamento()); // Para o formulário

        return "pagamento/lista"; // Aponta para templates/pagamento/lista.html
    }

    // UC10 - Registrar um novo pagamento
    @PostMapping("/associado/{associadoId}/salvar")
    public String salvarPagamento(@PathVariable Long associadoId,
                                  @ModelAttribute("novoPagamento") Pagamento pagamento,
                                  RedirectAttributes ra) {
        Associado associado = associadoRepository.findById(associadoId)
                .orElseThrow(() -> new RuntimeException("Associado não encontrado!"));

        pagamento.setAssociado(associado);
        pagamentoRepository.save(pagamento);

        ra.addFlashAttribute("mensagem", "Pagamento registrado com sucesso!");
        return "redirect:/pagamentos/associado/" + associadoId;
    }
}