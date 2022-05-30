package domain.persistence.entities;

import domain.persistence.constants.ColumnConstants;
import domain.persistence.constants.TableConstants;
import domain.persistence.constants.TypeConstants;
import domain.persistence.entities.enums.Language;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = TableConstants.Names.MATCHES)
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = TypeConstants.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = ColumnConstants.Names.USER_ID, referencedColumnName = ColumnConstants.Names.ID)
    private User user;

    @NotNull
    private int guessesCount;

    @NotNull
    private LocalDate date;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Language language;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getGuessesCount() {
        return guessesCount;
    }

    public void setGuessesCount(int guessesCount) {
        this.guessesCount = guessesCount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
