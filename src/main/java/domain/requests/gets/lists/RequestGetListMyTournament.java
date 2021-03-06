package domain.requests.gets.lists;

import com.fasterxml.jackson.annotation.JsonIgnore;
import constants.RSQLConstants;
import domain.persistence.entities.enums.Visibility;
import domain.requests.common.gets.lists.RequestCommonGetListTournament;
import domain.validators.RegexSortBy;

import java.util.UUID;

@RegexSortBy(allowedValues = { "name", "state", "language", "id", "startDate", "endDate", "visibility" })
public class RequestGetListMyTournament extends RequestCommonGetListTournament {
    private Visibility visibility;

    @JsonIgnore
    private UUID userCreatorId;

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public UUID getUserCreatorId() {
        return userCreatorId;
    }

    public void setUserCreatorId(UUID userCreatorId) {
        this.userCreatorId = userCreatorId;
    }

    @Override
    protected void addRestrictions() {
        super.addRestrictions();
        this.addRestriction(RSQLConstants.Filters.getEqual("userCreator.id"), this.userCreatorId);
        this.addRestriction(RSQLConstants.Filters.getEqual("visibility"), this.visibility);
    }
}
