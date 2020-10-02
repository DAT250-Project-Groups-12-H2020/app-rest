package no.hvl.dat250.app.security.models;

import java.io.Serializable;

public class AuthUser implements Serializable {

    private static final long serialVersionUID = 4408418647685225829L;
    private String uid;
    private String name;
    private String email;
    private boolean isEmailVerified;
    private String issuer;
    private String picture;

    public AuthUser() {}

    public String getUid() {return this.uid;}

    public String getName() {return this.name;}

    public String getEmail() {return this.email;}

    public boolean isEmailVerified() {return this.isEmailVerified;}

    public String getIssuer() {return this.issuer;}

    public String getPicture() {return this.picture;}

    public void setUid(String uid) {this.uid = uid; }

    public void setName(String name) {this.name = name; }

    public void setEmail(String email) {this.email = email; }

    public void setEmailVerified(boolean isEmailVerified) {this.isEmailVerified = isEmailVerified; }

    public void setIssuer(String issuer) {this.issuer = issuer; }

    public void setPicture(String picture) {this.picture = picture; }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AuthUser)) {
            return false;
        }
        final AuthUser other = (AuthUser) o;
        if (!other.canEqual((Object) this)) {
            return false;
        }
        final Object this$uid = this.getUid();
        final Object other$uid = other.getUid();
        if (this$uid == null ? other$uid != null : !this$uid.equals(other$uid)) {
            return false;
        }
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) {
            return false;
        }
        if (this.isEmailVerified() != other.isEmailVerified()) {
            return false;
        }
        final Object this$issuer = this.getIssuer();
        final Object other$issuer = other.getIssuer();
        if (this$issuer == null ? other$issuer != null : !this$issuer.equals(other$issuer)) {
            return false;
        }
        final Object this$picture = this.getPicture();
        final Object other$picture = other.getPicture();
        if (this$picture == null ? other$picture != null : !this$picture.equals(other$picture)) {
            return false;
        }
        return true;
    }

    protected boolean canEqual(final Object other) {return other instanceof AuthUser;}

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $uid = this.getUid();
        result = result * PRIME + ($uid == null ? 43 : $uid.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        result = result * PRIME + (this.isEmailVerified() ? 79 : 97);
        final Object $issuer = this.getIssuer();
        result = result * PRIME + ($issuer == null ? 43 : $issuer.hashCode());
        final Object $picture = this.getPicture();
        result = result * PRIME + ($picture == null ? 43 : $picture.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "User(uid=" + this.getUid() + ", name=" + this.getName() + ", email=" + this.getEmail() +
               ", isEmailVerified=" + this.isEmailVerified() + ", issuer=" + this.getIssuer() + ", picture=" +
               this.getPicture() + ")";
    }
}
