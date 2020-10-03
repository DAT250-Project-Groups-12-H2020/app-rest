package no.hvl.dat250.app.controllers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.SessionCookieOptions;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.hvl.dat250.app.security.SecurityService;
import no.hvl.dat250.app.security.models.Credentials;
import no.hvl.dat250.app.security.models.SecurityProperties;
import no.hvl.dat250.app.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionAuthController {

  @Autowired
  private SecurityService securityService;

  @Autowired
  private CookieUtils cookieUtils;

  @Autowired
  private SecurityProperties secProps;

  @PostMapping("/session/login")
  public void sessionLogin(HttpServletRequest request, HttpServletResponse response)
  throws IOException, FirebaseAuthException {
    String idToken = securityService.getBearerToken(request);
    if (idToken == null) {
      response.sendError(400, "No bearer token found");
      return;
    }

    int sessionExpiryDays = secProps.getFirebaseProps().getSessionExpiryInDays();
    long expiresIn = TimeUnit.DAYS.toMillis(sessionExpiryDays);
    SessionCookieOptions options = SessionCookieOptions.builder().setExpiresIn(expiresIn).build();
    try {
      String sessionCookieValue = FirebaseAuth.getInstance().createSessionCookie(idToken, options);
      cookieUtils.setSecureCookie("session", sessionCookieValue, sessionExpiryDays);
      cookieUtils.setCookie("authenticated", Boolean.toString(true), sessionExpiryDays);
    } catch (FirebaseAuthException e) {
      e.printStackTrace();
      response.sendError(403, e.getMessage());
    }
  }

  @PostMapping("/session/logout")
  public void sessionLogout() {
    if (securityService.getCredentials().getType() == Credentials.CredentialType.SESSION &&
        secProps.getFirebaseProps().isEnableLogoutEverywhere()) {
      try {
        FirebaseAuth.getInstance().revokeRefreshTokens(securityService.getUser().getUid());
      } catch (FirebaseAuthException e) {
        e.printStackTrace();
      }
    }
    cookieUtils.deleteSecureCookie("session");
    cookieUtils.deleteCookie("authenticated");

  }

}
