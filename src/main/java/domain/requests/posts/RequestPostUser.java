package domain.requests.posts;

import constants.PatternConstants;

import javax.validation.constraints.*;

//TODO: Add spanish validations messages
public class RequestPostUser {
    @Min(4)
    @Max(60)
    @NotBlank
    private String name;

    @Max(100)
    @Pattern(regexp = PatternConstants.EMAIL)
    @NotBlank
    private String email;

    @Min(8)
    @Max(32)
    @Pattern(regexp = PatternConstants.ATLEASTONENUMBER)
    @Pattern(regexp = PatternConstants.ATLEASTONELOWERCASELETTER)
    @Pattern(regexp = PatternConstants.ATLEASTONEUPPERCASELETTER)
    @Pattern(regexp = PatternConstants.ATLEASTONESPECIALCHARACTER)
    @NotBlank
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
