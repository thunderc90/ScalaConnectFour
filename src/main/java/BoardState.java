import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class BoardState {
  //6x7 rows first
  private static final int ROWS = 6;
  private static final int COLS = 7;
  private static final int POINTGROUP_LENGTH = 4;
  
  private static List<PointGroup> pointGroups;
  
  public static void main(String args[]) {
    System.out.println("This main only used for testing.");
    
    char[][] chars = {
        {' ',' ',' ',' ',' ',' ',' '},
        {' ',' ',' ',' ',' ',' ',' '},
        {' ',' ',' ',' ',' ',' ',' '},
        {' ',' ',' ',' ',' ',' ',' '},
        {' ',' ',' ',' ',' ',' ',' '},
        {' ',' ',' ',' ',' ',' ',' '}
        };
    
    BoardState board = new BoardState(chars);
    board.printBoard();
    System.out.println();
    int redUtil = board.utility(CheckerType.RED);
    System.out.println("Red Board Utility: " + redUtil);
    int blackUtil = board.utility(CheckerType.BLACK);
    System.out.println("Black Board Utility: " + blackUtil);
  }
  
  public enum PointGroupType {
    RIGHT,
    DOWN,
    DIAG_RIGHT,
    DIAG_LEFT
  }
  
  private final CheckerType[][] boardCells;
  
  public BoardState() {
    boardCells = new CheckerType[ROWS][COLS];
    for(int i = 0; i < ROWS; i++) {
      for(int j = 0; j < COLS; j++ ) {
        boardCells[i][j] = CheckerType.EMPTY;
      }
    }
  }
  
  public BoardState(BoardState state) {
    boardCells = new CheckerType[ROWS][COLS];
    for(int row = 0; row < ROWS; row++) {
      System.arraycopy(state.boardCells[row], 0, boardCells[row], 0, COLS);
    }
  }
  
  private BoardState(char[][] chars) {
    this();
    for(int i = 0; i < chars.length; i ++) {
      for(int j = 0; j < chars[0].length; j++) {
        switch(chars[i][j]) {
          case 'R':
            boardCells[i][j]= CheckerType.RED;
            break;
          case 'B':
            boardCells[i][j] = CheckerType.BLACK;
            break;
          default:
            break;
        }
      }
    }
  }
  
  public boolean dropChecker(CheckerType type, int dropCol) {
    boolean retVal = true;
    if(type == CheckerType.EMPTY) {
      retVal = false; 
    } else {
      int dropRow = nextDropRow(dropCol);
      if(dropRow >= 0) {
        boardCells[dropRow][dropCol] = type;
      } else {
        retVal = false;
      }
    }
    return retVal;
  }
  
  public boolean complete() {
    boolean retVal = false;
    
    List<PointGroup> pointGroups = getPointGroups();
    
    if(isFull()){
      retVal = true;
    } else {
      for(PointGroup p : pointGroups) {
        if(p.containsWin(this, CheckerType.BLACK)) {
          retVal = true;
          break;
        }
        if(p.containsWin(this, CheckerType.RED)) {
          retVal = true;
          break;
        }
      }
    }
    
    return retVal;
  }

  private boolean isFull() {
    boolean retVal;
    retVal = true;
    for(int col = 0; col < COLS; col++) {
      if(boardCells[0][col] == CheckerType.EMPTY) {
        retVal = false;
        break;
      }
    }
    return retVal;
  }
  
  public void printBoard() {
    for(int i = 0; i < ROWS; i++) {
      for(int j = 0; j < COLS; j++) {
        System.out.print(" " + print(boardCells[i][j]));
      }
      System.out.println();
    }
  }
  
  private String print(CheckerType checkerType) {
    String retVal = null;
    switch(checkerType) {
      case BLACK:
        retVal = "B";
        break;
      case RED:
        retVal = "R";
        break;
      case EMPTY:
        retVal = " ";
        break;
    }
    return retVal;
  }

  private static List<PointGroup> getPointGroups(){
    if(pointGroups == null) {
      pointGroups = createPointGroups();
    }
    return pointGroups;
  }
  
  public void printCompletion() {
    boolean iscomplete = false;
    if(isFull()) {
      System.out.println("Red and Black are Tied");
      iscomplete=true;
    } else {
      for (PointGroup p : pointGroups) {
        if (p.containsWin(this, CheckerType.BLACK)) {
          System.out.println("BLACK has won the game.");
          iscomplete = true;
          break;
        }
        if (p.containsWin(this, CheckerType.RED)) {
          System.out.println("RED has won the game.");
          iscomplete = true;
          break;
        }
      }
    }
    if(!iscomplete) {
      System.out.println("Game is incomplete");
    }
  }
  
  public List<Action> getValidActions(CheckerType type) {
    List<Action> retVal = new ArrayList<>();
    
    for(int col = 0; col < COLS; col++) {
      if(boardCells[0][col] == CheckerType.EMPTY) {
        retVal.add(new Action(type, col));
      }
    }
    
    return retVal;
  }
  
  public int utility(CheckerType nextType) {
    int retVal = 512; //start at neutral value
    
    if(hasWin(CheckerType.BLACK)) {
      retVal = 1023;
    } else if(hasWin(CheckerType.RED)){
      retVal = 1;
    } else {
      
      for(PointGroup p : getPointGroups()) {
        retVal += p.utility(this);
      }
      
      switch(nextType) {
        case BLACK:
          retVal += 16;
          break;
        case RED:
          retVal -= 16;
          break;
        default:
          break;
      }
      
      if(retVal > 1022) {
        retVal = 1022;
      } else if (retVal < 2) {
        retVal = 2;
      }
    }
    
    return retVal;
  }

  private boolean hasWin(CheckerType type) {
    boolean retVal = false;
    
    for(PointGroup p : getPointGroups()) {
      if(p.containsWin(this, type)) {
        retVal = true;
        break;
      }
    }
    
    return retVal;
  }

  private static List<PointGroup> createPointGroups() {
    List<PointGroup> retVal = new ArrayList<>();
    
    for(int row = 0; row < ROWS; row++) {
      for(int col = 0; col < COLS; col++) {
        if(row < ROWS-(POINTGROUP_LENGTH-1)) {
          retVal.add(new PointGroup(row, col, PointGroupType.DOWN));
          
          if(col < COLS-(POINTGROUP_LENGTH-1)) {
            retVal.add(new PointGroup(row, col, PointGroupType.DIAG_RIGHT));
          }
          
          if(col > POINTGROUP_LENGTH-2) {
            retVal.add(new PointGroup(row, col, PointGroupType.DIAG_LEFT));
          }
        }
        
        if(col < COLS-(POINTGROUP_LENGTH-1)) {
          retVal.add(new PointGroup(row, col, PointGroupType.RIGHT));
        }
      }
    }
    
    return retVal;
  }

  private int nextDropRow(int dropCol) {
    int retVal = -1;
    for (int i = 0; i < ROWS; i++) {
      if(boardCells[i][dropCol] == CheckerType.EMPTY) {
        retVal = i;
      } else {
        break;
      }
    }
    return retVal;
  }
  
  /**
   * helper class to assist in checking board completion / 
   * and calculating minimax algorithms
   * @author Thunder
   *
   */
  private static class PointGroup {
    private final List<Point> points;
    
    public PointGroup(int row, int col, PointGroupType type) {
      //set points from start row/col
      points = new ArrayList<>();
      
      switch (type){
        case DOWN:
          for(int i = row; i < row+POINTGROUP_LENGTH; i++) {
            points.add(new Point(i,col));
          }
          break;
        case RIGHT:
          for(int i = col; i < col+POINTGROUP_LENGTH; i++) {
            points.add(new Point(row, i));
          }
          break;
        case DIAG_RIGHT:
          for(int i = 0; i < POINTGROUP_LENGTH; i++) {
            points.add(new Point(row+i, col+i));
          }
          break;
        case DIAG_LEFT:
          for(int i = 0; i < POINTGROUP_LENGTH; i++) {
            points.add(new Point(row+i,col-i));
          }
          break;
      }
    }

    public int utility(BoardState board) {
      int blackCount = 0;
      int redCount = 0;
      int retVal = 0;
      
      for(Point p : points) {
        switch(board.boardCells[p.x][p.y]) {
        case BLACK:
          blackCount++;
          break;
        case RED:
          redCount++;
          break;
        case EMPTY:
          break;
        }
      }
      
      if(blackCount > 0 && redCount > 0) {
        retVal = 0;
      } else {
        if(blackCount > 0) {
          retVal += utilityValue(blackCount);
        } else if (redCount > 0) {
          retVal += -1*utilityValue(redCount);
        }
      }
      
      return retVal;
    }

    private int utilityValue(int blackCount) {
      int retVal = 0;
      
      switch(blackCount) {
        case 1:
          retVal += 1;
          break;
        case 2:
          retVal += 10;
          break;
        case 3: 
          retVal += 50;
      }
      
      return retVal;
    }

    public boolean containsWin(BoardState board, CheckerType type) {
      boolean retVal = true;
      for(Point p : points) {
        if(board.boardCells[p.x][p.y] != type) {
          retVal =false;
          break;
        }
      }
      
      return retVal;
    }
  }
}
