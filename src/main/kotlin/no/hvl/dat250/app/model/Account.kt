package no.hvl.dat250.app.model

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToMany
import javax.persistence.OneToMany


@Entity
class Account {

  @field:Id
  lateinit var id: String

  @field:Enumerated(EnumType.STRING)
  var role: Role = Role.USER

  @field:OneToMany(cascade = [CascadeType.ALL])
  @field:JoinColumn
  var polls: MutableSet<Poll> = mutableSetOf()

  @field:ManyToMany(cascade = [CascadeType.ALL])
  var votes: MutableMap<Poll, Vote> = mutableMapOf()

  //////////////
  // Firebase //
  //////////////

  var name: String? = null

  var email: String? = null

  var isEmailVerified = false

  var picture: String? = null

  override fun toString(): String {
    return "Account(id='$id', role=$role, name='$name', email='$email')"
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Account

    if (id != other.id) return false
    if (role != other.role) return false
    if (polls != other.polls) return false
    if (votes != other.votes) return false
    if (name != other.name) return false
    if (email != other.email) return false
    if (isEmailVerified != other.isEmailVerified) return false
    if (picture != other.picture) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id.hashCode()
    result = 31 * result + role.hashCode()
    result = 31 * result + polls.hashCode()
    result = 31 * result + votes.hashCode()
    result = 31 * result + (name?.hashCode() ?: 0)
    result = 31 * result + (email?.hashCode() ?: 0)
    result = 31 * result + isEmailVerified.hashCode()
    result = 31 * result + (picture?.hashCode() ?: 0)
    return result
  }
}
