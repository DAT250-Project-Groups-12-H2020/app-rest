package no.hvl.dat250.app.security.models;

public class FirebaseProperties {

  private int sessionExpiryInDays;
  private String databaseUrl;
  private boolean enableStrictServerSession;
  private boolean enableCheckSessionRevoked;
  private boolean enableLogoutEverywhere;

  public FirebaseProperties() {}

  public int getSessionExpiryInDays() {
    return this.sessionExpiryInDays;
  }

  public String getDatabaseUrl() {
    return this.databaseUrl;
  }

  public boolean isEnableStrictServerSession() {
    return this.enableStrictServerSession;
  }

  public boolean isEnableCheckSessionRevoked() {
    return this.enableCheckSessionRevoked;
  }

  public boolean isEnableLogoutEverywhere() {
    return this.enableLogoutEverywhere;
  }

  public void setSessionExpiryInDays(int sessionExpiryInDays) {
    this.sessionExpiryInDays = sessionExpiryInDays;
  }

  public void setDatabaseUrl(String databaseUrl) {
    this.databaseUrl = databaseUrl;
  }

  public void setEnableStrictServerSession(boolean enableStrictServerSession) {
    this.enableStrictServerSession = enableStrictServerSession;
  }

  public void setEnableCheckSessionRevoked(boolean enableCheckSessionRevoked) {
    this.enableCheckSessionRevoked = enableCheckSessionRevoked;
  }

  public void setEnableLogoutEverywhere(boolean enableLogoutEverywhere) {
    this.enableLogoutEverywhere = enableLogoutEverywhere;
  }

  @Override
  public boolean equals(final Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof FirebaseProperties)) {
      return false;
    }
    final FirebaseProperties other = (FirebaseProperties) o;
    if (!other.canEqual((Object) this)) {
      return false;
    }
    if (this.getSessionExpiryInDays() != other.getSessionExpiryInDays()) {
      return false;
    }
    final Object this$databaseUrl = this.getDatabaseUrl();
    final Object other$databaseUrl = other.getDatabaseUrl();
    if (this$databaseUrl == null
        ? other$databaseUrl != null
        : !this$databaseUrl.equals(other$databaseUrl)) {
      return false;
    }
    if (this.isEnableStrictServerSession() != other.isEnableStrictServerSession()) {
      return false;
    }
    if (this.isEnableCheckSessionRevoked() != other.isEnableCheckSessionRevoked()) {
      return false;
    }
    if (this.isEnableLogoutEverywhere() != other.isEnableLogoutEverywhere()) {
      return false;
    }
    return true;
  }

  protected boolean canEqual(final Object other) {
    return other instanceof FirebaseProperties;
  }

  @Override
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSessionExpiryInDays();
    final Object $databaseUrl = this.getDatabaseUrl();
    result = result * PRIME + ($databaseUrl == null ? 43 : $databaseUrl.hashCode());
    result = result * PRIME + (this.isEnableStrictServerSession() ? 79 : 97);
    result = result * PRIME + (this.isEnableCheckSessionRevoked() ? 79 : 97);
    result = result * PRIME + (this.isEnableLogoutEverywhere() ? 79 : 97);
    return result;
  }

  @Override
  public String toString() {
    return "FirebaseProperties(sessionExpiryInDays="
        + this.getSessionExpiryInDays()
        + ", databaseUrl="
        + this.getDatabaseUrl()
        + ", enableStrictServerSession="
        + this.isEnableStrictServerSession()
        + ", enableCheckSessionRevoked="
        + this.isEnableCheckSessionRevoked()
        + ", enableLogoutEverywhere="
        + this.isEnableLogoutEverywhere()
        + ")";
  }
}
