package br.com.cadastrodeassociados.relatorio;

import br.com.cadastrodeassociados.associado.Associado;
import br.com.cadastrodeassociados.associado.AssociadoRepository;
import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/relatorios")
public class RelatorioControler {

    @Autowired
    private RelatorioService relatorioService;

    @Autowired
    private AssociadoRepository associadoRepository;

    @GetMapping
    public String mostrarFormularioDeFiltros(Model model) {
        return "relatorio/formulario";
    }

    @GetMapping("/associados/pdf")
    public void exportarPdfAssociados(
            @RequestParam(required = false) String bairro,
            @RequestParam(required = false) String escolaridade,
            HttpServletResponse response) throws IOException, DocumentException {

        List<Associado> associadosFiltrados = associadoRepository.findWithFilters(bairro, escolaridade);

        response.setContentType("application/pdf");
        String headerValue = "attachment; filename=relatorio_associados_" + getCurrentDateTime() + ".pdf";
        response.setHeader("Content-Disposition", headerValue);

        relatorioService.gerarRelatorioPdfAssociados(response, associadosFiltrados);
    }

    @GetMapping("/associados/excel")
    public void exportarExcelAssociados(
            @RequestParam(required = false) String bairro,
            @RequestParam(required = false) String escolaridade,
            HttpServletResponse response) throws IOException {

        List<Associado> associadosFiltrados = associadoRepository.findWithFilters(bairro, escolaridade);

        response.setContentType("application/octet-stream");
        String headerValue = "attachment; filename=relatorio_associados_" + getCurrentDateTime() + ".xlsx";
        response.setHeader("Content-Disposition", headerValue);

        relatorioService.gerarRelatorioExcelAssociados(response, associadosFiltrados);
    }

    private String getCurrentDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        return LocalDateTime.now().format(formatter);
    }
}
