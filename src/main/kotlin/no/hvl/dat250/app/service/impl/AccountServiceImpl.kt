package no.hvl.dat250.app.service.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.UserRecord
import com.google.firebase.auth.UserRecord.UpdateRequest
import no.hvl.dat250.app.dto.AccountRequest
import no.hvl.dat250.app.exception.AccountNotFoundException
import no.hvl.dat250.app.exception.InsufficientAccessException
import no.hvl.dat250.app.exception.NotLoggedInException
import no.hvl.dat250.app.exception.PollNotOwnedByUserException
import no.hvl.dat250.app.model.Account
import no.hvl.dat250.app.model.Poll
import no.hvl.dat250.app.model.Role
import no.hvl.dat250.app.model.Role.ADMIN
import no.hvl.dat250.app.repository.AccountRepository
import no.hvl.dat250.app.security.SecurityService
import no.hvl.dat250.app.service.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AccountServiceImpl : AccountService {

  @Autowired
  private lateinit var securityService: SecurityService

  @Autowired
  private lateinit var accountRepository: AccountRepository

  override fun updateAccount(uid: String, accountRequest: AccountRequest) {
    val account = getCurrentAccount()
    if (uid != account.id && account.role != ADMIN) {
      throw InsufficientAccessException("update user data")
    }

    val target = getAccountByUid(uid)
    val updateRequest = UpdateRequest(uid)
    if (accountRequest.name != target.name) {
      updateRequest.setDisplayName(accountRequest.name)
    }
    if (accountRequest.email != target.email) {
      updateRequest.setEmail(accountRequest.email)
    }
    if (accountRequest.photoUrl != target.photoUrl) {
      updateRequest.setPhotoUrl(accountRequest.photoUrl)
    }
    if (accountRequest.disabled != null && accountRequest.disabled != target.disabled) {
      updateRequest.setDisabled(accountRequest.disabled)
    }
    FirebaseAuth.getInstance().updateUser(updateRequest)
    refreshAccount(uid)
  }

  override fun deleteAccount(uid: String) {
    if (getCurrentAccount().role != ADMIN) {
      throw InsufficientAccessException("delete users")
    }
    accountRepository.deleteById(uid)
    FirebaseAuth.getInstance().deleteUserAsync(uid)
  }

  override fun changeRole(uid: String, role: Role) {
    if (getCurrentAccount().role != ADMIN) {
      throw InsufficientAccessException("change role of users")
    }
    val target = getAccountByUid(uid)
    target.role = role
    accountRepository.saveAndFlush(target)
  }

  override fun refreshAccount(uid: String): Account {
    val userRecord: UserRecord = try {
      FirebaseAuth.getInstance().getUser(uid)
    } catch (_: FirebaseAuthException) {
      throw AccountNotFoundException(uid)
    }

    val accountOptional = accountRepository.findById(userRecord.uid)
    val account: Account = if (accountOptional.isEmpty) {
      // first time we have this account
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
    return accountRepository.saveAndFlush(account)
  }

  override fun getCurrentAccount(): Account {
    return getCurrentAccountOrNull() ?: throw NotLoggedInException()
  }

  override fun getCurrentAccountOrNull(): Account? {
    val uid = securityService.getLoggedInUid() ?: return null
    return refreshAccount(uid)
  }

  override val isLoggedIn: Boolean get() = securityService.getLoggedInUid() != null
  override val isNotLoggedIn: Boolean get() = securityService.getLoggedInUid() == null

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

  override fun removePoll(poll: Poll, account: Account) {
    // check user owns poll
    if (isNotOwnerOf(poll, account)) {
      throw PollNotOwnedByUserException(poll.id)
    }

    account.polls.remove(poll)
    accountRepository.saveAndFlush(account)
  }

  override fun isOwnerOf(poll: Poll, account: Account): Boolean {
    return poll in account.polls
  }
}
