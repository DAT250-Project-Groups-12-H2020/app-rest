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
import no.hvl.dat250.app.security.models.FirebaseUser;
import no.hvl.dat250.app.security.models.SecurityProperties;
import no.hvl.dat250.app.utils.CookieUtils;
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
  @Autowired
  private SecurityService securityService;

  @Autowired
  private CookieUtils cookieUtils;

  @Autowired
  private SecurityProperties securityProps;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
  throws ServletException, IOException {
    verifyToken(request);
    filterChain.doFilter(request, response);
  }

  private void verifyToken(HttpServletRequest request) {
    String sessionCookieValue = null;
    FirebaseToken decodedToken = null;
    CredentialType type = null;
    boolean strictServerSessionEnabled = securityProps.getFirebaseProps().isEnableStrictServerSession();
    Cookie sessionCookie = cookieUtils.getCookie("session");
    String token = securityService.getBearerToken(request);
    try {
      if (sessionCookie != null) {
        sessionCookieValue = sessionCookie.getValue();
        decodedToken = FirebaseAuth.getInstance().verifySessionCookie(sessionCookieValue,
                                                                      securityProps.getFirebaseProps()
                                                                                   .isEnableCheckSessionRevoked());
        type = CredentialType.SESSION;
      }
      else if (!strictServerSessionEnabled) {
        if (token != null && !token.equalsIgnoreCase("undefined")) {
          decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
          type = CredentialType.ID_TOKEN;
        }
      }
    } catch (FirebaseAuthException e) {
      log.error("Firebase Exception:: %s", e.getLocalizedMessage());
      e.printStackTrace();
    }
    FirebaseUser firebaseUser = firebaseTokenToUserDto(decodedToken);
    if (firebaseUser != null) {
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(firebaseUser,
                                                                                                   new Credentials(type,
                                                                                                                   decodedToken,
                                                                                                                   token,
                                                                                                                   sessionCookieValue),
                                                                                                   null);
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
  }

  private FirebaseUser firebaseTokenToUserDto(FirebaseToken decodedToken) {
    FirebaseUser firebaseUser = null;
    if (decodedToken != null) {
      firebaseUser = new FirebaseUser();
      firebaseUser.setUid(decodedToken.getUid());
      firebaseUser.setName(decodedToken.getName());
      firebaseUser.setEmail(decodedToken.getEmail());
      firebaseUser.setPicture(decodedToken.getPicture());
      firebaseUser.setIssuer(decodedToken.getIssuer());
      firebaseUser.setEmailVerified(decodedToken.isEmailVerified());
    }
    return firebaseUser;
  }

}
