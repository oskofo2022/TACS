package domain.persistence.constants;

public class ColumnConstants {
    public static class Names {
        public static final String USER_ID = "userId";
        public static final String USER_CREATOR_ID = "userCreatorId";
        public static final String TOURNAMENT_ID = "tournamentId";
        public static final String GAME_ID = "gameId";
        public static final String ID = "id";

        public static class TournamentsDailyGames {
            public static final String TOURNAMENT_ID = TournamentsDailyGames.BASE + "tournamentId";
            public static final String GAME_ID = TournamentsDailyGames.BASE + "gameId";
            private static final String BASE = "tournamentDailyGame_";
        }
    }
}
