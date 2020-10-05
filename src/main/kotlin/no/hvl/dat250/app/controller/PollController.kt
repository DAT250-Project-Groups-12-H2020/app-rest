package no.hvl.dat250.app.controller

import no.hvl.dat250.app.dto.PollCreateRequest
import no.hvl.dat250.app.dto.PollRequest
import no.hvl.dat250.app.dto.PollResponse
import no.hvl.dat250.app.dto.toResponse
import no.hvl.dat250.app.exception.PollNotOwnedByUserException
import no.hvl.dat250.app.service.AccountService
import no.hvl.dat250.app.service.PollService
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
@RequestMapping("api/v1/polls")
class PollController {

  @Autowired
  private lateinit var accountService: AccountService

  @Autowired
  private lateinit var pollService: PollService

  @PostMapping("/create")
  fun createPoll(@Valid @RequestBody pollRequest: PollCreateRequest): PollResponse {
    val poll = pollService.createPoll(pollRequest)
    accountService.addPoll(poll)
    return poll.toResponse()
  }

  @PutMapping("/{id}")
  fun updatePoll(@PathVariable id: Long, @Valid @RequestBody pollRequest: PollRequest): PollResponse {
    val poll = pollService.updatePoll(pollRequest, id)
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
    pollService.delete(poll)
    return poll.toResponse()
  }
}
