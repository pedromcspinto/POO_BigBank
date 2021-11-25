package iterators;

import java.util.GregorianCalendar;
import java.util.Iterator;

import bank.Account;
import bank.Savings;

public class DueAccountsIterator implements Iterator<Account> {

	private Iterator<Account> it;
	private GregorianCalendar today;
	private Account current;
	
	public DueAccountsIterator(Iterator<Account> accounts, GregorianCalendar today) {
		this.it = accounts;
		this.today = today;
		searchNext();
	}

	private void searchNext() {
		while (it.hasNext()) {
			current = it.next();
			if ((current instanceof Savings) && ((Savings)current).dueToday(today)) 
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
