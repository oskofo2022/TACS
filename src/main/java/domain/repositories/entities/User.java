package domain.repositories.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;


    @Size(min = 4, max = 60)
    @Column(nullable = false)
    private String name;

    @Size(max = 100)
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private byte[] password;

    @Column(nullable = false)
    private byte[] salt;
}
