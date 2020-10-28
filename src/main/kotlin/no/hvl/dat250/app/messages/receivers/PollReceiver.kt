package no.hvl.dat250.app.messages.receivers

import no.hvl.dat250.app.messages.Dweet
import no.hvl.dat250.app.model.Poll
import no.hvl.dat250.app.repository.PollRepository
import org.hibernate.Hibernate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional
@Component
class PollReceiver(@Autowired val dweet: Dweet, @Autowired val pollRepository: PollRepository) {

  private fun processPoll(pollId: Long): Poll? {
    val poll = pollRepository.findByIdOrNull(pollId) ?: return null
    Hibernate.initialize(poll.votes)
    return poll
  }

  fun handleOpened(pollId: Long) {
    val poll = processPoll(pollId) ?: return
    dweet.sendOpenedDweet(poll)
    println("Poll opened: $pollId")
  }

  fun handleClosed(pollId: Long) {
    val poll = processPoll(pollId) ?: return
    dweet.sendClosedDweet(poll)
    println("Poll closed: $pollId")
  }
}
