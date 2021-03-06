package constants;

import domain.persistence.entities.enums.Language;
import domain.security.UriComparer;
import domain.security.UriWhitelist;
import domain.security.filters.AuthenticationAdapterRequestFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class UriConstants {
    public static class AntMatchers {
        public static String[] getAnonymousWhitelist() {
            return new String[]{
            };
        }

        public static String[] getPermitAllWhitelist() {
            return new String[] {
                "/logins",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/tournaments/public",
                "/dictionaries/**",
                "/games/**",
                "/users",
            };
        }
    }

    public static class AuthenticationAdapterRequestFilter {
        public static UriWhitelist[] getPermitAllWhitelist() {
            return new UriWhitelist[] {
                    new UriWhitelist("/logins"),
                    new UriWhitelist("/v3/api-docs"),
                    new UriWhitelist("/swagger-ui"),
                    new UriWhitelist("/tournaments/public"),
                    new UriWhitelist("/dictionaries"),
                    new UriWhitelist("/games"),
                    new UriWhitelist("/api/users", List.of(RequestMethod.POST), UriComparer.EQUAL)
            };
        }

        public static Stream<Pattern> getExcludeExceptionsWhitelist() {
            return Arrays.stream(new String[] {
                    "/api/tournaments/public/[^/]+/inscriptions/myself/?",
            }).map(Pattern::compile);
        }
    }

    public static final String DELIMITER = "/";

    public static class Games {
        private static final String URL = "/games";
        public static class Language {
            private static final String URL = Games.URL + "/{language}";
            public static class Helps {
                public static final String URL = Language.URL + "/helps";
            }
        }

    }

    public static class Queries {
        public static String ID = "id={id}";
    }

    public static class Users {
        public static final String URL = "users";
        public static final String ID = "/{userId}";

        public static class Myself {
            public static final String URL = Users.URL + "/myself";

            public static class Matches {
                public static final String URL = Myself.URL + "/matches";
                public static class Today {
                    public static final String URL = Matches.URL + "/today";
                }
            }

            public static class Inscriptions {
                private static final String URL = Myself.URL + "/inscriptions";

                public static class Tournaments {
                    public static final String URL = Inscriptions.URL + "/tournaments";
                    public static final String ID = "/{tournamentId}";

                    public static class One {
                        private static final String URL = Tournaments.URL + Tournaments.ID;

                        public static class Positions {
                            public static final String URL = One.URL + "/positions";
                        }
                    }

                    public static class Positions {
                        public static final String URL = Tournaments.URL + "/positions";
                    }

                    public static class Queries {
                        public static String ID = "tournamentId={tournamentId}";
                    }
                }
            }

            public static class Tournaments {
                public static final String URL = Myself.URL + "/tournaments";
            }
        }
    }

    public static class Tournaments {
        public static final String URL = "tournaments";
        public static final String ID = "/{tournamentId}";

        public static class Inscriptions {
            public static final String URL = Tournaments.URL + Tournaments.ID +  "/inscriptions";
        }

        public static class Public {
            public static final String URL = Tournaments.URL + "/public";
            public static final String SUBTYPE = "/public";

            public static class Inscriptions {
                public static final String URL = Public.URL + Tournaments.ID +  "/inscriptions";
                public static final String CURRENT_USER = "/myself";
            }
        }
    }

    public static class Dictionaries {
        public static final String URL = "dictionaries";
        public static final String LANGUAGE = "/{language}";
        public static final String Word = "/{word}";

        public static class English {
            private static final String URL = Dictionaries.URL + "/ENGLISH";
            public static class Words {
                public static final String URL = English.URL + "/words";
            }
        }

        public static class Spanish {
            private static final String URL = Dictionaries.URL + "/SPANISH";
            public static class Words {
                public static final String URL = Spanish.URL + "/words";
            }
        }
    }

    public static class Logins {
        public static final String URL = "logins";
    }
}
