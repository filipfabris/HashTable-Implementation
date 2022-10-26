/*
 * 
 */
package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;

import java.util.Iterator;

import java.util.NoSuchElementException;
import java.util.Objects;
import static java.lang.Math.abs;



// TODO: Auto-generated Javadoc
/**
 * The Class SimpleHashtable.
 *
 * @author filip fabris
 * @param <K> the key type
 * @param <V> the value type
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	/**
	 * The Class TableEntry used as an entry of SimpleHashtable.
	 *
	 * @author filip fabris
	 * @param <K> the key type
	 * @param <V> the value type
	 */
	public static class TableEntry<K, V> {
		
		/** The key of the given entry. */
		K key;
		
		/** The value of given entry. */
		V value;
		
		/** The next element of given entry. */
		TableEntry<K, V> next;

		/**
		 * Instantiates a new table entry.
		 *
		 * @param key the key
		 * @param value the value
		 */
		TableEntry(K key, V value) {
			if (key == null) {
				throw new NullPointerException();
			}
			this.key = key;
			this.value = value;
			this.next = null;
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
			TableEntry<?, ?> other = (TableEntry<?, ?>) obj;
			return Objects.equals(key, other.key) && Objects.equals(value, other.value);
		}

		/**
		 * Gets the value.
		 *
		 * @return the value
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Sets the value.
		 *
		 * @param value the new value
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * Gets the key.
		 *
		 * @return the key
		 */
		public K getKey() {
			return key;
		}

	}

	/** The Constant DEFAULT_SIZE used in default counstructor. */
	final static int DEFAULT_SIZE = 16;
	
	/** The table which stores main entrys inside its slots. */
	TableEntry<K, V>[] table;
	
	/** The key list. */
	K[] keyList;
	
	/** Current number of elements inside TableEntry table. */
	int size;
	
	/** The modification count made to TableEntry table. */
	long modificationCount;

	/**
	 * Instantiates a new simple hashtable.
	 */
	public SimpleHashtable() {
		this(DEFAULT_SIZE);
	}

	/**
	 * Instantiates a new simple hashtable.
	 *
	 * @param capacity the capacity
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1)
			throw new IllegalArgumentException("Initial capacity ha to be larger than 1");
		table = new TableEntry[capacity];
		this.size = 0;
		this.modificationCount = 0;
	}

	/**
	 * Iterator.
	 *
	 * @return the iterator
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	/**
	 * Put method used to add entry to table.
	 *
	 * @param key the key
	 * @param value the value
	 * @return the v
	 * @throws NullPointerException key is null
	 */
	public V put(K key, V value) {
		if (key == null) {
			throw new NullPointerException("key is null");
		}

		this.checkCccupancy();

		int slot = this.getSlot(key);
		V oldValue = null;

		if (this.containsKey(key)) {
			TableEntry<K, V> element = this.table[slot];
			while (element != null) {
				if (element.key.equals(key)) {
					oldValue = element.value;
					element.value = value;
					break;
				}
				element = element.next;
			}
		} else {
			TableEntry<K, V> element = this.table[slot];
			// Ako je prvi element
			if (element == null) {
				this.table[slot] = new TableEntry<K, V>(key, value);
			} else {
				while (element.next != null) {
					if (element.key.equals(key)) {
						oldValue = element.value;
						element.value = value;
						break;
					}
					element = element.next;
				}
				element.next = new TableEntry<K, V>(key, value);
			}
			this.size++;
		}
		
		this.modificationCount++;
		
		return oldValue;
	}

	/**
	 * Gets the value of given Entry using key.
	 *
	 * @param key the key
	 * @return the value which corresponds to given key value ,
	 * null if element does not exists for given key value
	 */
	public V get(Object key) {
		if (key == null)
			return null;

		TableEntry<K, V> element = this.table[this.getSlot(key)];
		while (element != null) {
			if (element.key.equals(key)) {
				return element.value;
			}
			element = element.next;
		}

		return null;
	}

	/**
	 * Size.
	 *
	 * @return current number of elements inside TableEntry table.
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Contains key.
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	public boolean containsKey(Object key) {
		if (key == null)
			return false;

//		V element = this.get(key);
		return this.get(key) != null;
	}

	/**
	 * Contains value.
	 *
	 * @param value the value to check if this object exists in internal storage
	 * @return true, if successful
	 */
	public boolean containsValue(Object value) {
		for (TableEntry<K, V> element : this.table) {
			while (element != null) {
				if (element.value.equals(value)) {
					return true;
				}
				element = element.next;
			}
		}
		return false;
	}

	/**
	 * Removes the.
	 *
	 * @param key the key
	 * @return null if given object does not exists in internal storage
	 */
	public V remove(Object key) {
		if (key == null)
			return null;
		if (this.containsKey(key) == false)
			return null;

		int slot = this.getSlot(key);
		TableEntry<K, V> element = this.table[slot];
		V value = null;

		if (element.key.equals(key)) {
			value = element.value;
			this.table[slot] = element.next;
		} else {
			TableEntry<K, V> previous;
			while (element.next != null) {
				previous = element;
				element = element.next;

				if (element.key.equals(key)) {
					value = element.value;
					previous.next = element.next;
					break;
				}
			}
		}
		this.size--;
		this.modificationCount++;

		return value;
	}

	/**
	 * Checks if is empty.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}

	/**
	 * To string.
	 *
	 * @return the string as an array of given object stored inside internal storage unit
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");

		for (TableEntry<K, V> element : table) {
			while (element != null) {
				sb.append(element.key);
				sb.append("=");

				if (element.value != null) {
					sb.append(element.value);
				} else {
					sb.append("");
				}

				sb.append(", ");

				element = element.next;
			}
		}
		if (sb.length() > 3) {
			sb.replace(sb.length() - 2, sb.length(), "]");
		} else {
			sb.append("]");
		}
		return sb.toString();
	}

	/**
	 * To array.
	 *
	 * @return the table of array
	 */
	@SuppressWarnings("unchecked")
	public TableEntry<K, V>[] toArray() {

		TableEntry<K, V>[] array = (TableEntry<K, V>[]) new TableEntry[this.size];
		int index = 0;

		for (TableEntry<K, V> element : table) {

			while (element != null) {
				array[index] = element;
				element = element.next;
				index++;
			}
		}

		return array;
	}

	/**
	 * Clear internal storage unit.
	 */
	public void clear() {
		for (int i = 0; i < this.table.length; i++) {
			this.table[i] = null;
		}

		this.size = 0;
		this.modificationCount++;
	}
	
	/**
	 * Gets the keys.
	 *
	 * @return the keys
	 */
	@SuppressWarnings("unchecked")
	public K[] getKeys() {	
		K[] array = (K[]) new Object[this.size];
		int index = 0;
		
		for (TableEntry<K, V> element : table) {

			while (element != null) {
				array[index] = element.getKey();
				element = element.next;
				index++;
			}
		}
		return array;
	}


	/**
	 * The Class IteratorImpl which is created as iterator.
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

		/** The current element. */
		TableEntry<K, V> currentElement;

		/** The current slot. */
		int currentSlot;

		/** The next element. */
		TableEntry<K, V> nextElement;

		/** The array. */
		TableEntry<K, V>[] array;
		
		/** The size. */
		long size;
				
		/** The internal modification count. */
		long internalModificationCount;
		
		/**
		 * Instantiates a new iterator impl.
		 */
		IteratorImpl() {
			currentElement = null;
			nextElement = null;
			currentSlot = 0;
			array = SimpleHashtable.this.table;
			size = SimpleHashtable.this.size;
			internalModificationCount = SimpleHashtable.this.modificationCount;
			this.nextElement();
		}


		/**
		 * Checks for next.
		 *
		 * @return true, if successful
		 * @throws ConcurrentModificationException if internal storage unit has been changed
		 */
		public boolean hasNext() {
			this.checkModification();

			return nextElement != null;
		}

		/**
		 * Next.
		 *
		 * @return the simple hashtable. table entry
		 * @throws NoSuchElementException if there is no more elements
		 */
		public SimpleHashtable.TableEntry<K, V> next() {
			this.checkModification();

			if (this.nextElement == null)
				throw new NoSuchElementException("No more element available");

			this.currentElement = this.nextElement;

			this.nextElement();

			return currentElement;
		}

		/**
		 * Removes the.
		 * @throws ConcurrentModificationException if internal storage unit has been changed
		 */
		public void remove() {
			this.checkModification();
			
			if(currentElement == null) {
				throw new IllegalStateException("Only one element can be removed from iterator per iterator.next() call");
			}
			SimpleHashtable.this.remove(this.currentElement.key);
			this.currentElement = null;
			this.size--;
			this.internalModificationCount++;
		}

		/**
		 * Next element.
		 * @throws ConcurrentModificationException if internal storage unit has been changed
		 */
		private void nextElement() {
			this.checkModification();
			
			if (nextElement != null && nextElement.next != null) {
				nextElement = nextElement.next;
				return;
			} else {
				for (int i = currentSlot; i < array.length; i++) {
					TableEntry<K, V> temp = table[currentSlot];
					if (temp != null) {
						nextElement = temp;
						currentSlot++;
						return;
					}
					currentSlot++;
				}
			}
			nextElement = null;
			return;
		}
		
		/**
		 * Check modification.
		 * @throws ConcurrentModificationException if internal storage unit has been changed
		 */
		private void checkModification() {
			if(this.internalModificationCount != SimpleHashtable.this.modificationCount) {
				throw new ConcurrentModificationException("Dictionary has been changed");
			}
			return;
		}

	}

	/**
	 * Gets the slot.
	 *
	 * @param key the key
	 * @return the slot
	 */
	private int getSlot(Object key) {
		return abs(key.hashCode()) % this.table.length;
	}

	/**
	 * Double internal storage unit of given sorage unit.
	 */
	@SuppressWarnings("unchecked")
	private void checkCccupancy() {
		if (1.0 * this.size / (this.table.length) < 0.75) {
			return;
		}

		TableEntry<K, V>[] oldTable = this.toArray();

		this.table = (TableEntry<K, V>[]) new TableEntry[oldTable.length * 2];

		// Old elements repopulation
		for (TableEntry<K, V> element : oldTable) {
			int slot = getSlot(element.key);
			// Nuliraj prosli table next
			element.next = null;

			TableEntry<K, V> position = this.table[slot];

			if (position == null) {
				this.table[slot] = element;
			} else {
				// Ima jos elemenata
				while (position.next != null) {
					position = position.next;
				}
				// Sada je next prazan
				position.next = element;
			}
		}
		this.modificationCount++;
	}

}
