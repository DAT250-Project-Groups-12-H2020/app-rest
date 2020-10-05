package no.hvl.dat250.app.dto

import no.hvl.dat250.app.model.Poll
import java.time.OffsetDateTime

data class PollRequest(
  val question: String? = null,
  val firstAnswer: String? = null,
  val secondAnswer: String? = null,
  val startDateTime: OffsetDateTime? = null,
  val endDateTime: OffsetDateTime? = null,
  val private: Boolean? = null
)

data class PollCreateRequest(
  val question: String,
  val firstAnswer: String,
  val secondAnswer: String,
  val startDateTime: OffsetDateTime? = null,
  val endDateTime: OffsetDateTime? = null,
  val private: Boolean? = null
)

data class PollResponse(
  val id: Long,
  val startDateTime: OffsetDateTime?,
  val endDateTime: OffsetDateTime?,
  val private: Boolean,
  val question: String,
  val firstAnswer: String,
  val secondAnswer: String,
  val votes: List<VoteResponse>,
)

fun PollCreateRequest.toPoll(id: Long = -1): Poll {
  val poll = Poll()
  poll.id = id
  poll.startDateTime = startDateTime
  poll.endDateTime = endDateTime
  poll.private = private ?: false
  poll.question = question
  poll.firstAnswer = firstAnswer
  poll.secondAnswer = secondAnswer
  return poll
}

fun Poll.toResponse(): PollResponse {
  return PollResponse(
    id,
    startDateTime,
    endDateTime,
    private,
    question,
    firstAnswer,
    secondAnswer,
    votes.map { it.toResponse() }
  )
}
