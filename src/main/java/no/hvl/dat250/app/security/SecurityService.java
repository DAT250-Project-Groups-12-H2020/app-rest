package no.hvl.dat250.app.security;

import javax.servlet.http.HttpServletRequest;
import no.hvl.dat250.app.security.models.Credentials;
import no.hvl.dat250.app.security.models.FirebaseUser;
import no.hvl.dat250.app.security.models.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SecurityService {

  @Autowired
  private HttpServletRequest httpServletRequest;

  @Autowired
  private SecurityProperties securityProps;

  public FirebaseUser getUser() {
    FirebaseUser firebaseUserPrincipal = null;
    SecurityContext securityContext = SecurityContextHolder.getContext();
    Object principal = securityContext.getAuthentication().getPrincipal();
    if (principal instanceof FirebaseUser) {
      firebaseUserPrincipal = ((FirebaseUser) principal);
    }
    return firebaseUserPrincipal;
  }

  public Credentials getCredentials() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    return (Credentials) securityContext.getAuthentication().getCredentials();
  }

  public boolean isPublic() {
    return securityProps.getAllowedPublicApis().contains(httpServletRequest.getRequestURI());
  }

  public String getBearerToken(HttpServletRequest request) {
    String bearerToken = null;
    String authorization = request.getHeader("Authorization");
    if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
      bearerToken = authorization.substring(7, authorization.length());
    }
    return bearerToken;
  }

}
