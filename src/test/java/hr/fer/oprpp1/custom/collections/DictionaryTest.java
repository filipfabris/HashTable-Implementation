package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DictionaryTest {
	Dictionary<Integer, String> dictionaryTest;
	
	@BeforeEach
	void inicialise(){
		dictionaryTest = new Dictionary<>();
	}
	

	@Test
	void testConstructor() {
		assertEquals(dictionaryTest.size(), 0);
	}
	
	@Test
	void testPut() {
		dictionaryTest.put(255, "Filip");
		dictionaryTest.put(200, "Robert");
		dictionaryTest.put(100, null);
		assertEquals(dictionaryTest.size(), 3);
	}
	
	@Test
	void testPutChange() {
		dictionaryTest.put(255, "Filip");
		dictionaryTest.put(200, "Robert");
		dictionaryTest.put(255, "Fabris");
		
		assertEquals(dictionaryTest.size(), 2, "entry has value has been changed");
		assertEquals(dictionaryTest.get(255), "Fabris", "new entry value");
	}
	
	@Test
	void testPutThrow() {
		dictionaryTest.put(255, "Filip");
		dictionaryTest.put(200, "Robert");
		
		assertThrows(NullPointerException.class, null, () -> dictionaryTest.put(null, "Kljuc je null"));
		assertEquals(dictionaryTest.size(), 2);
	}
	
	@Test
	void testGet() {
		dictionaryTest.put(255, "Filip");
		dictionaryTest.put(200, "Robert");
		dictionaryTest.put(1, "HRK");
		dictionaryTest.put(10, "EUR");
		dictionaryTest.put(11, "GBP");


		assertEquals(dictionaryTest.size(), 5);
		
		assertEquals(dictionaryTest.get(10), "EUR");
		assertEquals(dictionaryTest.get(11), "GBP");
	}
	
	@Test
	void testRemoveConatins() {
		dictionaryTest.put(255, "Filip");
		dictionaryTest.put(200, "Robert");
		dictionaryTest.put(1, "HRK");
		dictionaryTest.put(10, "EUR");
		dictionaryTest.put(11, "GBP");


		assertEquals(dictionaryTest.size(), 5);

		assertEquals(dictionaryTest.get(10), "EUR");
		assertEquals(dictionaryTest.remove(10), "EUR");
		assertEquals(dictionaryTest.get(10), null);


	}
	

}
