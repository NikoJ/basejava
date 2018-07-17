package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.DataSerializerStrategyTest;
import com.urise.webapp.storage.serializer.JsonSerializerStrategyTest;
import com.urise.webapp.storage.serializer.XmlPathStorageTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                ListStorageTest.class,
                MapStorageTest.class,
                MapStorageOtherTest.class,
                ArrayStorageTest.class,
                SortedArrayStorageTest.class,
                FileStorageTest.class,
                PathStorageTest.class,
                XmlPathStorageTest.class,
                JsonSerializerStrategyTest.class,
                DataSerializerStrategyTest.class,
                SqlStorageTest.class
        })
public class CommonStorageTest {
/*
    public static void main(String[] args) {
        JUnitCore runner = new JUnitCore();
        printResultTest(runner.run(ArrayStorageTest.class), "ArrayStorageTest");
        printResultTest(runner.run(SortedArrayStorageTest.class), "SortedArrayStorageTest");
        printResultTest(runner.run(ListStorageTest.class), "ListStorageTest");
        printResultTest(runner.run(MapStorageTest.class), "MapStorageTest");
    }

    private static void printResultTest(Result result, String title) {
        System.out.println(title);
        System.out.println("run tests: " + result.getRunCount());
        System.out.println("failed tests: " + result.getFailureCount());
        System.out.println("ignored tests: " + result.getIgnoreCount());
        System.out.println("success: " + result.wasSuccessful() + "\n");
    }*/
}
