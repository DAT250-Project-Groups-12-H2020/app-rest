package no.hvl.dat250.app.model

import java.time.OffsetDateTime
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Poll {

  @field:Id
  @field:GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = -1

  var startDateTime: OffsetDateTime? = null

  var endDateTime: OffsetDateTime? = null

  var private: Boolean = false

  lateinit var question: String
  lateinit var firstAnswer: String
  lateinit var secondAnswer: String

  @field:OneToMany(cascade = [CascadeType.ALL])
  var votes: MutableSet<Vote> = HashSet()
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Poll

    if (id != other.id) return false
    if (startDateTime != other.startDateTime) return false
    if (endDateTime != other.endDateTime) return false
    if (private != other.private) return false
    if (question != other.question) return false
    if (firstAnswer != other.firstAnswer) return false
    if (secondAnswer != other.secondAnswer) return false
    if (votes != other.votes) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id.hashCode()
    result = 31 * result + (startDateTime?.hashCode() ?: 0)
    result = 31 * result + (endDateTime?.hashCode() ?: 0)
    result = 31 * result + private.hashCode()
    result = 31 * result + question.hashCode()
    result = 31 * result + firstAnswer.hashCode()
    result = 31 * result + secondAnswer.hashCode()
    result = 31 * result + votes.hashCode()
    return result
  }
}
