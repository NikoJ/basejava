package com.urise.webapp.util;

public class LazySingleton {
    private static volatile LazySingleton INSTANCE;
    public double sin = Math.sin(13.);
    int i;

    private LazySingleton() {
    }

    public static LazySingleton getInstance() {
        return LazySingletonHolder.INSTANCE;
    }

    //    public static LazySingleton getInstance() {
//        if (INSTANCE == null) {
//            synchronized (LazySingleton.class) {
//                if (INSTANCE == null) {
//                    int i = 13;
//                    INSTANCE = new LazySingleton();
//                }
//            }
//        }
//        return INSTANCE;
//    }
    private static class LazySingletonHolder {
        private static final LazySingleton INSTANCE = new LazySingleton();
    }
}
