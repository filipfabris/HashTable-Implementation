/*
 * 
 */
package hr.fer.oprpp1.custom.collections;

// TODO: Auto-generated Javadoc
/**
 * The Class ObjectStack.
 * @author filip
 * @param <T> the generic type
 */
public class ObjectStack<T> {

	/** The stack. */
	ArrayIndexedCollection<T> stack = new ArrayIndexedCollection<>();

	/**
	 * Store the collection of objects. It is based on Last-In-First-Out
	 * Using Adapter pattern of ArrayIndexedCollection
	 */
	public ObjectStack(){

	}

	/**
	 * Checks if is empty.
	 *
	 * @return Returns true if stack contains no objects and false otherwise
	 */
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	/**
	 * Current size of stack.
	 *
	 * @return integer size
	 */
	public int size() {
		return stack.size();
	}

	/**
	 * Pushes given value on the stack.<br>
	 * Null value must not be allowed to be placed on stack.
	 * @param value to be added to stack
	 * @throws NullPointerException if other collection is null
	 */
	public void push(T value) {
		stack.add(value);
	}

	/**
	 * Removes last value pushed on stack from stack and returns it.
	 *
	 * @return last value pushed on stack
	 * @throws EmptyStackException if the stack is empty when method pop is called
	 */
	public T pop() {
		if (this.isEmpty()) {
			throw new EmptyStackException("Stack is empty");
		}
		T temp = stack.get(stack.size()-1);
		stack.remove(size()-1);
		return temp;
	}

	/**
	 * Returns last element placed on stack but does not delete it from stack.
	 *
	 * @return last value pushed on stack
	 * @throws EmptyStackException if the stack is empty when method pop is called
	 */
	public T peek() {
		if (this.isEmpty()) {
			throw new EmptyStackException("Stack is empty");
		}
		T temp = stack.get(stack.size()-1);
		return temp;
	}

	/**
	 * Removes all elements from this collection.
	 */
	public void clear() {
		stack.clear();
	}

}
