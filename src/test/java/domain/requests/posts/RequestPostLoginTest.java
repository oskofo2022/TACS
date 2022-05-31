package domain.requests.posts;

import domain.requests.RequestAnnotationTest;
import org.junit.jupiter.api.Test;

class RequestPostLoginTest extends RequestAnnotationTest<RequestPostLogin> {
    @Test
    public void emailNotSet() {
        this.request.setEmail(null);

        this.invalid("email", "NotNull");
    }

    @Test
    public void emailPatternMissingPrefix() {
        this.request.setEmail("@email.com");

        this.invalid("email", "Pattern");
    }

    @Test
    public void emailPatternMissingAt() {
        this.request.setEmail("someemail.com");

        this.invalid("email", "Pattern");
    }

    @Test
    public void emailPatternMissingDomain() {
        this.request.setEmail("some@.com");

        this.invalid("email", "Pattern");
    }

    @Test
    public void emailPatternMissingSubDomain() {
        this.request.setEmail("some@email");

        this.invalid("email", "Pattern");
    }

    @Test
    public void passwordNotSet() {
        this.request.setPassword(null);

        this.invalid("password", "NotNull");
    }

    @Test
    public void passwordOverSized() {
        this.request.setPassword("@1String@1String@1String@1String1");

        this.invalid("password", "Size");
    }

    @Test
    public void passwordDownSized() {
        this.request.setPassword("@1Str");

        this.invalid("password", "Size");
    }

    @Test
    public void passwordPatternMissingANumber() {
        this.request.setPassword("@Sstring");

        this.invalid("password", "Pattern");
    }

    @Test
    public void passwordPatternMissingALowercaseLetter() {
        this.request.setPassword("@1STRING");

        this.invalid("password", "Pattern");
    }

    @Test
    public void passwordPatternMissingAUppercaseLetter() {
        this.request.setPassword("@1string");

        this.invalid("password", "Pattern");
    }

    @Test
    public void passwordPatternMissingASpecialCharacter() {
        this.request.setPassword("01String");

        this.invalid("password", "Pattern");
    }

    @Override
    protected RequestPostLogin createValidRequest() {
        final var requestPostLogin = new RequestPostLogin();

        requestPostLogin.setEmail("email@email.com");
        requestPostLogin.setPassword("@1String");

        return requestPostLogin;
    }
}