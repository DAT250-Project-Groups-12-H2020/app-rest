package no.hvl.dat250.app.controller

import no.hvl.dat250.app.API_VERSION_1
import no.hvl.dat250.app.dto.PollCreateRequest
import no.hvl.dat250.app.dto.PollRequest
import no.hvl.dat250.app.dto.PollResponse
import no.hvl.dat250.app.dto.VoteRequest
import no.hvl.dat250.app.dto.VoteResponse
import no.hvl.dat250.app.dto.toResponse
import no.hvl.dat250.app.exception.NotLoggedInException
import no.hvl.dat250.app.exception.PollNotOwnedByUserException
import no.hvl.dat250.app.service.AccountService
import no.hvl.dat250.app.service.PollService
import no.hvl.dat250.app.service.VoteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("$API_VERSION_1/polls")
class PollController {

  @Autowired
  private lateinit var accountService: AccountService

  @Autowired
  private lateinit var pollService: PollService

  @Autowired
  private lateinit var voteService: VoteService

  @PostMapping("/create")
  fun createPoll(@Valid @RequestBody pollRequest: PollCreateRequest): PollResponse {
    if (accountService.isNotLoggedIn) {
      throw NotLoggedInException()
    }
    val poll = pollService.createPoll(pollRequest)
    accountService.addPoll(poll)
    return poll.toResponse()
  }

  @PostMapping("/{id}/vote")
  fun vote(@PathVariable id: Long, @Valid @RequestBody voteRequest: VoteRequest): VoteResponse {
    return voteService.castVote(id, voteRequest)
  }

  @PutMapping("/{id}")
  fun updatePoll(@PathVariable id: Long, @Valid @RequestBody pollRequest: PollRequest): PollResponse {
    val poll = pollService.updatePoll(id, pollRequest)
    return poll.toResponse()
  }

  @GetMapping
  fun getPoll(@RequestParam("id") id: Long): PollResponse {
    val poll = pollService.getPoll(id)
    return poll.toResponse()
  }

  @DeleteMapping("/{id}")
  fun deletePoll(@PathVariable id: Long): PollResponse {
    val poll = pollService.getPoll(id)
    if (accountService.isNotOwnerOf(poll)) {
      throw PollNotOwnedByUserException(id)
    }
    accountService.removePoll(poll)
    pollService.delete(id)
    return poll.toResponse()
  }
}
