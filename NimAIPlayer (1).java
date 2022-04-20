import java.util.Random;

public class NimAIPlayer extends NimPlayer
{
	Random rand;

	public NimAIPlayer(String username, String familyname, String givenname, int gamesPlayed, int gamesWon) 
	{
		super(username, familyname, givenname, gamesPlayed, gamesWon);
		rand = new Random();
	}
	
	public String advancedMove(boolean[] available, String lastMove) 
	{
		return null;
	}

	@Override
	public int removeStone(NimGame game) {
		int m = game.getUpperBound();
		int n = game.getStonesLeft();
		if(n  == 1) {
			return 1;
		}
		int stones;
		
		for(stones = 1; stones <= m ; stones++) {
			if( (n - stones) % (m + 1) == 1) {
				return stones;
			}
		}
		// start with 20 stones
		//4 
		//5
		return 1;
	}
}


