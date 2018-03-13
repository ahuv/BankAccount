package bank;

import java.time.LocalDate;
import java.util.ArrayList;

public class Bank 
{
	private String name = "";
	private ArrayList<BankAccount> accounts = new ArrayList<BankAccount>();

	
	/**
	 * Sets up a new Bank with an arrayList to hold accounts.
	 */
	public Bank()
	{
		accounts = new ArrayList<BankAccount>();
	}
	

	public void setName(String name) 
	{
		this.name = name;
	}
	
	public String getName() 
	{
		return name;
	}
	
	/**
	 * The addAccount method adds an account to the ArrayList of BankAccount objects.
	 * Throws exception if the ArrayList already contains an account with the specified account ID,
	 * if the ID is improperly formatted, or if the initial account balance is lower than the required amount.
	 * @param acctID The account identification number.
	 * @param amt The initial account balance.
	 */
	public void addAccount(String acctID, double amt) throws InvalidAmountException,InvalidAccountException,NumberFormatException,InvalidIDException
	{
		try{	
		BankAccount myAcct = new BankAccount(acctID, amt);
		
		for(BankAccount account : accounts)
		{
			if(account.getAccountID().equals(myAcct.getAccountID()))
			{
				throw new InvalidAccountException("Error: duplicate account.");
			}
		}
		accounts.add(myAcct);
		}
		
		catch(InvalidAmountException e){
			throw e;
		}
		catch(InvalidAccountException e){
			throw e;
		}
		catch(InvalidIDException e)
		{
			throw e;
		}
		catch(NumberFormatException e){
			throw e;
		}
	}
	
	/**
	 * The closeAccount method removes an existing account from the ArrayList.
	 * Passes the given account ID to findAccount(); if account exists, it is removed,
	 * otherwise, exception is thrown.
	 * @param acctID The account identification number.
	 */
	public void closeAccount(String acctID)
	{
		try{
		BankAccount account = findAccount(acctID);
		accounts.remove(account);
		}
		catch(InvalidAccountException e){
			throw e;
		}
	}
	
	/**
	 * The findAccount method checks if an account specified by the account ID exists in the ArrayList.
	 * Throws exception if the account with the specified account id is not found.
	 * @param acctID The account identification number.
	 * @return account A BankAccount object.
	 */
	private BankAccount findAccount(String acctID) throws InvalidAccountException
	{
		for(BankAccount account : accounts)
		{
			if(account.getAccountID().equals(acctID))
			{
				return account;
			}
		}
		throw new InvalidAccountException("Error: account not found.");
	}
	
	/**
	 * The deposit method deposits a specified amount of money to a specified account.
	 * Throws exception if the deposit amount is zero or less.
	 * @param acctID The account identification number.
	 * @param amt The amount the user wants to deposit to the account.
	 */
	public void deposit(String acctID, double amt)
	{
		try{
		BankAccount account = findAccount(acctID);
		account.deposit(amt);
		}
		catch(InvalidAmountException e){
			throw e;
		}
	}
	
	/**
	 * The withdraw method withdraws a specified amount of money from a specified account.
	 * Throws exception if the requested amount is more than the current account balance.
	 * @param acctID The account identification number.
	 * @param amt The amount the user wants to withdraw from the account.
	 */
	public void withdraw(String acctID, double amt)
	{
		try{
		BankAccount account = findAccount(acctID);
		account.withdraw(amt);
		}
		catch(LowBalanceException e){
			throw e;
		}
	}
	
	/**
	 * The accountBal method returns the account balance of a specified account.
	 * @param acctID The account identification number.
	 * @return acctBal The account balance.
	 */
	public double accountBal(String acctID)
	{
		BankAccount account = findAccount(acctID);
		double acctBal = account.getCurrentBal();
		
		return acctBal;
	}
	
	/**
	 * the getTotal method sums the current balance of all BankAccount accounts
	 * in the account array.
	 * @return total The current total of all account balances.
	 */
	public double getTotal()
	{
		double total = 0;
		for(BankAccount account : accounts)
		{
			total+=account.getCurrentBal();
		}
		return total;
	}
	
	/**
	 * The getAccountList method iterates through the ArrayList and 
	 * prints the toString String of each BankAccount object.
	 */
	public ArrayList<BankAccount> getAccountList()
	{
		ArrayList<BankAccount>accts = new ArrayList<>();
		for(BankAccount account : accounts)
		{
			accts.add(new BankAccount(account));
		}
		return accounts;
	}
	
	/**
	 * The getDeposits method creates an ArrayList containing all deposits related to a specified account.
	 * @param acctID The account identification number.
	 * @return deposits An ArrayList of deposit transactions
	 */
	public ArrayList<Transaction> getDeposits(String acctID)
	{
		BankAccount account = findAccount(acctID);
		ArrayList<Transaction> deposits = new ArrayList<Transaction>(account.getDeposits());
		return deposits;
	}
	
	/**
	 * The getWithdrawals method creates an ArrayList containing all withdrawals related to a specified account.
	 * @param acctID The account identification number.
	 * @return withdrawals An ArrayList of withdrawal transactions
	 */
	public ArrayList<Transaction> getWithdrawals(String acctID)
	{
		BankAccount account = findAccount(acctID);
		ArrayList<Transaction> withdrawals = new ArrayList<Transaction>(account.getWithdrawals());
		return withdrawals;
	}
	
	/**
	 * The getTransactions method creates an ArrayList containing all transactions
	 * for as specified account in a specified date range.
	 * @param acctID The account identification number.
	 * @param fromDate The start date of the range
	 * @param toDate The end date of the range
	 * @return datedTransac An ArrayList of date-specified transactions
	 */
	public ArrayList<Transaction> getTransactions(String acctID, LocalDate fromDate, LocalDate toDate)
	{
		BankAccount account = findAccount(acctID);
		LocalDate first = fromDate;
		LocalDate last = toDate;
		ArrayList<Transaction> datedTransac = new ArrayList<Transaction>(account.getTransactions(fromDate, toDate));
		return datedTransac;
	}
	
	
	/**
	 * The getTotalDeposits method sums deposits of all bank accounts.
	 * @return totalDeposits the sum of all account deposits
	 */
	public double getTotalDeposits()
	{
		double totalDeposits = 0;
		for(BankAccount account : accounts)
		{
			totalDeposits += account.getTotalDeposits();
		}
		return totalDeposits;
	}
	
	/**
	 * The getTotalDeposits method sum all deposits of a specified account.
	 * @param acctID The account identification number.
	 * @return acctDeposits the sum of all deposits
	 */
	public double getTotalDeposits(String acctID)
	{
		BankAccount account = findAccount(acctID);
		
		double acctDeposits = account.getTotalDeposits();
		return acctDeposits;
	}
	
	/**
	 * The getTotalWithdrawals method sums withdrawals of all bank accounts.
	 * @return totalWithdrawals the sum of all account withdrawals
	 */
	public double getTotalWithdrawals()
	{
		double totalWithdrawals = 0;
		for(BankAccount account : accounts)
		{
			totalWithdrawals += account.getTotalWithdrawals();
		}
		return totalWithdrawals;
	}
	
	/**
	 * The getTotalWithdrawals method sums all withdrawals of a specified account.
	 * @param acctID The account identification number.
	 * @return acctWithdrawals the sum of all withdrawals
	 */
	public double getTotalWithdrawals(String acctID)
	{
		BankAccount account = findAccount(acctID);
		
		double acctWithdrawals = account.getTotalWithdrawals();
		return acctWithdrawals;
	}
	 

	@Override
	public String toString() 
	{
		return "Bank [name=" + name + ", accounts=" + accounts + "]";
	}


	
	

}
