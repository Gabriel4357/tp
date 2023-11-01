package seedu.address.model;

import java.nio.file.Path;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.user.User;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getAddressBookFilePath();

    Path getDeliveryBookFilePath();

    Path getAuthenticationFilePath();

    User getStoredUser();

}
