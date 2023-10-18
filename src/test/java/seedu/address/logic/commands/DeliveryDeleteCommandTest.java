package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showDeliveryAtIndex;
import static seedu.address.testutil.TypicalDeliveries.getTypicalDeliveryBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.delivery.DeliveryDeleteCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.delivery.Delivery;

public class DeliveryDeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalDeliveryBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Delivery deliveryToDelete = model.getFilteredDeliveryList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeliveryDeleteCommand deleteCommand = new DeliveryDeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeliveryDeleteCommand.MESSAGE_DELETE_DELIVERY_SUCCESS,
                Messages.formatDelivery(deliveryToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getDeliveryBook(), new UserPrefs());
        expectedModel.deleteDelivery(deliveryToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDeliveryList().size() + 1);
        DeliveryDeleteCommand deleteCommand = new DeliveryDeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showDeliveryAtIndex(model, INDEX_FIRST_PERSON);

        Delivery deliveryToDelete = model.getFilteredDeliveryList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeliveryDeleteCommand deleteCommand = new DeliveryDeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeliveryDeleteCommand.MESSAGE_DELETE_DELIVERY_SUCCESS,
                Messages.formatDelivery(deliveryToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getDeliveryBook(), new UserPrefs());
        expectedModel.deleteDelivery(deliveryToDelete);
        showNoDelivery(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoDelivery(Model model) {
        model.updateFilteredDeliveryList(p -> false);

        assertTrue(model.getFilteredDeliveryList().isEmpty());
    }
}
