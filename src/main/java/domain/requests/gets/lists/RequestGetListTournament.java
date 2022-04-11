package domain.requests.gets.lists;

import domain.repositories.entities.Visibility;
import domain.requests.common.gets.lists.RequestCommonGetListTournament;

public class RequestGetListTournament extends RequestCommonGetListTournament {
    private Visibility visibility;

    public Visibility getVisibility() {
        return visibility;
    }
    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }
}
