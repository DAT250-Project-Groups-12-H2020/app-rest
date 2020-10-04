package no.hvl.dat250.app.controller

import no.hvl.dat250.app.API_VERSION_1
import no.hvl.dat250.app.dto.AccountResponse
import no.hvl.dat250.app.dto.PublicAccountResponse
import no.hvl.dat250.app.dto.toPublicResponse
import no.hvl.dat250.app.dto.toResponse
import no.hvl.dat250.app.exception.AccountNotFoundException
import no.hvl.dat250.app.exception.NotLoggedInException
import no.hvl.dat250.app.repository.AccountRepository
import no.hvl.dat250.app.security.SecurityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * @author Elg
 */
@RestController
@RequestMapping("$API_VERSION_1/accounts")
class AccountController {

  @Autowired
  lateinit var accountRepository: AccountRepository

  @Autowired
  lateinit var securityService: SecurityService

  @ExceptionHandler(NotLoggedInException::class)
  @GetMapping("/me")
  fun getUser(): AccountResponse {
    return securityService.account?.toResponse()?.also {
      println(accountRepository.findByIdOrNull(it.id))
    } ?: throw NotLoggedInException("Retrieving current user info")
  }

  @ExceptionHandler(AccountNotFoundException::class)
  @GetMapping
  fun publicAccountInformation(@RequestParam("id") id: String): PublicAccountResponse {

    val account = accountRepository.findByIdOrNull(id) ?: throw AccountNotFoundException(id)
    return account.toPublicResponse()
  }
}
