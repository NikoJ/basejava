import java.util.Arrays;

/**
 * Array based storage for Resumes
 */

public class ArrayStorage {

    private int sizeGlobal;
    Resume[] storage = new Resume[10000];
    private final int STORAGE_LENGTH = storage.length;

    /**
     * Equate all values of Resume to null
     */
    void clear() {
        for (int i = 0; i < STORAGE_LENGTH; i++) {
            storage[i] = null;
        }
        size();
    }

    /**
     * @param r - object Resume to be saved
     */
    void save(Resume r) {

        for (int i = 0; i < STORAGE_LENGTH; i++) {
            if (storage[i] == null) {
                storage[i] = r;
                break;
            }
        }
        size();
    }

    /**
     * @param uuid - Unique identifier in Resume
     * @return Resume or null
     */
    Resume get(String uuid) {

        for (int i = 0; i < sizeGlobal; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }

        return null;
    }

    /**
     * @param uuid - Unique identifier in Resume
     */
    void delete(String uuid) {

        for (int i = 0; i < sizeGlobal; i++) {
            if (storage[i].uuid.equals(uuid)) {
                while (i < size()) {
                    storage[i] = storage[i + 1];
                    i++;
                }

            }
        }
        size();
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {

        return Arrays.copyOf(storage, sizeGlobal);
    }

    /**
     * @return size array, contains only Resumes in storage (without null)
     */
    int size() {

        sizeGlobal = 0;

        for (int i = 0; i < STORAGE_LENGTH; i++) {
            if (storage[i] != null) sizeGlobal++;
        }

        return sizeGlobal;
    }
}
