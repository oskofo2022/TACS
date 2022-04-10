package constants;

public class UriConstants {

    public static class Users {
        public static final String URL = "users";
        public static final String ID = "/{userId}";

        public static class Myself {
            public static final String URL = Users.URL + "/myself";

            public static class Inscriptions {
                private static final String URL = Myself.URL + "/inscriptions";

                public static class Tournaments {
                    public static final String URL = Inscriptions.URL + "/tournaments";
                    public static final String ID = "/{tournamentId}";

                    public static class Games {
                        public static final String URL_ALL = Tournaments.URL + "/games";
                        public static final String URL = Tournaments.URL + Tournaments.ID + "/games";
                        public static final String ID = "/{gameId}";
                    }
                }
            }
        }

        // /api/users/tournaments
        public static class Tournaments {
            public static final String URL = Users.URL + "/tournaments";
            public static final String ID = "/{tournamentId}";
        }
    }

    // /api/tournaments
    public static class Tournaments {
        public static final String URL = "tournaments";
        public static final String ID = "/{tournamentId}";
        public static final String PUBLIC = "/public";
        public static final String TOURNAMENTPUBLIC = Tournaments.PUBLIC + Tournaments.ID;

        public static class UserTournament{
            public static final String INSCRIPTION = "/inscriptions";
            public static final String MYSELF = "/myself";
            public static final String URL = Tournaments.TOURNAMENTPUBLIC + INSCRIPTION + MYSELF;
        }
    }

    public static class Dictionaries {
        public static final String URL = "dictionaries";
        public static final String LANGUAGE = "/{language}";

        public static class Words {
            public static final String URL = Dictionaries.URL + Dictionaries.LANGUAGE + "/words";
            public static final String Word = "/{word}";
        }
    }

    public static class Logins {
        public static final String URL = "logins";
    }

    public static class Logouts {
        public static final String URL = "logouts";
    }
}
