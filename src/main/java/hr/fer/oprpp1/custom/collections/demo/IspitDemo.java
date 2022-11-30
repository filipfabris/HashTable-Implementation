package hr.fer.oprpp1.custom.collections.demo;

import java.util.Arrays;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

public class IspitDemo {
	public static void main(String[] args) {
		ArrayIndexedCollection<String> c1 = new ArrayIndexedCollection<>();
		c1.add("Kristina");
		c1.add("Jasna");
		
		ArrayIndexedCollection<String> c2 = new ArrayIndexedCollection<>();
		c2.addModified(c2, String::toLowerCase);
		
		ArrayIndexedCollection<Integer> c3 = new ArrayIndexedCollection<>();
		c3.addModified(c1, String::length);
		
		System.out.println(Arrays.toString(c3.toArray()));

	}

}
