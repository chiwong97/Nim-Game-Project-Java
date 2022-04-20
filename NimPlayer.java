import java.io.Serializable;

public abstract class NimPlayer implements Serializable
{
	public static String DEFAULT_USERNAME = "Anon";
	public static String DEFAULT_FIRSTNAME = "Anon";
	public static String DEFAULT_LASTNAME = "Anon";
	public static int DEFAULT_GAMESPLAYED = 0;
	public static int DEFAULT_GAMESWON = 0;
	
	// player attributes 
	
	private String username;
	private String familyname;
	private String givenname;
	private int gamesPlayed;
	private int gamesWon;
	
	// Constructors
	
	public NimPlayer() 
	{
		username = DEFAULT_USERNAME;
		familyname = DEFAULT_FIRSTNAME;
		givenname = DEFAULT_LASTNAME;
		gamesPlayed = DEFAULT_GAMESPLAYED;
		gamesWon = DEFAULT_GAMESWON;
	}
	
	public NimPlayer(String username, String familyname, String givenname, int gamesPlayed, int gamesWon) 
	{
		this.username = username;
		this.familyname = familyname;
		this.givenname = givenname;
		this.gamesPlayed = DEFAULT_GAMESPLAYED;
		this.gamesWon = DEFAULT_GAMESWON;
	}
	
	
	// Getters
	
	public String getUsername()
	{
		return username;
	}
	
	public String getFamilyname()
	{
		return familyname;
	}
	
	public String getGivenname()
	{
		return givenname;
	}
	
	public int getGamesPlayed()
	{
		return gamesPlayed;
	}
	
	public int getGamesWon()
	{
		return gamesWon;
	}
	
	public double getWinPercentage() 
	{
		if(gamesPlayed == 0) 
		{
			return 0.0;
		}
		return gamesWon * 100 / gamesPlayed;
	}

	// Setters 
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public void setFamilyname(String familyname)
	{
		this.familyname = familyname;
	}
	
	public void setGivenname(String givenname)
	{
		this.givenname = givenname;
	}
	
	public void setGamesPlayed(int gamesPlayed)
	{
		this.gamesPlayed = gamesPlayed;
	}
	
	public void setGamesWon(int gamesWon)
	{
		this.gamesWon = gamesWon;
	}
	
	public abstract int removeStone(NimGame game);
	
	@Override
	public String toString() {
		return username + "," + familyname + "," + givenname  + "," + gamesPlayed  + " games," + gamesWon + " wins";
	}
	
}
