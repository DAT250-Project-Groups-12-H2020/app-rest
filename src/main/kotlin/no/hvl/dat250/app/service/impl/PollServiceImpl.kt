package no.hvl.dat250.app.service.impl

import no.hvl.dat250.app.exception.PollNotFoundException
import no.hvl.dat250.app.model.Poll
import no.hvl.dat250.app.repository.PollRepository
import no.hvl.dat250.app.service.PollService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service

class PollServiceImpl : PollService {
  @Autowired
  private lateinit var pollRepository: PollRepository

  override fun createPoll(poll: Poll): Poll {
    return pollRepository.saveAndFlush(poll)
  }

  override fun getPoll(id: Long): Poll {
    return pollRepository.findByIdOrNull(id) ?: throw PollNotFoundException(id)
  }

  override fun updatePoll(poll: Poll): Poll {
    pollRepository.saveAndFlush(poll)
    return poll
  }

  override fun delete(poll: Poll): Poll {
    pollRepository.delete(poll)
    return poll
  }
}
