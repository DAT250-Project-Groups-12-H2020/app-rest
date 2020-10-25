package no.hvl.dat250.app.schedule

import no.hvl.dat250.app.POLL_TOPIC_EXCHANGE_NAME
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.Calendar

@Component
class PollStatusScheduler(val rabbitTemplate: RabbitTemplate) {

  @Scheduled(initialDelay = 1000, fixedRate = 10000)
  fun run() {
    println("Current time is :: " + Calendar.getInstance().time)

    rabbitTemplate.convertAndSend(POLL_TOPIC_EXCHANGE_NAME, "app.poll.opened", "1")
    rabbitTemplate.convertAndSend(POLL_TOPIC_EXCHANGE_NAME, "app.poll.closed", "2")
  }
}
