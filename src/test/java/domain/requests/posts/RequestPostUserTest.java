package domain.requests.posts;

import domain.requests.RequestAnnotationTest;
import org.junit.jupiter.api.Test;

class RequestPostUserTest extends RequestAnnotationTest<RequestPostUser> {
    @Test
    public void nameNotSet() {
        this.request.setName(null);

        super.invalid("name", "NotNull");
    }

    @Test
    public void nameOverSized() {
        this.request.setName("a".repeat(3));

        super.invalid("name", "Size");
    }

    @Test
    public void nameDownSized() {
        this.request.setName("a".repeat(61));

        super.invalid("name", "Size");
    }

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
    protected RequestPostUser createValidRequest() {
        final var requestPostUser = new RequestPostUser();
        requestPostUser.setName("name");
        requestPostUser.setPassword("@1String");
        requestPostUser.setEmail("some@email.com");

        return requestPostUser;
    }
}