package no.hvl.dat250.app.service.impl

import no.hvl.dat250.app.dto.PollCreateRequest
import no.hvl.dat250.app.dto.PollRequest
import no.hvl.dat250.app.dto.toPoll
import no.hvl.dat250.app.exception.NotLoggedInException
import no.hvl.dat250.app.exception.PollNotFoundException
import no.hvl.dat250.app.exception.PollNotOwnedByUserException
import no.hvl.dat250.app.exception.PollNotPublicException
import no.hvl.dat250.app.model.Poll
import no.hvl.dat250.app.repository.PollRepository
import no.hvl.dat250.app.service.AccountService
import no.hvl.dat250.app.service.PollService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service

class PollServiceImpl : PollService {
  @Autowired
  private lateinit var pollRepository: PollRepository
  @Autowired
  private lateinit var accountService: AccountService

  override fun createPoll(request: PollCreateRequest): Poll {
    val poll = request.toPoll()
    if (poll.private && accountService.isNotLoggedIn) {
      throw NotLoggedInException()
    }
    return pollRepository.saveAndFlush(poll)
  }

  override fun getPoll(id: Long): Poll {
    val poll = pollRepository.findByIdOrNull(id) ?: throw PollNotFoundException(id)
    if (poll.private && accountService.isNotLoggedIn) {
      throw PollNotPublicException(id)
    }
    return poll
  }

  override fun updatePoll(request: PollRequest, id: Long): Poll {
    var poll = pollRepository.findByIdOrNull(id) ?: throw PollNotFoundException(id)

    if (accountService.isNotOwnerOf(poll)) {
      throw PollNotOwnedByUserException(poll.id)
    }

    if (request.question?.isNotBlank() == true) {
      poll.question = request.question
    }
    if (request.firstAnswer?.isNotBlank() == true) {
      poll.firstAnswer = request.firstAnswer
    }
    if (request.secondAnswer?.isNotBlank() == true) {
      poll.secondAnswer = request.secondAnswer
    }
    if (request.startDateTime != null) {
      poll.startDate = request.startDateTime
    }
    if (request.endDateTime != null) {
      poll.endDate = request.endDateTime
    }
    if (request.private != null) {
      poll.private = request.private
    }
    poll = pollRepository.saveAndFlush(poll)
    return poll
  }

  override fun delete(poll: Poll): Poll {
    pollRepository.delete(poll)
    return poll
  }
}
