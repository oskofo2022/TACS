package domain.requests.posts;

import constants.PatternConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RequestPostLogin {

    @Size(max = 100)
    @Pattern(regexp = PatternConstants.EMAIL)
    @NotBlank
    private String email;

    @Size(min = 8, max = 32)
    @Pattern(regexp = PatternConstants.AT_LEAST_ONE_NUMBER)
    @Pattern(regexp = PatternConstants.AT_LEAST_ONE_LOWERCASE_LETTER)
    @Pattern(regexp = PatternConstants.AT_LEAST_ONE_UPPERCASE_LETTER)
    @Pattern(regexp = PatternConstants.AT_LEAST_ONE_SPECIAL_CHARACTER)
    @NotBlank
    private String password;

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
