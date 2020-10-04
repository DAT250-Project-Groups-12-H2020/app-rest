package no.hvl.dat250.app.controller

import no.hvl.dat250.app.API_VERSION_1
import no.hvl.dat250.app.dto.PollRequest
import no.hvl.dat250.app.dto.PollResponse
import no.hvl.dat250.app.dto.toPoll
import no.hvl.dat250.app.dto.toResponse
import no.hvl.dat250.app.exception.NotLoggedInException
import no.hvl.dat250.app.exception.PollNotFoundException
import no.hvl.dat250.app.exception.PollNotPublicException
import no.hvl.dat250.app.model.Poll
import no.hvl.dat250.app.repository.AccountRepository
import no.hvl.dat250.app.repository.PollRepository
import no.hvl.dat250.app.security.SecurityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.Optional
import javax.validation.Valid

@RestController
@RequestMapping("$API_VERSION_1/polls")
class PollController {

  @Autowired
  lateinit var accountRepository: AccountRepository

  @Autowired
  lateinit var pollRepository: PollRepository

  @Autowired
  lateinit var securityService: SecurityService

  @ExceptionHandler(NotLoggedInException::class)
  @PostMapping("/create")
  fun createPoll(@Valid @RequestBody pollRequest: PollRequest): PollResponse {
    val account = securityService.account ?: throw NotLoggedInException("Create poll")
    val poll = pollRepository.saveAndFlush(pollRequest.toPoll())
    account.polls.add(poll)
    accountRepository.saveAndFlush(account)
    return poll.toResponse()
  }

  @PutMapping("/{id}")
  fun updatePoll(@PathVariable id: Long, @Valid @RequestBody pollRequest: PollRequest): PollResponse {
    val account = securityService.account ?: throw NotLoggedInException("Update poll")
    TODO()
  }

  @ExceptionHandler(PollNotFoundException::class, PollNotPublicException::class)
  @GetMapping
  fun getPoll(@RequestParam("id") id: Long): PollResponse {
    val optionalPoll: Optional<Poll> = pollRepository.findById(id)
    if (optionalPoll.isEmpty) {
      throw PollNotFoundException(id)
    }
    val poll = optionalPoll.get()
    if (poll.private && securityService.account == null) {
      throw PollNotPublicException(id)
    }
    return poll.toResponse()
  }

  @DeleteMapping("/{id}")
  fun deletePoll(@PathVariable id: Long): PollResponse {
    val account = securityService.account ?: throw NotLoggedInException("Delete poll")
    TODO()
  }
}
