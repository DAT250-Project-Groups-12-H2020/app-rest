package no.hvl.dat250.app.dto

import no.hvl.dat250.app.model.Account
import no.hvl.dat250.app.model.Role

/**
 * @author Elg
 */
data class AccountRequest(
  val name: String? = null,
  val email: String? = null,
  val photoUrl: String? = null,
  val disabled: Boolean? = null,
)

data class AccountResponse(
  val id: String,
  val role: Role,
  val polls: List<PollResponse>,
  val name: String?,
  val email: String?,
  val votes: Map<PollResponse, VoteResponse>
)

data class PublicAccountResponse(
  val id: String,
  val role: Role,
  val name: String?
)

fun Account.toResponse(): AccountResponse {
  return AccountResponse(
    id,
    role,
    polls.map { it.toResponse() },
    name,
    email,
    votes.mapKeys { it.key.toResponse() }.mapValues { it.value.toResponse() }
  )
}

fun Account.toPublicResponse(): PublicAccountResponse {
  return PublicAccountResponse(id, role, name)
}
