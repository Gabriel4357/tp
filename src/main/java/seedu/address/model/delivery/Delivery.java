package seedu.address.model.delivery;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Address;
import seedu.address.model.person.Customer;

/**
 * Represents a Delivery in the address book.
 */
public class Delivery {
    // Delivery ID Generation
    private static int deliveryCount = 1;

    // Identity fields
    private int deliveryId;
    private DeliveryName name;
    private Customer customer;
    private OrderDate orderDate;
    private DeliveryDate deliveryDate;
    private DeliveryStatus status;
    private Address address; //The address of the delivery, which is automatically tied to Customer's address.
    private Note note;


    /**
     * Constructor for Delivery.
     * Every field must be present and not null.
     *
     * @param name         The name of the delivery.
     * @param customer     The customer who ordered the delivery.
     * @param orderDate    The date the delivery was ordered.
     * @param deliveryDate The date the delivery was delivered.
     * @param status       The status of the delivery.
     * @param note         The note of the delivery
     */
    public Delivery(DeliveryName name, Customer customer, OrderDate orderDate,
                    DeliveryDate deliveryDate,
                    DeliveryStatus status,
                    Note note) {
        this.deliveryId = Delivery.deliveryCount++;
        this.name = name;
        this.customer = customer;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.note = note;
        this.address = customer.getAddress();
    }


    /**
     * Constructor for Delivery.
     *
     * @param deliveryId   The ID of the delivery.
     * @param name         The name of the delivery.
     * @param customer     The customer who ordered the delivery.
     * @param orderDate    The date the delivery was ordered.
     * @param deliveryDate The date the delivery will be delivered.
     * @param status       The status of the delivery.
     */
    public Delivery(int deliveryId, DeliveryName name, Customer customer, OrderDate orderDate,
                    DeliveryDate deliveryDate,
                    DeliveryStatus status) {
        Delivery.deliveryCount = Math.max(deliveryCount, deliveryId + 1);
        this.deliveryId = deliveryId;
        this.name = name;
        this.customer = customer;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.address = customer.getAddress();
    }

    /**
     * Constructor for Delivery.
     * Every field must be present and not null.
     *
     * @param name         The name of the delivery.
     * @param customer     The customer who ordered the delivery.
     * @param orderDate    The date the delivery was ordered.
     * @param deliveryDate The date the delivery was delivered.
     * @param status       The status of the delivery.
     */
    public Delivery(DeliveryName name, Customer customer, OrderDate orderDate,
                    DeliveryDate deliveryDate,
                    DeliveryStatus status) {
        this.deliveryId = Delivery.deliveryCount++;
        this.name = name;
        this.customer = customer;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.address = customer.getAddress();
    }


    /**
     * Constructor for Delivery.
     *
     * @param deliveryId   The ID of the delivery.
     * @param name         The name of the delivery.
     * @param customer     The customer who ordered the delivery.
     * @param orderDate    The date the delivery was ordered.
     * @param deliveryDate The date the delivery was delivered.
     * @param status       The status of the delivery.
     * @param note         The note of the delivery.
     */
    public Delivery(int deliveryId, DeliveryName name, Customer customer, OrderDate orderDate,
                    DeliveryDate deliveryDate,
                    DeliveryStatus status, Note note) {
        Delivery.deliveryCount = Math.max(deliveryCount, deliveryId + 1);
        this.deliveryId = deliveryId;
        this.name = name;
        this.customer = customer;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.note = note;
        this.address = customer.getAddress();
    }

    public void setOrderDate(OrderDate orderDate) {
        this.orderDate = orderDate;
    }

    public void setDeliveryDate(DeliveryDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public DeliveryName getName() {
        return name;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getCustomerId() {
        return customer.getCustomerId();
    }

    public OrderDate getOrderDate() {
        return orderDate;
    }

    public DeliveryDate getDeliveryDate() {
        return deliveryDate;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public Address getAddress() {
        return address;
    }

    public Note getNote() {
        return note;
    }

    public static void setDeliveryCount(int deliveryCount) {
        Delivery.deliveryCount = deliveryCount;
    }

    /**
     * Returns true if both deliveries have the same identity and data fields.
     *
     * @param otherDelivery The other delivery to compare with.
     * @return True if both deliveries have the same identity and data fields.
     */
    public boolean isSameDelivery(Delivery otherDelivery) {
        if (otherDelivery == this) {
            return true;
        }

        return otherDelivery != null
                && otherDelivery.getDeliveryId() == getDeliveryId();
    }

    /**
     * Returns true if both deliveries have the same identity fields.
     *
     * @param other The other delivery to compare with.
     * @return True if both deliveries have the same identity fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Delivery)) {
            return false;
        }

        Delivery otherDelivery = (Delivery) other;

        return otherDelivery.deliveryId == deliveryId
                && otherDelivery.deliveryDate.equals(deliveryDate)
                && otherDelivery.name.equals(name)
                && otherDelivery.customer.equals(customer)
                && Objects.equals(otherDelivery.note, note)
                && otherDelivery.orderDate.equals(orderDate)
                && otherDelivery.status.equals(status)
                && otherDelivery.address.equals(address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliveryId, name, customer, orderDate, deliveryDate, address);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("deliveryId", deliveryId)
                .add("name", name)
                .add("customer", customer)
                .add("orderedAt", orderDate)
                .add("deliveredAt", deliveryDate)
                .add("address:", address)
                .add("note:", Optional.ofNullable(note)
                        .map(n -> String.format("\n Note:%s", n)).orElse(""))
                .toString();
    }
}
