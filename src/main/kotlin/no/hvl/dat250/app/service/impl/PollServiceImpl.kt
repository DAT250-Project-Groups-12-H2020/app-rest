package no.hvl.dat250.app.service.impl

import no.hvl.dat250.app.dto.PollCreateRequest
import no.hvl.dat250.app.dto.toPoll
import no.hvl.dat250.app.exception.PollNotFoundException
import no.hvl.dat250.app.model.Poll
import no.hvl.dat250.app.repository.PollRepository
import no.hvl.dat250.app.service.PollService
import org.springframework.data.repository.findByIdOrNull

class PollServiceImpl : PollService {

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