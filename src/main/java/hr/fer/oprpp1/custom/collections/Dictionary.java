/*
 * 
 */
package hr.fer.oprpp1.custom.collections;

import java.util.Objects;

// TODO: Auto-generated Javadoc
/**
 * The Class Dictionary.
 *
 * @author filip fabris
 * @param <K> the key type
 * @param <V> the value type
 */
public class Dictionary<K, V> {

	/** The dictionary internal storage. */
	private ArrayIndexedCollection<Entry<K, V>> dictionary;

	/**
	 * The Class Entry.
	 *
	 * @param <K> the key type
	 * @param <V> the value type
	 */
	private static class Entry<K, V> {
		
		/** The key. */
		K key;
		
		/** The value. */
		V value;

		/**
		 * Instantiates a new entry.
		 *
		 * @param key the key
		 * @param value the value
		 * @throws NullPointerException if key is null
		 */
		Entry(K key, V value) {
			if (key == null) {
				throw new NullPointerException();
			}
			this.key = key;
			this.value = value;
		}

		/**
		 * Hash code.
		 *
		 * @return the int
		 */
		@Override
		public int hashCode() {
			return Objects.hash(key, value);
		}

		/**
		 * Equals.
		 *
		 * @param obj the obj
		 * @return true, if successful
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Entry<?, ?> other = (Entry<?, ?>) obj;
			return Objects.equals(key, other.key) && Objects.equals(value, other.value);
		}
		
	}

	/**
	 * Instantiates a new dictionary.
	 */
	public Dictionary() {
		dictionary = new ArrayIndexedCollection<>();
	}

	/**
	 * Checks if is empty.
	 *
	 * @return true, if is empty
	 */
	boolean isEmpty() {
		return dictionary.isEmpty();
	}

	/**
	 * Size.
	 *
	 * @return the int
	 */
	int size() {
		return dictionary.size();
	}

	/**
	 * Clear.
	 */
	void clear() {
		dictionary.clear();
	}

	/**
	 * Put.
	 *
	 * @param key the key
	 * @param value the value
	 * @return the v
	 * @throws NullPointerException if key is null
	 */
	V put(K key, V value) {
		if (key == null) {
			throw new NullPointerException();
		}

		Entry<K, V> entry = getEntry(key);
		V oldValue = null;

		if (entry == null) {
			dictionary.add(new Entry<K, V>(key, value));
		} else {
			oldValue = entry.value;
			entry.value = value;
		}
		return oldValue;
	}

	/**
	 * Gets the.
	 *
	 * @param key the key
	 * @return the v
	 */
	V get(Object key) {
		if (key == null) {
			throw new NullPointerException();
		}

		Entry<K, V> entry = getEntry(key);
		if(entry != null) {
			return entry.value;			
		}
		
		return null;
	}

	/**
	 * Removes the.
	 *
	 * @param key the key
	 * @return the v
	 * @throws NullPointerException if key is null
	 */
	V remove(K key) {
		if (key == null) {
			throw new NullPointerException();
		}
		
		Entry<K, V> entry = getEntry(key);
		V value = null;
		
		if(entry != null) {
			value = entry.value;
			dictionary.remove(entry);
		}
		
		return value;
	}

	/**
	 * Gets the entry.
	 *
	 * @param key the key
	 * @return the entry, null if entry does not exists
	 */
	private Entry<K, V> getEntry(Object key) {
		ElementsGetter<Entry<K, V>> it = dictionary.createElementsGetter();
		Entry<K, V> entry;

		while (it.hasNextElement()) {
			entry = it.getNextElement();
			if (entry.key.equals(key)) {
				return entry;
			}
		}
		return null;
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		return Objects.hash(dictionary);
	}

	/**
	 * Equals.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dictionary<?, ?> other = (Dictionary<?, ?>) obj;
		return Objects.equals(dictionary, other.dictionary);
	}
	
	

}
