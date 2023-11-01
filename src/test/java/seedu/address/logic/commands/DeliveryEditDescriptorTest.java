package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_EDIT_CHIPS;
import static seedu.address.logic.commands.CommandTestUtil.DESC_EDIT_MILK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CUSTOMER_ID_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_2;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.delivery.DeliveryEditCommand.DeliveryEditDescriptor;
import seedu.address.testutil.DeliveryEditDescriptorBuilder;

public class DeliveryEditDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        DeliveryEditDescriptor descriptorWithSameValues = new DeliveryEditDescriptor(DESC_EDIT_MILK);
        assertTrue(DESC_EDIT_MILK.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_EDIT_CHIPS.equals(DESC_EDIT_CHIPS));

        // null -> returns false
        assertFalse(DESC_EDIT_CHIPS.equals(null));

        // different types -> returns false
        assertFalse(DESC_EDIT_CHIPS.equals(5));

        // different values -> returns false
        assertFalse(DESC_EDIT_CHIPS.equals(DESC_EDIT_MILK));

        // different name -> returns false
        DeliveryEditDescriptor editedChips =
                new DeliveryEditDescriptorBuilder(DESC_EDIT_CHIPS).build();
        assertFalse(DESC_EDIT_MILK.equals(editedChips));

        // different customer id -> returns false
        editedChips = new DeliveryEditDescriptorBuilder(DESC_EDIT_CHIPS).withCustomerId(VALID_CUSTOMER_ID_2).build();
        assertFalse(DESC_EDIT_MILK.equals(editedChips));

        // different delivery date -> returns false
        editedChips = new DeliveryEditDescriptorBuilder(DESC_EDIT_CHIPS)
                .withDeliveryDate(VALID_DELIVERY_DATE_2).build();
        assertFalse(DESC_EDIT_MILK.equals(editedChips));

    }

    @Test
    public void toStringMethod() {
        DeliveryEditDescriptor deliveryEditDescriptor = new DeliveryEditDescriptorBuilder(DESC_EDIT_MILK).build();
        String expected = DeliveryEditDescriptor.class.getCanonicalName() + "{Delivery Name="
                + deliveryEditDescriptor.getDeliveryName().orElse(null) + ", Customer Id="
                + deliveryEditDescriptor.getCustomerId().orElse(null) + ", Delivery Date="
                + deliveryEditDescriptor.getDeliveryDate().orElse(null) + ", Status="
                + deliveryEditDescriptor.getStatus().orElse(null) + ", Note="
                + deliveryEditDescriptor.getNote().orElse(null) + "}";
        assertEquals(expected, deliveryEditDescriptor.toString());
    }
}

