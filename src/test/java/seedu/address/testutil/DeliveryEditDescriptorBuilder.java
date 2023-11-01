package seedu.address.testutil;

import seedu.address.logic.commands.delivery.DeliveryEditCommand.DeliveryEditDescriptor;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryDate;
import seedu.address.model.delivery.DeliveryName;
import seedu.address.model.delivery.DeliveryStatus;
import seedu.address.model.delivery.Note;
import seedu.address.model.delivery.OrderDate;


/**
 * A utility class to help with building DeliveryEditDescriptor objects.
 */
public class DeliveryEditDescriptorBuilder {
    private DeliveryEditDescriptor descriptor;

    public DeliveryEditDescriptorBuilder() {
        descriptor = new DeliveryEditDescriptor();
    }

    public DeliveryEditDescriptorBuilder(DeliveryEditDescriptor descriptor) {
        this.descriptor = new DeliveryEditDescriptor(descriptor);
    }

    /**
     * Returns an {@code DeliveryEditDescriptor} with fields containing {@code delivery}'s details
     */
    public DeliveryEditDescriptorBuilder(Delivery delivery) {
        descriptor = new DeliveryEditDescriptor();
        descriptor.setDeliveryName(delivery.getName());
        descriptor.setDeliveryDate(delivery.getDeliveryDate());
        descriptor.setCustomerId(delivery.getCustomerId());
        descriptor.setOrderDate(delivery.getOrderDate());
        descriptor.setStatus(delivery.getStatus());
        descriptor.setDeliveryId(delivery.getDeliveryId());
        descriptor.setNote(delivery.getNote());
    }

    /**
     * Sets the {@code DeliveryName} of the {@code DeliveryEditDescriptor} that we are building.
     */
    public DeliveryEditDescriptorBuilder withDeliveryName(String deliveryName) {
        descriptor.setDeliveryName(new DeliveryName(deliveryName));
        return this;
    }

    /**
     * Sets the customer id of the {@code DeliveryEditDescriptor} that we are building.
     */
    public DeliveryEditDescriptorBuilder withCustomerId(int customerId) {
        descriptor.setCustomerId(customerId);
        return this;
    }

    /**
     * Sets the {@code DeliveryDate} of the {@code DeliveryEditDescriptor} that we are building.
     */
    public DeliveryEditDescriptorBuilder withDeliveryDate(String deliveryDate) {
        descriptor.setDeliveryDate(new DeliveryDate(deliveryDate));
        return this;
    }
    /**
     * Sets the {@code OrderDate} of the {@code DeliveryEditDescriptor} that we are building.
     */
    public DeliveryEditDescriptorBuilder withOrderDate(String orderDate) {
        descriptor.setOrderDate(new OrderDate(orderDate));
        return this;
    }

    /**
     * Sets the {@code DeliveryStatus} of the {@code DeliveryEditDescriptor} that we are building.
     */
    public DeliveryEditDescriptorBuilder withStatus(DeliveryStatus status) {
        descriptor.setStatus(status);
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code DeliveryEditDescriptor} that we are building.
     */
    public DeliveryEditDescriptorBuilder withNote(String note) {
        descriptor.setNote(new Note(note));
        return this;
    }

    /**
     * Sets the delivery id of the {@code DeliveryEditDescriptor} that we are building.
     */
    public DeliveryEditDescriptorBuilder withDeliveryId(int deliveryId) {
        descriptor.setDeliveryId(deliveryId);
        return this;
    }

    public DeliveryEditDescriptor build() {
        return descriptor;
    }
}



