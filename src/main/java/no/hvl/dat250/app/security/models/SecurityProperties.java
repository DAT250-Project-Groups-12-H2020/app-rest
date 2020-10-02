package no.hvl.dat250.app.security.models;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties("security")
public class SecurityProperties {

    private CookieProperties cookieProps;
    private FirebaseProperties firebaseProps;
    private boolean allowCredentials;
    private List<String> allowedOrigins;
    private List<String> allowedHeaders;
    private List<String> exposedHeaders;
    private List<String> allowedMethods;
    private List<String> allowedPublicApis;

    public SecurityProperties() {}

    public CookieProperties getCookieProps() {return this.cookieProps;}

    public FirebaseProperties getFirebaseProps() {return this.firebaseProps;}

    public boolean isAllowCredentials() {return this.allowCredentials;}

    public List<String> getAllowedOrigins() {return this.allowedOrigins;}

    public List<String> getAllowedHeaders() {return this.allowedHeaders;}

    public List<String> getExposedHeaders() {return this.exposedHeaders;}

    public List<String> getAllowedMethods() {return this.allowedMethods;}

    public List<String> getAllowedPublicApis() {return this.allowedPublicApis;}

    public void setCookieProps(CookieProperties cookieProps) {this.cookieProps = cookieProps; }

    public void setFirebaseProps(FirebaseProperties firebaseProps) {this.firebaseProps = firebaseProps; }

    public void setAllowCredentials(boolean allowCredentials) {this.allowCredentials = allowCredentials; }

    public void setAllowedOrigins(List<String> allowedOrigins) {this.allowedOrigins = allowedOrigins; }

    public void setAllowedHeaders(List<String> allowedHeaders) {this.allowedHeaders = allowedHeaders; }

    public void setExposedHeaders(List<String> exposedHeaders) {this.exposedHeaders = exposedHeaders; }

    public void setAllowedMethods(List<String> allowedMethods) {this.allowedMethods = allowedMethods; }

    public void setAllowedPublicApis(List<String> allowedPublicApis) {this.allowedPublicApis = allowedPublicApis; }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SecurityProperties)) {
            return false;
        }
        final SecurityProperties other = (SecurityProperties) o;
        if (!other.canEqual((Object) this)) {
            return false;
        }
        final Object this$cookieProps = this.getCookieProps();
        final Object other$cookieProps = other.getCookieProps();
        if (this$cookieProps == null ? other$cookieProps != null : !this$cookieProps.equals(other$cookieProps)) {
            return false;
        }
        final Object this$firebaseProps = this.getFirebaseProps();
        final Object other$firebaseProps = other.getFirebaseProps();
        if (this$firebaseProps == null ? other$firebaseProps != null
                                       : !this$firebaseProps.equals(other$firebaseProps)) {
            return false;
        }
        if (this.isAllowCredentials() != other.isAllowCredentials()) {
            return false;
        }
        final Object this$allowedOrigins = this.getAllowedOrigins();
        final Object other$allowedOrigins = other.getAllowedOrigins();
        if (this$allowedOrigins == null ? other$allowedOrigins != null
                                        : !this$allowedOrigins.equals(other$allowedOrigins)) {
            return false;
        }
        final Object this$allowedHeaders = this.getAllowedHeaders();
        final Object other$allowedHeaders = other.getAllowedHeaders();
        if (this$allowedHeaders == null ? other$allowedHeaders != null
                                        : !this$allowedHeaders.equals(other$allowedHeaders)) {
            return false;
        }
        final Object this$exposedHeaders = this.getExposedHeaders();
        final Object other$exposedHeaders = other.getExposedHeaders();
        if (this$exposedHeaders == null ? other$exposedHeaders != null
                                        : !this$exposedHeaders.equals(other$exposedHeaders)) {
            return false;
        }
        final Object this$allowedMethods = this.getAllowedMethods();
        final Object other$allowedMethods = other.getAllowedMethods();
        if (this$allowedMethods == null ? other$allowedMethods != null
                                        : !this$allowedMethods.equals(other$allowedMethods)) {
            return false;
        }
        final Object this$allowedPublicApis = this.getAllowedPublicApis();
        final Object other$allowedPublicApis = other.getAllowedPublicApis();
        if (this$allowedPublicApis == null ? other$allowedPublicApis != null
                                           : !this$allowedPublicApis.equals(other$allowedPublicApis)) {
            return false;
        }
        return true;
    }

    protected boolean canEqual(final Object other) {return other instanceof SecurityProperties;}

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $cookieProps = this.getCookieProps();
        result = result * PRIME + ($cookieProps == null ? 43 : $cookieProps.hashCode());
        final Object $firebaseProps = this.getFirebaseProps();
        result = result * PRIME + ($firebaseProps == null ? 43 : $firebaseProps.hashCode());
        result = result * PRIME + (this.isAllowCredentials() ? 79 : 97);
        final Object $allowedOrigins = this.getAllowedOrigins();
        result = result * PRIME + ($allowedOrigins == null ? 43 : $allowedOrigins.hashCode());
        final Object $allowedHeaders = this.getAllowedHeaders();
        result = result * PRIME + ($allowedHeaders == null ? 43 : $allowedHeaders.hashCode());
        final Object $exposedHeaders = this.getExposedHeaders();
        result = result * PRIME + ($exposedHeaders == null ? 43 : $exposedHeaders.hashCode());
        final Object $allowedMethods = this.getAllowedMethods();
        result = result * PRIME + ($allowedMethods == null ? 43 : $allowedMethods.hashCode());
        final Object $allowedPublicApis = this.getAllowedPublicApis();
        result = result * PRIME + ($allowedPublicApis == null ? 43 : $allowedPublicApis.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "SecurityProperties(cookieProps=" + this.getCookieProps() + ", firebaseProps=" +
               this.getFirebaseProps() + ", allowCredentials=" + this.isAllowCredentials() + ", allowedOrigins=" +
               this.getAllowedOrigins() + ", allowedHeaders=" + this.getAllowedHeaders() + ", exposedHeaders=" +
               this.getExposedHeaders() + ", allowedMethods=" + this.getAllowedMethods() + ", allowedPublicApis=" +
               this.getAllowedPublicApis() + ")";
    }
}
