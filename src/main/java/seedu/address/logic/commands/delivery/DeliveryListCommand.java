package seedu.address.logic.commands.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_USER_NOT_AUTHENTICATED;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DELIVERIES;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Predicate;

import seedu.address.logic.Sort;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.delivery.Date;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryStatus;

/**
 * Lists all deliveries in the delivery list to the user.
 */
public class DeliveryListCommand extends DeliveryCommand {
    public static final String COMMAND_WORD = DeliveryCommand.COMMAND_WORD + " " + "list";
    public static final String MESSAGE_SUCCESS = "Listed all Deliveries";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all deliveries in the delivery list.\n"
        + "Parameters: "
        + "STATUS (must be one of CREATED/SHIPPED/COMPLETED/CANCELLED) "
        + "SORT (must be one of ASC/DESC)\n"
        + "Example: " + COMMAND_WORD + " "
        + "CREATED ASC";
    private DeliveryStatus status;
    private final Integer customerId;
    private final Date deliveryDate;
    private Sort sortType = Sort.DESC;


    /**
     * Constructor for a DeliveryList Command with status filter and sort type.
     *
     * @param status   status to filter by.
     * @param sortType asc or desc.
     */
    public DeliveryListCommand(DeliveryStatus status, Integer customerId, Date deliveryDate, Sort sortType) {
        this.status = status;
        this.customerId = customerId;
        this.deliveryDate = deliveryDate;

        if (sortType != null) {
            this.sortType = sortType;
        }
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // User cannot perform this operation before logging in
        if (!model.getUserLoginStatus()) {
            throw new CommandException(MESSAGE_USER_NOT_AUTHENTICATED);
        }


        Predicate<Delivery> filters = PREDICATE_SHOW_ALL_DELIVERIES;


        if (status != null) {
            // filter by status
            filters = filters.and(delivery -> delivery.getStatus().equals(status));
        }

        if (customerId != null) {
            // filter by customer id
            filters = filters.and(delivery -> delivery.getCustomer().getCustomerId() == customerId);
        }

        if (deliveryDate != null) {
            // filter by expected delivery date
            filters = filters.and(delivery -> delivery.getDeliveryDate().equals(deliveryDate));
        }

        model.updateFilteredDeliveryList(filters);

        // sort by expected delivery date
        model.sortFilteredDeliveryList(
            sortType.equals(Sort.ASC) ? Comparator.comparing(Delivery::getDeliveryDate) : Comparator.comparing(
                    Delivery::getDeliveryDate)
                .reversed());

        //TODO: UI
        return new CommandResult(MESSAGE_SUCCESS, true);
    }

    @Override
    public boolean equals(Object other) {

        if (other == this) {
            return true;
        }

        if (!(other instanceof DeliveryListCommand)) {
            return false;
        }

        DeliveryListCommand otherDeliveryListCommand = (DeliveryListCommand) other;

        if (this.status != otherDeliveryListCommand.status) {
            return false;
        }

        if (!Objects.equals(this.customerId, otherDeliveryListCommand.customerId)) {
            return false;
        }

        if (this.deliveryDate != null && otherDeliveryListCommand.deliveryDate != null
            && !this.deliveryDate.equals(otherDeliveryListCommand.deliveryDate)) {
            return false;
        }

        return sortType.equals(otherDeliveryListCommand.sortType);
    }
}
