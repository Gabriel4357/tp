package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import seedu.address.logic.commands.delivery.DeliveryEditCommand.DeliveryEditDescriptor;

/**
 * A utility class for a Delivery.
 */
public class DeliveryUtil {

    /**
     * Returns the part of command string for the given {@code DeliveryEditDescriptor}'s details.
     */
    public static String getEditDeliveryDescriptorDetails(DeliveryEditDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getDeliveryName().ifPresent(name -> sb.append(PREFIX_NAME).append(name).append(" "));
        descriptor.getCustomerId().ifPresent(customerId -> sb.append(PREFIX_CUSTOMER_ID).append(customerId).append(
                " "));
        descriptor.getDeliveryDate().ifPresent(deliveryDate -> sb.append(PREFIX_DATE).append(deliveryDate).append(" "));
        descriptor.getNote().ifPresent(note -> sb.append(PREFIX_NOTE).append(note).append(" "));
        descriptor.getStatus().ifPresent(status -> sb.append(PREFIX_STATUS).append(status).append(" "));

        return sb.toString();
    }
}
