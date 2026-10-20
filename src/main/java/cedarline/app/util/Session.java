package cedarline.app.util;

import cedarline.app.models.StaffAccount;

/** Keeps track of who is currently signed in. */
public final class Session {

    private static StaffAccount current;

    private Session() {
    }

    public static void start(StaffAccount account) {
        current = account;
    }

    public static void end() {
        current = null;
    }

    public static StaffAccount getCurrent() {
        return current;
    }
}
