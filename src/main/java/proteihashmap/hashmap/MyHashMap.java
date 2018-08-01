package proteihashmap.hashmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

/**
 * @author lelay
 */
public class MyHashMap<K, V> {

    private double loadFactor;

    private int capacity;

    private int size;

    private double threshold;

    private final int MAX_CAPACITY_VALUE = (Integer.MAX_VALUE / 2);

    private final int MAX_MAP_SIZE = Integer.MAX_VALUE;

//    private final double DEFAULT_LOAD_FACTOR = 0.5;

    private List<Entry<K, V>>[] entries;

    private Function<K, Integer> hashFunction;

    public MyHashMap(int capacity, Function<K, Integer> hashFunction, double loadFactor) {
        this.capacity = capacity;
        this.hashFunction = hashFunction;
        this.loadFactor = loadFactor;
        this.threshold = (int) (loadFactor * capacity);
        this.size = 0;

        entries = new List[capacity];
    }

    private int hash(K key) {
        return Math.abs(hashFunction.apply(key) % capacity);
    }

    private void resize() {

        if (capacity > MAX_CAPACITY_VALUE) {
            return;
        }

        capacity = 2 * capacity;
        threshold = (int) (capacity * loadFactor);

        List<Entry<K, V>>[] resizedList = new List[capacity];

        for (List<Entry<K, V>> entryList : entries) {
            if (entryList == null) { //should I check this?
                continue;
            }

            for (Entry<K, V> entry : entryList) {
                int entryHash = hash(entry.getKey());

                if (resizedList[entryHash] == null) {
                    resizedList[entryHash] = new LinkedList<>();
                }

                resizedList[entryHash].add(entry);
            }
        }

        entries = resizedList;
    }

    public boolean add(K key, V value) { //putEntry
        if (size == MAX_MAP_SIZE) {
            return false;
        }

        Entry<K, V> newEntry = new Entry<>(key, value);

        int hash = 0;

        if (key != null) {
            hash = hash(key);
        }

        if (entries[hash] == null) {
            entries[hash] = new LinkedList<>();
        }

        entries[hash].add(newEntry);

        if (++size == threshold) {
            resize();
        }

        return true;
    }

    public V search(K key) { //getValueByKey

        int wantedEntryHash = 0;

        if (key != null) {
            wantedEntryHash = hash(key);
        }

        if (entries[wantedEntryHash] == null) {
            return null; //not found
        }

        List<Entry<K, V>> entryList = entries[wantedEntryHash];

        for (Entry<K, V> entry : entryList) {
            K entryKey = entry.getKey();

            if (key == null) { //null key case
                if (entryKey == null) {
                    return entry.getValue();
                }
            }
            else if (entryKey != null) {
                if (entryKey.equals(key)) {         //if found
                    return entry.getValue();        //then return value
                }
            }
        }

        return null; //not found
    }

    public V remove(K key) { //deleteFromEntriesByKeyAndValue
        int wantedEntryHash = 0;

        if (key != null) {
            wantedEntryHash = hash(key);
        }

        if (entries[wantedEntryHash] == null) {
            return null; //not found
        }

        List<Entry<K, V>> entryList = entries[wantedEntryHash];

        int elementIndex = 0;

        for (Entry<K, V> entry : entryList) {
            K entryKey = entry.getKey();

            if (key == null) { //null key case
                if (entryKey == null) {
                    -- size;
                    return entryList.remove(elementIndex).getValue();
                }
            }
            else if (entryKey != null) {
                if (entryKey.equals(key)) {
                    --size;
                    return entryList.remove(elementIndex).getValue();
                }
            }

            ++elementIndex;
        }

        return null; //not found
    }

    public int size() {
        return this.size;
    }

    public void statistics() {
        int maxEntryListSize = 0;

        for (List<Entry<K, V>> entryList : entries) {
            if (entryList.size() > maxEntryListSize) {
                maxEntryListSize = entryList.size();
            }
        }

        StringBuilder strBuilder = new StringBuilder("[");
        strBuilder.append(" capacity = ");
        strBuilder.append(capacity);
        strBuilder.append("; size = ");
        strBuilder.append(size);
        strBuilder.append("; max_collision_list_size = ");
        strBuilder.append(maxEntryListSize);
        strBuilder.append("; load_factor = ");
        strBuilder.append(loadFactor);
        strBuilder.append(" ]");
    }

//    private double calculateLoadFactor(int size, int capacity) {
//        return size / capacity;
//    }

    public class Entry<K, V> {

        private K key;

        private V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}
