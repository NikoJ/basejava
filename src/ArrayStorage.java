import java.util.ArrayList;
import java.util.List;

/**
 * Array based storage for Resumes
 */

public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    /**
     * Equate all values of Resume to null
     */
    void clear() {
        for (int i =0; i< storage.length;i++ )
        {
            storage[i]=null;
        }
    }

    /**
     *
     * @param r - object Resume to be saved
     */
    void save(Resume r) {

        for (int i =0; i< storage.length;i++ )
        {
            if (storage[i] == null)
            {
                storage[i]=r;
                break;
            }
        }
    }

    /**
     *
     * @param uuid - Unique identifier in Resume
     * @return
     */
    Resume get(String uuid) {

        for (int i =0; i< size();i++ )
        {
            if (storage[i].uuid == uuid)
            {
                return storage[i];
            }
        }

        return null;
    }

    /**
     *
     * @param uuid - Unique identifier in Resume
     */
    void delete(String uuid) {

        for (int i =0; i< size();i++ )
        {
            if (storage[i].uuid == uuid)
            {
                storage[i]=null;
                while (i<size()){
                    storage[i]=storage[i+1];
                    i++;
                }

            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {

        List<Resume> list = new ArrayList<>();
        for (int i =0; i<size();i++ )
        {
            list.add(storage[i]);
        }

        Resume[] temp = new Resume[list.size()];

        list.toArray(temp);

        return temp;
    }

    /**
     *
     * @return size array, contains only Resumes in storage (without null)
     */
    int size() {

        int size=0;

        for (int i =0; i< storage.length;i++ )
        {
            if (storage[i] != null) size++;
        }

        return size;
    }
}
