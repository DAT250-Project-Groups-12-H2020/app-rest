package no.hvl.dat250.app.security;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import no.hvl.dat250.app.model.Account;
import no.hvl.dat250.app.repository.AccountRepository;
import no.hvl.dat250.app.security.models.Credentials;
import no.hvl.dat250.app.security.models.FirebaseUser;
import no.hvl.dat250.app.security.models.SecurityProperties;
import org.jetbrains.annotations.Nullable;
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

  @Autowired
  private AccountRepository accountRepository;


  @Nullable
  public FirebaseUser getFirebaseUser() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    Object principal = securityContext.getAuthentication().getPrincipal();
    if (principal instanceof FirebaseUser) {
      return ((FirebaseUser) principal);
    }
    return null;
  }

  @Nullable
  public Account getAccount() {
    FirebaseUser firebaseUser = getFirebaseUser();
    if (firebaseUser == null) {
      return null;
    }
    Account account;
    Optional<Account> accountOptional = accountRepository.findById(firebaseUser.getUid());
    if (accountOptional.isEmpty()) {
      //first time we have this account
      account = new Account();
      account.id = firebaseUser.getUid();
    }
    else {
      account = accountOptional.get();
    }
    account.setName(firebaseUser.getName());
    account.setEmail(firebaseUser.getEmail());
    account.setEmailVerified(firebaseUser.isEmailVerified());
    account.setPicture(firebaseUser.getPicture());
    return accountRepository.saveAndFlush(account);
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
      bearerToken = authorization.substring(7);
    }
    return bearerToken;
  }

}
