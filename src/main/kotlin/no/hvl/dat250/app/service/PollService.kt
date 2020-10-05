package no.hvl.dat250.app.service

import no.hvl.dat250.app.dto.PollCreateRequest
import no.hvl.dat250.app.dto.PollRequest
import no.hvl.dat250.app.model.Poll

interface PollService {
    /**
     * create a poll
     */
    fun createPoll(poll: Poll) : Poll

    /**
     * get a poll with a id
     */
    fun getPoll(id:Long): Poll

    /**
     * save and flush changes to a poll
     */
    fun updatePoll(poll: Poll): Poll

    /**
     * deletes a poll
     */
    fun delete(poll: Poll): Poll

}