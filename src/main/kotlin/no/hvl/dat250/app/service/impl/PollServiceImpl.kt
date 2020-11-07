package no.hvl.dat250.app.service.impl

import no.hvl.dat250.app.dto.PollCreateRequest
import no.hvl.dat250.app.dto.PollRequest
import no.hvl.dat250.app.dto.toPoll
import no.hvl.dat250.app.exception.InvalidPollException
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

  private fun validatePollTime(currStart: OffsetDateTime?, currEnd: OffsetDateTime?, newStart: OffsetDateTime?, newEnd: OffsetDateTime?) {
    // Disallow ending or opening polls too far back, but do give some slack
    val lastValidTime = OffsetDateTime.now().minusMinutes(5)

    if (currStart != null && newStart != null) {
      throw InvalidPollException("Cannot change the start date time once entered")
    } else if (currEnd != null && newEnd != null) {
      throw InvalidPollException("Cannot change the end date time once entered")
    }

    if (currStart == null && newStart != null && newStart.isBefore(lastValidTime)) {
      throw InvalidPollException("A polls start date time cannot be set to the past")
    }
    if (currEnd == null && newEnd != null) {
      if (newEnd.isBefore(lastValidTime)) {
        throw InvalidPollException("A polls end date time cannot be set to the past")
      }
      val start = newStart ?: currStart ?: throw InvalidPollException("Cannot set an end date without a start date")
      if (newEnd.isBefore(start)) {
        throw InvalidPollException("A poll cannot end before it starts")
      }
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
    validatePollTime(null, null, poll.startDateTime, poll.endDateTime)
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
    validatePollTime(poll.startDateTime, poll.endDateTime, request.startDateTime, request.endDateTime)

    if (request.startDateTime != null) {
      poll.startDateTime = request.startDateTime
    }
    if (request.endDateTime != null) {
      poll.endDateTime = request.endDateTime
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
    if (request.private != null) {
      poll.private = request.private
    }
    return pollRepository.saveAndFlush(poll)
  }

  override fun delete(id: Long) {
    pollRepository.delete(getOwnedPoll(id))
  }

  override fun getActivePublicPolls(page: Pageable): Page<Poll> {
    val now = OffsetDateTime.now()
    return pollRepository.findAllByPrivateFalseAndStartDateTimeBeforeAndEndDateTimeAfterOrEndDateTimeNull(now, now, page)
  }

  override fun getAllPublicPolls(page: Pageable): Page<Poll> {
    return pollRepository.findAllByPrivateIsFalse(page)
  }

  override fun getActivePolls(page: Pageable): Page<Poll> {
    val now = OffsetDateTime.now()
    return pollRepository.findAllByStartDateTimeBeforeAndEndDateTimeAfterOrEndDateTimeNull(now, now, page)
  }

  override fun getAllPolls(page: Pageable): Page<Poll> {
    return pollRepository.findAll(page)
  }
}
