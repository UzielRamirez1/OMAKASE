package mx.unam.fesaragon.frontend.controller;

import jakarta.servlet.http.HttpSession;
import mx.unam.fesaragon.frontend.dto.LoginRequest;
import mx.unam.fesaragon.frontend.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${backend.url}")
    private String backendUrl;

    @GetMapping("/")
    public String root(HttpSession session) {
        if (session.getAttribute("jwt") != null) {
            return "redirect:/dashboard";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String logout,
                            @RequestParam(required = false) String error,
                            HttpSession session, Model model) {
        if (session.getAttribute("jwt") != null) {
            return "redirect:/dashboard";
        }
        if (logout != null) model.addAttribute("logoutMsg", "Sesión cerrada correctamente.");
        if (error != null) model.addAttribute("errorMsg", "Credenciales incorrectas. Intente de nuevo.");
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {
        try {
            LoginRequest req = new LoginRequest();
            req.setUsername(username);
            req.setPassword(password);

            LoginResponse res = restTemplate.postForObject(
                    backendUrl + "/api/auth/login", req, LoginResponse.class);

            if (res == null) throw new RuntimeException("Respuesta vacía del backend");

            session.setAttribute("jwt", res.getToken());
            session.setAttribute("username", res.getUsername());
            session.setAttribute("rol", res.getRol());
            session.setAttribute("userId", res.getId());
            session.setAttribute("nombreCompleto", res.getNombreCompleto());

            return "redirect:/alumno/dashboard";
        } catch (HttpClientErrorException.Unauthorized e) {
            redirectAttributes.addFlashAttribute("errorMsg", "Credenciales incorrectas.");
            return "redirect:/login?error";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", "Error de conexión con el servidor.");
            return "redirect:/login?error";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        if (session.getAttribute("jwt") == null) return "redirect:/login";
        return "redirect:/alumno/dashboard";
    }
}
