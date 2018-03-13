package bank;

import java.time.LocalDate;
import java.util.ArrayList;


public class BankAccount
{
	private String accountID;
	private double initBal;
	private double currentBal;
	private LocalDate accountOpened;
	protected ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	

	//copy constructor
	public BankAccount(BankAccount account)
	{
		accountID = account.accountID;
		initBal = account.initBal;
		currentBal = account.currentBal;
		accountOpened = account.accountOpened;
	}

	/**
	 * The BankAccount constructor sets up a new bank account, using the account ID
	 * and starting balance set by user.
	 * If starting balance is less than the required $100.00, exception is thrown.
	 * @param acctID The account identification string.
	 * @param startBalance The initial account balance set by user.
	 */
	public BankAccount(String acctID, double startBalance)throws InvalidAmountException, InvalidIDException, NumberFormatException
	{
		if(acctID.length() == 4)
		{
				accountID = acctID;
				try{
					int IDnum = Integer.parseInt(accountID);
				}
				catch(NumberFormatException e){
					throw e;
				}
		}
		else
		{
			throw new InvalidIDException("Error");
		}
		
		if(startBalance >= 100)
		{
			initBal = startBalance;
			currentBal = initBal;
			accountOpened = LocalDate.now();
			transactions = new ArrayList<Transaction>();
		}
		else
		{
			throw new InvalidAmountException("Starting balance must be at least $100.00.");
		}
	}
	
	/**
	 * The deposit method makes a deposit into the account,
	 * with the amount set by the user.
	 * Throws exception if deposit amount is <= $0.00.
	 * @param amt The amount to add to the current balance.
	 */
	public void deposit(double amt)throws InvalidAmountException
	{
		Transaction deposit = new Deposit(amt);
		transactions.add(deposit);
		
		if (amt > 0)
		{
			currentBal += amt;
		}
		else
		{
			throw new InvalidAmountException("Deposit Error.");
		}
	}
	
	/**
	 * The withdrawal method withdraws an amount from the account,
	 * with the amount set by the user.
	 * Throws exception if the account balance is less than the requested withdrawal amount.
	 * @param amt The amount to subtract from the current balance.
	 */
	public void withdraw(double amt)throws LowBalanceException
	{
		if (amt > 0 && amt <= currentBal)
		{
			Transaction withdraw = new Withdrawal(amt);
			transactions.add(withdraw);
			currentBal -= amt;
		}
		else
		{
			throw new LowBalanceException("Withdrawal Error.");
		}	
	}
	
	/**
	 * The getDeposits method makes a deep copy of all deposits processed for the account.
	 * @return deposits An ArrayList of deposits
	 */
	public ArrayList<Deposit> getDeposits()
	{
		ArrayList<Deposit> deposits = new ArrayList<Deposit>();
		for (Transaction t: transactions)
		{
			if(t instanceof Deposit)
			{
				deposits.add(new Deposit ((Deposit)(t)));
			}
		}
		return deposits;
	}
	
	/**
	 * The getTotalDeposits method sums the total of all deposits processed for the account.
	 * @return totalD The sum of all deposits
	 */
	public Double getTotalDeposits()
	{
		double totalD = initBal;
		for(Transaction t : transactions)
		{
			if(t instanceof Deposit)
			{
				totalD += t.getAmount();
			}
		}
		return totalD;
	}
	
	/**
	 * The getWithdrawals method makes a deep copy of all withdrawals processed for the account.
	 * @return withdrawals An ArrayList of withdrawals
	 */
	public ArrayList<Withdrawal> getWithdrawals()
	{
		ArrayList<Withdrawal> withdrawals = new ArrayList<Withdrawal>();
		for (Transaction t: transactions)
		{
			if(t instanceof Withdrawal)
			{
				withdrawals.add(new Withdrawal ((Withdrawal)(t)));
			}
		}
		return withdrawals;
	}
	
	/**
	 * The getTotalDeposits method sums the total of all withdrawals processed for the account.
	 * @return totalW The sum of all withdrawals
	 */
	public Double getTotalWithdrawals()
	{
		double totalW = 0;
		for(Transaction t : transactions)
		{
			if(t instanceof Withdrawal)
			{
				totalW += t.getAmount();
			}
		}
		return totalW;
	}
	
	/**
	 * The getTransactions method makes a deep copy of all transactions processed for the account.
	 * @return copy An ArrayList of all transactions
	 */
	public ArrayList<Transaction> getTransactions()
	{
		ArrayList<Transaction> copy = new ArrayList<Transaction>();
		for(Transaction t : transactions)
		{
			if(t instanceof Deposit)
			{
				copy.add(new Deposit((Deposit)(t)));
			}
			else {
				if(t instanceof Withdrawal)
				{
					copy.add(new Withdrawal((Withdrawal)(t)));
				}
			}
		}
		return copy;
	}
	
	/**
	 * The getTransactions method makes a deep copy of all transactions in a date range 
	 * specified by the user with the most recent transactions listed first.
	 * If the specified start date comes chronologically after the specified end date, throw exception
	 * for an invalid date range
	 * @param fromDate The start date of the range
	 * @param toDate The end date of the range
	 * @return datedCopy an ArrayList containing specified-by-date transactions
	 */
	public ArrayList<Transaction> getTransactions(LocalDate fromDate, LocalDate toDate) throws InvalidAmountException, InvalidDateException
	{
		if(fromDate.compareTo(toDate) > 0)
		{
			throw new InvalidDateException("Start date must be before end date.");
		}
		
		ArrayList<Transaction> transac = getTransactions();
		
		ArrayList<Transaction> datedCopy = new ArrayList<Transaction>();
		
		for(int i = transac.size() - 1; i >= 0; i--)
		{
				if(transac.get(i).getDate().compareTo(fromDate) >= 0 && transac.get(i).getDate().compareTo(toDate) <= 0)
				{
					if(transac.get(i) instanceof Deposit)
					{
						datedCopy.add(new Deposit((Deposit)(transac.get(i))));
					}
					else if(transac.get(i) instanceof Withdrawal)
					{
						datedCopy.add(new Withdrawal((Withdrawal)(transac.get(i))));
					}
				}
		}
		return datedCopy;
	}
	
	/**
	 * The compareTo method compares accounts based on
	 * the value of the accountID string associated with the account.
	 * @param other
	 * @return
	 */
	public int compareTo(BankAccount other) 
	{
		return this.accountID.compareTo(other.accountID);
	}
	
	/**
	 * The equals method
	 */
	@Override
	public boolean equals(Object other)
	{
		if (this == other)
		{
			return true;
		}
		if (other == null)
		{
			return false;
		}
		if (other instanceof BankAccount)
		{
			BankAccount another = (BankAccount)other;
			return this.accountID.equals(another.accountID);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * The toString method returns a String containing the account information.
	 */
	@Override
	public String toString() 
	{

		String account = "BankAccount [accountID=" + accountID + ", initBal=" + initBal + ", currentBal=" + currentBal
				+ ", accountOpened=" + accountOpened + "]" ;
		return account;
	}
	
	// getters/setters for accountID, initBal, currentBal, accountOpened
	public String getAccountID() {
		return accountID;
	}
	
	public Double getInitBal() {
		return initBal;
	}

	public Double getCurrentBal() {
		return currentBal;
	}

	public LocalDate getAccountOpened() {
		return accountOpened;
	}

	
	

}
