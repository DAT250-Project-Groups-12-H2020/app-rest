package no.hvl.dat250.app.security.models;

public class CookieProperties {

    private String domain;
    private String path;
    private boolean httpOnly;
    private boolean secure;
    private int maxAgeInMinutes;

    public CookieProperties() {}

    public String getDomain() {return this.domain;}

    public String getPath() {return this.path;}

    public boolean isHttpOnly() {return this.httpOnly;}

    public boolean isSecure() {return this.secure;}

    public int getMaxAgeInMinutes() {return this.maxAgeInMinutes;}

    public void setDomain(String domain) {this.domain = domain; }

    public void setPath(String path) {this.path = path; }

    public void setHttpOnly(boolean httpOnly) {this.httpOnly = httpOnly; }

    public void setSecure(boolean secure) {this.secure = secure; }

    public void setMaxAgeInMinutes(int maxAgeInMinutes) {this.maxAgeInMinutes = maxAgeInMinutes; }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CookieProperties)) {
            return false;
        }
        final CookieProperties other = (CookieProperties) o;
        if (!other.canEqual((Object) this)) {
            return false;
        }
        final Object this$domain = this.getDomain();
        final Object other$domain = other.getDomain();
        if (this$domain == null ? other$domain != null : !this$domain.equals(other$domain)) {
            return false;
        }
        final Object this$path = this.getPath();
        final Object other$path = other.getPath();
        if (this$path == null ? other$path != null : !this$path.equals(other$path)) {
            return false;
        }
        if (this.isHttpOnly() != other.isHttpOnly()) {
            return false;
        }
        if (this.isSecure() != other.isSecure()) {
            return false;
        }
        if (this.getMaxAgeInMinutes() != other.getMaxAgeInMinutes()) {
            return false;
        }
        return true;
    }

    protected boolean canEqual(final Object other) {return other instanceof CookieProperties;}

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $domain = this.getDomain();
        result = result * PRIME + ($domain == null ? 43 : $domain.hashCode());
        final Object $path = this.getPath();
        result = result * PRIME + ($path == null ? 43 : $path.hashCode());
        result = result * PRIME + (this.isHttpOnly() ? 79 : 97);
        result = result * PRIME + (this.isSecure() ? 79 : 97);
        result = result * PRIME + this.getMaxAgeInMinutes();
        return result;
    }

    @Override
    public String toString() {
        return "CookieProperties(domain=" + this.getDomain() + ", path=" + this.getPath() + ", httpOnly=" +
               this.isHttpOnly() + ", secure=" + this.isSecure() + ", maxAgeInMinutes=" + this.getMaxAgeInMinutes() +
               ")";
    }
}
