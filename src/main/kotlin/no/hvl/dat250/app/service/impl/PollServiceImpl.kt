package no.hvl.dat250.app.service.impl

import no.hvl.dat250.app.dto.PollCreateRequest
import no.hvl.dat250.app.dto.PollRequest
import no.hvl.dat250.app.dto.PollResponse
import no.hvl.dat250.app.dto.toPoll
import no.hvl.dat250.app.dto.toResponse
import no.hvl.dat250.app.exception.InvalidPoll
import no.hvl.dat250.app.exception.PollNotFoundException
import no.hvl.dat250.app.exception.PollNotOwnedByUserException
import no.hvl.dat250.app.exception.PollNotPublicException
import no.hvl.dat250.app.model.Poll
import no.hvl.dat250.app.repository.PollRepository
import no.hvl.dat250.app.service.AccountService
import no.hvl.dat250.app.service.PollService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service

class PollServiceImpl : PollService {

  @Autowired
  private lateinit var pollRepository: PollRepository

  @Autowired
  private lateinit var accountService: AccountService

  private fun validatePollTime(poll: Poll) {
    // Disallow ending or opening polls too far back, but do give some slack
    val lastValidTime = OffsetDateTime.now().minusMinutes(5)

    val start = poll.startDateTime
    val end = poll.endDateTime
    if (start == null && end != null) {
      throw InvalidPoll("A poll cannot have an end date time without a start date time")
    }
    if (start != null && end != null && end.isBefore(start)) {
      throw InvalidPoll("A poll cannot end before it starts")
    }
    if (start?.isBefore(lastValidTime) == true) {
      throw InvalidPoll("A polls start date time cannot be set to the past")
    }
    if (end?.isBefore(lastValidTime) == true) {
      throw InvalidPoll("A polls end date time cannot be set to the past")
    }
  }

  override fun getOwnedPoll(id: Long): Poll {
    val poll = pollRepository.findByIdOrNull(id) ?: throw PollNotFoundException(id)
    if (accountService.isNotOwnerOf(poll)) {
      throw PollNotOwnedByUserException(poll.id)
    }
    return poll
  }

  override fun createPoll(request: PollCreateRequest): Poll {
    val poll = request.toPoll()
    validatePollTime(poll)
    return pollRepository.saveAndFlush(poll)
  }

  override fun getPoll(id: Long): Poll {
    val poll = pollRepository.findByIdOrNull(id) ?: throw PollNotFoundException(id)
    if (poll.private && accountService.isNotLoggedIn) {
      throw PollNotPublicException(id)
    }
    return poll
  }

  override fun updatePoll(id: Long, request: PollRequest): Poll {
    val poll = getOwnedPoll(id)

    if (request.startDateTime != null) {
      poll.startDateTime = request.startDateTime
    }
    if (request.endDateTime != null) {
      poll.endDateTime = request.endDateTime
    }
    validatePollTime(poll)

    if (request.question?.isNotBlank() == true) {
      poll.question = request.question
    }
    if (request.firstAnswer?.isNotBlank() == true) {
      poll.firstAnswer = request.firstAnswer
    }
    if (request.secondAnswer?.isNotBlank() == true) {
      poll.secondAnswer = request.secondAnswer
    }
    if (request.private != null) {
      poll.private = request.private
    }
    return pollRepository.saveAndFlush(poll)
  }

  override fun delete(id: Long) {
    pollRepository.delete(getOwnedPoll(id))
  }
}
