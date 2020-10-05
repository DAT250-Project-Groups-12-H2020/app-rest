package no.hvl.dat250.app.service

import no.hvl.dat250.app.dto.AccountRequest
import no.hvl.dat250.app.exception.AccountNotFoundException
import no.hvl.dat250.app.exception.InsufficientAccessException
import no.hvl.dat250.app.exception.NotLoggedInException
import no.hvl.dat250.app.exception.PollNotOwnedByUserException
import no.hvl.dat250.app.model.Account
import no.hvl.dat250.app.model.Poll
import no.hvl.dat250.app.model.Role
import org.springframework.web.bind.annotation.ExceptionHandler

interface AccountService {

  /**
   * Update an account information. Does not work if the used does not exist locally.
   * If the given [uid] is not the logged in user an [InsufficientAccessException] might be thrown
   */
  @ExceptionHandler(NotLoggedInException::class, AccountNotFoundException::class, InsufficientAccessException::class)
  fun updateAccount(uid: String, accountRequest: AccountRequest)

  @ExceptionHandler(NotLoggedInException::class, AccountNotFoundException::class, InsufficientAccessException::class)
  fun deleteAccount(uid: String)

  @ExceptionHandler(NotLoggedInException::class, AccountNotFoundException::class, InsufficientAccessException::class)
  fun changeRole(uid: String, role: Role)

  @ExceptionHandler(AccountNotFoundException::class)
  fun refreshAccount(uid: String): Account

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
