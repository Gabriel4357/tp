package seedu.address.logic.commands.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_USER_NOT_AUTHENTICATED;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.delivery.Delivery;

/**
 * Deletes a delivery identified using it's displayed index from the address book.
 */
public class DeliveryDeleteCommand extends DeliveryCommand {

    public static final String COMMAND_WORD = DeliveryCommand.COMMAND_WORD + " " + "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the delivery identified by the index number used in the displayed delivery list.\n"
            + "Parameters: DELIVERY_ID (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_DELIVERY_SUCCESS = "Deleted Delivery: %1$s";

    private final Index targetIndex;

    public DeliveryDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // User cannot perform this operation before logging in
        if (!model.getUserLoginStatus()) {
            throw new CommandException(MESSAGE_USER_NOT_AUTHENTICATED);
        }

        Delivery deliveryToDelete = model.getDeliveryUsingFilteredList(targetIndex.getOneBased());

        if (deliveryToDelete != null) {
            model.deleteDelivery(deliveryToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_DELIVERY_SUCCESS,
                    Messages.format(deliveryToDelete)), true);
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeliveryDeleteCommand)) {
            return false;
        }

        DeliveryDeleteCommand otherDeleteCommand = (DeliveryDeleteCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
