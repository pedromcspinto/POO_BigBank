package bank.exceptions;

import java.util.Iterator;
import java.util.List;

@SuppressWarnings("serial")
public class InexistentClientsException extends Exception {
	List<String> names; 
	
	public InexistentClientsException(List<String> names) {
		super();
		this.names = names;
	}

	public Iterator<String> getNonClientNames() {
		return names.iterator();
	}
}
