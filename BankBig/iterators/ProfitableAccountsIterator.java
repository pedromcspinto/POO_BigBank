package iterators;

import java.util.Iterator;

import bank.Account;
import bank.Savings;

public class ProfitableAccountsIterator implements Iterator<Account> {

	private Iterator<Account> it;
	private double rate;
	private Account current;
	
	public ProfitableAccountsIterator(Iterator<Account> it,double rate) {
		this.rate = rate;
		this.it = it;
		searchNext();
	}
	
	private void searchNext() {
		while (it.hasNext()) {
			current = it.next();
			if ((current instanceof Savings) && ((Savings)current).getAnnualInterestRate() > rate) 
				return;
		}
		current = null;
	}
	
	public boolean hasNext() {
		return current != null;
	}
	
	public Account next() {
		Account res = current;
		searchNext();
		return res;
	}

	@Override
	public void remove() {	}

}
