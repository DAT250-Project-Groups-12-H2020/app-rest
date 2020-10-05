package no.hvl.dat250.app.controller

import no.hvl.dat250.app.API_VERSION_1
import no.hvl.dat250.app.dto.AccountCreationRequest
import no.hvl.dat250.app.dto.AccountRequest
import no.hvl.dat250.app.dto.AccountResponse
import no.hvl.dat250.app.dto.PublicAccountResponse
import no.hvl.dat250.app.dto.toPublicResponse
import no.hvl.dat250.app.dto.toResponse
import no.hvl.dat250.app.service.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

/**
 * @author Elg
 */
@RestController
@RequestMapping("$API_VERSION_1/accounts")
class AccountController {

  @Autowired
  private lateinit var accountService: AccountService

  @PostMapping("/create")
  fun createAccount(@Valid @RequestBody accountRequest: AccountCreationRequest): AccountResponse {
    return accountService.createAccount(accountRequest).toResponse()
  }

  @GetMapping("/me")
  fun getMe(): AccountResponse {
    return accountService.getCurrentAccount().toResponse()
  }

  @PutMapping("/me")
  fun updateMe(@Valid @RequestBody accountRequest: AccountRequest): AccountResponse {
    val account = accountService.getCurrentAccount()
    return accountService.updateAccount(account.id, accountRequest).toResponse()
  }

  @DeleteMapping("/me")
  fun deleteMe(): AccountResponse {
    val account = accountService.getCurrentAccount()
    accountService.deleteAccount(account.id)
    return account.toResponse()
  }

  @GetMapping
  fun publicAccountInformation(@RequestParam("id") id: String): PublicAccountResponse {
    return accountService.getAccountByUid(id).toPublicResponse()
  }

  @PutMapping("/{id}")
  fun updateAccount(@PathVariable id: String, @Valid @RequestBody accountRequest: AccountRequest): AccountResponse {
    return accountService.updateAccount(id, accountRequest).toResponse()
  }

  @DeleteMapping("/{id}")
  fun deleteAccount(@PathVariable id: String): AccountResponse {
    val deleted = accountService.getAccountByUid(id)
    accountService.deleteAccount(id)
    return deleted.toResponse()
  }

  @GetMapping("/{id}")
  fun getAccount(@PathVariable id: String): AccountResponse {
    return accountService.getAccountByUid(id).toResponse()
  }
}
