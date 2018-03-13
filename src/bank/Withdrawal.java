package bank;

import java.time.LocalDateTime;

public class Withdrawal extends Transaction{
	
	/**
	 * The Withdrawal constructor sets the amount according to the superclass constructor
	 * and the transaction type to the enum withdrawal.
	 * @param amount The transaction amount
	 */
	public Withdrawal(Double amount)
	{
		super(amount);
		transType = TransactionType.WITHDRAWAL;
	}
	
	public Withdrawal(Withdrawal wthdr)throws InvalidAmountException
	{
		this(wthdr.amount);
		this.transDateTime = wthdr.transDateTime;
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
