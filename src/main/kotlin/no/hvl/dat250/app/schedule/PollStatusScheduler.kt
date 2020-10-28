package no.hvl.dat250.app.schedule

import no.hvl.dat250.app.POLL_TOPIC_EXCHANGE_NAME
import no.hvl.dat250.app.repository.PollRepository
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class PollStatusScheduler(
  @Autowired val rabbitTemplate: RabbitTemplate,
  @Autowired val pollRepository: PollRepository
) {

  private var lastRan = OffsetDateTime.now()

  @Scheduled(initialDelay = 1000, fixedRate = 10_000L)
  fun run() {

    val now = OffsetDateTime.now()

    val opened = pollRepository.findAllByStartDateTimeBetween(lastRan, now)
    val closed = pollRepository.findAllByEndDateTimeBetween(lastRan, now)
    lastRan = now

    for (poll in opened) {
      rabbitTemplate.convertAndSend(POLL_TOPIC_EXCHANGE_NAME, "app.poll.opened", poll.id)
    }

    for (poll in closed) {
      rabbitTemplate.convertAndSend(POLL_TOPIC_EXCHANGE_NAME, "app.poll.closed", poll.id)
    }
  }
}
