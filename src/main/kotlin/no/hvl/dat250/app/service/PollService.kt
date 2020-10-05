package no.hvl.dat250.app.service

import no.hvl.dat250.app.dto.PollCreateRequest
import no.hvl.dat250.app.dto.PollRequest
import no.hvl.dat250.app.exception.NotLoggedInException
import no.hvl.dat250.app.exception.PollNotFoundException
import no.hvl.dat250.app.exception.PollNotPublicException
import no.hvl.dat250.app.model.Poll
import org.springframework.web.bind.annotation.ExceptionHandler

interface PollService {
  @ExceptionHandler(
    PollNotFoundException :: class,
    PollNotFoundException::class, PollNotPublicException::class, NotLoggedInException::class
  )

  /**
   * create a poll
   */
  fun createPoll(request: PollCreateRequest): Poll

  /**
   * get a poll with a id
   * @throws PollNotFoundException
   */

  fun getPoll(id: Long): Poll

  /**
   * save and flush changes to a poll
   */
  fun updatePoll(request: PollRequest, id: Long): Poll

  /**
   * deletes a poll
   */
  fun delete(poll: Poll): Poll
}
