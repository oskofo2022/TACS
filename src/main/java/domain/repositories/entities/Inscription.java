package domain.repositories.entities;

import domain.repositories.constants.ColumnConstants;

import javax.persistence.*;
import java.util.List;

@Entity
public class Inscription {

    @EmbeddedId
    private InscriptionIdentifier inscriptionIdentifier;

    @MapsId(ColumnConstants.TOURNAMENT_ID)
    @ManyToOne
    private Tournament tournament;

    @MapsId(ColumnConstants.USER_ID)
    @ManyToOne
    private User user;

    @OneToMany
    private List<Game> games;
}
