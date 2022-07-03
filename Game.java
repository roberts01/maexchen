 import java.util.Scanner;

public class Game
{
  Player[] players;
  int current_player_number;
  int previous_player_number;
  
  public Game()
  {
    current_player_number = 0;
    previous_player_number = -1;
  }

  public void SetUpPlayers(Scanner scan)
  {
    int[] options = {2,3,4,5,6,7,8,9,10};
    System.out.print("How many players would you like (2 to 10)? >");
    int numPlayer = Player.getPlayerIntInput(scan, options);
    
    System.out.print("How many lives does each player get (2 to 10)? >");
    int lives = Player.getPlayerIntInput(scan, options);

    players = new Player[numPlayer];
    for (int player_number = 0; player_number<players.length; player_number++)
    {
      System.out.print("Enter name of player "+(player_number+1)+" >");
      
      String name = "";
      while (name == "")
      {
        try
        {
          name = scan.nextLine();
        }
        catch (Exception e) 
        {
          System.out.println("Invalid input! Error: "+e);
          name = "";
        }
      }
      players[player_number] = new Player(name, player_number, lives);
    }
  }
  
    
  public void StartRound(Scanner scan)
  {
    if (previous_player_number==-1)
    {
      players[current_player_number].BeginTurn(scan, null);
    }
    else
    {
      players[current_player_number].BeginTurn(scan, players[previous_player_number]);
    }

    while (NextPlayer(scan));
    previous_player_number = -1;
  }

  // method to determine if only one player remains
  public boolean onlyOneAlive()
  {
    int count = 0;
    for (int i = 0; i<players.length; i++)
    {
      if (players[i].lives>0)
      {
        count++;
      }
    }
    return count <= 1;
  }
  
  // method to find next player that still have lives
  public int findNextPlayer()
  {
      do
      {
        current_player_number=(current_player_number+1)%players.length;
      }
      while(players[current_player_number].lives<=0);
    return current_player_number;
  }
  
  public boolean NextPlayer(Scanner scan)
  { 
    // check to ensure game only continues if more than one player is left
    if (onlyOneAlive())
    {
      return false;   // there is only one player left alive
    }
    previous_player_number = current_player_number;
    current_player_number = findNextPlayer(); 
    return players[current_player_number].BeginTurn(scan, players[previous_player_number]);
  } 
}