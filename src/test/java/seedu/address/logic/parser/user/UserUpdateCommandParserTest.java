package seedu.address.logic.parser.user;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ANSWER_DESC_AARON;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PASSWORD_CONFIRM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PASSWORD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_USERNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_CONFIRM_DESC_AARON;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_CONFIRM_DESC_FOODBEAR;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_DESC_AARON;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.SECRET_QUESTION_DESC_AARON;
import static seedu.address.logic.commands.CommandTestUtil.USERNAME_DESC_AARON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_AARON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_AARON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SECRET_QUESTION_AARON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_AARON;
import static seedu.address.logic.commands.user.UserUpdateCommand.MESSAGE_MISSING_FIELDS;
import static seedu.address.logic.commands.user.UserUpdateCommand.MESSAGE_PASSWORD_MISMATCH;
import static seedu.address.logic.commands.user.UserUpdateCommand.MESSAGE_PASSWORD_OR_CONFIRM_PASSWORD_MISSING;
import static seedu.address.logic.commands.user.UserUpdateCommand.MESSAGE_QUESTION_OR_ANSWER_MISSING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD_CONFIRM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SECRET_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.user.UserUpdateCommand;
import seedu.address.logic.commands.user.UserUpdateCommand.UserUpdateDescriptor;
import seedu.address.model.user.Password;
import seedu.address.model.user.Username;
import seedu.address.testutil.UpdateUserDescriptorBuilder;

public class UserUpdateCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_MISSING_FIELDS, UserUpdateCommand.MESSAGE_USAGE);

    private UserUpdateCommandParser parser = new UserUpdateCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // password specified, but no confirm password
        assertParseFailure(parser, PASSWORD_DESC_AARON, MESSAGE_PASSWORD_OR_CONFIRM_PASSWORD_MISSING);

        // confirm password specified, but no password
        assertParseFailure(parser, PASSWORD_CONFIRM_DESC_AARON, MESSAGE_PASSWORD_OR_CONFIRM_PASSWORD_MISSING);

        // secret question specified, but no answer
        assertParseFailure(parser, SECRET_QUESTION_DESC_AARON, MESSAGE_QUESTION_OR_ANSWER_MISSING);

        // answer specified, but no secret question
        assertParseFailure(parser, ANSWER_DESC_AARON, MESSAGE_QUESTION_OR_ANSWER_MISSING);

        // password and secret question specified, but no confirm password and answer
        // password pair is checked first
        assertParseFailure(parser, PASSWORD_DESC_AARON + SECRET_QUESTION_DESC_AARON,
                MESSAGE_PASSWORD_OR_CONFIRM_PASSWORD_MISSING);

        // answer and confirm password specified, but no password and secret question
        // password pair is checked first
        assertParseFailure(parser, ANSWER_DESC_AARON + PASSWORD_CONFIRM_DESC_AARON,
                MESSAGE_PASSWORD_OR_CONFIRM_PASSWORD_MISSING);

    }

    @Test
    public void parse_invalidValue_failure() {
        // There is no invalid secret question and answer
        assertParseFailure(parser, INVALID_USERNAME_DESC, Username.MESSAGE_CONSTRAINTS); // invalid username
        assertParseFailure(parser, INVALID_PASSWORD_DESC + INVALID_PASSWORD_CONFIRM_DESC,
                Password.MESSAGE_CONSTRAINTS); // invalid password

        // invalid username followed by valid password and confirm password
        assertParseFailure(parser, INVALID_USERNAME_DESC + PASSWORD_DESC_AARON + PASSWORD_CONFIRM_DESC_AARON,
                Username.MESSAGE_CONSTRAINTS);

        // valid username followed by invalid password and confirm password
        assertParseFailure(parser, USERNAME_DESC_AARON + INVALID_PASSWORD_DESC + INVALID_PASSWORD_CONFIRM_DESC,
                Password.MESSAGE_CONSTRAINTS);

        // valid username followed by mismatched password and confirm password
        assertParseFailure(parser, USERNAME_DESC_AARON + PASSWORD_DESC_AARON + PASSWORD_CONFIRM_DESC_FOODBEAR,
                MESSAGE_PASSWORD_MISMATCH);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, INVALID_USERNAME_DESC + INVALID_PASSWORD_DESC
                        + INVALID_PASSWORD_CONFIRM_DESC, Username.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + USERNAME_DESC_AARON,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UserUpdateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = USERNAME_DESC_AARON + PASSWORD_DESC_AARON + PASSWORD_CONFIRM_DESC_AARON
                + SECRET_QUESTION_DESC_AARON + ANSWER_DESC_AARON;

        UserUpdateDescriptor descriptor = new UpdateUserDescriptorBuilder().withUsername(VALID_USERNAME_AARON)
                .withPassword(VALID_PASSWORD_AARON).withSecretQuestion(VALID_SECRET_QUESTION_AARON)
                .withAnswer(VALID_ANSWER_AARON).build();
        UserUpdateCommand expectedCommand = new UserUpdateCommand(descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = USERNAME_DESC_AARON + SECRET_QUESTION_DESC_AARON + ANSWER_DESC_AARON;

        UserUpdateDescriptor descriptor = new UpdateUserDescriptorBuilder().withUsername(VALID_USERNAME_AARON)
                .withSecretQuestion(VALID_SECRET_QUESTION_AARON).withAnswer(VALID_ANSWER_AARON).build();
        UserUpdateCommand expectedCommand = new UserUpdateCommand(descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // username
        String userInput = USERNAME_DESC_AARON;
        UserUpdateDescriptor descriptor = new UpdateUserDescriptorBuilder().withUsername(VALID_USERNAME_AARON).build();
        UserUpdateCommand expectedCommand = new UserUpdateCommand(descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // password pair
        userInput = PASSWORD_DESC_AARON + PASSWORD_CONFIRM_DESC_AARON;
        descriptor = new UpdateUserDescriptorBuilder().withPassword(VALID_PASSWORD_AARON).build();
        expectedCommand = new UserUpdateCommand(descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // secret question and answer pair
        userInput = SECRET_QUESTION_DESC_AARON + ANSWER_DESC_AARON;
        descriptor = new UpdateUserDescriptorBuilder().withSecretQuestion(VALID_SECRET_QUESTION_AARON)
                .withAnswer(VALID_ANSWER_AARON).build();
        expectedCommand = new UserUpdateCommand(descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {

        // valid followed by invalid
        String userInput = USERNAME_DESC_AARON + INVALID_USERNAME_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_USER));

        // invalid followed by valid
        userInput = INVALID_USERNAME_DESC + USERNAME_DESC_AARON;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_USER));

        // multiple valid fields repeated
        userInput = USERNAME_DESC_AARON + PASSWORD_DESC_AARON + PASSWORD_CONFIRM_DESC_AARON
                + USERNAME_DESC_AARON + SECRET_QUESTION_DESC_AARON + ANSWER_DESC_AARON + ANSWER_DESC_AARON
                + PASSWORD_DESC_AARON + PASSWORD_CONFIRM_DESC_AARON + SECRET_QUESTION_DESC_AARON;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_USER, PREFIX_PASSWORD, PREFIX_PASSWORD_CONFIRM,
                        PREFIX_SECRET_QUESTION, PREFIX_ANSWER));

        // multiple invalid values
        userInput = INVALID_USERNAME_DESC + INVALID_PASSWORD_DESC + INVALID_PASSWORD_CONFIRM_DESC
                + INVALID_USERNAME_DESC + INVALID_PASSWORD_DESC + INVALID_PASSWORD_CONFIRM_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_USER, PREFIX_PASSWORD, PREFIX_PASSWORD_CONFIRM));
    }
}
