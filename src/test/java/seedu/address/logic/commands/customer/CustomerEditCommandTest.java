package seedu.address.logic.commands.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalDeliveries.getTypicalDeliveryBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.customer.CustomerEditCommand.CustomerEditDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.DeliveryBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Customer;
import seedu.address.testutil.CustomerBuilder;
import seedu.address.testutil.CustomerEditDescriptorBuilder;


/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class CustomerEditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(),
            getTypicalDeliveryBook(), new UserPrefs(), true);

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {

        Customer editedCustomer = new CustomerBuilder().build();
        CustomerEditCommand.CustomerEditDescriptor descriptor = new CustomerEditDescriptorBuilder(editedCustomer)
                .build();

        CustomerEditCommand editCommand = new CustomerEditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(CustomerEditCommand.MESSAGE_EDIT_CUSTOMER_SUCCESS,
            Messages.format(editedCustomer));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new DeliveryBook(model.getDeliveryBook()),
                new UserPrefs(), model.getUserLoginStatus());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedCustomer);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, true);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Customer lastCustomer = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());


        CustomerBuilder personInList = new CustomerBuilder(lastCustomer);
        Customer editedCustomer = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();

        CustomerEditCommand.CustomerEditDescriptor descriptor = new CustomerEditDescriptorBuilder()
                .withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();


        CustomerEditCommand editCommand = new CustomerEditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(CustomerEditCommand.MESSAGE_EDIT_CUSTOMER_SUCCESS,
            Messages.format(editedCustomer));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new DeliveryBook(model.getDeliveryBook()),
                new UserPrefs(), model.getUserLoginStatus());
        expectedModel.setPerson(lastCustomer, editedCustomer);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, true);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {

        CustomerEditCommand editCommand = new CustomerEditCommand(INDEX_FIRST_PERSON, new CustomerEditDescriptor());

        Customer editedCustomer = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(CustomerEditCommand.MESSAGE_EDIT_CUSTOMER_SUCCESS,
            Messages.format(editedCustomer));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new DeliveryBook(model.getDeliveryBook()),
                new UserPrefs(), model.getUserLoginStatus());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, true);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Customer customerInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Customer editedCustomer = new CustomerBuilder(customerInFilteredList)
                .withCustomerId(customerInFilteredList.getCustomerId()).withName(VALID_NAME_BOB).build();
        CustomerEditCommand editCommand = new CustomerEditCommand(INDEX_FIRST_PERSON,
                new CustomerEditDescriptorBuilder().withCustomerId(customerInFilteredList.getCustomerId())
                        .withName(VALID_NAME_BOB).build());


        String expectedMessage = String.format(CustomerEditCommand.MESSAGE_EDIT_CUSTOMER_SUCCESS,
            Messages.format(editedCustomer));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new DeliveryBook(model.getDeliveryBook()),
                new UserPrefs(), model.getUserLoginStatus());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedCustomer);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, true);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Customer firstCustomer = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        CustomerEditCommand.CustomerEditDescriptor descriptor = new CustomerEditDescriptorBuilder(firstCustomer)
                .build();
        CustomerEditCommand editCommand = new CustomerEditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, CustomerEditCommand.MESSAGE_DUPLICATE_CUSTOMER);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        Customer customerInList = model.getAddressBook().getList().get(INDEX_SECOND_PERSON.getZeroBased());
        CustomerEditCommand editCommand = new CustomerEditCommand(INDEX_FIRST_PERSON,
            new CustomerEditDescriptorBuilder(customerInList).build());

        assertCommandFailure(editCommand, model, CustomerEditCommand.MESSAGE_DUPLICATE_CUSTOMER);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        CustomerEditCommand.CustomerEditDescriptor descriptor = new CustomerEditDescriptorBuilder()
                .withName(VALID_NAME_BOB).build();
        CustomerEditCommand editCommand = new CustomerEditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getList().size());

        CustomerEditCommand editCommand = new CustomerEditCommand(outOfBoundIndex,
            new CustomerEditDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredListLoggedOut_failure() {
        // set state of model to be logged out
        model.setLogoutSuccess();

        Customer editedCustomer = new CustomerBuilder().build();

        CustomerEditCommand.CustomerEditDescriptor descriptor = new CustomerEditDescriptorBuilder(editedCustomer)
                .build();
        CustomerEditCommand editCommand = new CustomerEditCommand(INDEX_FIRST_PERSON, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_USER_NOT_AUTHENTICATED);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredListLoggedOut_failure() {
        // set state of model to be logged out
        model.setLogoutSuccess();
        // customerId = 1
        Index indexLastPerson = Index.fromOneBased(1);

        CustomerEditCommand.CustomerEditDescriptor descriptor = new CustomerEditDescriptorBuilder().withCustomerId(1)
                .withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).build();
        CustomerEditCommand editCommand = new CustomerEditCommand(indexLastPerson, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_USER_NOT_AUTHENTICATED);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredListLoggedOut_failure() {
        // set state of model to be logged out
        model.setLogoutSuccess();

        CustomerEditCommand editCommand = new CustomerEditCommand(INDEX_SECOND_PERSON, new CustomerEditDescriptor());


        assertCommandFailure(editCommand, model, Messages.MESSAGE_USER_NOT_AUTHENTICATED);
    }

    @Test
    public void equals() {
        final CustomerEditCommand standardCommand = new CustomerEditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        CustomerEditDescriptor copyDescriptor = new CustomerEditDescriptor(DESC_AMY);
        CustomerEditCommand commandWithSameValues = new CustomerEditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new CustomerEditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new CustomerEditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);

        CustomerEditDescriptor customerEditDescriptor = new CustomerEditDescriptor();
        CustomerEditCommand editCommand = new CustomerEditCommand(index, customerEditDescriptor);
        String expected = CustomerEditCommand.class.getCanonicalName() + "{index=" + index
                + ", customerEditDescriptor=" + customerEditDescriptor + "}";

        assertEquals(expected, editCommand.toString());
    }

}
