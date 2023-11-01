package seedu.address.logic.parser.delivery;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERY_LIST;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERY_LIST_CUSTOMER_ID;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERY_LIST_DELIVERY_DATE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERY_LIST_DELIVERY_DATE_TODAY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERY_LIST_SORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_LIST_ALL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_LIST_CANCELLED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_LIST_COMPLETED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_LIST_CREATED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_LIST_CUSTOMER_ID;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_LIST_DELIVERY_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_LIST_DELIVERY_DATE_TODAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_LIST_SHIPPED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_LIST_SORT_ASC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_LIST_SORT_DESC;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Sort;
import seedu.address.logic.commands.delivery.DeliveryListCommand;
import seedu.address.logic.parser.CommandParserTestUtil;
import seedu.address.logic.parser.DeliveryListParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.delivery.Date;
import seedu.address.model.delivery.DeliveryStatus;


public class DeliveryListParserTest {
    private DeliveryListParser parser = new DeliveryListParser();


    @Test
    public void parse_validArgs_returnsDeliveryListCommand() {

        CommandParserTestUtil.assertParseSuccess(parser, DeliveryListCommand.COMMAND_WORD
                + VALID_DELIVERY_LIST_ALL,
            new DeliveryListCommand(null, null, null, Sort.DESC));
        CommandParserTestUtil.assertParseSuccess(parser, DeliveryListCommand.COMMAND_WORD
                + VALID_DELIVERY_LIST_SHIPPED,
            new DeliveryListCommand(DeliveryStatus.SHIPPED, null, null, Sort.DESC));
        CommandParserTestUtil.assertParseSuccess(parser, DeliveryListCommand.COMMAND_WORD
            + VALID_DELIVERY_LIST_CREATED, new DeliveryListCommand(DeliveryStatus
            .CREATED, null, null, Sort.DESC));
        CommandParserTestUtil.assertParseSuccess(parser, DeliveryListCommand.COMMAND_WORD
                + VALID_DELIVERY_LIST_COMPLETED,
            new DeliveryListCommand(DeliveryStatus.COMPLETED, null, null, null));
        CommandParserTestUtil.assertParseSuccess(parser, DeliveryListCommand.COMMAND_WORD
                + VALID_DELIVERY_LIST_CANCELLED,
            new DeliveryListCommand(DeliveryStatus.CANCELLED, null, null, Sort.DESC));

        // Test customerId
        CommandParserTestUtil.assertParseSuccess(parser, DeliveryListCommand.COMMAND_WORD
                + VALID_DELIVERY_LIST_ALL + VALID_DELIVERY_LIST_CUSTOMER_ID,
            new DeliveryListCommand(null, 1, null, Sort.DESC));

        // Test deliveryDate
        CommandParserTestUtil.assertParseSuccess(parser, DeliveryListCommand.COMMAND_WORD
                + VALID_DELIVERY_LIST_ALL + VALID_DELIVERY_LIST_DELIVERY_DATE,
            new DeliveryListCommand(null, null, new Date("2023-12-12"),
                Sort.DESC));

        // Test delivery date if "today"
        CommandParserTestUtil.assertParseSuccess(parser,
            DeliveryListCommand.COMMAND_WORD + VALID_DELIVERY_LIST_ALL + " "
                + VALID_DELIVERY_LIST_DELIVERY_DATE_TODAY,
            new DeliveryListCommand(null, null,
                new Date(LocalDate.now().format(DateTimeFormatter.ofPattern(Date.FORMAT))),
                Sort.DESC));

        CommandParserTestUtil.assertParseSuccess(parser, DeliveryListCommand.COMMAND_WORD
                + VALID_DELIVERY_LIST_ALL + VALID_DELIVERY_LIST_SORT_ASC,
            new DeliveryListCommand(null, null, null, Sort.ASC));

        CommandParserTestUtil.assertParseSuccess(parser, DeliveryListCommand.COMMAND_WORD
                + VALID_DELIVERY_LIST_ALL + VALID_DELIVERY_LIST_SORT_DESC,
            new DeliveryListCommand(null, null, null, Sort.DESC));


    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // invalid status
        CommandParserTestUtil.assertParseFailure(parser, DeliveryListCommand.COMMAND_WORD + INVALID_DELIVERY_LIST,
            String.format(DeliveryStatus.MESSAGE_CONSTRAINTS));

        // invalid customer id
        CommandParserTestUtil.assertParseFailure(parser, DeliveryListCommand.COMMAND_WORD
                + INVALID_DELIVERY_LIST_CUSTOMER_ID,
            String.format(ParserUtil.MESSAGE_INVALID_INDEX));

        // invalid expected delivery date
        CommandParserTestUtil.assertParseFailure(parser, DeliveryListCommand.COMMAND_WORD
                + INVALID_DELIVERY_LIST_DELIVERY_DATE,
            String.format(Date.MESSAGE_CONSTRAINTS));

        // invalid delivery date word
        CommandParserTestUtil.assertParseFailure(parser, DeliveryListCommand.COMMAND_WORD
                + INVALID_DELIVERY_LIST_DELIVERY_DATE_TODAY,
            String.format(Date.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_emptyArgs_returnsDeliveryListCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, "",
            new DeliveryListCommand(null, null, null, null));
    }

    @Test
    public void parse_invalidStatus_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, DeliveryListCommand.COMMAND_WORD + INVALID_DELIVERY_LIST,
            String.format(DeliveryStatus.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_invalidSort_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, DeliveryListCommand.COMMAND_WORD + INVALID_DELIVERY_LIST_SORT,
            String.format(Sort.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_invalidStatusAndSort_throwsParseException() {
        CommandParserTestUtil.assertParseFailure(parser, DeliveryListCommand.COMMAND_WORD
                + INVALID_DELIVERY_LIST,
            String.format(DeliveryStatus.MESSAGE_CONSTRAINTS));

        CommandParserTestUtil.assertParseFailure(parser, DeliveryListCommand.COMMAND_WORD
                + INVALID_DELIVERY_LIST_SORT,
            String.format(Sort.MESSAGE_CONSTRAINTS));
        CommandParserTestUtil.assertParseFailure(parser, DeliveryListCommand.COMMAND_WORD
                + INVALID_DELIVERY_LIST + INVALID_DELIVERY_LIST_SORT,
            String.format(Sort.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_validStatusAndSort_returnsDeliveryListCommand() {
        CommandParserTestUtil.assertParseSuccess(parser, DeliveryListCommand.COMMAND_WORD
                + VALID_DELIVERY_LIST_SHIPPED + VALID_DELIVERY_LIST_SORT_ASC,
            new DeliveryListCommand(DeliveryStatus.SHIPPED, null, null, Sort.ASC));
        CommandParserTestUtil.assertParseSuccess(parser, DeliveryListCommand.COMMAND_WORD
                + VALID_DELIVERY_LIST_SHIPPED + VALID_DELIVERY_LIST_SORT_DESC,
            new DeliveryListCommand(DeliveryStatus.SHIPPED, null, null, Sort.DESC));
    }
}
