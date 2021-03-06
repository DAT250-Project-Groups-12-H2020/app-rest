package no.hvl.dat250.app.security;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.hvl.dat250.app.security.models.Credentials;
import no.hvl.dat250.app.security.models.Credentials.CredentialType;
import no.hvl.dat250.app.security.models.SecurityProperties;
import no.hvl.dat250.app.utils.CookieUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class SecurityFilter extends OncePerRequestFilter {

  private static final Logger log = org.slf4j.LoggerFactory.getLogger(SecurityFilter.class);
  @Autowired private SecurityService securityService;

  @Autowired private CookieUtils cookieUtils;

  @Autowired private SecurityProperties securityProps;

  @Override
  protected void doFilterInternal(
      @NotNull HttpServletRequest request,
      @NotNull HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {
    verifyToken(request);
    filterChain.doFilter(request, response);
  }

  private void verifyToken(HttpServletRequest request) {
    String sessionCookieValue = null;
    FirebaseToken decodedToken = null;
    CredentialType type = null;
    boolean strictServerSessionEnabled =
        securityProps.getFirebaseProps().isEnableStrictServerSession();
    Cookie sessionCookie = cookieUtils.getCookie("session");
    String token = securityService.getBearerToken(request);
    try {
      if (sessionCookie != null) {
        sessionCookieValue = sessionCookie.getValue();
        decodedToken =
            FirebaseAuth.getInstance()
                .verifySessionCookie(
                    sessionCookieValue,
                    securityProps.getFirebaseProps().isEnableCheckSessionRevoked());
        type = CredentialType.SESSION;
      } else if (!strictServerSessionEnabled
          && token != null
          && !token.equalsIgnoreCase("undefined")) {
        decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
        type = CredentialType.ID_TOKEN;
      }
    } catch (FirebaseAuthException e) {
      log.error("Firebase Exception:: {}", e.getLocalizedMessage());
      e.printStackTrace();
    }
    if (decodedToken != null && decodedToken.getUid() != null) {
      var credentials = new Credentials(type, decodedToken, token, sessionCookieValue);
      var authentication =
          new UsernamePasswordAuthenticationToken(decodedToken.getUid(), credentials, null);
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
  }
}
