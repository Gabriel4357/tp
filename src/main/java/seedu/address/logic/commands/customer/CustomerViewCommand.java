package seedu.address.logic.commands.customer;

import static seedu.address.logic.Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_USER_NOT_AUTHENTICATED;

import java.util.Optional;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Customer;

/**
 * Represents a CustomerViewCommand which displays a single customer.
 */
public class CustomerViewCommand extends CustomerCommand {

    public static final String COMMAND_WORD = CustomerCommand.COMMAND_WORD + " view";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays a single customer.\n"
        + "Parameters: "
        + "CUSTOMER_ID\n"
        + "Example: " + COMMAND_WORD + " "
        + "1";

    public static final String MESSAGE_SUCCESS = "Customer displayed: %1$s";

    private final int customerId;

    /**
     * Creates a DeliveryViewCommand to display the specified {@code Delivery}.
     */
    public CustomerViewCommand(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // User cannot perform this operation before logging in
        if (!model.getUserLoginStatus()) {
            throw new CommandException(MESSAGE_USER_NOT_AUTHENTICATED);
        }

        Optional<Customer> customer = model.getCustomer(customerId);

        if (customer.isEmpty()) {
            throw new CommandException(MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
        }

        return new CommandResult(Messages.format(customer.get()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof CustomerViewCommand // instanceof handles nulls
            && customerId == ((CustomerViewCommand) other).customerId); // state check
    }
}
