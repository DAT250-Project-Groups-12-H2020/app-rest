package no.hvl.dat250.app.security

import no.hvl.dat250.app.security.models.Credentials
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import javax.servlet.http.HttpServletRequest

@Service
class SecurityService {

  /**
   * @return Unique id of the logged in user, or {@code null} if the requester is not logged in
   */
  fun getLoggedInUid(): String? {
    val securityContext = SecurityContextHolder.getContext()
    return securityContext.authentication.principal as? String
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
