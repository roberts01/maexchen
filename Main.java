/*
Interactive dice game Maexchen (English also known as Mia)
Written by Robert H. Schuppan
last changed: 03.07.2022

Copyright:
Licensed under the MIT License
Inspriation and literature that made this project possible:
- geeksforgeeks.com
- javatpoint.com
- stackoverflow.com
Code snippets of less than 15 symbols may be similar to existing resources, as part of the Public Domain

In addition to the aforementioned notes:
- Player.findInArray adapted https://www.geeksforgeeks.org/check-if-a-value-is-present-in-an-array-in-java/ 
- line 57 & 58 in Player from https://stackoverflow.com/a/32295974
*/

import java.util.Scanner;

class Main 
{
  public static void main(String[] args) 
  {
    Scanner scan = new Scanner(System.in);
    Game game = new Game();
    game.SetUpPlayers(scan);
    // play the game until only one place remains
    while(!game.onlyOneAlive())
    {
      game.StartRound(scan);
    }
    System.out.println("Congrats "+game.players[game.findNextPlayer()].name+" you win!");
  }
}