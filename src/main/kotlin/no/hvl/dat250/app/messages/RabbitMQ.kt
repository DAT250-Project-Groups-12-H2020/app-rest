package no.hvl.dat250.app.messages

import no.hvl.dat250.app.QUEUE_NAME
import no.hvl.dat250.app.messages.receivers.PollReceiver
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class RabbitMQ {

  @Bean
  fun containerOpened(
    connectionFactory: ConnectionFactory,
    listenerOpenedAdapter: MessageListenerAdapter
  ): SimpleMessageListenerContainer {
    val container = SimpleMessageListenerContainer()
    container.connectionFactory = connectionFactory
    container.setQueueNames(QUEUE_NAME)
    container.setMessageListener(listenerOpenedAdapter)
    return container
  }

  @Bean
  fun containerClosed(
    connectionFactory: ConnectionFactory,
    listenerClosedAdapter: MessageListenerAdapter
  ): SimpleMessageListenerContainer {
    val container = SimpleMessageListenerContainer()
    container.connectionFactory = connectionFactory
    container.setQueueNames(QUEUE_NAME)
    container.setMessageListener(listenerClosedAdapter)
    return container
  }

  @Bean
  fun listenerOpenedAdapter(receiver: PollReceiver): MessageListenerAdapter {
    return MessageListenerAdapter(receiver, "handleOpened")
  }

  @Bean
  fun listenerClosedAdapter(receiver: PollReceiver): MessageListenerAdapter {
    return MessageListenerAdapter(receiver, "handleClosed")
  }
}
