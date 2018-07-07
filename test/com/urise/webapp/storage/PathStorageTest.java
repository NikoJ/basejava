package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectSerializerStrategy;

public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectSerializerStrategy()));
    }

}