package no.hvl.dat250.app.service

import no.hvl.dat250.app.dto.PollCreateRequest
import no.hvl.dat250.app.dto.PollRequest
import no.hvl.dat250.app.exception.NotLoggedInException
import no.hvl.dat250.app.exception.PollNotFoundException
import no.hvl.dat250.app.exception.PollNotOwnedByUserException
import no.hvl.dat250.app.exception.PollNotPublicException
import no.hvl.dat250.app.model.Poll
import org.springframework.web.bind.annotation.ExceptionHandler

interface PollService {

  @ExceptionHandler(PollNotOwnedByUserException::class, PollNotFoundException::class)
  fun getOwnedPoll(id: Long): Poll

  /**
   * create a poll
   */
  @ExceptionHandler(NotLoggedInException::class)
  fun createPoll(request: PollCreateRequest): Poll

  /**
   * get a poll with a id
   */
  @ExceptionHandler(PollNotPublicException::class, PollNotFoundException::class)
  fun getPoll(id: Long): Poll

  /**
   * save and flush changes to a poll
   */

  fun updatePoll(id: Long, request: PollRequest): Poll

  /**
   * deletes a poll
   */
  fun delete(id: Long)
}
