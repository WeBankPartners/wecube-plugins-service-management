package com.webank.servicemanagement.interceptor;

import com.webank.servicemanagement.commons.AuthenticationContextHolder;
import com.webank.servicemanagement.commons.AuthenticationContextHolder.AuthenticatedUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Component
public class AuthenticationRequestContextInterceptor implements HandlerInterceptor {
    private static final String AUTHORIZATION = "Authorization";
    public static final String REQ_ATTR_KEY_CURRENT_USER = "REQ_ATTR_KEY_CURRENT_USER";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Principal userPrincipal = request.getUserPrincipal();
        if (userPrincipal != null) {
            AuthenticatedUser currentUser = new AuthenticatedUser(userPrincipal.getName());

            currentUser.withAuthorities(extractAuthorities(userPrincipal));
            currentUser.setToken(request.getHeader(AUTHORIZATION));
            AuthenticationContextHolder.setAuthenticatedUser(currentUser);

            request.setAttribute(REQ_ATTR_KEY_CURRENT_USER, currentUser);
        }
        // TODO - for test
//        if (userPrincipal == null) {
//            AuthenticatedUser currentUser = new AuthenticatedUser("umadmin");
//            currentUser.setToken(
//                    "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJIVFRQLU1PQ0siLCJpYXQiOjE1NzYwNTMxOTcsInR5cGUiOiJhY2Nlc3NUb2tlbiIsImNsaWVudFR5cGUiOiJTVUJfU1lTVEVNIiwiZXhwIjoxNjAxOTczMTk3LCJhdXRob3JpdHkiOiJbU1VCX1NZU1RFTV0ifQ.Wd5rhFX3G-dtqlqYzgnkzd9i8T0xJkkPQSAckzO3V3NWXMCw3B9JWe7JlMjbNtE7va8qce1qcrz6qaa4pB0t5A");
//            AuthenticationContextHolder.setAuthenticatedUser(currentUser);
//
//            request.setAttribute(REQ_ATTR_KEY_CURRENT_USER, currentUser);
//        }

        return true;
    }

    private String[] extractAuthorities(Principal userPrincipal) {
        String[] authorities = new String[0];
        if (userPrincipal instanceof UsernamePasswordAuthenticationToken) {
            authorities = ((UsernamePasswordAuthenticationToken) userPrincipal).getAuthorities().stream()
                    .map(GrantedAuthority::toString).toArray(String[]::new);
        }
        return authorities;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        AuthenticationContextHolder.clearCurrentUser();
        request.removeAttribute(REQ_ATTR_KEY_CURRENT_USER);
    }
}
