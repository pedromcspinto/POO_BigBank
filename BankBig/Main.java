import java.util.Iterator;
import java.util.Scanner;

import bank.exceptions.*;
import bank.*;

public class Main {
	public static final int MINCHECKING = 100;
	private static final int MINSAVINGS = 500;
	private static final String PROMPT = "Bank> ";
	private static final String NOACCOUNT = "Account does not exist.\n";
	private static final String INITIALBALANCE = "Initial balance %s bellow %d euros.\n";
	private static final String NEGATIVE = "Amount is negative.\n";
	private static final String DEBIT = "Current balance does not allow a withdraw of%s euros.\n";

	private enum Command {
		HELP("Ajuda"), ADDCHECKING("New banking account"), 
		ADDSAVINGS("New savings account"), CLOSE("Close account"), 
		TRANSACTIONS("List transactions"), DEBIT("Debit"), CREDIT("Credit"),
		CLIENTS("List clients"), ACCOUNTS("List accounts"),
		CACCOUNTS ("Clients accounts"),
		EXIT("Exiting..."), UNKNOWN("Unknwon command");

		private String description;

		Command (String description) { this.description = description; }
		public String toString() { return this.description; }    
	}; 

	private static Command getCommand(Scanner in) {
		try {
			System.out.printf(PROMPT);
			return Command.valueOf(in.next().toUpperCase());
		}
		catch (IllegalArgumentException e) {
			return Command.UNKNOWN;
		}
	}

	/* 
	 * So far, there is no input validation whatsoever ...
	 * 
	 * Challenge for students:
	 * 
	 *  - To check the program behavior and then adjust if required
	 * 	- To add exceptions as appropriate
	 *  - To make the program more flexible as far as accounts domain is concerned.
	 *    For instance, to pick up lists of just savings accounts, or else checking
	 *    accounts, or both
	 *  - To add comments and then create a complete Javadoc file
	 *  
	 */

	public static void main(String[] args)  {
		Scanner in = new Scanner(System.in);
		Bank bank = new BankClass();

		Command cmd = getCommand(in);
		while (!cmd.equals(Command.EXIT)) {
			switch (cmd) {
			case HELP:  
				printHelp();
				break;
			case ADDCHECKING :
				openCheckingAccount(bank, in);
				break;
			case ADDSAVINGS:
				openSavingsAccount(bank, in);
				break;
			case CLOSE :
				closeAccount(bank, in);
				break;
			case TRANSACTIONS :
				printTransactions(bank, in);						
				break;
			case DEBIT :
				debitAccount(bank, in);						
				break;
			case CREDIT :
				creditAccount(bank, in);						
				break;
			case CLIENTS : 
				printClients(bank);
				break;
			case ACCOUNTS : 
				printAccountsIds(bank);
				break;
			case CACCOUNTS : 
				ClientAccountsIds(bank,in);
				break;	
			default:
				break;
			}
			cmd = getCommand(in);
		}
		System.out.println(Command.EXIT);
	}

	private static void openCheckingAccount(Bank bank, Scanner in) {
		String client = in.next();
		String value = in.nextLine();
		double amount = Double.parseDouble(value);
		try {
			if (amount < Main.MINCHECKING)
				throw new InsuficientInitialAmountException();
			else
				bank.openCheckingAccount(client, amount);
		}
		catch (InsuficientInitialAmountException e) {
			System.out.printf(INITIALBALANCE,amount,MINCHECKING);
		}
	}

	private static void openSavingsAccount(Bank bank, Scanner in) {
		String client = in.next();
		double amount = in.nextDouble();
		int days = in.nextInt(); 
		double rate = in.nextDouble();
		try {
			if (amount < Main.MINSAVINGS) 
				throw new InsuficientInitialAmountException();
			else
				bank.openSavingsAccount(client, amount, days, rate);
		}
		catch (InsuficientInitialAmountException e) {
			System.out.printf(INITIALBALANCE,amount,MINSAVINGS);
		}
	}

	private static void closeAccount(Bank bank, Scanner in) {
		String accountID = in.next();
		try {
			bank.closeAccount(accountID);
		}
		catch (InexistentAccountException e) {
			System.out.printf(NOACCOUNT);
		}
	}

	private static void debitAccount(Bank bank, Scanner in) {
		String accountID = in.next();
		String value = in.nextLine();
		double amount = Double.parseDouble(value);
		try {
			if (amount < 0)
				throw new NegativeAmountException();
			bank.updateAccount(accountID, amount);	
		}
		catch(NegativeAmountException e) {
			System.out.printf(NEGATIVE);
		}
		catch(InexistentAccountException e) {
			System.out.printf(NOACCOUNT);
		}
		catch(InsuficientBalanceException e) {
			System.out.printf(DEBIT,value);
		}
	}

	private static void creditAccount(Bank bank, Scanner in) {

		String accountID = in.next();
		String value = in.nextLine().trim();
		double amount = Double.parseDouble(value);
		try {
			if (amount < 0)
				throw new NegativeAmountException();
			bank.updateAccount(accountID, amount);	
		}
		catch(NegativeAmountException e) {
			System.out.printf(NEGATIVE);
		}
		catch(InexistentAccountException e) {
			System.out.printf(NOACCOUNT);
		}
		catch(InsuficientBalanceException e) {
			System.out.printf(DEBIT);
		}
	}

	private static void ClientAccountsIds(Bank bank, Scanner in) {
		try {
			int num = in.nextInt(); in.nextLine();
			String[] names = new String[num];
			for (int i=0; i<num; i++)
				names[i] = in.nextLine();
			Iterator<Account> it = bank.accountsClients(names);
			while (it.hasNext()) {
				Account account = it.next();
				System.out.println(account.getAccountID() + " " + account.getClient());
			}
		}
		catch(InexistentClientsException e) {
			Iterator<String> it = e.getNonClientNames();
			while (it.hasNext())
				System.out.println(it.next());
		}
	}

	private static void printTransactions(Bank bank, Scanner in) {
		String accountID = in.next();
		try {
			Iterator<Transaction> it = bank.getTransactions(accountID);	
			printIterator(it);
		}
		catch(InexistentAccountException e) {
			System.out.printf(NOACCOUNT);
		}
	}

	private static void printAccountsIds(Bank bank) {
		System.out.println("Number of accounts: " + bank.numberOfAccounts());
		Iterator<String> it = bank.accountsIDs();
		printIterator(it);
	}

	private static void printClients(Bank bank ) {
		System.out.println("Clients:");
		Iterator<String> it = bank.clients();
		printIterator(it);
	}

	private static void printHelp() {
		StringBuilder str = new StringBuilder();
		str.append("ADDCHECKING: \t" + Command.ADDCHECKING.toString() + "\t: [S]name + [D]value.\n");
		str.append("ADDSAVINGS: \t" + Command.ADDSAVINGS.toString() + "\t: [S]name + [D]value + [I]days + [D]interest.\n");
		str.append("CLOSE: \t\t" + Command.CLOSE.toString() + "\t\t: [S]account.\n");
		str.append("TRANSACTIONS: \t" + Command.TRANSACTIONS.toString() + "\t: [S]account.\n");
		str.append("DEBIT: \t\t" + Command.DEBIT.toString() + "\t\t\t: [S]name + [D]value.\n");
		str.append("CREDIT: \t" + Command.CREDIT.toString() + "\t\t\t: [S]name + [D]value.\n");
		str.append("CLIENTS: \t" + Command.CLIENTS.toString() + "\t\t: \n");
		str.append("ACCOUNTS: \t" + Command.ACCOUNTS.toString() + "\t\t: \n");
		str.append("HELP: \t\t" + Command.HELP.toString() + "\t\t\t: \n");
		str.append("EXIT: \t\t" + Command.EXIT.toString() + "\t\t: \n");
		System.out.println(str);
	}

	private static <E> void printIterator(Iterator<E> it) {
		while (it.hasNext())
			System.out.println(it.next().toString());	
	}
}
