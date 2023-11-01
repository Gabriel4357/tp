package seedu.address.logic.commands.customer;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_USER_NOT_AUTHENTICATED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CUSTOMERS;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

/**
 * Edits the details of an existing customer in the address book.
 */
public class CustomerEditCommand extends CustomerCommand {

    public static final String COMMAND_WORD = CustomerCommand.COMMAND_WORD + " " + "edit";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the customer identified "
            + "by the index number used in the displayed customer list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer)\n "
            + "At least one field must be specified."
            + "[" + PREFIX_NAME + " NAME] "
            + "[" + PREFIX_PHONE + " PHONE] "
            + "[" + PREFIX_EMAIL + " EMAIL] "
            + "[" + PREFIX_ADDRESS + " ADDRESS]\n "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + " 91234567 "
            + PREFIX_EMAIL + " johndoe@example.com";

    public static final String MESSAGE_EDIT_CUSTOMER_SUCCESS = "Edited Customer: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CUSTOMER = "This customer already exists in the address book.";

    private final Index targetIndex;
    private final CustomerEditDescriptor customerEditDescriptor;

    /**
     * @param targetIndex of the person in the filtered person list to edit
     * @param customerEditDescriptor details to edit the person with
     */
    public CustomerEditCommand(Index targetIndex, CustomerEditDescriptor customerEditDescriptor) {
        requireNonNull(targetIndex);
        requireNonNull(customerEditDescriptor);

        this.targetIndex = targetIndex;
        this.customerEditDescriptor = new CustomerEditDescriptor(customerEditDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // User cannot perform this operation before logging in
        if (!model.getUserLoginStatus()) {
            throw new CommandException(MESSAGE_USER_NOT_AUTHENTICATED);
        }

        boolean found = false;
        Customer editedCustomer = null;

        Customer customerToEdit = model.getCustomerUsingFilteredList(targetIndex.getOneBased());

        if (customerToEdit != null) {
            found = true;
            editedCustomer = createEditedCustomer(customerToEdit, customerEditDescriptor);
        }

        boolean isNull = customerToEdit == null || editedCustomer == null || !found;

        if (isNull) {
            throw new CommandException(Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
        } else if (!customerToEdit.isSamePerson(editedCustomer) && model.hasPerson(editedCustomer)) {
            throw new CommandException(MESSAGE_DUPLICATE_CUSTOMER);
        } else {
            model.setPerson(customerToEdit, editedCustomer);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_CUSTOMERS);
            return new CommandResult(String.format(MESSAGE_EDIT_CUSTOMER_SUCCESS,
                    Messages.format(editedCustomer)), true);
        }

    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code customerEditDescriptor}.
     */

    private static Customer createEditedCustomer(Customer customerToEdit,
                                                 CustomerEditDescriptor customerEditDescriptor) {

        assert customerToEdit != null;

        Name updatedName = customerEditDescriptor.getName().orElse(customerToEdit.getName());
        Phone updatedPhone = customerEditDescriptor.getPhone().orElse(customerToEdit.getPhone());
        Email updatedEmail = customerEditDescriptor.getEmail().orElse(customerToEdit.getEmail());
        Address updatedAddress = customerEditDescriptor.getAddress().orElse(customerToEdit.getAddress());

        return new Customer(customerToEdit.getCustomerId(), updatedName, updatedPhone,
                updatedEmail, updatedAddress);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CustomerEditCommand)) {
            return false;
        }

        CustomerEditCommand otherEditCommand = (CustomerEditCommand) other;
        return targetIndex.equals(otherEditCommand.targetIndex)
            && customerEditDescriptor.equals(otherEditCommand.customerEditDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("index", targetIndex)
            .add("customerEditDescriptor", customerEditDescriptor)
            .toString();
    }


    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class CustomerEditDescriptor {
        private int customerId;
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;

        public CustomerEditDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public CustomerEditDescriptor(CustomerEditDescriptor toCopy) {
            setCustomerId(toCopy.customerId);
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address);
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof CustomerEditDescriptor)) {
                return false;
            }

            CustomerEditDescriptor otherCustomerEditDescriptor = (CustomerEditDescriptor) other;
            return Objects.equals(name, otherCustomerEditDescriptor.name)
                && Objects.equals(phone, otherCustomerEditDescriptor.phone)
                && Objects.equals(email, otherCustomerEditDescriptor.email)
                && Objects.equals(address, otherCustomerEditDescriptor.address);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .toString();
        }
    }
}
