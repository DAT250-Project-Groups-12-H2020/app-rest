package no.hvl.dat250.app.service.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.UserRecord
import com.google.firebase.auth.UserRecord.CreateRequest
import com.google.firebase.auth.UserRecord.UpdateRequest
import no.hvl.dat250.app.dto.AccountCreationRequest
import no.hvl.dat250.app.dto.AccountRequest
import no.hvl.dat250.app.exception.AccountCreationFailedException
import no.hvl.dat250.app.exception.AccountNotFoundException
import no.hvl.dat250.app.exception.AccountUpdateFailedException
import no.hvl.dat250.app.exception.InsufficientAccessException
import no.hvl.dat250.app.exception.NotLoggedInException
import no.hvl.dat250.app.exception.PollNotOwnedByUserException
import no.hvl.dat250.app.model.Account
import no.hvl.dat250.app.model.Poll
import no.hvl.dat250.app.model.Role
import no.hvl.dat250.app.model.Role.ADMIN
import no.hvl.dat250.app.model.Role.USER
import no.hvl.dat250.app.repository.AccountRepository
import no.hvl.dat250.app.repository.PollRepository
import no.hvl.dat250.app.security.SecurityService
import no.hvl.dat250.app.service.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AccountServiceImpl(
  @Autowired
  private val securityService: SecurityService,
  @Autowired
  private val accountRepository: AccountRepository,
  @Autowired
  private val pollRepository: PollRepository
) : AccountService {

  private fun Account.canNotUpdate(uid: String): Boolean {
    return disabled || uid != this.id && this.role != ADMIN
  }

  private fun Account.isNotAdmin(): Boolean {
    return disabled || this.role != ADMIN
  }

  @Bean
  fun createAdmin() {
    val uid = "aPUGQjz0OLdyCObODLJ4gtZzXd12"
    val adminAccount = getAccountByUid(uid)
    FirebaseAuth.getInstance().setCustomUserClaims(uid, mapOf(ROLE_CUSTOM_CLAIM to ADMIN.name))
    accountRepository.saveAndFlush(adminAccount)
  }

  @Bean
  fun loadAccounts() {
    val page = FirebaseAuth.getInstance().listUsers(null)
    for (user in page.iterateAll()) {
      refreshAccount(user, false)
    }
    accountRepository.flush()
  }

  override fun createAccount(request: AccountCreationRequest): Account {
    val admin = getCurrentAccount()
    if (admin.isNotAdmin()) {
      throw InsufficientAccessException("create new account")
    }

    val userRecord: UserRecord = try {
      val createRequest = CreateRequest()
      createRequest.setEmail(request.email)
      createRequest.setPassword(request.password)
      if (request.name?.isNotBlank() == true) {
        createRequest.setDisplayName(request.name)
      }
      if (request.photoUrl?.isNotBlank() == true) {
        createRequest.setPhotoUrl(request.photoUrl)
      }

      FirebaseAuth.getInstance().createUser(createRequest)
    } catch (e: Exception) {
      throw AccountCreationFailedException(e.message)
    }

    return refreshAccount(userRecord)
  }

  override fun updateAccount(uid: String, request: AccountRequest): Account {
    val account = getCurrentAccount()
    if (account.canNotUpdate(uid)) {
      throw InsufficientAccessException("update account data")
    }

    val target = getAccountByUid(uid)
    val updateRequest = UpdateRequest(uid)

    try {
      if (request.name != null && request.name != target.name) {
        updateRequest.setDisplayName(request.name)
      }
      if (request.email != null && request.email != target.email) {
        updateRequest.setEmail(request.email)
        updateRequest.setEmailVerified(false)
      }
      if (request.photoUrl != null && request.photoUrl != target.photoUrl) {
        updateRequest.setPhotoUrl(request.photoUrl)
      }
      if (request.disabled != null && request.disabled != target.disabled) {
        updateRequest.setDisabled(request.disabled)
      }

      if (request.role != null && request.role != target.role) {
        if (account.isNotAdmin()) {
          throw InsufficientAccessException("change role when not logged in as admin")
        }
        val claims: MutableMap<String, Any> = HashMap()
        claims[ROLE_CUSTOM_CLAIM] = request.role.name
        updateRequest.setCustomClaims(claims)
      }

      FirebaseAuth.getInstance().updateUser(updateRequest)
    } catch (e: Exception) {
      throw AccountUpdateFailedException(e.message)
    }
    return refreshAccount(uid)
  }

  override fun deleteAccount(uid: String) {
    if (getCurrentAccount().canNotUpdate(uid)) {
      throw InsufficientAccessException("delete account")
    }
    accountRepository.deleteById(uid)
    FirebaseAuth.getInstance().deleteUserAsync(uid)
  }

  override fun refreshAccount(uid: String, flush: Boolean): Account {
    val userRecord: UserRecord = try {
      FirebaseAuth.getInstance().getUser(uid)
    } catch (_: FirebaseAuthException) {
      throw AccountNotFoundException(uid)
    }
    return refreshAccount(userRecord, flush)
  }

  override fun refreshAccount(userRecord: UserRecord, flush: Boolean): Account {
    val accountOptional = accountRepository.findById(userRecord.uid)
    val account: Account = if (accountOptional.isEmpty) {
      // first time we have seen this account
      Account().also {
        it.id = userRecord.uid
      }
    } else {
      accountOptional.get()
    }
    account.name = userRecord.displayName
    account.email = userRecord.email
    account.isEmailVerified = userRecord.isEmailVerified
    account.photoUrl = userRecord.photoUrl
    account.disabled = userRecord.isDisabled
    try {
      account.role = Role.valueOf(userRecord.customClaims[ROLE_CUSTOM_CLAIM] as String)
    } catch (e: Exception) {
      account.role = USER
    }
    return accountRepository.save(account).also { if (flush) accountRepository.flush() }
  }

  override fun getCurrentAccount(): Account {
    return getCurrentAccountOrNull() ?: throw NotLoggedInException()
  }

  override fun getCurrentAccountOrNull(): Account? {
    val uid = securityService.getLoggedInUid() ?: return null
    return refreshAccount(uid)
  }

  override val loggedInUid: String get() = loggedInUidOrNull ?: throw NotLoggedInException()
  override val loggedInUidOrNull: String? get() = securityService.getLoggedInUid()

  override val isLoggedIn: Boolean get() = loggedInUidOrNull != null
  override val isNotLoggedIn: Boolean get() = loggedInUidOrNull == null

  override fun getAccountByUid(uid: String): Account {
    return accountRepository.findByIdOrNull(uid) ?: return refreshAccount(uid)
  }

  override fun getAccountByUidOrNull(uid: String): Account? {
    return try {
      getAccountByUid(uid)
    } catch (e: Exception) {
      null
    }
  }

  override fun addPoll(poll: Poll, account: Account) {
    account.polls.add(poll)
    accountRepository.saveAndFlush(account)
  }

  override fun deletePoll(poll: Poll, account: Account) {
    // check user owns poll
    if (isNotOwnerOf(poll, account)) {
      throw PollNotOwnedByUserException(poll.id)
    }

    account.polls.remove(poll)
    accountRepository.saveAndFlush(account)
    pollRepository.delete(poll)
  }

  override fun isOwnerOf(poll: Poll, account: Account): Boolean {
    return poll in account.polls || account.role == ADMIN
  }

  private fun findAllAccounts(query: () -> Page<Account>): Page<Account> {
    if (getCurrentAccount().isNotAdmin()) {
      throw InsufficientAccessException("list accounts")
    }
    return query()
  }

  override fun findAllByRoleAndDisabled(role: Role, disabled: Boolean, pageable: Pageable) =
    findAllAccounts { accountRepository.findAllByRoleAndDisabled(role, disabled, pageable) }

  override fun findAllByDisabled(disabled: Boolean, pageable: Pageable) =
    findAllAccounts { accountRepository.findAllByDisabled(disabled, pageable) }

  override fun findAllByRole(role: Role, pageable: Pageable) =
    findAllAccounts { accountRepository.findAllByRole(role, pageable) }

  override fun findAll(pageable: Pageable) =
    findAllAccounts { accountRepository.findAll(pageable) }

  companion object {
    const val ROLE_CUSTOM_CLAIM = "role"
  }
}
