package no.hvl.dat250.app.controller

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.SessionCookieOptions
import no.hvl.dat250.app.API_VERSION_1
import no.hvl.dat250.app.exception.InvalidCredentialsException
import no.hvl.dat250.app.exception.NoBearerTokenException
import no.hvl.dat250.app.security.SecurityService
import no.hvl.dat250.app.security.models.Credentials.CredentialType.SESSION
import no.hvl.dat250.app.security.models.SecurityProperties
import no.hvl.dat250.app.utils.CookieUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.Boolean
import java.util.concurrent.TimeUnit.DAYS
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("$API_VERSION_1/session")
class SessionAuthController {

  @Autowired
  private lateinit var securityService: SecurityService

  @Autowired
  private lateinit var cookieUtils: CookieUtils

  @Autowired
  private lateinit var secProps: SecurityProperties

  @PostMapping("/login")
  @ExceptionHandler(NoBearerTokenException::class, FirebaseAuthException::class)
  fun sessionLogin(request: HttpServletRequest, response: HttpServletResponse) {
    val idToken = securityService.getBearerToken(request) ?: throw NoBearerTokenException()

    val sessionExpiryDays = secProps.firebaseProps.sessionExpiryInDays
    val expiresIn = DAYS.toMillis(sessionExpiryDays.toLong())
    val options = SessionCookieOptions.builder().setExpiresIn(expiresIn).build()

    try {
      val sessionCookieValue = FirebaseAuth.getInstance().createSessionCookie(idToken, options)
      cookieUtils.setSecureCookie(SESSION_PATH, sessionCookieValue, sessionExpiryDays)
      cookieUtils.setCookie(AUTHENTICATED_PATH, Boolean.toString(true), sessionExpiryDays)
    } catch (e: FirebaseAuthException) {
      throw InvalidCredentialsException(e.message)
    }
  }

  @PostMapping("/logout")
  fun sessionLogout() {
    cookieUtils.deleteSecureCookie(SESSION_PATH)
    cookieUtils.deleteCookie(AUTHENTICATED_PATH)
    if (securityService.getCredentials().type == SESSION && secProps.firebaseProps.isEnableLogoutEverywhere) {
      try {
        val firebaseUser = securityService.getCurrentFirebaseUser() ?: return
        FirebaseAuth.getInstance().revokeRefreshTokens(firebaseUser.uid)
      } catch (ignore: FirebaseAuthException) {
        // ignored
      }
    }
  }

  companion object {
    const val SESSION_PATH = "session"
    const val AUTHENTICATED_PATH = "authenticated"
  }
}
