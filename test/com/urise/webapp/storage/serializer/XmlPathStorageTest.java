package com.urise.webapp.storage.serializer;

import com.urise.webapp.storage.AbstractStorageTest;
import com.urise.webapp.storage.PathStorage;

public class XmlPathStorageTest extends AbstractStorageTest {

public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new XmlSerializerStrategy()));
        }
        }