import java.util.InputMismatchException;
import java.util.Scanner;

public class NimGame 
{
	private int stonesLeft;
	private int upperBound;
	private NimPlayer player1;
	private NimPlayer player2;

	public NimGame(int numStones, int upperBound, NimPlayer player1, NimPlayer player2) 
	{
		super();
		this.stonesLeft = numStones;
		this.upperBound = upperBound;
		this.player1 = player1;
		this.player2 = player2;
	}

	public void playGame() 
	{
		// the person who picks the last stone loses
		System.out.println();
		System.out.println("Initial stone count: " + stonesLeft);
		System.out.println("Maximum stone removal: " + upperBound);
		System.out.println("Player 1: " +  player1.getGivenname() + " " + player1.getFamilyname());
		System.out.println("Player 2: " +  player2.getGivenname() + " " + player2.getFamilyname());
		
		// update games played
		player1.setGamesPlayed(player1.getGamesPlayed() + 1);
		player2.setGamesPlayed(player2.getGamesPlayed() + 1);
		
		// set current player
		NimPlayer currentPlayer = player1;
		
		
		// maybe add some code here to check if player 1 or 2 is a type of nimAIPlayer and then execute different versions of the game 
		
		
		System.out.println();
		

		while (stonesLeft >= 0) 
		{
			try 
			{
				System.out.print(stonesLeft + " stones left:");
				int astericks = stonesLeft; 
				while (astericks > 0)
				{
					System.out.print(" *");
					--astericks;
				}
				System.out.println();
				System.out.println(currentPlayer.getGivenname() + "'s turn - remove how many?");	
			
				int stonesRemove = currentPlayer.removeStone(this);
				
				if (stonesRemove > upperBound || stonesRemove <= 0) 
					throw new IllegalArgumentException("Invalid move. You must remove between 1 and " + upperBound + " stones.");
				
				else if (stonesRemove > stonesLeft)
					throw new IllegalArgumentException("Invalid move. You must remove between 1 and " + stonesLeft + " stones.");

				else 
				{
					stonesLeft = stonesLeft - stonesRemove;
					// switch to next player for their turn
					if (currentPlayer == player1) 
						currentPlayer = player2;
					else 
						currentPlayer = player1;	
				
					if(stonesLeft == 0) 
					{
						System.out.println("Game Over");
						System.out.println(currentPlayer.getGivenname() + " " + currentPlayer.getFamilyname() + " wins!");
						currentPlayer.setGamesWon(currentPlayer.getGamesWon() + 1);
						break;
					}
				}
			}
			catch (InputMismatchException e) 
			{
				Nimsys.getKeyBoard().nextLine();
				System.out.println("Please enter only integer valuees");

			} catch(IllegalArgumentException aex) {
				System.out.println(aex.getMessage());
				System.out.println();

			}
		}
	}

	/**
	 * @return the stonesLeft
	 */
	public int getStonesLeft() {
		return stonesLeft;
	}

	/**
	 * @return the upperBound
	 */
	public int getUpperBound() {
		return upperBound;
	}
	
	
}
	
	