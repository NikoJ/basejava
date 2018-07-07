package com.urise.webapp.storage.serializer;

import com.urise.webapp.storage.AbstractStorageTest;
import com.urise.webapp.storage.PathStorage;

public class JsonSerializerStrategyTest extends AbstractStorageTest {

    public JsonSerializerStrategyTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new JsonSerializerStrategy()));
    }

}