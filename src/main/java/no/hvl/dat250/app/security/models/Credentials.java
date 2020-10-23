package no.hvl.dat250.app.security.models;

import com.google.firebase.auth.FirebaseToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Credentials {

  public Credentials(
      @NotNull CredentialType type,
      @NotNull FirebaseToken decodedToken,
      @Nullable String idToken,
      @Nullable String session) {
    this.type = type;
    this.decodedToken = decodedToken;
    this.idToken = idToken;
    this.session = session;
  }

  @NotNull
  public CredentialType getType() {
    return type;
  }

  @NotNull
  public FirebaseToken getDecodedToken() {
    return decodedToken;
  }

  @Nullable
  public String getIdToken() {
    return idToken;
  }

  @Nullable
  public String getSession() {
    return session;
  }

  public void setType(@NotNull CredentialType type) {
    this.type = type;
  }

  public void setDecodedToken(@NotNull FirebaseToken decodedToken) {
    this.decodedToken = decodedToken;
  }

  public void setIdToken(@Nullable String idToken) {
    this.idToken = idToken;
  }

  public void setSession(@Nullable String session) {
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
    final Object this$type = getType();
    final Object other$type = other.getType();
    if (this$type == null ? other$type != null : !this$type.equals(other$type)) {
      return false;
    }
    final Object this$decodedToken = getDecodedToken();
    final Object other$decodedToken = other.getDecodedToken();
    if (this$decodedToken == null
        ? other$decodedToken != null
        : !this$decodedToken.equals(other$decodedToken)) {
      return false;
    }
    final Object this$idToken = getIdToken();
    final Object other$idToken = other.getIdToken();
    if (this$idToken == null ? other$idToken != null : !this$idToken.equals(other$idToken)) {
      return false;
    }
    final Object this$session = getSession();
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
    final Object $type = getType();
    result = result * PRIME + ($type == null ? 43 : $type.hashCode());
    final Object $decodedToken = getDecodedToken();
    result = result * PRIME + ($decodedToken == null ? 43 : $decodedToken.hashCode());
    final Object $idToken = getIdToken();
    result = result * PRIME + ($idToken == null ? 43 : $idToken.hashCode());
    final Object $session = getSession();
    result = result * PRIME + ($session == null ? 43 : $session.hashCode());
    return result;
  }

  @Override
  public String toString() {
    return "Credentials(type="
        + getType()
        + ", decodedToken="
        + getDecodedToken()
        + ", idToken="
        + getIdToken()
        + ", session="
        + getSession()
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
