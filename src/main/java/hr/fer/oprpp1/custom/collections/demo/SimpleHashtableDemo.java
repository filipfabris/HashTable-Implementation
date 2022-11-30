package hr.fer.oprpp1.custom.collections.demo;

import java.util.Iterator;

import hr.fer.oprpp1.custom.collections.SimpleHashtable;
import hr.fer.oprpp1.custom.collections.SimpleHashtable.TableEntry;

/**
 * @author filip
 *
 */
public class SimpleHashtableDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		examMarks.put("Filip", 5);
		examMarks.put("Robert", 5);
		

		
		// query collection:
		Integer kristinaGrade = examMarks.get("Kristina");
//		examMarks.clear();
		System.out.println("Kristina's exam grade is: " + kristinaGrade); // writes: 5
		
		// What is collection's size? Must be four!
		System.out.println("Number of stored pairs: " + examMarks.size()); // writes: 4
		
		System.out.println(examMarks.toString());
		
		SimpleHashtable<String, Integer> dictionary;
		dictionary = new SimpleHashtable<>();
		
		
		dictionary.put("Ivana", 2);
		dictionary.put("Ante", 2);
		dictionary.put("Jasna", 2);
		
		Iterator<TableEntry<String, Integer>> iterator = dictionary.iterator();
		
		iterator.next();
		

	}

}
