
public class text 
  { 
	String words;
	int coins, interest, total;
	public text()
	{
		
	}
	public text(String text)
	{
		words = text;
	}
	public void interest(int coinSet, int interestSet)
	{
		coins = coinSet;
		interest = interestSet;
	}
	public int getCoins()
	{
		return coins;
	}
	public int getInterest()
	{
		return interest;
	}
	public int getTotal()
	{
		total = coins*(1+(interest/100));
		return total;
	}
	public String toString()
	{
		return words;
	}
	
  }
