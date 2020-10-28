package no.hvl.dat250.app.messages

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import no.hvl.dat250.app.dto.toResponse
import no.hvl.dat250.app.model.Poll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpClient.Version.HTTP_2
import java.net.http.HttpRequest
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.HttpResponse.BodyHandlers
import java.util.HashMap

@Component
class Dweet(@Autowired val mapper: ObjectMapper) {

  private fun sendDweet(map: Map<String, Any?>) {
    val request = HttpRequest
      .newBuilder()
      .uri(URI.create(POST_URL))
      .header("Content-Type", "application/json")
      .POST(BodyPublishers.ofString(mapper.writeValueAsString(map)))
      .build()

    HTTP_CLIENT.send(request, BodyHandlers.ofString())
  }

  private fun processPoll(poll: Poll): MutableMap<String, Any?> {
    return mapper.convertValue(poll.toResponse(), typeRef)
  }

  fun sendOpenedDweet(poll: Poll) {
    val map = processPoll(poll)
    map[EVENT_TYPE] = EVENT_OPENED
    map.remove("votes") // don't need votes when we're opening the poll
    sendDweet(map)
  }

  fun sendClosedDweet(poll: Poll) {
    val map = processPoll(poll)
    map[EVENT_TYPE] = EVENT_CLOSED
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
    sendDweet(map)
  }

  companion object {
    var typeRef: TypeReference<HashMap<String, Any?>> = object : TypeReference<HashMap<String, Any?>>() {}

    private val HTTP_CLIENT = HttpClient.newBuilder().version(HTTP_2).build()

    const val THING = "dat250-gr-2-h2020-app"
    const val POST_URL = "https://dweet.io/dweet/for/$THING"
    const val EVENT_TYPE = "event_type"
    const val EVENT_OPENED = "opened"
    const val EVENT_CLOSED = "closed"
  }
}
