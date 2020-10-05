package no.hvl.dat250.app.controller

import no.hvl.dat250.app.API_VERSION_1
import no.hvl.dat250.app.dto.AccountResponse
import no.hvl.dat250.app.dto.PublicAccountResponse
import no.hvl.dat250.app.dto.toPublicResponse
import no.hvl.dat250.app.dto.toResponse
import no.hvl.dat250.app.service.AccountService
import org.springframework.beans.factory.annotation.Autowired
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
  private lateinit var accountService: AccountService

  @GetMapping("/me")
  fun getUser(): AccountResponse {
    return accountService.getCurrentAccount().toResponse()
  }

  @GetMapping
  fun publicAccountInformation(@RequestParam("id") id: String): PublicAccountResponse {
    return accountService.getAccountByUid(id).toPublicResponse()
  }
}
