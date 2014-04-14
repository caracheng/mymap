package edu.macalester.comp124.mymap;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple implementation of a hashtable.
 *
 * @author shilad
 *
 * @param <K> Class for keys in the table.
 * @param <V> Class for values in the table.
 */
public class MyMap <K, V> {
	private static final int INITIAL_SIZE = 4;
	
	/** The table is package-protected so that the unit test can examine it. */
	List<MyEntry<K, V>> [] buckets;
	
	/** Number of unique entries (e.g. keys) in the table */
	private int numEntries = 0;
	
	/** Threshold that determines when the table should expand */
	private double loadFactor = 0.75;
	
	/**
	 * Initializes the data structures associated with a new hashmap.
	 */
	public MyMap() {
		buckets = newArrayOfEntries(INITIAL_SIZE);
	}
	
	/**
	 * Returns the number of unique entries (e.g. keys) in the table.
	 * @return the number of entries.
	 */
	public int size() {
		return numEntries;
	}
	
	/**
	 * Adds a new key, value pair to the table.
	 * @param key
	 * @param value
	 */

    /*A single key / value pair is stored in a MyEntry object.
    MyEntry is a generic type with type parameters for the key and the value: MyEntry<K, V>.
    A single hash bucket is a list of MyEntry objects: List<MyEntry<K, V>>
    The type for the array of hash buckets is a simple extension of the previous data structure: List<MyEntry<K, V>> []*/

	public void put(K key, V value) {
		//Complete the put() method:
       // Hint: the key may already exist!
       //         Hint: every Java Object has a hashCode() and equals() method.
       //         After you complete the put() method, the first unit test in TestMyMap should pass.
		// TODO: Store the key.
        expandIfNecessary();
        int compressedHashCode = key.hashCode() % buckets.length;
        List<MyEntry<K,V>> listInBucket = buckets[compressedHashCode];
        for(int i = 0; i < listInBucket.size(); i++)
        {
            if(listInBucket.get(i).getKey().equals(key)) //if key already exists
            {
                listInBucket.get(i).setValue(value);
                return;
            }
        }
        MyEntry<K,V> newEntry = new MyEntry<>(key,value); //creates a new entry
        listInBucket.add(newEntry);
        numEntries++;
	}

	/**
	 * Returns the value associated with the specified key, or null if it
	 * doesn't exist.
	 * 
	 * @param key
	 * @return
	 */
	public V get(K key) {
		// TODO: retrieve the key.
        int compressedHashCode = Math.abs(key.hashCode()) % buckets.length;
        V value = null;
        List<MyEntry<K,V>> listInBucket = buckets[compressedHashCode];
        for (int i = 0; i < listInBucket.size(); i++)
        {
            MyEntry<K,V> entry = listInBucket.get(i);
           if(listInBucket.get(i).getKey().equals(key))
           {
               return entry.getValue();
           }
        }
       return value;
	}
	
	/**
	 * Expands the table to double the size, if necessary.
	 */
	private void expandIfNecessary() {
		// TODO: expand if necessary

        if(numEntries > buckets.length * loadFactor)
        {
            int newSize = buckets.length * 2;
            List<MyEntry<K, V>> [] newBuckets = newArrayOfEntries(newSize); //make new array //for each array slot, make new list for each slot
            for(int i = 0; i < buckets.length; i++) //loops through each bucket in old array
            {
                List<MyEntry<K,V>> aList = buckets[i]; //grabs list from old bucket
                for(int j = 0; j < aList.size(); j++) //loops through the list
                {
                    K key = aList.get(j).getKey(); //grabs the key
                    V value = aList.get(j).getValue(); //grabs value of key
                    int compressedHashCode = Math.abs(key.hashCode()) % newBuckets.length; //compresses the hashcode of the key
                    List<MyEntry<K,V>> listInBucket = newBuckets[compressedHashCode]; //makes a list in slot of new array
                    MyEntry<K,V> newEntry = new MyEntry<>(key,value); //makes a new entry
                    listInBucket.add(newEntry); //shoves the entry into the list in slot of new array
                }
            }
            System.out.println("done making new buckets, making it the main buckets");
            buckets = newBuckets; //copies over
            System.out.println("new # buckets = "+buckets.length);
        }
	}


	/**
	 * Returns an array of the specified size, each
	 * containing an empty linked list that can be
	 * filled with MyEntry objects.
	 * 
	 * @param capacity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<MyEntry<K,V>>[] newArrayOfEntries(int capacity) {
		List<MyEntry<K, V>> [] entries = (List<MyEntry<K,V>> []) (
				java.lang.reflect.Array.newInstance(LinkedList.class, capacity));
		for (int i = 0; i < entries.length; i++) {
			entries[i] = new LinkedList();
		}
		return entries;
	}
	
}
