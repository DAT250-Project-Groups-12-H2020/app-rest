package no.hvl.dat250.app.repository

import no.hvl.dat250.app.model.Poll
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
interface PollRepository : JpaRepository<Poll, Long?> {

  fun findAllByEndDateTimeBetween(startEndDateTime: OffsetDateTime, endEndDateTime: OffsetDateTime): List<Poll>

  fun findAllByStartDateTimeBetween(startStartDateTime: OffsetDateTime, endStartDateTime: OffsetDateTime): List<Poll>
}
