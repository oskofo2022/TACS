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
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @Size(max = 80)
    private String password;

    @OneToMany
    @JoinColumn(name = ColumnConstants.Names.USER_ID)
    private List<Inscription> inscriptions;

    @OneToMany
    @JoinColumn(name = ColumnConstants.Names.USER_ID, referencedColumnName = ColumnConstants.Names.ID)
    private List<Match> matches;

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

    public List<Inscription> getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(List<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
