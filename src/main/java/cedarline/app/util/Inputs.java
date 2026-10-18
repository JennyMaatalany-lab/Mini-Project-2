package cedarline.app.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

/** Validation helpers shared by the form controllers. All of them expect trimmed text. */
public final class Inputs {

    private static final Pattern MONEY = Pattern.compile("\\d{1,9}(\\.\\d{1,2})?");
    private static final Pattern WHOLE_NUMBER = Pattern.compile("\\d{1,6}");
    private static final Pattern EMAIL = Pattern.compile("[^@\\s]+@[^@\\s]+\\.[^@\\s]+");
    private static final Pattern PHONE = Pattern.compile("\\+?[0-9 ]{7,16}");

    private Inputs() {
    }

    public static boolean isMoney(String text) {
        return MONEY.matcher(text).matches();
    }

    public static boolean isWholeNumber(String text) {
        return WHOLE_NUMBER.matcher(text).matches();
    }

    public static boolean isEmail(String text) {
        return EMAIL.matcher(text).matches();
    }

    public static boolean isPhone(String text) {
        return PHONE.matcher(text).matches();
    }

    /** Accepts dates written as yyyy-mm-dd, e.g. 2026-10-18. */
    public static boolean isIsoDate(String text) {
        try {
            LocalDate.parse(text);
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }
}
