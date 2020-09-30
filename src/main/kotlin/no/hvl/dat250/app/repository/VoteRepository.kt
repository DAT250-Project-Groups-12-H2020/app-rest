package no.hvl.dat250.app.repository

import no.hvl.dat250.app.model.Vote
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VoteRepository : JpaRepository<Vote, Long?>
