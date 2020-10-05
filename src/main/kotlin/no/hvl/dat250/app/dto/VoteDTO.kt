package no.hvl.dat250.app.dto

import no.hvl.dat250.app.model.Vote
import java.time.OffsetDateTime
import javax.validation.constraints.Min

data class VoteRequest(
  @field:Min(0)
  val firstVotes: Int?,
  @field:Min(0)
  val secondVotes: Int?
)

data class VoteResponse(
  val id: Long,
  val firstVotes: Int,
  val secondVotes: Int,
  val castTime: OffsetDateTime,
)

fun VoteRequest.toVote(): Vote {
  val vote = Vote()
  vote.firstVotes = firstVotes!!
  vote.secondVotes = secondVotes!!
  vote.castTime = OffsetDateTime.now()
  return vote
}

fun Vote.toResponse(): VoteResponse {
  return VoteResponse(id, firstVotes, secondVotes, castTime)
}
