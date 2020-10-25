package no.hvl.dat250.app.messages.receivers

import org.springframework.stereotype.Component

@Component
class PollReceiver {

  fun handleOpened(pollId: Long) {
    println("Poll opened: $pollId")
  }

  fun handleClosed(pollId: Long) {
    println("Poll closed: $pollId")
  }
}
