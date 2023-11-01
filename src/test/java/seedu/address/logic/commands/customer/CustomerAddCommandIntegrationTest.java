package seedu.address.logic.commands.customer;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDeliveries.getTypicalDeliveryBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Customer;
import seedu.address.testutil.CustomerBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class CustomerAddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalDeliveryBook(), new UserPrefs(), true);
    }

    @Test
    public void execute_newPerson_success() {
        Customer validCustomer = new CustomerBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getDeliveryBook(),
                new UserPrefs(), model.getUserLoginStatus());
        expectedModel.addPerson(validCustomer);

        assertCommandSuccess(new CustomerAddCommand(validCustomer), model,
                String.format(CustomerAddCommand.MESSAGE_SUCCESS, Messages.format(validCustomer)),
                expectedModel, true);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Customer customerInList = model.getAddressBook().getList().get(0);

        assertCommandFailure(new CustomerAddCommand(customerInList), model,
                CustomerAddCommand.MESSAGE_DUPLICATE_CUSTOMER);

    }

    @Test
    public void execute_newPersonLoggedOut_failure() {
        model.setLogoutSuccess();
        Customer validCustomer = new CustomerBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getDeliveryBook(),
                new UserPrefs(), model.getUserLoginStatus());
        expectedModel.addPerson(validCustomer);

        assertCommandFailure(new CustomerAddCommand(validCustomer), model, Messages.MESSAGE_USER_NOT_AUTHENTICATED);
    }

    @Test
    public void execute_duplicatePersonLoggedOut_throwsCommandException() {
        model.setLogoutSuccess();
        Customer customerInList = model.getAddressBook().getList().get(0);
        assertCommandFailure(new CustomerAddCommand(customerInList), model,
                Messages.MESSAGE_USER_NOT_AUTHENTICATED);
    }

}
