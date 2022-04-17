package constants;

public class ApplicationProperties {
    private static final String BIND_START_TOKEN = "${";
    private static final String BIND_END_TOKEN = "}";
    private static final String SEPARATOR = ".";

    public static class Wordle {
        private static final String NAME = BIND_START_TOKEN + "wordle";
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

    public static class Spring {
        private static final String NAME = BIND_START_TOKEN + "spring";
        public static class Datasource {
            private static final String NAME = Spring.NAME + SEPARATOR + "datasource";
            public static class Arguments {
                public static final String URL = Datasource.NAME + SEPARATOR + "url" + BIND_END_TOKEN;
                public static final String USERNAME = Datasource.NAME + SEPARATOR + "username" + BIND_END_TOKEN;
                public static final String PASSWORD = Datasource.NAME + SEPARATOR + "password" + BIND_END_TOKEN;
                public static final String DRIVER_CLASS_NAME = Datasource.NAME + SEPARATOR + "driver-class-name" + BIND_END_TOKEN;
            }
        }

        public static class JPA {
            private static final String NAME = Spring.NAME + SEPARATOR + "jpa";

            public static class Arguments {
                public static final String OPEN_IN_VIEW = JPA.NAME + SEPARATOR + "open-in-view" + BIND_END_TOKEN;
                public static final String GENERATE_DDL = JPA.NAME + SEPARATOR + "generate-ddl" + BIND_END_TOKEN;
            }
            public static class Hibernate {
                private static final String NAME = JPA.NAME + SEPARATOR + "hibernate";
                public static class Arguments {
                    public static final String DDL_AUTO = Hibernate.NAME + SEPARATOR + "ddl-auto" + BIND_END_TOKEN;
                }
            }
            public static class Properties {
                private static final String NAME = JPA.NAME + SEPARATOR + "properties";
                public static class Hibernate {
                    private static final String NAME = Properties.NAME + SEPARATOR + "hibernate";
                    public static class Arguments {
                        public static final String DIALECT = Hibernate.NAME + SEPARATOR + "dialect" + BIND_END_TOKEN;
                    }
                }
            }
        }
    }
}
