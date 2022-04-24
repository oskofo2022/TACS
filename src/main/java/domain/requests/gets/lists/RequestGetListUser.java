package domain.requests.gets.lists;

import constants.RSQLConstants;

import java.util.Optional;

public class RequestGetListUser extends RequestGetPagedList {
    private String name;
    private String email;

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

    @Override
    protected void addRestrictions() {
        this.addRestriction(RSQLConstants.Filters.getLike("email"), this.email);
        this.addRestriction(RSQLConstants.Filters.getLike("name"), this.name);
    }

    @Override
    protected String defaultSortBy() {
        return "name";
    }
}
