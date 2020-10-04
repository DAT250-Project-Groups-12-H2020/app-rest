package no.hvl.dat250.app.controller

// import no.hvl.dat250.app.API_VERSION_1
import no.hvl.dat250.app.dto.PollRequest
import no.hvl.dat250.app.dto.PollResponse
import no.hvl.dat250.app.dto.toPoll
import no.hvl.dat250.app.dto.toResponse
import no.hvl.dat250.app.exception.*
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
const val API_VERSION_1 = "/api/v1"

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
    if (pollRequest.question == null || pollRequest.question == "") {
      throw MissingFieldException("question")
    }
    if (pollRequest.firstAnswer == null || pollRequest.firstAnswer == "") {
      throw MissingFieldException("firstAnswer")
    }
    if (pollRequest.secondAnswer == null || pollRequest.secondAnswer == "") {
      throw MissingFieldException("secondAnswer")
    }
    val account = securityService.account ?: throw NotLoggedInException("Create poll")
    val poll = pollRepository.saveAndFlush(pollRequest.toPoll())
    account.polls.add(poll)
    accountRepository.saveAndFlush(account)
    return poll.toResponse()
  }

  @PutMapping("/{id}")
  fun updatePoll(@PathVariable id: Long, @Valid @RequestBody pollRequest: PollRequest): PollResponse {
    val account = securityService.account ?: throw NotLoggedInException("Update poll")
    val optionalPoll: Optional<Poll> = pollRepository.findById(id)
    if (optionalPoll.isEmpty) {
      throw PollNotFoundException(id)
    }
    val poll = optionalPoll.get()
    // check user owns poll
    if (!account.polls.contains(poll)) {
      throw PollNotOwnedByUserException(id, account.name)
    }
    if (pollRequest.question?.isNotBlank() == true) {
      poll.question = pollRequest.question
    }
    if (pollRequest.firstAnswer?.isNotBlank() == true) {
      poll.firstAnswer = pollRequest.firstAnswer
    }
    if (pollRequest.secondAnswer?.isNotBlank() == true) {
      poll.secondAnswer = pollRequest.secondAnswer
    }
    if (pollRequest.startDateTime != null) {
      poll.startDate = pollRequest.startDateTime
    }
    if (pollRequest.endDateTime != null) {
      poll.endDate = pollRequest.endDateTime
    }
    if (pollRequest.private != null) {
      poll.private = pollRequest.private
    }
    pollRepository.saveAndFlush(poll)
    return poll.toResponse()
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
    val optionalPoll: Optional<Poll> = pollRepository.findById(id)
    if (optionalPoll.isEmpty) {
      throw PollNotFoundException(id)
    }
    val poll = optionalPoll.get()
    // check user owns poll
    if (!account.polls.contains(poll)) {
      throw PollNotOwnedByUserException(id, account.name)
    }
    account.polls.remove(poll)
    accountRepository.saveAndFlush(account)
    pollRepository.delete(poll)
    return poll.toResponse()

  }
}
