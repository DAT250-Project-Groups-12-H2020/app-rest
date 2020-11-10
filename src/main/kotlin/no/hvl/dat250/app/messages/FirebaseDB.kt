package no.hvl.dat250.app.messages
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import no.hvl.dat250.app.dto.toResponse
import no.hvl.dat250.app.model.Poll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileInputStream
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpClient.Version.HTTP_2
import java.net.http.HttpRequest
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.HttpResponse.BodyHandlers
import java.util.Arrays

@Component
class FirebaseDB(@Autowired val mapper: ObjectMapper) {

  private val HTTP_CLIENT = HttpClient.newBuilder().version(HTTP_2).build()

  /**
   * store a poll as json in the firebase 'realtime database' using a PUT REST api request
   * called when the poll is finished/end date expires
   */
  fun storePoll(poll: Poll) {
    // Load the service account key JSON file
    var path = System.getenv("GOOGLE_APPLICATION_CREDENTIALS")
    val directory = File("./")
    var serviceAccount = FileInputStream(path)
    // Authenticate a Google credential with the service account
    val googleCred = GoogleCredential.fromStream(serviceAccount)
    // Add the required scopes to the Google credential
    val scoped = googleCred.createScoped(
      Arrays.asList(
        "https://www.googleapis.com/auth/firebase.database",
        "https://www.googleapis.com/auth/userinfo.email"
      )
    )
    // Use the Google credential to generate an access token
    scoped.refreshToken()
    val token = scoped.accessToken
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
    val PUT_URL = "https://DAT250-Gr-2-H2020-APP.firebaseio.com/rest/closed-polls/$id.json?access_token=$token"
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
