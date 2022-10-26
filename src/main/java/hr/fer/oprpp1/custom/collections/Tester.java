package hr.fer.oprpp1.custom.collections;


/**
 * Interface tester which tests given object and return true or false
 * @author filip fabris
 * @param <T> type
 */
public interface Tester<T> {
	
	/**
	 * @param obj to test 
	 * @return true or false deppending on implemented test on Object obj
	 */
	abstract boolean test(T obj);

}
