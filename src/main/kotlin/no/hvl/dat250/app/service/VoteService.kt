package no.hvl.dat250.app.service

import no.hvl.dat250.app.dto.VoteRequest
import no.hvl.dat250.app.dto.VoteResponse
import no.hvl.dat250.app.exception.PollClosedException
import no.hvl.dat250.app.exception.PollNotOpenedException
import org.springframework.web.bind.annotation.ExceptionHandler

/**
 * @author Elg
 */
interface VoteService {

  @ExceptionHandler(PollNotOpenedException::class, PollClosedException::class)
  fun castVote(pollId: Long, request: VoteRequest): VoteResponse
}
