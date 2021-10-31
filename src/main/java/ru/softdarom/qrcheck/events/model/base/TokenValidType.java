package ru.softdarom.qrcheck.events.model.base;

public enum TokenValidType {

    VALID {
        @Override
        public boolean isValid() {
            return true;
        }
    },
    EXPIRED {
        @Override
        public boolean isValid() {
            return false;
        }
    },
    INCORRECT {
        @Override
        public boolean isValid() {
            return false;
        }
    };


    public abstract boolean isValid();

}