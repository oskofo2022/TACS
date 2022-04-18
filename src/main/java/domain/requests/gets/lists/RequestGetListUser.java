package domain.requests.gets.lists;

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
    protected void addQueries() {
        this.addQuery("email=like='%s'", this.email);
        this.addQuery("name=like='%s'", this.name);
    }
}
