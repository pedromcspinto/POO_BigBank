package bank;

import iterators.ClientAccountsIterator;
import iterators.DueAccountsIterator;
import iterators.ProfitableAccountsIterator;
import iterators.RichAccountsIterator;

import java.util.*;

import bank.exceptions.InexistentAccountException;
import bank.exceptions.InexistentClientsException;
import bank.exceptions.InsuficientBalanceException;

public class BankClass implements Bank {

	private static final int NUMACCOUNTS = 500;
	private static final int NUMCLIENTS = 50;

	//accountID -> Account
	private Map<String, Account> accounts;
	//clientID -> SortedSet<Account>
	private Map<String, SortedSet<Account>> accountsperclient;

	public BankClass() {
		this.accounts = new HashMap<String, Account>(NUMACCOUNTS);
		this.accountsperclient = 
			new HashMap<String,SortedSet<Account>>(NUMCLIENTS);
	}

	private void addClientAccount(Account acc, String client) {
		//Map<String, SortedSet<Account>> accountsperclient;
		SortedSet<Account> s = accountsperclient.get(client);
		if (s == null) {
			s = new TreeSet<Account>();
			accountsperclient.put(client,s);
		}
		s.add(acc);
	}
	
	public void openCheckingAccount(String client, double amount) {
		Account acc = 
			new CheckingAccountClass(client, amount, 
					new GregorianCalendar());
		// Map<String, Account> accounts;
		accounts.put(acc.getAccountID(), acc);
		addClientAccount(acc,client);
	}
	
	public int numberOfAccounts() {
		return accounts.size();
	}

	public void openSavingsAccount(String client,
			double amount, int savingDays, double interestRate) {
		Account acc = new SavingsAccountClass(client, 
				new GregorianCalendar(), amount, 
				savingDays, interestRate);
		accounts.put(acc.getAccountID(), acc);
		addClientAccount(acc,client);
	}
	
	public void updateAccount(String accountID, double amount) 
			throws InexistentAccountException, InsuficientBalanceException{
		Account acc = accounts.get(accountID);
		
		if (acc == null) throw new InexistentAccountException();
		if (! acc.canWithdraw(amount)) throw new InsuficientBalanceException();
		
		if (acc instanceof Savings)
			((Savings)acc).updateBalance(amount);
		else if (acc instanceof Checking)
			((Checking)acc).addTransaction(new GregorianCalendar(),amount);		
	}

	public double closeAccount(String accountID) throws InexistentAccountException{
		Account acc = accounts.remove(accountID);
		
		if (acc == null) throw new InexistentAccountException();
		
		Set<Account> s = accountsperclient.get(acc.getClient());
		s.remove(acc);
		if (s.isEmpty())
			accountsperclient.remove(acc.getClient());
		return acc.getBalance();
	}

	public Iterator<String> accountsIDs() {
		return accounts.keySet().iterator();
	}

	public Iterator<String> clients() {
		return accountsperclient.keySet().iterator();
	}
	
	public Iterator<Account> accounts() {
		return accounts.values().iterator();
	}

	public Iterator<Account> accounts(String client) {
		return accountsperclient.get(client).iterator();
	}
	
	public Iterator<Transaction> getTransactions(String accountID) 
			throws InexistentAccountException {
		Account acc = accounts.get(accountID);
		
		if (acc == null) throw new InexistentAccountException();
		
		Checking c = (Checking)accounts.get(accountID);
		return c.getTransactions();
	}

	public Iterator<Account> accountsBalanceGreaterThan(double amount) {
		return new RichAccountsIterator(accounts.values().iterator(),amount);
	}

	public Iterator<Account> accountsInterestGreaterThan(double rate) {
		return new ProfitableAccountsIterator(accounts.values().iterator(),rate);
	}

	public Iterator<Account> accountsToPayInterestToday() {
		return new DueAccountsIterator(accounts.values().iterator(), new GregorianCalendar());
	}

	public Iterator<Account> accountsClients(String[] clients) 
		throws InexistentClientsException {
		List<String> nonClients = new ArrayList<String>(clients.length);
		for (int i = 0; i < clients.length; i++)
			if (!accountsperclient.containsKey(clients[i]))
				nonClients.add(clients[i]);
		
		if (nonClients.size() > 0)
			throw new InexistentClientsException(nonClients);
		
		return new ClientAccountsIterator(accountsperclient, clients);
	}
	
	public void updateInterests(String accountID) throws InexistentAccountException{
		Account acc = accounts.get(accountID);
		
		if (acc == null) throw new InexistentAccountException();
		Savings s = (Savings)accounts.get(accountID);
		s.updateInterests(new GregorianCalendar());
	}
}
