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

            public static class Configuration {
                private static final String NAME = Cors.NAME + SEPARATOR + "configuration";

                public static class Allowed {
                    private static final String NAME = Configuration.NAME + SEPARATOR + "allowed";

                    public static class Arguments {
                        public static final String ORIGINS = Allowed.NAME + SEPARATOR + "origins" + BIND_END_TOKEN;
                        public static final String HEADERS = Allowed.NAME + SEPARATOR + "headers" + BIND_END_TOKEN;
                        public static final String METHODS = Allowed.NAME + SEPARATOR + "methods" + BIND_END_TOKEN;
                    }
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
                public static final String SAME_SITE = Cookie.NAME + SEPARATOR + "same-site" + BIND_END_TOKEN;
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

        public static class Integrations {
            private static final String NAME = Wordle.NAME + SEPARATOR + "integrations";
            public static class Dictionaries {
                private static final String NAME = Integrations.NAME + SEPARATOR + "dictionaries";

                public static class English {
                    private static final String NAME = Dictionaries.NAME + SEPARATOR + "english";
                    public static class Arguments {
                        public static final String URL = English.NAME + SEPARATOR + "url" + BIND_END_TOKEN;
                    }
                }

                public static class Spanish {
                    private static final String NAME = Dictionaries.NAME + SEPARATOR + "spanish";
                    public static class Arguments {
                        public static final String URL = Spanish.NAME + SEPARATOR + "url" + BIND_END_TOKEN;
                    }
                    public static class App {
                        private static final String NAME = Spanish.NAME + SEPARATOR + "app";
                        public static class Arguments {
                            public static final String ID = App.NAME + SEPARATOR + "id" + BIND_END_TOKEN;
                            public static final String KEY = App.NAME + SEPARATOR + "key" + BIND_END_TOKEN;
                        }
                    }
                }
            }
        }
    }
}
