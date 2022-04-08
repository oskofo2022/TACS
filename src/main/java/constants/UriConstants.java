package constants;

public class UriConstants {

    public static class Users {
        public static final String URL = "users";
        public static final String ID = "/{userId}";

        public static class Tournaments {
            private static final String URL = Users.URL + "/tournaments";
            public static final String ID = "/{tournamentId}";
        }
    }

    public static class Myself {
        public static final String URL = "myself";
        public static class Inscriptions {
            public static final String URL = Myself.URL + "/inscriptions";
            public static class Games {
                public static final String URL = Inscriptions.URL + "/games";
            }
        }
    }
}
