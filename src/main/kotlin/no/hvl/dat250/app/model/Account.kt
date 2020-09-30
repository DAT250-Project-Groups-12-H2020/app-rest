package no.hvl.dat250.app.model

import javax.persistence.*


@Entity
class Account {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = -1

    @field:Enumerated(EnumType.STRING)
    lateinit var role: Role

    @field:OneToMany(cascade = [CascadeType.ALL])
    @field:JoinColumn
    var polls: MutableSet<Poll> = mutableSetOf()

    lateinit var name: String

    @field:Column(unique = true)
    lateinit var email: String

    var password: String? = null

    @field:ManyToMany(cascade = [CascadeType.ALL])
    var votes: MutableMap<Poll, Vote> = mutableMapOf()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Account) return false

        if (id != other.id) return false
        if (role != other.role) return false
        if (polls != other.polls) return false
        if (name != other.name) return false
        if (email != other.email) return false
        if (password != other.password) return false
        if (votes != other.votes) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + role.hashCode()
        result = 31 * result + polls.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + (password?.hashCode() ?: 0)
        result = 31 * result + votes.hashCode()
        return result
    }

    override fun toString(): String {
        return "Account(id='$id', admin=$role, name='$name', email='$email')"
    }
}
