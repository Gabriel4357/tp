package seedu.address.logic.commands;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MILK;
import static seedu.address.logic.commands.CommandTestUtil.DESC_RICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CUSTOMER_ID_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_JAMES_MILK;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.delivery.DeliveryAddCommand.DeliveryAddDescriptor;
import seedu.address.testutil.DeliveryAddDescriptorBuilder;
public class DeliveryAddDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        DeliveryAddDescriptor descriptorWithSameValues = new DeliveryAddDescriptor(DESC_MILK);
        assertTrue(DESC_MILK.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_MILK.equals(DESC_MILK));

        // null -> returns false
        assertFalse(DESC_MILK.equals(null));

        // different types -> returns false
        assertFalse(DESC_MILK.equals(5));

        // different values -> returns false
        assertFalse(DESC_MILK.equals(DESC_RICE));

        // different name -> returns false
        DeliveryAddDescriptor editedMilk =
                new DeliveryAddDescriptorBuilder(DESC_MILK).withDeliveryName(VALID_NAME_JAMES_MILK).build();
        assertFalse(DESC_MILK.equals(editedMilk));

        // different customer id -> returns false
        editedMilk = new DeliveryAddDescriptorBuilder(DESC_MILK).withCustomerId(VALID_CUSTOMER_ID_2).build();
        assertFalse(DESC_MILK.equals(editedMilk));

        // different expected delivery date -> returns false
        editedMilk = new DeliveryAddDescriptorBuilder(DESC_MILK).withDeliveryDate(VALID_DELIVERY_DATE_2).build();
        assertFalse(DESC_MILK.equals(editedMilk));

    }

    @Test
    public void toStringMethod() {
        DeliveryAddDescriptor deliveryAddDescriptor = new DeliveryAddDescriptorBuilder(DESC_MILK).build();
        String expected = DeliveryAddDescriptor.class.getCanonicalName() + "{name="
                + deliveryAddDescriptor.getDeliveryName().orElse(null) + ", customer="
                + deliveryAddDescriptor.getCustomerId().orElse(null) + ", deliveredAt="
                + deliveryAddDescriptor.getDate().orElse(null) + "}";
        assertEquals(expected, deliveryAddDescriptor.toString());
    }
}
