package domain.persistence.entities;

import domain.persistence.constants.ColumnConstants;
import domain.persistence.constants.TableConstants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = TableConstants.Names.USERS)
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    @Size(min = 4, max = 60)
    @NotNull
    private String name;

    @Column(unique = true)
    @Size(max = 100)
    @NotNull
    private String email;

    @NotNull
    private byte[] password;

    @NotNull
    private byte[] salt;

    @OneToMany
    @JoinColumn(name = ColumnConstants.Names.ID)
    private List<Inscription> inscriptions;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public byte[] getPassword() {
        return password;
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

    public List<Inscription> getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(List<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }
}
