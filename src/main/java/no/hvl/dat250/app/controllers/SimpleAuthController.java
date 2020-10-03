package no.hvl.dat250.app.controllers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import no.hvl.dat250.app.security.SecurityService;
import no.hvl.dat250.app.security.models.FirebaseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleAuthController {

  @Autowired
  private SecurityService securityService;

  @GetMapping("/me")
  public FirebaseUser getUser() {
    return securityService.getUser();
  }

  @GetMapping("/create/token")
  public String getCustomToken() throws FirebaseAuthException {
    return FirebaseAuth.getInstance().createCustomToken(String.valueOf(securityService.getUser().getUid()));
  }

}
