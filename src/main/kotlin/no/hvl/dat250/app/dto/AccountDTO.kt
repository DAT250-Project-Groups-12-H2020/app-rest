package no.hvl.dat250.app.dto

import no.hvl.dat250.app.model.Account
import no.hvl.dat250.app.model.Role

/**
 * @author Elg
 */
data class AccountRequest(
        val role: Role? = null,
        val name: String,
        val email: String,
        val password: String
)

data class AccountResponse(
        val id: Long,
        val role: Role,
        val polls: List<PollResponse>,
        val name: String,
        val email: String,
        val votes: Map<PollResponse, VoteResponse>
)

data class PublicAccountResponse(
        val id: Long,
        val role: Role,
        val name: String,
)

fun AccountRequest.toAccount(): Account {
    val account = Account()
    account.role = role ?: Role.USER
    account.name = name
    account.email = email
    account.password = password
    return account
}

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
