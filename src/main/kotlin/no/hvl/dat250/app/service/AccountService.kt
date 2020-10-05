package no.hvl.dat250.app.service

import no.hvl.dat250.app.dto.AccountRequest
import no.hvl.dat250.app.exception.AccountNotFoundException
import no.hvl.dat250.app.exception.NotLoggedInException
import no.hvl.dat250.app.exception.PollNotOwnedByUserException
import no.hvl.dat250.app.model.Account
import no.hvl.dat250.app.model.Poll
import org.springframework.web.bind.annotation.ExceptionHandler

interface AccountService {

  fun createAccount(uid: String, accountRequest: AccountRequest)

  fun updateAccount(uid: String, accountRequest: AccountRequest)

  @ExceptionHandler(AccountNotFoundException::class)
  fun deleteAccount(uid: String)

  @ExceptionHandler(NotLoggedInException::class)
  fun getCurrentAccount(): Account

  fun getCurrentAccountOrNull(): Account?

  val isLoggedIn: Boolean

  val isNotLoggedIn: Boolean

  @ExceptionHandler(AccountNotFoundException::class)
  fun getAccountByUid(uid: String): Account

  fun getAccountByUidOrNull(uid: String): Account?

  /**
   * Add a poll to the given account
   */
  @ExceptionHandler(NotLoggedInException::class)
  fun addPoll(poll: Poll, account: Account = getCurrentAccount())

  /**
   * Remove a poll from the given account.
   *
   * @throws NotLoggedInException If the requester is not logged in
   * @throws PollNotOwnedByUserException If the given poll is not owned by the currently logged in user
   */
  @ExceptionHandler(NotLoggedInException::class, PollNotOwnedByUserException::class)
  fun removePoll(poll: Poll, account: Account = getCurrentAccount())

  /**
   * If the given account owns the given poll
   */
  @ExceptionHandler(NotLoggedInException::class)
  fun isOwnerOf(poll: Poll, account: Account = getCurrentAccount()): Boolean

  fun isNotOwnerOf(poll: Poll, account: Account = getCurrentAccount()): Boolean = !isOwnerOf(poll, account)
}
