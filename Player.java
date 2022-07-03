import java.util.Scanner; 
import java.lang.Math;

public class Player
{
  String name;
  int player_number;
  int previous_player_number;
  int lives;
  int communicated_number;
  int real_number;
  static int[] ranks = {21, 66, 55, 44, 33, 22, 11, 65, 64, 63, 62, 61, 54, 53, 52, 51, 43, 42, 41, 32, 31};
  

  public Player(String name, int player_number, int lives)
  {
    this.name = name;
    this.player_number = player_number;
    this.lives = lives;
  }
  public static int findInArray(int value, int[] ary)
  {
    for (int i = 0; i < ary.length; i++)
      {
         if (ary[i] == value)
         {
           return i;
         }
      }
    return -1;// -1 means the value is not in the array
  }
  
  int RollDice()
  {
    // defining the range
    int min = 1;
    int max = 6;
    int range = max - min + 1;

    int dice_1 = (int)(Math.random() * range) + min;
    int dice_2 = (int)(Math.random() * range) + min;

    // ensuring that the higher number is first
    if (dice_1>dice_2)
    {
      return dice_1*10+dice_2;
    }
    else
    {
      return dice_2*10+dice_1;
    }
  }

  boolean BeginTurn(Scanner scan, Player previous_player)
  {
    // clear screen so next player does not see the previous players dices
    System.out.print("\033[H\033[2J");  
    System.out.flush();  

    // CHECK: There is a previous roll
    int choice;
    if (previous_player != null)
    {
      // prompt user if they want to accuse the previous player of lying
      System.out.print("\n"+this.name+", "+previous_player.name+" said they rolled a "+ previous_player.communicated_number+". Do you want to accuse them of lying? Enter 99 for yes, 0 for no >");
      int[] options = {0};
      choice = getPlayerIntInput(scan, options);

      // CHECK : player accuses the previous player of lying
      if (choice == 99)
      {
        System.out.println(this.name+" accuses "+previous_player.name+" of lying!");
        if (previous_player.real_number != previous_player.communicated_number)
        {
          System.out.println(previous_player.name+" was lying!");
          System.out.println(previous_player.name+" loses one life!");
          System.out.println(this.name+", confirm the start of a new round by entering anything.");
          scan.next();
          previous_player.lives--;
          return false;
        }
        else
        {
          System.out.println(previous_player.name+" was not lying!");
          System.out.println(this.name+" loses one life!");
          System.out.println(this.name+", confirm the start of a new round by entering anything.");
          scan.next();
          this.lives--;
          return false;
        }
      }

      // CHECK: New player does NOT accuse them of lying   
      else if (choice == 0)
      {
        this.real_number = RollDice();

        //CHECK: Only higher numbers are allowed, smaller index in array means higher rank
        if (findInArray(this.real_number, ranks) >= findInArray(previous_player.communicated_number, ranks))
        {
          System.out.println("\n"+name+", you rolled a "+real_number+", which is not greater than the "+previous_player.communicated_number+" rolled by "+previous_player.name+". You have to lie about your number or lose a life.");
          System.out.print("Enter 99 to lose a life, or if you want to lie, enter the dice number you would like to tell the other players >");
          
          do
          {
            choice = getPlayerIntInput(scan, ranks);
            
            if (choice == 99)
            {
              System.out.println(this.name+" loses one life, as they did not roll a higher number!");
              System.out.println(this.name+", confirm the start of a new round by entering anything.");
              scan.next();
              this.lives--;
              return false;
            }
            else if (findInArray(choice, ranks) < findInArray(previous_player.communicated_number, ranks))
            {
              this.communicated_number = choice;
              System.out.print(name+" has rolled a "+communicated_number+".");
              return true;
            }
          } while (!(findInArray(choice, ranks) < findInArray(previous_player.communicated_number, ranks)) || choice != 1);
        }

        
        System.out.println("\n"+name+", you rolled a "+real_number+". Do you want to lie about your number?");
        System.out.print("Enter 99 to tell the truth, or if you want to lie, enter the dice number you would like to tell the other players >");
        
        choice = getPlayerIntInput(scan, ranks);
    
        if (choice == 99)
        {
          this.communicated_number = this.real_number;
        }
        else
        {
          this.communicated_number = choice;
        }
        System.out.print(name+" has rolled a "+communicated_number+".");
        return true;  
      }
    }
    // Check: There is no previous player, ergo this is the first roll of new round
    else
    {
        this.real_number = RollDice();
      
        System.out.println("\n"+name+", you rolled a "+real_number+". Do you want to lie about your number?");
        System.out.print("Enter 99 to tell the truth, or if you want to lie, enter the dice number you would like to tell the other players >");
        
        choice = getPlayerIntInput(scan, ranks);
    
        if (choice == 99)
        {
          this.communicated_number = this.real_number;
        }
        else
        {
          this.communicated_number = choice;
        }
        System.out.print(name+" has rolled a "+communicated_number+".");
        return true;  
    }
        return true;
         
  }

  public static int getPlayerIntInput(Scanner scan, int[] range_of_options)
  {
    int input = 0;    
    do
    {
      try
      {
        input = scan.nextInt();
        
        if (input == 99 || findInArray(input,range_of_options) != -1)
        {
          return input;
        }
        else
        {
          System.out.println("The number you entered is not valid. Please enter a valid number: >");
          input = 0;
        }
      }
      catch (Exception e)
      {
        input = 0;
        System.out.println("Invalid input! Error: "+e);
        System.out.println("Please enter a valid number: >");
        scan.next();
        
      }
    } while (input == 0);
    return input = -1;
  }
}
