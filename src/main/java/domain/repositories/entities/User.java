package domain.repositories.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table (name="users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Size(min = 4, max = 60)
    @NotNull
    private String name;

    @Size(max = 100)
    @NotNull
    private String email;

    @NotNull
    private byte[] password;

    @NotNull
    private byte[] salt;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return this.email;
    }

    public byte[] getPassword() { return this.password; }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
}
