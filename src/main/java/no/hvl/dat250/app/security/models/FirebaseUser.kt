package no.hvl.dat250.app.security.models

import java.io.Serializable

data class FirebaseUser(
  var uid: String,
  var name: String? = null,
  var email: String? = null,
  var isEmailVerified: Boolean = false,
  var issuer: String? = null,
  var picture: String? = null
) : Serializable {

  companion object {
    private const val serialVersionUID = 4408418647685225829L
  }
}
