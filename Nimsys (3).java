import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.Collections;
import java.util.Comparator;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.EOFException;
import java.io.File;

public class Nimsys {
	private static final String DATA_FILE = "players.dat";

	private static String[] userInputArray = new String[2]; // seperate command and details
	private static Scanner keyboard = new Scanner(System.in);
	private static ArrayList<NimPlayer> players = new ArrayList<NimPlayer>();

	private static void loadPlayers() {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(DATA_FILE));
			while (true) {
				NimPlayer player = (NimPlayer) in.readObject();
				if (player != null) {
					players.add(player);
				} else {
					break;
				}
			}
			in.close();
		} catch (EOFException ex) {
			/* no need to worry about this one */
		} catch (IOException | ClassNotFoundException e) {
			//System.out.println("Could not load player data");
		}
	}

	private static void savePlayers() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATA_FILE));
			for (NimPlayer player : players) {
				out.writeObject(player);
			}

			out.close();
		} catch (IOException e) {
			System.out.println("Could not save player data");
		}
	}

	public static void main(String[] args) {
		Set<String> commands = Set.of("editplayer", "addaiplayer", "addplayer", "removeplayer", "resetstats",
				"displayplayer", "rankings", "startgame", "exit");

		System.out.println("Welcome to Nim");
		boolean runLoop = true;
		System.out.println();
		System.out.print("$");
		loadPlayers();
		while (runLoop) {
			String command = "";
			userInputArray = keyboard.nextLine().strip().split(" ");

			try {
				command = userInputArray[0];
				if (!commands.contains(command)) {
					throw new IllegalArgumentException(String.format("'%s' is not a valid command", command));
				}

				if (command.equals("addplayer")) {
					Nimsys.addplayer(userInputArray[1]);
				} else if (command.equals("addaiplayer")) {
					Nimsys.addaiplayer(userInputArray[1]);
				} else if (command.equals("removeplayer")) {
					if (userInputArray.length == 2)
						Nimsys.removeplayer(userInputArray[1]);
					else
						Nimsys.removeplayer(null);
				} else if (command.equals("editplayer")) {
					Nimsys.editplayer(userInputArray[1]);
				} else if (command.equals("resetstats")) {
					if (userInputArray.length == 2)
						Nimsys.resetstats(userInputArray[1]);
					else
						Nimsys.resetstats(null);
				} else if (command.equals("displayplayer")) {
					if (userInputArray.length == 2)
						Nimsys.displayplayer(userInputArray[1]);
					else
						Nimsys.displayplayer(null);
				}

				else if (command.equals("rankings")) {
					if (userInputArray.length == 2)
						Nimsys.rankings(userInputArray[1]);
					else
						Nimsys.rankings(null);
				} else if (command.equals("startgame")) {
					Nimsys.startgame(userInputArray[1]);
				} else if (command.equals("exit")) {
					break;
				}
			} catch (ArrayIndexOutOfBoundsException aex) {
				System.out.println("Incorrect number of arguments supplied to command.");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			System.out.println();
			System.out.print("$");

		}

		savePlayers();
		keyboard.close();
	}

	// ADDPLAYER
	// --------------------------------------------------------------------------------------------
	public static void addplayer(String details) {

		String[] arr = details.split(",");
		NimPlayer player = findPlayer(arr[0]);
		if (player != null) {
			System.out.println("The player already exists.");
		} else {
			if (players.size() == 100) {
				System.out.println("Maximum number of players already added");
			} else {
				NimHumanPlayer p = new NimHumanPlayer(arr[0], arr[1], arr[2], NimPlayer.DEFAULT_GAMESPLAYED,
						NimPlayer.DEFAULT_GAMESWON);
				players.add(p);
			}
		}
	}

	// ADDAIPLAYER
	// -----------------------------------------------------------------------------------------------
	public static void addaiplayer(String details) {
		String[] arr = details.split(",");
		NimPlayer player = findPlayer(arr[0]);

		if (player != null)
			System.out.println("The player already exists.");
		else {
			if (players.size() == 100) {
				System.out.println("Maximum number of players already added");
			} else {
				NimAIPlayer p = new NimAIPlayer(arr[0], arr[1], arr[2], NimPlayer.DEFAULT_GAMESPLAYED,
						NimPlayer.DEFAULT_GAMESWON);
				players.add(p);
			}
		}
	}

	// REMOVEPLAYER METHOD
	// ----------------------------------------------------------------------------------------
	public static void removeplayer(String username) {
		if (username == null) {
			System.out.println("Are you sure you want to remove all players (Y/N)");
			String input = keyboard.nextLine().strip().toLowerCase();
			if (input.equals("y"))
				players.clear();
		} else {
			NimPlayer player = findPlayer(username);
			if (player != null) {
				players.remove(player);
			} else {
				System.out.println("Player does not exist");
			}
		}
	}

	// EDITPLAYER METHOD
	// --------------------------------------------------------------------------------------------------------
	public static void editplayer(String details) {
		String[] arr = details.split(",");

		NimPlayer player = findPlayer(arr[0]);
		if (player == null)
			System.out.println("Player does not exist");
		else {
			try {
				player.setFamilyname(arr[1]);
				player.setGivenname(arr[2]);
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Incorrect number of arguments supplied to command.");
			}
		}
	}

	// RESET STATS METHOD
	// -----------------------------------------------------------------------------------------------------
	public static void resetstats(String username) {
		if (username == null) {
			System.out.println("Are you sure you want to reset all player statistics? (y/n)");
			String removeAll = keyboard.nextLine().strip().toLowerCase();
			if (removeAll.contains("y")) {
				for (int i = 0; i < players.size(); i++) {
					players.get(i).setGamesPlayed(0);
					players.get(i).setGamesWon(0);
				}
			}
		} else {
			NimPlayer player = findPlayer(username);
			if (player == null)
				System.out.println("Player does not exist");
			else {
				player.setGamesPlayed(0);
				player.setGamesWon(0);
			}
		}
	}

	// DISPLAY METHOD
	// -----------------------------------------------------------------------------------------------------------------
	public static void displayplayer(String username) {
		if (username == null) {
			Collections.sort(players, new Comparator<NimPlayer>() {
				@Override
				public int compare(NimPlayer o1, NimPlayer o2) {
					return o1.getUsername().compareTo(o2.getUsername());
				}
			});

			for (NimPlayer player : players) {
				System.out.println(player);
			}
		} else {
			NimPlayer player = findPlayer(username);
			if (player == null)
				System.out.println("Player does not exist");
			else
				System.out.println(player);
		}
	}

	// RANKINGS METHOD
	// --------------------------------------------------------------------------------------------------
	public static void rankings(String order) {
		// ternary operator
		final boolean desc = (order == null || order.toLowerCase().equals("desc")) ? true : false;

		Collections.sort(players, new Comparator<NimPlayer>() {
			@Override
			public int compare(NimPlayer o1, NimPlayer o2) {
				double wp1 = o1.getWinPercentage();
				double wp2 = o2.getWinPercentage();

				if (wp1 == wp2) {
//					if(desc) {
//						return o2.getUsername().compareTo(o1.getUsername());
//					}
					return o1.getUsername().compareTo(o2.getUsername());
				}
				if (desc) {
					return (int) (wp2 - wp1);
				}
				return (int) (wp1 - wp2);
			}
		});
		int i = 0;
		for (NimPlayer player : players) {
			if (i == 10)
				break;
			System.out.printf("%-4s | %02d games | %s %s\n", String.format("%.0f%%", player.getWinPercentage()),
					player.getGamesPlayed(), player.getGivenname(), player.getFamilyname());
		}
	}

	// START GAME METHOD
	// ---------------------------------------------------------------------------------------------
	public static void startgame(String details) {
		String[] arr = details.split(",");
		try {
			NimPlayer player1 = findPlayer(arr[2]);
			NimPlayer player2 = findPlayer(arr[3]);
			if (player1 != null && player2 != null) {
				NimGame game = new NimGame(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), player1, player2);
				game.playGame();
			} else
				System.out.println("One of the players does not exist.");
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Incorrect number of arguments supplied to command.");
		}
	}

	// Find if player exists in array list
	private static NimPlayer findPlayer(String userName) {
		for (NimPlayer player : players) {
			if (player.getUsername().equals(userName))
				return player;
		}
		return null;
	}

	public static Scanner getKeyBoard() {
		return keyboard;
	}
}
