package iterators;

import java.util.Iterator;

import bank.Account;

public class RichAccountsIterator implements Iterator<Account> {

	private Iterator<Account> it;
	private double amount;
	private Account current;
	
	public RichAccountsIterator(Iterator<Account> it, double amount) {
		this.it = it;
		this.amount = amount;
		searchNext();
	}
	
	private void searchNext() {
		while (it.hasNext()) {
			current = it.next();
			if (current.getBalance() > amount)
				return;
		}
		current = null;
	}
	
	@Override
	public boolean hasNext() {
		return current != null;
	}

	@Override
	public Account next() {
		Account res = current;
		searchNext();
		return res;
	}

	@Override
	public void remove() { }

}
