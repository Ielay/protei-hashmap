package proteihashmap;

import org.junit.Assert;
import org.junit.Test;
import proteihashmap.hashmap.MyHashMap;

/**
 * @author lelay
 */
public class MyHashMapTest {

    @Test
    public void testAddValidValuesCorrect() {
        MyHashMap<String, Integer> hashMap = new MyHashMap<>(16, (x) -> x.hashCode(), 0.75d);

        int startedMapSize = hashMap.size();

        String keyToAdd = "aaa";
        Integer valueToAdd = 1;

        hashMap.add(keyToAdd, valueToAdd);

        Assert.assertEquals(startedMapSize + 1, hashMap.size());

        Assert.assertEquals(valueToAdd, hashMap.search(keyToAdd));
    }

    @Test
    public void testAddNullValueCorrect() {
        MyHashMap<String, Integer> hashMap = new MyHashMap<>(16, (x) -> x.hashCode(), 0.75d);

        int startedMapSize = hashMap.size();

        String keyToAdd = "aaa";
        Integer valueToAdd = null;

        hashMap.add(keyToAdd, valueToAdd);

        Assert.assertEquals(startedMapSize + 1, hashMap.size());

        Assert.assertEquals(valueToAdd, hashMap.search(keyToAdd));
    }

    @Test
    public void testAddNullKeyCorrect() {
        MyHashMap<String, Integer> hashMap = new MyHashMap<>(16, (x) -> x.hashCode(), 0.75d);

        int startedMapSize = hashMap.size();

        String keyToAdd = null;
        Integer valueToAdd = 1234;

        hashMap.add(keyToAdd, valueToAdd);

        Assert.assertEquals(startedMapSize + 1, hashMap.size());

        Assert.assertEquals(valueToAdd, hashMap.search(keyToAdd));
    }

}
