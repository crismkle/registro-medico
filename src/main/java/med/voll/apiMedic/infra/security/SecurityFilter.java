package med.voll.apiMedic.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.rmi.RemoteException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Obtener el token del header
        var token = request.getHeader("Authorization");
        if (token == null || token == ""){
            throw new RemoteException("El token enviado no es válido");
        }
        token = token.replace("Bearer ", "");
        System.out.println(token);
        filterChain.doFilter(request, response);
    }
}
