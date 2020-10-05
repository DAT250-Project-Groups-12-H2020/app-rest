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

data class AccountCreationRequest(
  val name: String? = null,
  val email: String,
  val photoUrl: String? = null,
  val password: String,
  val role: Role? = null,
)

data class AccountResponse(
  val id: String,
  val role: Role,
  val name: String?,
  val email: String?,
  val disabled: Boolean,
  val photoUrl: String?,
  val isEmailVerified: Boolean,
  val polls: List<PollResponse>,
  val votes: Map<PollResponse, VoteResponse>
)

data class PublicAccountResponse(
  val id: String,
  val role: Role,
  val name: String?,
  val disabled: Boolean,
  val photoUrl: String?,
)

fun Account.toResponse(): AccountResponse {
  return AccountResponse(
    id,
    role,
    name,
    email,
    disabled,
    photoUrl,
    isEmailVerified,
    polls.map { it.toResponse() },
    votes.mapKeys { it.key.toResponse() }.mapValues { it.value.toResponse() }
  )
}

fun Account.toPublicResponse(): PublicAccountResponse {
  return PublicAccountResponse(id, role, name, disabled, photoUrl)
}
