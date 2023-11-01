package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import seedu.address.logic.Sort;
import seedu.address.logic.commands.delivery.DeliveryListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.Date;
import seedu.address.model.delivery.DeliveryStatus;


/**
 * Parses input arguments and creates a new DeliveryListCommand object.
 */
public class DeliveryListParser implements Parser<DeliveryListCommand> {

    @Override
    public DeliveryListCommand parse(String userInput) throws ParseException {

        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(userInput, PREFIX_STATUS, PREFIX_SORT, PREFIX_CUSTOMER_ID, PREFIX_DATE);

        Optional<String> sort = argMultimap.getValue(PREFIX_SORT);
        Optional<String> inputStatus = argMultimap.getValue(PREFIX_STATUS);
        Optional<String> inputCustomerId = argMultimap.getValue(PREFIX_CUSTOMER_ID);
        Optional<String> inputDate = argMultimap.getValue(PREFIX_DATE);
        DeliveryStatus status = null;
        Integer customerId = null;
        Date deliveryDate = null;
        Sort sortString = ParserUtil.parseSort(sort.orElse("desc"));

        if (inputStatus.isPresent()) {
            status = ParserUtil.parseDeliveryStatus(inputStatus.get());
        }

        if (inputCustomerId.isPresent()) {
            customerId = ParserUtil.parseId(inputCustomerId.get());
        }

        if (inputDate.isPresent()) {

            if (inputDate.get().equalsIgnoreCase("today")) {
                deliveryDate = new Date(LocalDate.now().format(DateTimeFormatter.ofPattern(Date.FORMAT)));
            } else {
                deliveryDate = ParserUtil.parseDate(inputDate.get());
            }
        }


        return new DeliveryListCommand(status, customerId, deliveryDate, sortString);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeliveryListParser); // instanceof handles nulls

    }
}
