import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class HumanPlayer extends Player {

  private static BufferedReader reader;
  
  public HumanPlayer(CheckerType type) {
    super(type);
  }

  public String toString() {
    return getCheckerType() + " Human Player";
  }
  
  @Override
  public Action getMove(BoardState board) {
    
    //print current board state
    System.out.println(getCheckerType() + " Players turn");
    System.out.println("Current Board:");
    board.printBoard();
    
    boolean validMove = false;
    int colNum = -999;
    while(!validMove) {
      System.out.print("Enter Column Number(0-6): ");
      String colNumStr = null;
      try {
        colNumStr = getReader().readLine();
      } catch (IOException e) {
        e.printStackTrace();
      }
      try {
        if (colNumStr != null)
          colNum = Integer.parseInt(colNumStr);
      } catch (NumberFormatException n) {
        continue;
      }
      if(colNum > -1 && colNum < 7) {
        validMove = true;
      }else {
        System.out.println("Invalid Column.");
      }
    }
    
    return new Action(getCheckerType(), colNum);
  }
  
  private static BufferedReader getReader() {
    if(reader == null) {
      reader = new BufferedReader(new InputStreamReader(System.in));
    }
    
    return reader;
  }
}
