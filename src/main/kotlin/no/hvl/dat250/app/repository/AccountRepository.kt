package no.hvl.dat250.app.repository

import no.hvl.dat250.app.model.Account
import no.hvl.dat250.app.model.Role
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<Account, String> {

  fun findAllByRoleAndDisabled(role: Role, disabled: Boolean, pageable: Pageable): Page<Account>

  fun findAllByDisabled(disabled: Boolean, pageable: Pageable): Page<Account>

  fun findAllByRole(role: Role, pageable: Pageable): Page<Account>
}
