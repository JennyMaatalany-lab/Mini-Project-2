package cedarline.app.models;

import java.util.List;
import java.util.Optional;

/**
 * Hard-coded staff accounts, one per team member (no database in this mini project).
 */
public final class AccountRegistry {

    private static final List<StaffAccount> ACCOUNTS = List.of(
            new StaffAccount("HR2000", "2000HR", Department.HR),
            new StaffAccount("OD3000", "3000OD", Department.ORDERS),
            new StaffAccount("INV1000", "1000INV", Department.INVENTORY),
            new StaffAccount("CL4000", "4000CL", Department.CLIENTS)
    );

    private AccountRegistry() {
    }

    /** Username is not case sensitive, the password is. */
    public static Optional<StaffAccount> authenticate(String username, String password) {
        for (StaffAccount account : ACCOUNTS) {
            if (account.getUsername().equalsIgnoreCase(username)
                    && account.getPassword().equals(password)) {
                return Optional.of(account);
            }
        }
        return Optional.empty();
    }
}
