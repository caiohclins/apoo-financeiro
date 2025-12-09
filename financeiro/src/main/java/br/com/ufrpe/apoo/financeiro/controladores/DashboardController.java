package br.com.ufrpe.apoo.financeiro.controladores;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ufrpe.apoo.financeiro.dto.DashboardDTO;
import br.com.ufrpe.apoo.financeiro.servico.DashboardService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public DashboardDTO obterDashboard(
            @RequestParam int mes,
            @RequestParam int ano,
            JwtAuthenticationToken token) {
        return dashboardService.gerarDashboard(token.getToken().getSubject(), mes, ano);
    }
}
