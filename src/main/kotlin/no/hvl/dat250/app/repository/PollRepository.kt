package no.hvl.dat250.app.repository

import no.hvl.dat250.app.model.Poll
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
interface PollRepository : JpaRepository<Poll, Long?> {

  fun findAllByEndDateTimeBetween(startEndDateTime: OffsetDateTime, endEndDateTime: OffsetDateTime): List<Poll>

  fun findAllByStartDateTimeBetween(startStartDateTime: OffsetDateTime, endStartDateTime: OffsetDateTime): List<Poll>

  @Query(
    nativeQuery = true,
    value = "select * from poll p where p.private=false and p.start_date_time <= ? and (p.end_date_time > ? or p.end_date_time is null)"
  )
  fun findAllByPrivateFalseAndStartDateTimeBeforeAndEndDateTimeAfterOrEndDateTimeNull(
    startDateTime: OffsetDateTime,
    endDateTime: OffsetDateTime,
    pageable: Pageable
  ): Page<Poll>

  fun findAllByStartDateTimeBeforeAndEndDateTimeAfterOrEndDateTimeNull(
    startDateTime: OffsetDateTime,
    endDateTime: OffsetDateTime,
    pageable: Pageable
  ): Page<Poll>

  fun findAllByPrivateIsFalse(pageable: Pageable): Page<Poll>
}
