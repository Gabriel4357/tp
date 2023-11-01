package seedu.address.logic;


/**
 * Represents the sort order of the list.
 */
public enum Sort {
    ASC,
    DESC;

    public static final String MESSAGE_CONSTRAINTS = "Sort must be asc or desc";

    /**
     * Checks if string given is a valid sort.
     *
     * @param test string to be tested.
     * @return true if is a valid sort, false otherwise.
     */
    public static boolean isValidSort(String test) {
        for (Sort s : Sort.values()) {
            if (s.name().equals(test)) {
                return true;
            }
        }
        return false;
    }
}
