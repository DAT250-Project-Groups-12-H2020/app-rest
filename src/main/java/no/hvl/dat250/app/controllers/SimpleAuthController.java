package no.hvl.dat250.app.controllers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import no.hvl.dat250.app.dto.AccountDTOKt;
import no.hvl.dat250.app.dto.AccountResponse;
import no.hvl.dat250.app.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleAuthController {

  @Autowired
  private SecurityService securityService;

  @GetMapping("/me")
  public AccountResponse getUser() {
    return AccountDTOKt.toResponse(securityService.getAccount());
  }

  @GetMapping("/create/token")
  public String getCustomToken() throws FirebaseAuthException {
    return FirebaseAuth.getInstance().createCustomToken(String.valueOf(securityService.getFirebaseUser().getUid()));
  }

}
