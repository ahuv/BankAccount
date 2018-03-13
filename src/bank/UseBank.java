package bank;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;


public class UseBank 
{
	public static Scanner kbd = new Scanner(System.in);
	
	public static void main (String args[]) throws InvalidAccountException,InvalidAmountException,InvalidDateException,LowBalanceException
	{
		Bank myBank = new Bank();
		//getBankInfo(myBank);
		int choice;
		int subchoice;
		while(true)
		{
			choice = menu();
			switch(choice)
			{
			case 0:
				System.out.println("Ending application...");
				System.out.println("Bank application is closed.");
				System.exit(0);
				break;
			case 1:	//create new account
				newAccount(myBank);
				break;
			case 2:	//close account
				closeAccount(myBank);
				break;
			case 3:	//manage account
				System.out.println("What is your account number?");
				String acctNum = kbd.next();
				//check if account exists in the ArrayList;
				//if it does, allow operations, otherwise, throw exception.
				try{
					subchoice = subMenu();
					switch(subchoice)
					{
					case 1: //deposit
						try{
						System.out.println("How much money would you like to deposit?");
						double amt = kbd.nextDouble();
						myBank.deposit(acctNum, amt);
						System.out.printf("A deposit of $%,.2f was made to account " + acctNum, amt);
						}
						catch(InvalidAmountException e){
							System.out.println("Invalid deposit amount: deposit amount must be greater than $0.00.");
						}
						break;
					case 2: //withdraw
						try{
						System.out.println("How much money would you like to withdraw?");
						double amt = kbd.nextDouble();
						myBank.withdraw(acctNum, amt);
						System.out.printf("A withdrawal of $%,.2f was made from account " + acctNum, amt);
						}
						catch(LowBalanceException e){
							System.out.println("Invalid withdrawal amount: requested amount is not available in your account.");
						}
						break;
					case 3: //get balance
						double balance = myBank.accountBal(acctNum);
						System.out.printf("Your current account balance is $%,.2f", balance);
						break;
					case 4: //get deposits
						ArrayList<Transaction> deposits = myBank.getDeposits(acctNum);
						System.out.println("Please note: \nThe initial deposit given upon account initiation is not included in the list of account deposits.");
						System.out.println(deposits);
						break;
					case 5: //get total deposits
						double ttlDeposits = myBank.getTotalDeposits(acctNum);
						System.out.printf("The sum of all deposits is $%,.2f", ttlDeposits);
						break;
					case 6: //get withdrawals
						ArrayList<Transaction> withdrawals = myBank.getWithdrawals(acctNum);
						System.out.println(withdrawals);
						break;
					case 7: //get total withdrawals
						double ttlWithdrawals = myBank.getTotalWithdrawals(acctNum);
						System.out.printf("The sum of all withdrawals is $%,.2f", ttlWithdrawals);
						break;
					case 8: //get transactions
						try{
						System.out.println("Please note:\nDates should be entered in mm/dd/yyyy format.");
						System.out.println("Enter the date of the first transaction to view: ");
						String inputFrom = kbd.next();
						System.out.println("Enter the date of the last transaction to view: ");
						String inputTo = kbd.next();
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
						LocalDate fromDate = LocalDate.parse(inputFrom, formatter);
						LocalDate toDate = LocalDate.parse(inputTo, formatter);
						ArrayList<Transaction> transactions = myBank.getTransactions(acctNum, fromDate, toDate);
						System.out.println(transactions);
						}
						catch(InvalidDateException e)
						{
							System.out.println("The range must have a start date that chronologically precedes the end date.");
						}
						break;
					default: //if user choice is not an option
						System.out.println("Invalid option. Please reenter your menu choice: ");
						break;
					}
				}
				catch(InvalidAccountException e)
				{
					System.out.println("Error: account number " + acctNum + " does not exist.");
				}
				break;
			case 4:	//get total bank balance
				double total = myBank.getTotal();
				System.out.printf("Total bank balance is $%,.2f\n", total);
				double totalDepo = myBank.getTotalDeposits();
				System.out.printf("Total of deposits to bank is $%,.2f\n", totalDepo);
				double totalWthd = myBank.getTotalWithdrawals();
				System.out.printf("Total of withdrawals from bank is $%,.2f\n", totalWthd);
				break;
			case 5:	//list accounts
				ArrayList<BankAccount>accounts = myBank.getAccountList();
				System.out.println(accounts);
				break;
			default: //if user choice is not an option
				System.out.println("Invalid option. Please reenter your menu choice: ");
				break;
			}
		}
	}
	
	/**
	 * The getBankInfo method gets and sets the bank name and address,
	 * then prints the bank information.
	 * @param myBank A Bank object that holds an ArrayList of BankAccounts.
	 */
	
	public static void getBankInfo(Bank myBank)
	{
		System.out.println("Enter bank name: ");
		String name = kbd.nextLine();
		//bank address
		System.out.println("Enter street address: ");
		String street = kbd.nextLine();
		System.out.println("Enter city: ");
		String city = kbd.nextLine();
		System.out.println("Enter state: ");
		String state = kbd.nextLine();
		System.out.println("Enter zip: ");
		String zip = kbd.nextLine();
		Address myAddress = new Address(street, city, state, zip);
		
		myBank.setName(name);
		System.out.println(myBank.getName() + "\n" + myAddress.toString());
	}
	
	
	/**
	 * The menu method prints a menu of options available to the user to manipulate
	 * the bank accounts in the bank.
	 * @return choice An integer that is the value of the user choice.
	 */
	public static int menu()
	{
		int choice;
		System.out.println("\nPlease choose an option from the following menu: " +
						"\n1. Set up new Account" +
						"\n2. Close Account" +
						"\n3. Manage Account" +
						"\n4. Get Total Bank Balance" +
						"\n5. List Accounts" +
						"\n0. Exit Application");
		
		choice = kbd.nextInt();
		return choice;	
	}
	
	/**
	 * The subMenu method prints a menu of options available to the user to manipulate
	 * an individual account balance.
	 * @return subchoice An integer that is the value of the user choice.
	 */
	public static int subMenu()
	{
		int subchoice; 
		System.out.println("Please choose an option from the following menu: ");
		System.out.println("1. Deposit");
		System.out.println("2. Withdraw");
		System.out.println("3. Get Balance");
		System.out.println("4. Get Deposits");
		System.out.println("5. Get Total Deposits");
		System.out.println("6. Get Withdrawals");
		System.out.println("7. Get Total Withdrawals");
		System.out.println("8. Get Transactions");
		
		subchoice = kbd.nextInt();
		return subchoice;
	}
	
	/**
	 * The newAccount method creates new BankAccount objects and adds them to
	 * the ArrayList of accounts that belong to the Bank.
	 * Throws an exception if the ArrayList already holds an account with the specified account ID,
	 * or if the initial balance is lower than the required $100.00.
	 * @param myBank A Bank object that holds an ArrayList of BankAccounts.
	 */
	public static void newAccount(Bank myBank) throws InvalidIDException,NumberFormatException,InvalidAccountException,InvalidAmountException
	{
		double balance;
		String accountID;
		
		try{
		System.out.println("Enter your four digit account ID:");
		accountID = kbd.next();
		System.out.println("Enter the balance for account " + accountID + ": " );
		balance = kbd.nextDouble();
		
		myBank.addAccount(accountID, balance);
		System.out.printf("Account " + accountID + " created with an initial deposit of $%,.2f.", balance);
		}
		catch(InvalidIDException e){
			System.out.println("Error: account identification must be any four digit number.");
		}
		catch(NumberFormatException e){
			System.out.println("Error: account identification must be any four digit number.");
		}
		catch(InvalidAccountException e){
			System.out.println("Error: duplicate account.");
		}
		catch(InvalidAmountException e){
			System.out.println("Error: Starting balance must be at least $100.00");
		}
	}
	
	/**
	 * The closeAccount method deletes an account with the specified account ID from the 
	 * ArrayList of BankAccounts.
	 * Throws an exception if the account with the specified account ID is not present
	 * in the ArrayList.
	 * @param myBank myBank A Bank object that holds an ArrayList of BankAccounts.
	 */
	public static void closeAccount(Bank myBank)throws InvalidAccountException
	{
		String accountID;
		
		try{
		System.out.println("Enter your four digit account ID:");
		accountID = kbd.next();
		
		myBank.closeAccount(accountID);
		System.out.println("Account " + accountID + " has been closed.");
		}
		catch(InvalidAccountException e){
			System.out.println("Error: account not found.");
		}
	}
}
