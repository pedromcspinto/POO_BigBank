package bank;
import java.util.Iterator;

import bank.exceptions.InexistentAccountException;
import bank.exceptions.InexistentClientsException;
import bank.exceptions.InsuficientBalanceException;

public interface Bank {
	int numberOfAccounts();

	void openCheckingAccount(String client, double amount);
	void openSavingsAccount(String client, double amount, 
			int savingDays, double interestRate);
	double closeAccount(String accountID) throws InexistentAccountException;
	void updateInterests(String accountID) throws InexistentAccountException;
	void updateAccount(String accountID, double amount) 
			throws InexistentAccountException, InsuficientBalanceException;
	
	Iterator<String> accountsIDs();
	Iterator<String> clients();  

	Iterator<Account> accounts();
	Iterator<Account> accounts(String client);
	
	Iterator<Account> accountsClients(String[] clients) throws InexistentClientsException;
	
	Iterator<Account> accountsBalanceGreaterThan(double amount);	
	Iterator<Account> accountsInterestGreaterThan(double rate);
	Iterator<Account> accountsToPayInterestToday();

	Iterator<Transaction> getTransactions(String accountID) throws InexistentAccountException;
}
