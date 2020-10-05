package no.hvl.dat250.app.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.time.OffsetDateTime

/**
 * @author Elg
 */

@ResponseStatus(HttpStatus.FORBIDDEN)
class PollNotOpenedException(startDateTime: OffsetDateTime?) :
  RuntimeException(if (startDateTime != null) "The poll will open at $startDateTime" else "The poll is not yet open")

@ResponseStatus(HttpStatus.FORBIDDEN)
class PollClosedException(endDateTime: OffsetDateTime) : RuntimeException("The closed at $endDateTime")
