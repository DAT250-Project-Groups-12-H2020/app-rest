package no.hvl.dat250.app.messages

import com.fasterxml.jackson.databind.ObjectMapper
import no.hvl.dat250.app.dto.toResponse
import no.hvl.dat250.app.model.Poll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Component
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpClient.Version.HTTP_2
import java.net.http.HttpRequest
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.HttpResponse.BodyHandlers

@Component
class FirebaseDB(@Autowired val mapper: ObjectMapper) {

  private val HTTP_CLIENT = HttpClient.newBuilder().version(HTTP_2).build()

  /**
   * store a poll as json in the firebase 'realtime database'
   */
  fun storePoll(poll: Poll) {
    val map = processPoll(poll)
    val firstVotes = poll.votes.sumOf { it.firstVotes }
    val secondVotes = poll.votes.sumOf { it.secondVotes }
    val winner = when {
      firstVotes > secondVotes -> "first"
      firstVotes < secondVotes -> "second"
      else -> "tie"
    }
    map["winner"] = winner
    map["firstVotes"] = firstVotes
    map["secondVotes"] = secondVotes
    val id = poll.id
    val PUT_URL = "https://DAT250-Gr-2-H2020-APP.firebaseio.com/rest/closed-votes/$id/votes.json"
    val request = HttpRequest
      .newBuilder()
      .uri(URI.create(PUT_URL))
      .header("Content-Type", "application/json")
      .POST(BodyPublishers.ofString(mapper.writeValueAsString(map)))
      .build()
    HTTP_CLIENT.send(request, BodyHandlers.ofString())
  }
  private fun processPoll(poll: Poll): MutableMap<String, Any?> {
    return mapper.convertValue(poll.toResponse(), Dweet.typeRef)
  }
}
