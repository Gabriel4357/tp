package seedu.address.logic.commands.customer;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.person.Customer;
import seedu.address.model.user.User;
import seedu.address.testutil.CustomerBuilder;
import seedu.address.ui.ListItem;

public class CustomerAddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CustomerAddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Customer validCustomer = new CustomerBuilder().build();

        CommandResult commandResult = new CustomerAddCommand(validCustomer).execute(modelStub);

        assertEquals(String.format(CustomerAddCommand.MESSAGE_SUCCESS, Messages.format(validCustomer)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validCustomer), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Customer validCustomer = new CustomerBuilder().build();
        CustomerAddCommand customerAddCommand = new CustomerAddCommand(validCustomer);
        ModelStub modelStub = new ModelStubWithPerson(validCustomer);


        assertThrows(CommandException.class,
                CustomerAddCommand.MESSAGE_DUPLICATE_CUSTOMER, () -> customerAddCommand.execute(modelStub));

    }

    @Test
    public void execute_personAcceptedByModelLoggedOut_addFailure() throws Exception {
        ModelStubAcceptingPersonAddedLoggedOut modelStub = new ModelStubAcceptingPersonAddedLoggedOut();
        Customer validCustomer = new CustomerBuilder().build();

        CustomerAddCommand customerAddCommand = new CustomerAddCommand(validCustomer);

        assertThrows(CommandException.class,
                Messages.MESSAGE_USER_NOT_AUTHENTICATED, () -> customerAddCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Customer alice = new CustomerBuilder().withName("Alice").build();
        Customer bob = new CustomerBuilder().withName("Bob").build();
        CustomerAddCommand addAliceCommand = new CustomerAddCommand(alice);
        CustomerAddCommand addBobCommand = new CustomerAddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        CustomerAddCommand addAliceCommandCopy = new CustomerAddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        CustomerAddCommand customerAddCommand = new CustomerAddCommand(ALICE);
        String expected = CustomerAddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, customerAddCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUiListDelivery() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUiListCustomer() {

        }

        @Override
        public ObservableList<ListItem> getUiList() {
            return null;
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Customer customer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyBook<Customer> newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyBook<Customer> getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Customer> getCustomer(int id) {
            return Optional.empty();
        }

        @Override
        public Customer getCustomerUsingFilteredList(int id) {
            return null;
        }

        @Override
        public boolean hasPerson(Customer customer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Customer target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Customer target, Customer editedCustomer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Customer> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Customer> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getDeliveryBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDeliveryBookFilePath(Path deliveryBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDeliveryBook(ReadOnlyBook<Delivery> deliveryBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyBook<Delivery> getDeliveryBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Delivery> getDelivery(int id) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDelivery(Delivery delivery) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDelivery(Delivery target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDeliveryByCustomer(Customer target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDelivery(Delivery customer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDelivery(Delivery target, Delivery editedCustomer) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Delivery> getFilteredDeliveryList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Delivery> getSortedDeliveryList() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public Delivery getDeliveryUsingFilteredList(int id) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredDeliveryList(Predicate<Delivery> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredDeliveryList(Comparator<Delivery> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        public boolean getUserLoginStatus() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLoginSuccess() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLogoutSuccess() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean userMatches(User user) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public User getStoredUser() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void registerUser(User user) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLoggedInUser(User user) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteUser() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetPassword(User user) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateUser(User user) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getLoginStatus() {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Customer customer;

        ModelStubWithPerson(Customer customer) {
            requireNonNull(customer);
            this.customer = customer;
        }

        @Override
        public boolean hasPerson(Customer customer) {
            requireNonNull(customer);
            return this.customer.isSamePerson(customer);
        }

        @Override
        public boolean getUserLoginStatus() {
            return true;
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Customer> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Customer customer) {
            requireNonNull(customer);
            return personsAdded.stream().anyMatch(customer::isSamePerson);
        }

        @Override
        public void addPerson(Customer customer) {
            requireNonNull(customer);
            personsAdded.add(customer);
        }

        @Override
        public ReadOnlyBook<Customer> getAddressBook() {
            return new AddressBook();
        }

        @Override
        public boolean getUserLoginStatus() {
            return true;
        }
    }

    private class ModelStubAcceptingPersonAddedLoggedOut extends ModelStub {
        final ArrayList<Customer> personsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Customer customer) {
            requireNonNull(customer);
            return personsAdded.stream().anyMatch(customer::isSamePerson);
        }

        @Override
        public void addPerson(Customer customer) {
            requireNonNull(customer);
            personsAdded.add(customer);
        }

        @Override
        public ReadOnlyBook<Customer> getAddressBook() {
            return new AddressBook();
        }

        @Override
        public boolean getUserLoginStatus() {
            return false;
        }
    }

}
