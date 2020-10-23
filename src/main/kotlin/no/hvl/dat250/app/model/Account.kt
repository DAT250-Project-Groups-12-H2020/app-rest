package no.hvl.dat250.app.model

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany

@Entity
class Account {

  @field:Id
  lateinit var id: String

  @field:OneToMany(cascade = [CascadeType.ALL])
  @field:JoinColumn
  var polls: MutableSet<Poll> = mutableSetOf()

  // ////////////
  // Firebase  //
  // ////////////

  @field:Enumerated(EnumType.STRING)
  var role: Role = Role.USER

  var name: String? = null

  var email: String? = null

  var isEmailVerified = false

  var photoUrl: String? = null

  var disabled: Boolean = false
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Account

    if (id != other.id) return false
    if (role != other.role) return false
    if (polls != other.polls) return false
    if (name != other.name) return false
    if (email != other.email) return false
    if (isEmailVerified != other.isEmailVerified) return false
    if (photoUrl != other.photoUrl) return false
    if (disabled != other.disabled) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id.hashCode()
    result = 31 * result + role.hashCode()
    result = 31 * result + polls.hashCode()
    result = 31 * result + (name?.hashCode() ?: 0)
    result = 31 * result + (email?.hashCode() ?: 0)
    result = 31 * result + isEmailVerified.hashCode()
    result = 31 * result + (photoUrl?.hashCode() ?: 0)
    result = 31 * result + disabled.hashCode()
    return result
  }
}
