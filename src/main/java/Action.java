
public class Action {
  private final int dropCol;
  private final CheckerType type;
  
  public Action(CheckerType type, int dropCol) {
    this.dropCol = dropCol;
    this.type = type;
  }

  public int getDropValue() {
    return dropCol;
  }
  
  public BoardState applyAction(BoardState board) {
    board = new BoardState(board);
    board.dropChecker(type, dropCol);
    return board;
  }
  
  public String toString() {
    return type + " will drop checker on column: " + dropCol;
  }
}
