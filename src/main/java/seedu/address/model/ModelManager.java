package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.person.Customer;
import seedu.address.model.user.User;
import seedu.address.ui.ListItem;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private boolean isLoggedIn = false;

    private final AddressBook addressBook;
    private final DeliveryBook deliveryBook;
    private final UserPrefs userPrefs;

    private final FilteredList<Customer> filteredCustomers;
    private final FilteredList<Delivery> filteredDeliveries;
    private User loggedInUser;
    private SortedList<Delivery> sortedDeliveries;

    private ObservableList<ListItem> uiList;

    /**
     * Initializes a ModelManager with the given addressBook, deliveryBook and userPrefs.
     */
    public ModelManager(ReadOnlyBook<Customer> addressBook,
                        ReadOnlyBook<Delivery> deliveryBook,
                        ReadOnlyUserPrefs userPrefs, boolean isLoggedIn) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook
                + ", delivery book" + deliveryBook
                + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.deliveryBook = new DeliveryBook(deliveryBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredCustomers = new FilteredList<>(this.addressBook.getList());
        filteredDeliveries = new FilteredList<>(this.deliveryBook.getList());
        sortedDeliveries = new SortedList<>(filteredDeliveries);
        this.isLoggedIn = isLoggedIn;
        this.loggedInUser = userPrefs.getStoredUser();
        this.setUiListCustomer();

    }

    public ModelManager() {
        this(new AddressBook(), new DeliveryBook(), new UserPrefs(), false);
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
        this.loggedInUser = userPrefs.getStoredUser();
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public void setUiListDelivery() {

        this.uiList = this.getSortedDeliveryList().stream().map(
                        delivery -> new ListItem(String.format("[%d] %s", delivery.getDeliveryId(), delivery.getName()),
                                delivery.getOrderDate().toString(), delivery.getStatus().toString(),
                                delivery.getDeliveryDate().toString()))
                .collect(Collectors.toCollection(
                        FXCollections::observableArrayList));
    }


    @Override
    public void setUiListCustomer() {
        this.uiList = this.getFilteredPersonList().stream().map(
                        person -> new ListItem(String.format("[%d] %s", person.getCustomerId(), person.getName()),
                                person.getEmail().toString(), person.getPhone().toString()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    @Override
    public ObservableList<ListItem> getUiList() {
        return this.uiList;
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public Path getDeliveryBookFilePath() {
        return userPrefs.getDeliveryBookFilePath();
    }

    @Override
    public Delivery getDeliveryUsingFilteredList(int id) {
        for (Delivery d : filteredDeliveries) {
            if (d.getCustomerId() == id) {
                return d;
            }
        }
        return null;
    }

    @Override
    public void setDeliveryBookFilePath(Path deliveryBookFilePath) {
        requireNonNull(deliveryBookFilePath);
        userPrefs.setDeliveryBookFilePath(deliveryBookFilePath);
    }

    /**
     * Returns the status of the login as a string.
     *
     * @return the status of the login as a string
     */
    @Override
    public String getLoginStatus() {
        if (getStoredUser() == null) {
            return "No account found. Please register an account.";
        } else if (isLoggedIn) {
            return "Hello " + loggedInUser.getUsername() + ".";
        } else {
            return "Logged out. Please login to continue.";
        }
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyBook<Customer> addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyBook<Customer> getAddressBook() {
        return addressBook;
    }

    @Override
    public Optional<Customer> getCustomer(int id) {
        return this.addressBook.getById(id);
    }

    @Override
    public Customer getCustomerUsingFilteredList(int id) {
        for (Customer c : filteredCustomers) {
            if (c.getCustomerId() == id) {
                return c;
            }
        }
        return null;
    }

    @Override
    public boolean hasPerson(Customer customer) {
        requireNonNull(customer);
        return addressBook.hasPerson(customer);
    }

    @Override
    public void deletePerson(Customer target) {
        addressBook.removePerson(target);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_CUSTOMERS);
    }

    @Override
    public void addPerson(Customer customer) {
        addressBook.addPerson(customer);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_CUSTOMERS);
    }

    @Override
    public void setPerson(Customer target, Customer editedCustomer) {
        requireAllNonNull(target, editedCustomer);

        addressBook.setPerson(target, editedCustomer);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_CUSTOMERS);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Customer> getFilteredPersonList() {
        // only shows the customer list if the user is logged in
        if (!isLoggedIn) {
            filteredCustomers.setPredicate(PREDICATE_SHOW_NO_CUSTOMERS);
        }
        return filteredCustomers;
    }

    /**
     * Updates the filter of the filtered customer list to filter by the given {@code predicate}.
     */
    @Override
    public void updateFilteredPersonList(Predicate<Customer> predicate) {
        requireNonNull(predicate);
        // only shows the customer list if the user is logged in
        if (isLoggedIn) {
            filteredCustomers.setPredicate(predicate);
        } else {
            filteredCustomers.setPredicate(PREDICATE_SHOW_NO_CUSTOMERS);
        }

        setUiListCustomer();
    }

    //=========== User Related Methods =======================================================================

    /**
     * Returns true if the {@code user} is currently logged in.
     */
    @Override
    public boolean getUserLoginStatus() {
        return isLoggedIn;
    }

    /**
     * Returns true if the {@code user} matches the stored user.
     *
     * @param user the user to be checked
     * @return true if the {@code user} matches the stored user
     */
    @Override
    public boolean userMatches(User user) {
        requireNonNull(user);
        return user.equals(loggedInUser);
    }

    /**
     * Sets the login flag to true.
     */
    @Override
    public void setLoginSuccess() {
        isLoggedIn = true;
    }

    /**
     * Sets the logout flag to true.
     */
    @Override
    public void setLogoutSuccess() {
        isLoggedIn = false;
    }

    /**
     * Returns the stored user.
     */
    @Override
    public User getStoredUser() {
        return this.loggedInUser;
    }

    @Override
    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    @Override
    public void deleteUser() {
        userPrefs.deleteUser();
        // set the logged in user to null
        this.setLoggedInUser(null);
        setLogoutSuccess();
        setAddressBook(new AddressBook());
        setDeliveryBook(new DeliveryBook());
        setUiListCustomer();
    }

    /**
     * Registers the given {@code user}.
     */
    @Override
    public void registerUser(User user) {
        userPrefs.registerUser(user);
        this.setLoggedInUser(user);
        this.setLoginSuccess();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_CUSTOMERS);
    }

    /**
     * Resets the password of the given {@code user}.
     */
    @Override
    public void resetPassword(User user) {
        // overrides the stored user
        userPrefs.registerUser(user);
        setLoggedInUser(user);
        this.setLoginSuccess();
    }

    /**
     * Updates the stored {@code user} to the given {@code user} by re-registering the {@code user}.
     */
    @Override
    public void updateUser(User user) {
        // re-register the user with updated details
        userPrefs.registerUser(user);
        this.setLoggedInUser(user);
    }

    //=========== DeliveryBook ================================================================================

    /**
     * Replaces delivery book data with the data in {@code deliveryBook}.
     */
    @Override
    public void setDeliveryBook(ReadOnlyBook<Delivery> deliveryBook) {
        this.deliveryBook.resetData(deliveryBook);
    }

    /**
     * Returns the DeliveryBook
     */
    @Override
    public ReadOnlyBook<Delivery> getDeliveryBook() {
        return deliveryBook;
    }

    /**
     * Returns true if a delivery with the same identity as {@code delivery} exists in the delivery book.
     */
    @Override
    public Optional<Delivery> getDelivery(int id) {
        return this.deliveryBook.getById(id);
    }

    @Override
    public boolean hasDelivery(Delivery delivery) {
        requireNonNull(delivery);
        return deliveryBook.hasDelivery(delivery);
    }

    /**
     * Deletes the given delivery.
     */
    @Override
    public void deleteDelivery(Delivery target) {
        deliveryBook.removeDelivery(target);
        updateFilteredDeliveryList(PREDICATE_SHOW_ALL_DELIVERIES);
    }

    /**
     * Adds the given delivery.
     */
    @Override
    public void addDelivery(Delivery delivery) {
        deliveryBook.addDelivery(delivery);
        updateFilteredDeliveryList(PREDICATE_SHOW_ALL_DELIVERIES);
    }

    /**
     * Replaces the given delivery {@code target} with {@code editedDelivery}.
     */
    @Override
    public void setDelivery(Delivery target, Delivery editedDelivery) {
        requireAllNonNull(target, editedDelivery);

        deliveryBook.setDelivery(target, editedDelivery);
        updateFilteredDeliveryList(PREDICATE_SHOW_ALL_DELIVERIES);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Delivery> getFilteredDeliveryList() {
        // only shows the delivery list if the user is logged in
        if (!isLoggedIn) {
            filteredDeliveries.setPredicate(PREDICATE_SHOW_NO_DELIVERIES);
        }
        return filteredDeliveries;
    }

    /**
     * Updates the filter of the filtered delivery list to filter by the given {@code predicate}.
     */
    @Override
    public ObservableList<Delivery> getSortedDeliveryList() {
        return sortedDeliveries;
    }

    @Override
    public void updateFilteredDeliveryList(Predicate<Delivery> predicate) {
        requireNonNull(predicate);
        // only shows the delivery list if the user is logged in
        if (isLoggedIn) {
            filteredDeliveries.setPredicate(predicate);
        } else {
            filteredDeliveries.setPredicate(PREDICATE_SHOW_NO_DELIVERIES);
        }

        // Update the sorted list
        this.sortedDeliveries = new SortedList<>(filteredDeliveries);

        setUiListDelivery();
    }

    @Override
    public void sortFilteredDeliveryList(Comparator<Delivery> comparator) {
        requireNonNull(comparator);
        sortedDeliveries.setComparator(comparator);
        setUiListDelivery();
    }

    @Override
    public void deleteDeliveryByCustomer(Customer target) {
        deliveryBook.removeDeliveryByCustomer(target);
        updateFilteredDeliveryList(PREDICATE_SHOW_ALL_DELIVERIES);
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && deliveryBook.equals(otherModelManager.deliveryBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredCustomers.equals(otherModelManager.filteredCustomers)
                && filteredDeliveries.equals(otherModelManager.filteredDeliveries)
                && isLoggedIn == otherModelManager.isLoggedIn;
    }

}
