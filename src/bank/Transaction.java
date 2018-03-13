package bank;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class Transaction {
	
	protected Double amount;
	protected LocalDateTime transDateTime;
	protected TransactionType transType;
	
	
	public Transaction(Double amount)
	{
		if(amount <= 0)
		{
			throw new InvalidAmountException("");
		}
		else 
		{
			this.amount = amount;
		}
		
		this.transDateTime = LocalDateTime.now();
	}
		
	public Double getAmount()
	{
		return amount;
	}
	
	public LocalDate getDate()
	{
		LocalDate date = transDateTime.toLocalDate();
		return date;
	}
	public LocalTime getTime()
	{
		LocalTime time = transDateTime.toLocalTime();
		return time;
	}
	public abstract String toString();
	
	public abstract TransactionType getTransType();
	

}
