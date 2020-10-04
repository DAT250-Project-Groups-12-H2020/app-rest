package no.hvl.dat250.app.repository

import no.hvl.dat250.app.model.Account
import no.hvl.dat250.app.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<Account, String> {

  fun findByRole(role: Role): List<Account>

  fun findByName(name: String): List<Account>

  fun findByEmail(email: String): Account?

  fun existsAccountByEmail(email: String): Boolean
}
