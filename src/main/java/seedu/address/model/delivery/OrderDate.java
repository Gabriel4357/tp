package seedu.address.model.delivery;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Delivery's date in the address book.
 */
public class OrderDate extends Date {

    /**
     * Constructs a {@code DeliveryDate}.
     *
     * @param date A valid date.
     */
    public OrderDate(String date) {
        super(date);
    }

    /**
     * Returns true if a given string is a valid expected delivery date.
     *
     * @param date A string representing a date.
     * @return True if the date is valid.
     */
    public static boolean isPastDate(String date) {
        LocalDate orderDate = LocalDate.parse(date);
        return !orderDate.isAfter(LocalDate.now());
    }

    /**
     * Returns true if a given string is a valid expected delivery date.
     *
     * @param test A string representing a date.
     * @return True if the date is valid.
     */
    public static boolean isValidOrderDate(String test) {
        return Date.isValidDate(test) && isPastDate(test);
    }

    /**
     * Returns a string in the application's format
     * @return A string representing the date
     */
    @Override
    public String toString() {
        return this.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other);
    }
}
