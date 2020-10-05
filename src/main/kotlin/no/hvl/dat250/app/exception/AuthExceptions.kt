package no.hvl.dat250.app.exception

import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(BAD_REQUEST)
class NoBearerTokenException : RuntimeException("No bearer token passed to server")

@ResponseStatus(FORBIDDEN)
class InvalidCredentialsException(message: String? = null) : RuntimeException(message)
