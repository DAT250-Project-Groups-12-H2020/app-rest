package no.hvl.dat250.app.messages.receivers

import org.springframework.stereotype.Component

@Component
class PollReceiver {

  fun handleOpened(pollId: String) {
    println("Poll opened: $pollId")
  }

  fun handleClosed(pollId: String) {
    println("Poll closed: $pollId")
  }
}
