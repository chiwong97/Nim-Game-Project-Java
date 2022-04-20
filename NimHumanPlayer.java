import java.util.Scanner;

public class NimHumanPlayer extends NimPlayer
{
	public NimHumanPlayer() 
	{
		super();
	}
	
	public NimHumanPlayer(String username, String familyname, String givenname, int gamesPlayed, int gamesWon) 
	{
		super(username, familyname, givenname, gamesPlayed, gamesWon);
	}

	@Override
	public int removeStone(NimGame game) {
		int stonesRemove = Nimsys.getKeyBoard().nextInt();
		Nimsys.getKeyBoard().nextLine();
		return stonesRemove;
	}
}
