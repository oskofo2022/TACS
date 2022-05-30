package domain.requests.posts;

import domain.persistence.entities.enums.Language;
import domain.persistence.entities.enums.Visibility;
import domain.requests.RequestAnnotationTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class RequestPostTournamentTest extends RequestAnnotationTest<RequestPostTournament> {

    @Test
    public void nameNotSet() {
        this.request.setName(null);

        super.invalid("name", "NotNull");
    }

    @Test
    public void nameOverSized() {
        this.request.setName("a".repeat(61));

        super.invalid("name", "Size");
    }

    @Test
    public void nameDownSized() {
        this.request.setName("nam");

        super.invalid("name", "Size");
    }

    @Test
    public void LanguageNotSet() {
        this.request.setLanguage(null);

        super.invalid("language", "NotNull");
    }

    @Test
    public void VisibilityNotSet() {
        this.request.setVisibility(null);

        super.invalid("visibility", "NotNull");
    }

    @Test
    public void startDateIsBeforePresent() {
        this.request.setStartDate(LocalDate.now().minusDays(1));

        super.invalid("startDate", "FutureOrPresent");
    }

    @Test
    public void inconsistentLocalDateInterval() {
        this.request.setEndDate(LocalDate.now().minusDays(1));

        super.invalid("", "ConsistentLocalDateInterval");
    }

    @Override
    protected RequestPostTournament createValidRequest() {
        final var requestPostTournament = new RequestPostTournament();

        requestPostTournament.setLanguage(Language.SPANISH);
        requestPostTournament.setVisibility(Visibility.PUBLIC);
        requestPostTournament.setStartDate(LocalDate.now());
        requestPostTournament.setEndDate(LocalDate.now().plusDays(1));
        requestPostTournament.setName("Tournament");

        return requestPostTournament;
    }
}