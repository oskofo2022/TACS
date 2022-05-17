package domain.requests.gets.lists;

import constants.RSQLConstants;
import domain.persistence.entities.enums.Visibility;
import domain.requests.common.gets.lists.RequestCommonGetListTournament;
import domain.validators.RegexSortBy;

@RegexSortBy(allowedValues = { "name", "state", "language", "id", "startDate", "endDate" })
public class RequestGetListPublicTournament extends RequestCommonGetListTournament {

    @Override
    protected void addRestrictions() {
        super.addRestrictions();
        this.addRestriction(RSQLConstants.Filters.getEqual("visibility"), Visibility.PUBLIC);
    }
}
