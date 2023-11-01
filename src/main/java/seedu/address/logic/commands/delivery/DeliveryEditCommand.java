package seedu.address.logic.commands.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DELIVERY_DATE;
import static seedu.address.logic.Messages.MESSAGE_USER_NOT_AUTHENTICATED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DELIVERIES;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyBook;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryDate;
import seedu.address.model.delivery.DeliveryName;
import seedu.address.model.delivery.DeliveryStatus;
import seedu.address.model.delivery.Note;
import seedu.address.model.delivery.OrderDate;
import seedu.address.model.person.Customer;

/**
 * Edits the details of an existing Delivery in the address book.
 */
public class DeliveryEditCommand extends DeliveryCommand {

    public static final String COMMAND_WORD = DeliveryCommand.COMMAND_WORD + " " + "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the delivery identified "
            + "by the index number used in the displayed delivery list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer)\n "
            + "At least one field must be specified."
            + "[" + PREFIX_NAME + " DELIVERY_NAME] "
            + "[" + PREFIX_CUSTOMER_ID + " CUSTOMER_ID] "
            + "[" + PREFIX_DATE + " DELIVERY_DATE] "
            + "[" + PREFIX_STATUS + " STATUS] "
            + "[" + PREFIX_NOTE + " NOTE]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + " 10 Chocolate Cakes "
            + PREFIX_DATE + " 2025-12-12";

    public static final String MESSAGE_EDIT_DELIVERY_SUCCESS = "Edited Delivery: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field must be provided!";
    private final Index targetIndex;
    private final DeliveryEditDescriptor deliveryEditDescriptor;

    /**
     * @param targetIndex of the delivery in the delivery list to edit
     * @param deliveryEditDescriptor details to edit the delivery with
     */
    public DeliveryEditCommand(Index targetIndex, DeliveryEditDescriptor deliveryEditDescriptor) {
        requireNonNull(targetIndex);
        requireNonNull(deliveryEditDescriptor);
        this.targetIndex = targetIndex;
        this.deliveryEditDescriptor = new DeliveryEditDescriptor(deliveryEditDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // User cannot perform this operation before logging in
        if (!model.getUserLoginStatus()) {
            throw new CommandException(MESSAGE_USER_NOT_AUTHENTICATED);
        }

        boolean found = false;
        Delivery editedDelivery = null;

        Delivery deliveryToEdit = model.getDeliveryUsingFilteredList(targetIndex.getOneBased());

        if (deliveryToEdit != null) {
            found = true;
            editedDelivery = createEditedDelivery(model, deliveryToEdit, deliveryEditDescriptor);
        }
        boolean isNull = deliveryToEdit == null || editedDelivery == null || !found;

        if (isNull) {
            throw new CommandException(Messages.MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX);
        } else {
            model.setDelivery(deliveryToEdit, editedDelivery);
            model.updateFilteredDeliveryList(PREDICATE_SHOW_ALL_DELIVERIES);
            return new CommandResult(String.format(MESSAGE_EDIT_DELIVERY_SUCCESS,
                    Messages.format(editedDelivery)), true);
        }
    }

    /**
     * Creates and returns a {@code Delivery} with the details of edited with {@code editDeliveryDescriptor}.
     * @param model {@code Model} which the delivery is edited in.
     * @param deliveryToEdit {@code Delivery} which the command edits.
     * @param deliveryEditDescriptor {@code editDeliveryDescriptor} details to edit the delivery with.
     */
    private static Delivery createEditedDelivery(Model model, Delivery deliveryToEdit, DeliveryEditDescriptor
            deliveryEditDescriptor) throws CommandException {

        assert deliveryToEdit != null;

        DeliveryName updatedDeliveryName =
                deliveryEditDescriptor.getDeliveryName().orElse(deliveryToEdit.getName());

        int customerId = deliveryEditDescriptor.getCustomerId().orElse(deliveryToEdit.getCustomerId());
        Customer updatedCustomer = null;

        OrderDate orderDate = deliveryToEdit.getOrderDate();

        DeliveryDate updatedDeliveryDate =
                deliveryEditDescriptor.getDeliveryDate().orElse(deliveryToEdit.getDeliveryDate());

        DeliveryStatus updatedDeliveryStatus =
                deliveryEditDescriptor.getStatus().orElse(deliveryToEdit.getStatus());

        Note updatedNote = deliveryEditDescriptor.getNote().orElse(deliveryToEdit.getNote());

        ReadOnlyBook<Customer> customerReadOnlyBook = model.getAddressBook();

        if (checkValidCustomer(model, customerId)) {
            updatedCustomer = customerReadOnlyBook.getById(customerId).get();
        } else {
            throw new CommandException(MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
        }
        if (!DeliveryDate.isValidDeliveryDate(updatedDeliveryDate.toString())) {
            throw new CommandException(MESSAGE_INVALID_DELIVERY_DATE);
        }
        return new Delivery(deliveryToEdit.getDeliveryId(), updatedDeliveryName, updatedCustomer, orderDate,
                updatedDeliveryDate,
                updatedDeliveryStatus,
                updatedNote);
    }

    private static boolean checkValidCustomer(Model model, int customerId) {
        return model.getCustomerUsingFilteredList(customerId) != null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeliveryEditCommand)) {
            return false;
        }

        DeliveryEditCommand otherEditCommand = (DeliveryEditCommand) other;
        return targetIndex.equals(otherEditCommand.targetIndex)
                && deliveryEditDescriptor.equals(otherEditCommand.deliveryEditDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", targetIndex)
                .add("deliveryEditDescriptor", deliveryEditDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the delivery with. Each non-empty field value will replace the
     * corresponding field value of the delivery.
     */
    public static class DeliveryEditDescriptor {
        private int deliveryId;
        private DeliveryName deliveryName;
        private Integer customerId = null;
        private OrderDate orderDate;
        private DeliveryDate deliveryDate;
        private DeliveryStatus status;
        private Note note;

        public DeliveryEditDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy is used internally.
         */
        public DeliveryEditDescriptor(DeliveryEditDescriptor toCopy) {
            setDeliveryId(toCopy.deliveryId);
            setDeliveryName(toCopy.deliveryName);
            setCustomerId(toCopy.customerId);
            setOrderDate(toCopy.orderDate);
            setDeliveryDate(toCopy.deliveryDate);
            setStatus(toCopy.status);
            setNote(toCopy.note);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(deliveryName, customerId, deliveryDate, status, note);
        }

        public Optional<Integer> getDeliveryId() {
            return Optional.ofNullable(deliveryId);
        }

        public void setDeliveryId(int deliveryId) {
            this.deliveryId = deliveryId;
        }

        public void setDeliveryName(DeliveryName deliveryName) {
            this.deliveryName = deliveryName;
        }

        public Optional<DeliveryName> getDeliveryName() {
            return Optional.ofNullable(deliveryName);
        }

        public void setCustomerId(Integer customerId) {
            this.customerId = customerId;
        }

        public Optional<Integer> getCustomerId() {
            return Optional.ofNullable(customerId);
        }

        public void setOrderDate(OrderDate orderDate) {
            this.orderDate = orderDate;
        }

        public Optional<OrderDate> getOrderDate() {
            return Optional.ofNullable(orderDate);
        }

        public void setDeliveryDate(DeliveryDate deliveryDate) {
            this.deliveryDate = deliveryDate;
        }

        public Optional<DeliveryDate> getDeliveryDate() {
            return Optional.ofNullable(deliveryDate);
        }

        public void setStatus(DeliveryStatus status) {
            this.status = status;
        }

        public Optional<DeliveryStatus> getStatus() {
            return Optional.ofNullable(status);
        }

        public void setNote(Note note) {
            this.note = note;
        }

        public Optional<Note> getNote() {
            return Optional.ofNullable(note);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof DeliveryEditDescriptor)) {
                return false;
            }

            DeliveryEditDescriptor otherEditDeliveryDescriptor = (DeliveryEditDescriptor) other;
            return Objects.equals(deliveryName, otherEditDeliveryDescriptor.deliveryName)
                    && Objects.equals(customerId, otherEditDeliveryDescriptor.customerId)
                    && Objects.equals(deliveryDate, otherEditDeliveryDescriptor.deliveryDate)
                    && Objects.equals(status, otherEditDeliveryDescriptor.status)
                    && Objects.equals(note, otherEditDeliveryDescriptor.note);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("Delivery Name", deliveryName)
                    .add("Customer Id", customerId)
                    .add("Delivery Date", deliveryDate)
                    .add("Status", status)
                    .add("Note", note)
                    .toString();
        }
    }
}



