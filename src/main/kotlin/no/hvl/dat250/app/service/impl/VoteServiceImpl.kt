package no.hvl.dat250.app.service.impl

import no.hvl.dat250.app.dto.VoteRequest
import no.hvl.dat250.app.dto.VoteResponse
import no.hvl.dat250.app.dto.toResponse
import no.hvl.dat250.app.dto.toVote
import no.hvl.dat250.app.exception.PollClosedException
import no.hvl.dat250.app.exception.PollNotOpenedException
import no.hvl.dat250.app.repository.AccountRepository
import no.hvl.dat250.app.repository.PollRepository
import no.hvl.dat250.app.repository.VoteRepository
import no.hvl.dat250.app.service.AccountService
import no.hvl.dat250.app.service.PollService
import no.hvl.dat250.app.service.VoteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author Elg
 */
@Service
class VoteServiceImpl : VoteService {

  @Autowired
  private lateinit var voteRepository: VoteRepository

  @Autowired
  private lateinit var pollRepository: PollRepository

  @Autowired
  private lateinit var accountRepository: AccountRepository

  @Autowired
  private lateinit var accountService: AccountService

  @Autowired
  private lateinit var pollService: PollService

  override fun castVote(pollId: Long, request: VoteRequest): VoteResponse {
    val poll = pollService.getPoll(pollId)
    val startDate = poll.startDateTime
    val endDate = poll.endDateTime
    val unsavedVote = request.toVote()
    if (startDate == null || startDate >= unsavedVote.castTime) {
      throw PollNotOpenedException(startDate)
    } else if (endDate != null && endDate <= unsavedVote.castTime) {
      throw PollClosedException(endDate)
    }

    val vote = voteRepository.saveAndFlush(unsavedVote)
    poll.votes.add(vote)
    pollRepository.saveAndFlush(poll)
    return vote.toResponse()
  }
}
