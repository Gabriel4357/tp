package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyBook;

/**
 * Represents a storage for {@link seedu.address.model.AddressBook}.
 */
public interface BookStorage<T> {

    /**
     * Returns the file path of the data file.
     */
    Path getBookFilePath();

    /**
     * Returns the parent path of the data file.
     */
    Path getBookParentPath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyBook<T>> readBook() throws DataLoadingException;

    /**
     * @see #getBookFilePath()
     */
    Optional<ReadOnlyBook<T>> readBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveBook(ReadOnlyBook<T> addressBook) throws IOException;

    /**
     * @see #saveBook(ReadOnlyBook, Path)
     */
    void saveBook(ReadOnlyBook<T> addressBook, Path filePath) throws IOException;

}
