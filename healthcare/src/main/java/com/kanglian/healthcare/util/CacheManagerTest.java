package com.kanglian.healthcare.util;

public class CacheManagerTest {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        String key = "test";

        String value = CacheManager.getData(key, new CacheManager.Load<String>() {
            public String load() {
                return "testValue";
            }
        }, 2);

        System.out.println("value:" + value);

        Thread.sleep(3 * 1000);

        String value2 = CacheManager.getData(key, new CacheManager.Load<String>() {
            @Override
            public String load() {
                return "what";
            }
        }, 3);
        System.out.println("value2:" + value2);
        System.out.println("value3:" + CacheManager.getData(key));
    }

}
