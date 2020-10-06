package no.hvl.dat250.app.model

import java.time.OffsetDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * A vote can contain multiple sub-votes! As seen in the customer's design documentation.
 */
@Entity
class Vote {

  @field:Id
  @field:GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = -1

  var firstVotes: Int = 0
  var secondVotes: Int = 0

  /**
   * When the vote was cast
   */
  lateinit var castTime: OffsetDateTime
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Vote

    if (id != other.id) return false
    if (firstVotes != other.firstVotes) return false
    if (secondVotes != other.secondVotes) return false
    if (castTime != other.castTime) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id.hashCode()
    result = 31 * result + firstVotes
    result = 31 * result + secondVotes
    result = 31 * result + castTime.hashCode()
    return result
  }
}
