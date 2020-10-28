package no.hvl.dat250.app

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling

const val API_VERSION_1 = "/api/v1"
const val POLL_TOPIC_EXCHANGE_NAME = "poll"
const val QUEUE_NAME = "dat250.app"

@SpringBootApplication
@EnableScheduling
class Application {

  @Bean
  fun queue(): Queue {
    return Queue(QUEUE_NAME, false)
  }

  @Bean
  fun exchange(): TopicExchange {
    return TopicExchange(POLL_TOPIC_EXCHANGE_NAME)
  }

  @Bean
  fun binding(queue: Queue, exchange: TopicExchange): Binding {
    return BindingBuilder.bind(queue).to(exchange).with("app.poll.*")
  }
}

fun main(args: Array<String>) {
  runApplication<Application>(*args)
}
