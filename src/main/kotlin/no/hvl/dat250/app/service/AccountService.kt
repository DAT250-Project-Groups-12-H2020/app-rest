package no.hvl.dat250.app.service

import com.google.firebase.auth.UserRecord
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
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.ExceptionHandler

interface AccountService {

  @ExceptionHandler(
    NotLoggedInException::class,
    InsufficientAccessException::class,
    AccountCreationFailedException::class
  )
  fun createAccount(request: AccountCreationRequest): Account

  /**
   * Update account information. Does not work if the used does not exist locally.
   * If the given [uid] is not the logged in user an [InsufficientAccessException] might be thrown
   */
  @ExceptionHandler(
    NotLoggedInException::class,
    AccountNotFoundException::class,
    InsufficientAccessException::class,
    AccountUpdateFailedException::class
  )
  fun updateAccount(uid: String, request: AccountRequest): Account

  @ExceptionHandler(NotLoggedInException::class, AccountNotFoundException::class, InsufficientAccessException::class)
  fun deleteAccount(uid: String)

  @ExceptionHandler(AccountNotFoundException::class)
  fun refreshAccount(uid: String, flush: Boolean = true): Account

  fun refreshAccount(userRecord: UserRecord, flush: Boolean = true): Account

  @ExceptionHandler(NotLoggedInException::class)
  fun getCurrentAccount(): Account

  fun getCurrentAccountOrNull(): Account?

  @get:ExceptionHandler(AccountNotFoundException::class)
  val loggedInUid: String

  val loggedInUidOrNull: String?

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
  fun deletePoll(poll: Poll, account: Account = getCurrentAccount())

  /**
   * If the given account owns the given poll
   */
  @ExceptionHandler(NotLoggedInException::class)
  fun isOwnerOf(poll: Poll, account: Account = getCurrentAccount()): Boolean

  @ExceptionHandler(NotLoggedInException::class)
  fun isNotOwnerOf(poll: Poll, account: Account = getCurrentAccount()): Boolean = !isOwnerOf(poll, account)

  @ExceptionHandler(NotLoggedInException::class, InsufficientAccessException::class)
  fun findAllByRoleAndDisabled(role: Role, disabled: Boolean, pageable: Pageable): Page<Account>

  @ExceptionHandler(NotLoggedInException::class, InsufficientAccessException::class)
  fun findAllByDisabled(disabled: Boolean, pageable: Pageable): Page<Account>

  @ExceptionHandler(NotLoggedInException::class, InsufficientAccessException::class)
  fun findAllByRole(role: Role, pageable: Pageable): Page<Account>

  @ExceptionHandler(NotLoggedInException::class, InsufficientAccessException::class)
  fun findAll(pageable: Pageable): Page<Account>
}
