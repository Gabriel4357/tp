package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.person.Customer;
import seedu.address.model.user.User;
import seedu.address.ui.ListItem;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Customer> PREDICATE_SHOW_ALL_CUSTOMERS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Delivery> PREDICATE_SHOW_ALL_DELIVERIES = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Customer> PREDICATE_SHOW_NO_CUSTOMERS = unused -> false;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Delivery> PREDICATE_SHOW_NO_DELIVERIES = unused -> false;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    void setUiListDelivery();

    void setUiListCustomer();

    ObservableList<ListItem> getUiList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyBook<Customer> addressBook);

    /**
     * Returns the AddressBook
     */
    ReadOnlyBook<Customer> getAddressBook();

    /**
     * Returns an optional containing a customer with the given id.
     *
     * @param id the id of the customer
     * @return the optional containing customer with the given id
     */
    Optional<Customer> getCustomer(int id);

    /**
     * Returns a Customer with the same one-based id.
     *
     * @param id the customer's id.
     * @return Customer with the same one-based id
     */
    Customer getCustomerUsingFilteredList(int id);

    /**
     * Returns true if a customer with the same identity as {@code customer} exists in the address book.
     */
    boolean hasPerson(Customer customer);

    /**
     * Deletes the given customer.
     * The customer must exist in the address book.
     */
    void deletePerson(Customer target);

    /**
     * Adds the given customer.
     * {@code customer} must not already exist in the address book.
     */
    void addPerson(Customer customer);

    /**
     * Replaces the given customer {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The customer identity of {@code editedPerson} must not be the same
     * as another existing customer in the address book.
     */
    void setPerson(Customer target, Customer editedCustomer);

    /**
     * Returns an unmodifiable view of the filtered customer list
     */
    ObservableList<Customer> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Customer> predicate);

    /**
     * Returns the user prefs' delivery book file path.
     */
    Path getDeliveryBookFilePath();

    /**
     * Sets the user prefs' delivery book file path.
     */
    void setDeliveryBookFilePath(Path deliveryBookFilePath);

    /**
     * Replaces delivery book data with the data in {@code deliveryBook}.
     */
    void setDeliveryBook(ReadOnlyBook<Delivery> deliveryBook);

    /**
     * Returns the AddressBook
     */
    ReadOnlyBook<Delivery> getDeliveryBook();

    /**
     * Returns an optional containing a delivery with the given id.
     *
     * @param id the id of the delivery
     * @return the optional containing delivery with the given id
     */
    Optional<Delivery> getDelivery(int id);

    /**
     * Returns true if a delivery with the same identity as {@code delivery} exists in the address book.
     */
    boolean hasDelivery(Delivery delivery);

    /**
     * Deletes the given delivery.
     * The delivery must exist in the address book.
     */
    void deleteDelivery(Delivery target);

    /**
     * Deletes all deliveries associated with the given customer.
     *
     * @param target
     */
    void deleteDeliveryByCustomer(Customer target);

    /**
     * Adds the given delivery.
     * {@code delivery} must not already exist in the address book.
     */
    void addDelivery(Delivery customer);

    /**
     * Replaces the given delivery {@code target} with {@code editedDelivery}.
     * {@code target} must exist in the address book.
     * The delivery identity of {@code editedDelivery} must not be the same
     * as another existing customer in the address book.
     */
    void setDelivery(Delivery target, Delivery editedDelivery);

    /**
     * Returns an unmodifiable view of the filtered delivery list
     */
    ObservableList<Delivery> getFilteredDeliveryList();

    ObservableList<Delivery> getSortedDeliveryList();

    Delivery getDeliveryUsingFilteredList(int id);

    /**
     * Updates the filter of the filtered delivery list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDeliveryList(Predicate<Delivery> predicate);


    void sortFilteredDeliveryList(Comparator<Delivery> comparator);

    /**
     * Returns true if the {@code user} is currently logged in.
     */
    boolean getUserLoginStatus();

    /**
     * Sets the login flag to true.
     */
    void setLoginSuccess();

    /**
     * Sets the logout flag to true.
     */
    void setLogoutSuccess();

    /**
     * Returns true if the {@code user} matches the user loaded in the model.
     */
    boolean userMatches(User user);

    /**
     * Returns the stored user.
     */
    User getStoredUser();

    /**
     * Registers the given {@code user}.
     */
    void registerUser(User user);

    void setLoggedInUser(User user);

    void deleteUser();

    void resetPassword(User user);

    void updateUser(User user);

    String getLoginStatus();

}


