package no.hvl.dat250.app.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * @author Elg
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
class PollNotFoundException(id: Long) : RuntimeException("Failed to find a poll with the given id $id")

@ResponseStatus(HttpStatus.FORBIDDEN)
class PollNotPublicException(id: Long) : RuntimeException("The poll $id is not public")
