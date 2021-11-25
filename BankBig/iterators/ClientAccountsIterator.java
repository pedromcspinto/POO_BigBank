package iterators;

import java.util.*;

import bank.Account;

public class ClientAccountsIterator implements Iterator<Account> {

	private Map<String, SortedSet<Account>> clientaccounts;
	private Iterator<Account> current;
	private int index;
	private String[] clients;

	public ClientAccountsIterator(Map<String, SortedSet<Account>> accountsperclient,
			String[] clients) {
		index = 0;
		clientaccounts = accountsperclient;
		this.clients = clients;
		current = clientaccounts.get(clients[index++]).iterator();
	}

	@Override
	public boolean hasNext() {
		return (current.hasNext() || index < clients.length);
	}

	@Override
	public Account next() {
		Account res = current.next();
		if (!current.hasNext()) {
			current = clientaccounts.get(clients[index++]).iterator();
		}
		return res;
	}

	@Override
	public void remove() { }

}
