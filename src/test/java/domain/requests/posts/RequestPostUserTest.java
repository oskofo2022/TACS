package domain.requests.posts;

import domain.requests.RequestAnnotationTest;
import org.junit.jupiter.api.Test;

class RequestPostUserTest extends RequestAnnotationTest<RequestPostUser> {
    @Test
    public void nameNotSet() {
        this.request.setName(null);

        this.invalid("name", "NotNull");
    }

    @Test
    public void nameOverSized() {
        this.request.setName("a".repeat(3));

        this.invalid("name", "Size");
    }

    @Test
    public void nameDownSized() {
        this.request.setName("a".repeat(61));

        this.invalid("name", "Size");
    }

    @Test
    public void emailNotSet() {
        this.request.setEmail(null);

        this.invalid("email", "NotNull");
    }

    @Test
    public void emailPatternMissingPrefix() {
        this.request.setEmail("@email.com");

        super.invalid("email", "Pattern");
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
    protected RequestPostUser createValidRequest() {
        final var requestPostUser = new RequestPostUser();
        requestPostUser.setName("name");
        requestPostUser.setPassword("@1String");
        requestPostUser.setEmail("some@email.com");

        return requestPostUser;
    }
}