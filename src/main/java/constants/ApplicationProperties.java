package constants;

public class ApplicationProperties {
    private static final String BIND_START_TOKEN = "${";
    private static final String BIND_END_TOKEN = "}";
    private static final String SEPARATOR = ".";

    public static class Wordle {
        private static final String NAME = BIND_START_TOKEN + "wordle";

        public static class  Cors {
            private static final String NAME = Wordle.NAME + SEPARATOR + "cors";

            public static class Origin {
                private static final String NAME = Cors.NAME + SEPARATOR + "origin";

                public static class Arguments {
                    public static final String PATTERN = Origin.NAME + SEPARATOR + "pattern" + BIND_END_TOKEN;
                }
            }
        }

        public static class  Jwt {
            private static final String NAME = Wordle.NAME + SEPARATOR + "jwt";

            public static class Arguments {
                public static final String EXPIRATION = Jwt.NAME + SEPARATOR + "expiration" + BIND_END_TOKEN;
                public static final String SECRET = Jwt.NAME + SEPARATOR + "secret" + BIND_END_TOKEN;
            }
        }

        public static class Cookie {
            private static final String NAME = Wordle.NAME + SEPARATOR + "cookie";
            public static class Arguments {
                public static final String EXPIRATION = Cookie.NAME + SEPARATOR + "expiration" + BIND_END_TOKEN;
                public static final String NAME = Cookie.NAME + SEPARATOR + "name" + BIND_END_TOKEN;
                public static final String PATH = Cookie.NAME + SEPARATOR + "path" + BIND_END_TOKEN;
            }
        }

        public static class Hash {
            private static final String NAME = Wordle.NAME + SEPARATOR + "hash";
            public static class Arguments {
                public static final String SECRET = Hash.NAME + SEPARATOR + "secret" + BIND_END_TOKEN;
                public static final String ITERATIONS = Hash.NAME + SEPARATOR + "iterations" + BIND_END_TOKEN;
                public static final String WIDTH = Hash.NAME + SEPARATOR + "width" + BIND_END_TOKEN;
            }
        }
    }
}
