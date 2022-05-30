package domain.requests.posts;

import domain.requests.RequestAnnotationTest;
import org.junit.jupiter.api.Test;

class RequestPostLoginTest extends RequestAnnotationTest<RequestPostLogin> {
    @Test
    public void emailNotSet() {
        this.request.setEmail(null);

        super.invalid("email", "NotNull");
    }

    @Test
    public void emailPatternMissingPrefix() {
        this.request.setEmail("@email.com");

        super.invalid("email", "Pattern");
    }

    @Test
    public void emailPatternMissingAt() {
        this.request.setEmail("someemail.com");

        super.invalid("email", "Pattern");
    }

    @Test
    public void emailPatternMissingDomain() {
        this.request.setEmail("some@.com");

        super.invalid("email", "Pattern");
    }

    @Test
    public void emailPatternMissingSubDomain() {
        this.request.setEmail("some@email");

        super.invalid("email", "Pattern");
    }

    @Test
    public void passwordNotSet() {
        this.request.setPassword(null);

        super.invalid("password", "NotNull");
    }

    @Test
    public void passwordOverSized() {
        this.request.setPassword("@1String@1String@1String@1String1");

        super.invalid("password", "Size");
    }

    @Test
    public void passwordDownSized() {
        this.request.setPassword("@1Str");

        super.invalid("password", "Size");
    }

    @Test
    public void passwordPatternMissingANumber() {
        this.request.setPassword("@Sstring");

        super.invalid("password", "Pattern");
    }

    @Test
    public void passwordPatternMissingALowercaseLetter() {
        this.request.setPassword("@1STRING");

        super.invalid("password", "Pattern");
    }

    @Test
    public void passwordPatternMissingAUppercaseLetter() {
        this.request.setPassword("@1string");

        super.invalid("password", "Pattern");
    }

    @Test
    public void passwordPatternMissingASpecialCharacter() {
        this.request.setPassword("01String");

        super.invalid("password", "Pattern");
    }

    @Override
    protected RequestPostLogin createValidRequest() {
        final var requestPostLogin = new RequestPostLogin();

        requestPostLogin.setEmail("email@email.com");
        requestPostLogin.setPassword("@1String");

        return requestPostLogin;
    }
}