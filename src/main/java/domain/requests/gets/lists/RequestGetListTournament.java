package domain.requests.gets.lists;

import constants.RSQLConstants;
import domain.persistence.entities.enums.Visibility;
import domain.requests.common.gets.lists.RequestCommonGetListTournament;

public class RequestGetListTournament extends RequestCommonGetListTournament {

    private Visibility visibility;

    private long idUserCreator;

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public long getIdUserCreator() {
        return idUserCreator;
    }

    public void setIdUserCreator(long idUserCreator) {
        this.idUserCreator = idUserCreator;
    }

    @Override
    protected void addRestrictions() {
        super.addRestrictions();
        this.addRestriction(RSQLConstants.Filters.getEqual("visibility"), this.visibility);
        this.addRestriction(RSQLConstants.Filters.getEqual("id"), this.idUserCreator);
    }
}
