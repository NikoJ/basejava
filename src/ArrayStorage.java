import java.util.Arrays;

/**
 * Array based storage for Resumes
 */

public class ArrayStorage {

    private int size = 0;
    Resume[] storage = new Resume[10000];
    private final int STORAGE_LENGTH = storage.length;

    /**
     * Equate all values of Resume to null
     */
    void clear() {

        Arrays.fill(storage, null);

        size = 0;
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
        size++;
    }

    /**
     * @param uuid - Unique identifier in Resume
     * @return Resume or null
     */
    Resume get(String uuid) {

        for (int i = 0; i < size; i++) {
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

        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                System.arraycopy(storage, i + 1, storage, i, STORAGE_LENGTH - i - 1);
                break;
            }
        }
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {

        return Arrays.copyOf(storage, size);
    }

    /**
     * @return size array, contains only Resumes in storage (without null)
     */
    int size() {
        return size;
    }
}
