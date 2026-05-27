package mx.unam.fesaragon.frontend.controller;

import jakarta.servlet.http.HttpSession;
import mx.unam.fesaragon.frontend.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/alumno")
public class AlumnoController {

    @Autowired private RestTemplate restTemplate;
    @Value("${backend.url}") private String backendUrl;

    private HttpHeaders headers(HttpSession session) {
        HttpHeaders h = new HttpHeaders();
        h.setBearerAuth((String) session.getAttribute("jwt"));
        h.setContentType(MediaType.APPLICATION_JSON);
        return h;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Long alumnoId = (Long) session.getAttribute("userId");
        HttpEntity<Void> entity = new HttpEntity<>(headers(session));

        AlumnoDto alumno = restTemplate.exchange(
                backendUrl + "/api/alumnos/" + alumnoId, HttpMethod.GET, entity,
                AlumnoDto.class).getBody();

        List<RegistroHorasDto> registros = restTemplate.exchange(
                backendUrl + "/api/horas/alumno/" + alumnoId, HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<RegistroHorasDto>>() {}).getBody();

        model.addAttribute("alumno", alumno);
        model.addAttribute("registros", registros);
        model.addAttribute("nombreCompleto", session.getAttribute("nombreCompleto"));

        boolean tieneEntradaAbierta = registros != null &&
                registros.stream().anyMatch(r -> "EN_CURSO".equals(r.getEstado()));
        model.addAttribute("tieneEntradaAbierta", tieneEntradaAbierta);
        return "dashboard-alumno";
    }

    @PostMapping("/horas/modalidad1")
    public String registrarModal1(@RequestParam String horaEntrada,
                                  @RequestParam String horaSalida,
                                  @RequestParam(required = false) String observaciones,
                                  HttpSession session) {
        Long alumnoId = (Long) session.getAttribute("userId");
        Map<String, Object> body = new HashMap<>();
        body.put("alumnoId", alumnoId);
        body.put("modalidad", "MODALIDAD_1");
        body.put("horaEntrada", horaEntrada);
        body.put("horaSalida", horaSalida);
        body.put("observaciones", observaciones);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers(session));
        restTemplate.exchange(backendUrl + "/api/horas/registro-completo", HttpMethod.POST, entity, RegistroHorasDto.class);
        return "redirect:/alumno/dashboard";
    }

    @PostMapping("/horas/entrada")
    public String registrarEntrada(@RequestParam String horaEntrada,
                                   @RequestParam(required = false) String observaciones,
                                   HttpSession session) {
        Long alumnoId = (Long) session.getAttribute("userId");
        Map<String, Object> body = new HashMap<>();
        body.put("alumnoId", alumnoId);
        body.put("modalidad", "MODALIDAD_2");
        body.put("horaEntrada", horaEntrada);
        body.put("observaciones", observaciones);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers(session));
        restTemplate.exchange(backendUrl + "/api/horas/entrada", HttpMethod.POST, entity, RegistroHorasDto.class);
        return "redirect:/alumno/dashboard";
    }

    @PostMapping("/horas/salida")
    public String registrarSalida(@RequestParam String horaSalida,
                                  @RequestParam(required = false) String observaciones,
                                  HttpSession session) {
        Long alumnoId = (Long) session.getAttribute("userId");
        Map<String, Object> body = new HashMap<>();
        body.put("alumnoId", alumnoId);
        body.put("horaSalida", horaSalida);
        body.put("observaciones", observaciones);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers(session));
        restTemplate.exchange(backendUrl + "/api/horas/salida", HttpMethod.POST, entity, RegistroHorasDto.class);
        return "redirect:/alumno/dashboard";
    }
}
