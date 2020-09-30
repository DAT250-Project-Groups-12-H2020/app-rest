package no.hvl.dat250.app.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * @author Elg
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
class NotUniqueAccountEmailException : RuntimeException("An account with the given email already exists")

@ResponseStatus(HttpStatus.NOT_FOUND)
class AccountNotFoundException(id: Long) : RuntimeException("Failed to find an account with the given id $id")
