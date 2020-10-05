package no.hvl.dat250.app.security

import no.hvl.dat250.app.security.models.Credentials
import no.hvl.dat250.app.security.models.FirebaseUser
import no.hvl.dat250.app.security.models.SecurityProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import javax.servlet.http.HttpServletRequest

@Service
class SecurityService {

  @Autowired
  private lateinit var securityProps: SecurityProperties

  fun getCurrentFirebaseUser(): FirebaseUser? {
    val securityContext = SecurityContextHolder.getContext()
    val principal = securityContext.authentication.principal
    return if (principal is FirebaseUser) {
      principal
    } else null
  }

  fun getCredentials(): Credentials {
    val securityContext = SecurityContextHolder.getContext()
    return securityContext.authentication.credentials as Credentials
  }

  fun getBearerToken(request: HttpServletRequest): String? {
    var bearerToken: String? = null
    val authorization = request.getHeader("Authorization")
    if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
      bearerToken = authorization.substring(7)
    }
    return bearerToken
  }
}
