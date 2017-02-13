import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class CoinStrip {
    private final static int numCoins = 3;
    public static void main(String [] args){
	GameBoard gameBoard = new GameBoard();
	System.out.println(gameBoard);

	Scanner in = new Scanner(System.in);
	int player = 1;
	while(!gameBoard.checkWin()){

	    int cn, ns;
	    while (true) {
		System.out.print("Player " + player + ", give me the coin number you wish to move ");
		cn = in.nextInt();
		System.out.print("Player " + player + ", give me a number of spaces ");
		ns = in.nextInt();

		if (gameBoard.checkMove(cn,ns)) {
		    break;
		} else {
		    System.out.println("illegal move");
		}
	    } 
	    
	    gameBoard.moveCoin(cn, ns);
	    System.out.println(gameBoard);
	    if(gameBoard.checkWin()){
		System.out.print(player + " wins!");
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

    
class GameBoard{
    protected int[] gameBoard;
    private final static  int numCoins = 3;
     

    public GameBoard(){
	Random rand = new Random();

	/* creates a game board with a random length between 10 and 20 squares*/
	int boardSize = rand.nextInt((20-10)+1)+10;
	gameBoard = new int[boardSize];
	
	int coinsInserted = 0;
	while (coinsInserted < numCoins){
	    int coinPosition = rand.nextInt(boardSize);
	    if (gameBoard[coinPosition] == 0){
		gameBoard[coinPosition] = coinsInserted + 1;
		coinsInserted ++;
	    }
	}        
    }

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


    public void moveCoin(int coinNum, int numSpaces){
	for(int i = 0; i < gameBoard.length; i++){
	    if(gameBoard[i] == coinNum){
		gameBoard[i - numSpaces] = coinNum;
		gameBoard[i] = 0;
	    }
	}
    }


    public boolean checkWin(){
	for(int i = 0; i < numCoins; i++){
	    if(gameBoard[i] == 0){
		return false;
	    } 
	}
	return true;	
    }
}
