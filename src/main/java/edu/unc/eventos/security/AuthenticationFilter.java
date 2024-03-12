package edu.unc.eventos.security;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthenticationFilter  { // extends OncePerRequestFilter
//    @Autowired
//    private AuthService authService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String authHeader = request.getHeader("Authorization");
//        if (authHeader != null && authService.validateToken(authHeader, AuthUtil.API_VERSION)) {
//            filterChain.doFilter(request, response);
//        } else {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido o faltante");
//        }
//    }
}