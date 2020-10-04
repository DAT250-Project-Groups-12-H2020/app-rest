package no.hvl.dat250.app.security.models;

import com.google.firebase.auth.FirebaseToken;

public class Credentials {

  public Credentials(
      CredentialType type, FirebaseToken decodedToken, String idToken, String session) {
    this.type = type;
    this.decodedToken = decodedToken;
    this.idToken = idToken;
    this.session = session;
  }

  public CredentialType getType() {
    return this.type;
  }

  public FirebaseToken getDecodedToken() {
    return this.decodedToken;
  }

  public String getIdToken() {
    return this.idToken;
  }

  public String getSession() {
    return this.session;
  }

  public void setType(CredentialType type) {
    this.type = type;
  }

  public void setDecodedToken(FirebaseToken decodedToken) {
    this.decodedToken = decodedToken;
  }

  public void setIdToken(String idToken) {
    this.idToken = idToken;
  }

  public void setSession(String session) {
    this.session = session;
  }

  @Override
  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Credentials)) {
      return false;
    }
    final Credentials other = (Credentials) o;
    if (!other.canEqual((Object) this)) {
      return false;
    }
    final Object this$type = this.getType();
    final Object other$type = other.getType();
    if (this$type == null ? other$type != null : !this$type.equals(other$type)) {
      return false;
    }
    final Object this$decodedToken = this.getDecodedToken();
    final Object other$decodedToken = other.getDecodedToken();
    if (this$decodedToken == null
        ? other$decodedToken != null
        : !this$decodedToken.equals(other$decodedToken)) {
      return false;
    }
    final Object this$idToken = this.getIdToken();
    final Object other$idToken = other.getIdToken();
    if (this$idToken == null ? other$idToken != null : !this$idToken.equals(other$idToken)) {
      return false;
    }
    final Object this$session = this.getSession();
    final Object other$session = other.getSession();
    if (this$session == null ? other$session != null : !this$session.equals(other$session)) {
      return false;
    }
    return true;
  }

  protected boolean canEqual(final Object other) {
    return other instanceof Credentials;
  }

  @Override
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $type = this.getType();
    result = result * PRIME + ($type == null ? 43 : $type.hashCode());
    final Object $decodedToken = this.getDecodedToken();
    result = result * PRIME + ($decodedToken == null ? 43 : $decodedToken.hashCode());
    final Object $idToken = this.getIdToken();
    result = result * PRIME + ($idToken == null ? 43 : $idToken.hashCode());
    final Object $session = this.getSession();
    result = result * PRIME + ($session == null ? 43 : $session.hashCode());
    return result;
  }

  @Override
  public String toString() {
    return "Credentials(type="
        + this.getType()
        + ", decodedToken="
        + this.getDecodedToken()
        + ", idToken="
        + this.getIdToken()
        + ", session="
        + this.getSession()
        + ")";
  }

  public enum CredentialType {
    ID_TOKEN,
    SESSION
  }

  private CredentialType type;
  private FirebaseToken decodedToken;
  private String idToken;
  private String session;
}
