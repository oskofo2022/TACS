package domain.responses.gets.lists;

public enum CharProximity {
    HIT {
        @Override
        public CharProximity getProximity(String word, char character, int index) {
            if (word.charAt(index) == character) {
                return this;
            }

            return CharProximity.CONTAINED.getProximity(word, character, index);
        }
    },
    CONTAINED {
        @Override
        public CharProximity getProximity(String word, char character, int index) {
            if (word.indexOf(character) != -1) {
                return this;
            }

            return CharProximity.NONE.getProximity(word, character, index);
        }
    },
    NONE {
        @Override
        public CharProximity getProximity(String word, char character, int index) {
            return this;
        }
    };

    public abstract CharProximity getProximity(String word, char character, int index);
}
