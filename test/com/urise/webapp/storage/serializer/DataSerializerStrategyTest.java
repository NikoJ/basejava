package com.urise.webapp.storage.serializer;

import com.urise.webapp.storage.AbstractStorageTest;
import com.urise.webapp.storage.PathStorage;

public class DataSerializerStrategyTest extends AbstractStorageTest {

    public DataSerializerStrategyTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataSerializerStrategy()));
    }
}