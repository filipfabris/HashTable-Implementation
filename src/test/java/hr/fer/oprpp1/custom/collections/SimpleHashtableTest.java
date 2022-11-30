package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.collections.SimpleHashtable.TableEntry;

class SimpleHashtableTest {

	SimpleHashtable<String, Integer> dictionary;

	@BeforeEach
	void initialise() {
		dictionary = new SimpleHashtable<>();
	}

	@Test
	void testConstructor() {
		assertThrows(IllegalArgumentException.class, () -> new SimpleHashtable<>(0), "size must be greather then 1");
	}

	@Test
	void testPut() {
		dictionary.put("Ivana", 2);
		dictionary.put("Ante", 2);
		dictionary.put("Jasna", 2);
		dictionary.put("Kristina", 5);

		assertEquals(dictionary.size(), 4);
		assertEquals(dictionary.get("Ivana"), 2);

		dictionary.put("Ivana", 5); // overwrites old grade for Ivana

		assertEquals(dictionary.size(), 4, "Size did not change");
		assertEquals(dictionary.get("Ivana"), 5);
	}

	@Test
	void testClear() {
		dictionary.put("Ivana", 2);
		dictionary.put("Ante", 2);
		dictionary.put("Jasna", 2);
		dictionary.put("Kristina", 5);

		assertEquals(dictionary.size(), 4);
		assertEquals(dictionary.get("Ivana"), 2);

		dictionary.clear();

		assertEquals(dictionary.size(), 0);
		assertEquals(dictionary.get("Ivana"), null);
	}

	@Test
	void testPutThrows() {
		dictionary.put("Ivana", 2);

		assertThrows(NullPointerException.class, () -> dictionary.put(null, 2));
	}

	@Test
	void testContainsKey() {
		dictionary.put("Ivana", 2);
		dictionary.put("Ante", 2);
		dictionary.put("Jasna", 2);

		assertEquals(dictionary.containsKey(null), false);
		assertEquals(dictionary.containsKey("Ante"), true);

	}

	@Test
	void testContainsValue() {
		dictionary.put("Ivana", 3);
		dictionary.put("Ante", 4);
		dictionary.put("Jasna", 2);

		assertEquals(dictionary.containsValue(2), true);
		assertEquals(dictionary.containsValue(4), true);
		assertEquals(dictionary.containsValue(1), false);
	}
	
	@Test
	void testRemove() {
		dictionary.put("Ivana", 3);
		dictionary.put("Ante", 4);
		dictionary.put("Jasna", 2);

		assertEquals(dictionary.remove(null), null);
		assertEquals(dictionary.remove("Ne postokim"), null);
		assertEquals(dictionary.remove("Ante"), 4);
		assertEquals(dictionary.size(), 2);
	}
	
	@Test
	void testToString() {
		dictionary.put("Ivana", 2);
		dictionary.put("Ante", 2);
		dictionary.put("Jasna", 2);
		dictionary.put("Kristina", 5);
		dictionary.put("Ivana", 5); // overwrites old grade for Ivana
		dictionary.put("Filip", 5);
		dictionary.put("Robert", 5);
				
		assertEquals(dictionary.toString(), "[Filip=5, Ivana=5, Kristina=5, Ante=2, Robert=5, Jasna=2]");

		dictionary.clear();
		
		assertEquals(dictionary.toString(), "[]");
	}
	
	@Test
	void testGetIterator() {
		dictionary.put("Ivana", 2);
		dictionary.put("Ante", 2);
		dictionary.put("Jasna", 2);
		
		Iterator<TableEntry<String, Integer>> iterator = dictionary.iterator();
		
		assertEquals(iterator.next().getKey(), "Ivana");
		assertEquals(iterator.next().getKey(), "Ante");
		assertEquals(iterator.hasNext(), true);
		assertEquals(iterator.next().getKey(), "Jasna");
		
		assertEquals(iterator.hasNext(), false);
		assertThrows(NoSuchElementException.class, () -> iterator.next(), "No more elements");

	}
	
	@Test
	void testGetIteratorRemove() {
		dictionary.put("Ivana", 2);
		dictionary.put("Ante", 5);
		dictionary.put("Jasna", 2);
		
		Iterator<TableEntry<String, Integer>> iterator = dictionary.iterator();
		
		assertEquals(iterator.next().getKey(), "Ivana");
		dictionary.remove("Jasna");
		assertThrows(ConcurrentModificationException.class, () -> iterator.hasNext(), "Dictionary has been changed");
		assertThrows(ConcurrentModificationException.class, () -> iterator.next(), "Dictionary has been changed");
	}
	
	@Test
	void testGetIteratorRemove2() {
		dictionary.put("Ivana", 2);
		dictionary.put("Ante", 2);
		dictionary.put("Jasna", 4);
		
		Iterator<TableEntry<String, Integer>> iterator = dictionary.iterator();
		
		assertEquals(iterator.next().getKey(), "Ivana");
		dictionary.remove("Jasna");
		assertThrows(ConcurrentModificationException.class, () -> iterator.hasNext(), "Dictionary has been changed");
		assertThrows(ConcurrentModificationException.class, () -> iterator.next(), "Dictionary has been changed");
		
		
		Iterator<TableEntry<String, Integer>> iterator2 = dictionary.iterator();
				
		iterator2.next();
		iterator2.next();
		dictionary.remove("Ivana");
		
		assertThrows(ConcurrentModificationException.class, () -> iterator2.hasNext(), "Dictionary has been changed");
		assertThrows(ConcurrentModificationException.class, () -> iterator2.next(), "Dictionary has been changed");
		
	}
	
	@Test
	void testIteratorRemove() {
		dictionary.put("Ivana", 2);
		dictionary.put("Ante", 5);
		dictionary.put("Jasna", 2);
		dictionary.put("Ivan", 19);
		dictionary.put("Filip", 16);
		
		Iterator<TableEntry<String, Integer>> iterator = dictionary.iterator();
		
		iterator.next();
		iterator.next();
		iterator.remove(); // removes Jasna
		assertThrows(IllegalStateException.class, () -> iterator.remove(), "Only per one call can element be removed using iterator");
	}
	
}
