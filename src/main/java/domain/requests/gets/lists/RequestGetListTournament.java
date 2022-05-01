package domain.requests.gets.lists;

import constants.RSQLConstants;
import domain.persistence.entities.enums.Visibility;
import domain.requests.common.gets.lists.RequestCommonGetListTournament;

public class RequestGetListTournament extends RequestCommonGetListTournament {

    private Visibility visibility;

    private Long userCreatorId;

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public Long getUserCreatorId() {
        return userCreatorId;
    }

    public void setUserCreatorId(Long userCreatorId) {
        this.userCreatorId = userCreatorId;
    }

    @Override
    protected void addRestrictions() {
        super.addRestrictions();
        this.addRestriction(RSQLConstants.Filters.getEqual("visibility"), this.visibility);
        this.addRestriction(RSQLConstants.Filters.getEqual("userCreator.id"), this.userCreatorId);
    }
}
