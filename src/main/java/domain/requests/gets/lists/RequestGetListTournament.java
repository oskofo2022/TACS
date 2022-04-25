package domain.requests.gets.lists;

import constants.RSQLConstants;
import domain.requests.common.gets.lists.RequestCommonGetListTournament;

public class RequestGetListTournament extends RequestCommonGetListTournament {

    private long idUserCreator;

    public long getIdUserCreator() {
        return idUserCreator;
    }

    public void setIdUserCreator(long idUser) {
        this.idUserCreator = idUser;
    }

    @Override
    protected void addRestrictions() {
        super.addRestrictions();
        this.addRestriction(RSQLConstants.Filters.getEqual("id"), idUserCreator);
    }
}
