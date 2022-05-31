package domain.requests.common.gets.lists;

import domain.requests.gets.lists.RequestGetPagedListTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class RequestCommonGetListTournamentTest<TRequestCommonGetListTournament extends RequestCommonGetListTournament> extends RequestGetPagedListTest<TRequestCommonGetListTournament> {

    @Test
    @Override
    public void defaultSortBy() {
        assertEquals("name", this.request.defaultSortBy());
    }
}