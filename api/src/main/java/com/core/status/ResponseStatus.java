package com.core.status;

public enum ResponseStatus {

    SUCCESS {
        public String toString() {
            return "1";

        }
    },

    FAIL {
        public String toString() {
            return "0";

        }
    }
}
