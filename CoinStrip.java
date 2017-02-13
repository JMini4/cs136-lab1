/*
 * Name: Julia Mini
 * Lab: Thursday 1-4
 * Description: The class CoinStrip creates the gameboard (which had 
 * been stored as an array), prints the current state of the board,
 * prompts the user, uses methods from the GameBoard class, prints a 
 * string displaying which player won the game.
 *
 * Thought Questions
 * 1) In order to control the %chance of a certain number of coins, one would 
 *have to restricted the number of coinsin such a way that given the
 * distribution yields the desired % chance of the target number of coins.
 * So for a 50% chance of a game with 3 coins the random number generator
 * for the the numCoins would be between 3 and 4. For a 25% chance of a game
 * with 4 coins numCoins would be between 3 and 6. This technique makes me 
 * want to use a Vector for the numCoins so it can be expanded.
 * 2) To generate a game without an immediate win, one would have to ensure
 * that the gameboard does not have n-1 coins all in the first n-1 indices
 * of the board, because the one move, on the nth coin will put all coins
 * in the winning positions.If you wanted to guaruntee yourself n moves then
 * the number of moves is equal to the number coins as long as no coin is at
 * index zero.
 * 3) if the computer could scan the gameboard and tell which player was going
 * to win based on the least number of moves, then the computer could give a
 * hint prompting the predicted loosing player to make a defensive move in
 * which they don't move a coin all the way to the winning/final position. Thus
 * one would have to create a way to keep track of the coins and there final or
 * winning positions. It could be an array based on an array of the coin's 
 * initial positions.
 * 4) If my coinstrip class had two methods call optimalCoin()whcih selects
 * a coin and optimalSpaces(int coinNum) which determines how many spaces to
 * move the coinNum-th coin. The computerPlay() method could be written so that
 * for the gameboard, the computer scans to determine the number of moves until
 * checkWin() returns true. If the computer is always going in response to the
 * human user then if thenumber of moves until checkWin() is true is an even 
 * number, then the computer will win, but if the number of moves is odd then
 * the user will win. Therefore in the case where the computer wins, the 
 * computerPlay() method will call the optimalCoin() and optimalSpaces() 
 * methods and play as usual, scanning eachtime the gameboard is updated to
 * ensure the number of moves is in the computers favor, and if the number of
 * moves for a win changes from computer wins to player wins, then the method
 * defensiveMove() could be called in this case so that the optimalCoin() is 
 * moved a sub optimal number of spaces. This could be acheived simply by doing
 * the optimalSpaces() getting the int that returns and subtracting 1 (or 
 * any number of spaces as long as it is legal). I would change the prompts
 * going to two players to only going to one player. Also the winner print
 * statement would change to either player wins or computer wins.
 * 5) This modification wouldn't change my implementation significantly 
 * because all I would have to change is the checkMove method and change in
 * in the seconf for loop how if the elements are not zero then return false 
 * and change that to return true.
 */

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class CoinStrip {
    public static void main(String [] args){
	GameBoard gameBoard = new GameBoard();
	System.out.println(gameBoard);

	Scanner in = new Scanner(System.in);

	/* used to keep track of which player is requesting the moves*/
	int player = 1;

	/* continue this loop until check.Win returns true,
	 * or until someone wins
	 */
	while(!gameBoard.checkWin()){
	    
	    /* prompts user for the cn, coin number, and the ns,
	     * number of spaces
	     */
	    int cn, ns;
	    while (true) {
		System.out.print("Player " + player + ", give me the coin number you wish to move ");
		cn = in.nextInt();
		System.out.print("Player " + player + ", give me a number of spaces ");
		ns = in.nextInt();
		
		/* if the move requested is legal, break the loop 
		 * and move the coin
		 */
		if (gameBoard.checkMove(cn,ns)) {
		    break;
		} else {
		    System.out.println("illegal move");
		}
	    } 
	    
	    gameBoard.moveCoin(cn, ns);
	    System.out.println(gameBoard);
	    if(gameBoard.checkWin()){
		System.out.print("Player " + player + " wins!");
	    } else {
		if (player == 1) {
		    player = 2;
		} else {
		    player = 1;
		}
	    }
	}
    }
}

/* creates the gameboard and stores it as an array*/    
class GameBoard{
    Random rand = new Random();
    protected int[] gameBoard;

    /* randomizes the number of coins between 3 and 9 coins*/
    public final int numCoins = rand.nextInt((9-3)+1)+3;
     
    public GameBoard(){

	/* creates a gameboard with a random length between 10 and 20 spaces*/
	int boardSize = rand.nextInt((20-10)+1)+10;
	gameBoard = new int[boardSize];
	
	/* keeps track of the number of coins currently on the board*/
	int coinsInserted = 0;

	/* continues to insert coins on the gameboard until coins on the board 
	 * is equal to the number of coins the game is to be played with
	 */
	while (coinsInserted < numCoins){
	    int coinPosition = rand.nextInt(boardSize);
	    if (gameBoard[coinPosition] == 0){
		gameBoard[coinPosition] = coinsInserted + 1;
		coinsInserted ++;
	    }
	}        
    }

   /* prints a string representation of the board, called result,
    * where spaces are stars and coins are numbers
    */
    public String toString(){
	String result = " "; 
	for(int i = 0; i < gameBoard.length;i++){
	    if(gameBoard[i] == 0){
		    result += "* ";
	    } else {
		result += gameBoard[i] + " ";
	    }
	}
	return result;
}

    /* check that the requested move doesn't jump over other coins, and 
     * the index exists on the board, returns true for a legal move or 
     * false for an illegal move
     */
    public boolean checkMove(int coinNum, int numSpaces){
	if(numSpaces < 0){
	    return false;
}
	for(int i = 0; i < gameBoard.length; i++){
	    if(gameBoard[i] == coinNum){
		if(i - numSpaces < 0){
		    return false;
		}
		for(int j = i - numSpaces; j < i; j++){
		    if(gameBoard[j] != 0){			 
			return false;
		    }
		}
		return true;
	    }
	}
	return false;
    }

    /* moves the coin to the new space on board and removes coin from 
     * its previous space
     */
    public void moveCoin(int coinNum, int numSpaces){
	for(int i = 0; i < gameBoard.length; i++){
	    if(gameBoard[i] == coinNum){
		gameBoard[i - numSpaces] = coinNum;
		gameBoard[i] = 0;
	    }
	}
    }

    /* checks if all the coins are at the leftmost positions on board,
    without spaces - returns true for a win or false for no win yet*/
    public boolean checkWin(){
	for(int i = 0; i < numCoins; i++){
	    if(gameBoard[i] == 0){
		return false;
	    } 
	}
	return true;	
    }
}