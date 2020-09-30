package no.hvl.dat250.app.repository

import no.hvl.dat250.app.model.Poll
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PollRepository : JpaRepository<Poll, Long?> {

//    fun findAllByVotes(id: Long): List<Account>

}
