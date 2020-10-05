package no.hvl.dat250.app.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * @author Elg
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
class NotUniqueAccountEmailException : RuntimeException("An account with the given email already exists")

@ResponseStatus(HttpStatus.NOT_FOUND)
class AccountNotFoundException(id: String) : RuntimeException("Failed to find an account with the given id $id")

@ResponseStatus(HttpStatus.FORBIDDEN)
class NotLoggedInException : RuntimeException("You must be logged to use this endpoint")

@ResponseStatus(HttpStatus.BAD_REQUEST)
class MissingFieldException(field: String) : RuntimeException("Blank or missing required field: $field ")

@ResponseStatus(HttpStatus.FORBIDDEN)
class InsufficientAccessException(to: String) : RuntimeException("You are not allowed to $to")

@ResponseStatus(HttpStatus.BAD_REQUEST)
class AccountUpdateFailedException(message: String?) : RuntimeException(message)

@ResponseStatus(HttpStatus.BAD_REQUEST)
class AccountCreationFailedException(message: String?) : RuntimeException(message)
