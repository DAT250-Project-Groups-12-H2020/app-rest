package no.hvl.dat250.app.service.impl

import no.hvl.dat250.app.dto.AccountRequest
import no.hvl.dat250.app.exception.AccountNotFoundException
import no.hvl.dat250.app.exception.NotLoggedInException
import no.hvl.dat250.app.exception.PollNotOwnedByUserException
import no.hvl.dat250.app.model.Account
import no.hvl.dat250.app.model.Poll
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

  override fun createAccount(uid: String, accountRequest: AccountRequest) {
    TODO("not implemented")
  }

  override fun updateAccount(uid: String, accountRequest: AccountRequest) {
    TODO("not implemented")
  }

  override fun deleteAccount(uid: String) {
    TODO("not implemented")
  }

  override fun getCurrentAccount(): Account {
    return getCurrentAccountOrNull() ?: throw NotLoggedInException()
  }

  override fun getCurrentAccountOrNull(): Account? {
    val firebaseUser = securityService.getCurrentFirebaseUser() ?: return null

    val accountOptional = accountRepository.findById(firebaseUser.uid)

    val account: Account = if (accountOptional.isEmpty) {
      // first time we have this account
      Account().also {
        it.id = firebaseUser.uid
      }
    } else {
      accountOptional.get()
    }

    account.name = firebaseUser.name
    account.email = firebaseUser.email
    account.isEmailVerified = firebaseUser.isEmailVerified
    account.picture = firebaseUser.picture
    return accountRepository.saveAndFlush(account)
  }

  override val isLoggedIn: Boolean get() = securityService.getCurrentFirebaseUser() != null
  override val isNotLoggedIn: Boolean get() = securityService.getCurrentFirebaseUser() == null

  override fun getAccountByUid(id: String): Account {
    return getAccountByUidOrNull(id) ?: throw AccountNotFoundException(id)
  }

  override fun getAccountByUidOrNull(id: String): Account? {
    return accountRepository.findByIdOrNull(id)
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
