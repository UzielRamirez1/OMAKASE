package mx.unam.fesaragon.frontend.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String path = request.getRequestURI();

        // Permitir recursos estáticos y login
        if (path.startsWith("/css") || path.startsWith("/js") ||
            path.startsWith("/images") || path.equals("/login") ||
            path.equals("/logout")) {
            return true;
        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("jwt") == null) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
