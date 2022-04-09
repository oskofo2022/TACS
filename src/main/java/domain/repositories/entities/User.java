package domain.repositories.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
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
}
