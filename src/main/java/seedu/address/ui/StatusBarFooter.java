package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private Label statusBarText;

    /**
     * Creates a {@code StatusBarFooter} with the given {@code Path}.
     */
    public StatusBarFooter(String loginStatus) {
        super(FXML);
        statusBarText.setText(loginStatus);
    }

    public void setStatusBarText(String loginStatus) {
        requireNonNull(loginStatus);
        statusBarText.setText(loginStatus);
    }

}
