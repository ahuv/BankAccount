package bank;

public class Deposit extends Transaction {
	
	/**
	 * The Deposit constructor sets the amount according to the superclass constructor
	 * and the transaction type to the enum deposit.
	 * @param amount The transaction amount
	 */
	public  Deposit(Double amount)
	{
		super(amount);
		transType = TransactionType.DEPOSIT;
	}
	
	//copy constructor
	public Deposit(Deposit depo) throws InvalidAmountException
	{
		this(depo.amount);
		this.transDateTime = depo.transDateTime;
	}
	
	/**
	 * The TransactionType method returns the set transaction type.
	 */
	public TransactionType getTransType()
	{
		return transType;
	}
	
	/**
	 * The toString method returns a string containing information about the transaction.
	 */
	public String toString()
	{
		StringBuffer info= new StringBuffer();
		info.append("Transaction Type: ");
		info.append(transType + " ");
		info.append("Date: ");
		info.append(transDateTime + " ");
		info.append("Amount: ");
		info.append(amount + " \n");
		
		return info.toString();
		
	}
 
}
