package domain.requests.posts;

import constants.PatternConstants;

import javax.validation.constraints.*;

public class RequestPostUser {
    @Size(min = 4, max = 60)
    @NotNull
    private String name;

    @Size(max = 100)
    @Pattern(regexp = PatternConstants.EMAIL)
    @NotNull
    private String email;

    @Size(min = 8, max = 32)
    @Pattern(regexp = PatternConstants.AT_LEAST_ONE_NUMBER)
    @Pattern(regexp = PatternConstants.AT_LEAST_ONE_LOWERCASE_LETTER)
    @Pattern(regexp = PatternConstants.AT_LEAST_ONE_UPPERCASE_LETTER)
    @Pattern(regexp = PatternConstants.AT_LEAST_ONE_SPECIAL_CHARACTER)
    @NotNull
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
