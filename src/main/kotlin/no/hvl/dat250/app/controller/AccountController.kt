package no.hvl.dat250.app.controller

import no.hvl.dat250.app.API_VERSION_1
import no.hvl.dat250.app.dto.AccountRequest
import no.hvl.dat250.app.dto.AccountResponse
import no.hvl.dat250.app.dto.PublicAccountResponse
import no.hvl.dat250.app.dto.toAccount
import no.hvl.dat250.app.dto.toPublicResponse
import no.hvl.dat250.app.dto.toResponse
import no.hvl.dat250.app.exception.AccountNotFoundException
import no.hvl.dat250.app.exception.NotUniqueAccountEmailException
import no.hvl.dat250.app.repository.AccountRepository
import no.hvl.dat250.app.security.SecurityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

/**
 * @author Elg
 */
@RestController
@RequestMapping(API_VERSION_1)
class AccountController {

  @Autowired
  lateinit var accountRepository: AccountRepository

  @Autowired
  lateinit var securityService: SecurityService

  @GetMapping("/public/account/{id}")
  fun publicAccountInformation(@PathVariable id: String): PublicAccountResponse? {
    val account = accountRepository.findById(id)
    return if (account.isEmpty) {
      throw AccountNotFoundException(id)
    } else {
      account.get().toPublicResponse()
    }
  }

  @PostMapping("/protected/account")
  fun createAccount(@Valid @RequestBody accountRequest: AccountRequest): AccountResponse {
    if (accountRepository.existsAccountByEmail(accountRequest.email)) {
      throw NotUniqueAccountEmailException()
    }
    return accountRepository.saveAndFlush(accountRequest.toAccount()).toResponse()
  }
}
