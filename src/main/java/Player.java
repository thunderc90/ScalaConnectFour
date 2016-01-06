
public abstract class Player {

  private CheckerType type;
  
  public static Player createPlayer(CheckerType checkerType, PlayerType playerType,
      int depth, boolean prune) {
    
    Player retVal = null;
    
    switch(playerType) {
      case COMPUTER: {
        MiniMaxHelper helper;
        
        if(prune) {
          helper = new AlphaBetaPruneHelper();
        } else {
          helper = new DefaultMiniMaxHelper();
        }
        
        retVal = new ComputerPlayer(checkerType, depth, new MiniMaxAlgorithm(helper));
        break;
      }
      case HUMAN: {
        retVal = new HumanPlayer(checkerType);
      }
    }
    
    return retVal;
  }
  
  Player(CheckerType type) {
    this.type = type;
  }
  
  public CheckerType getCheckerType() {
    return type;
  }
  
  public void setCheckerType(CheckerType type){
    this.type = type;
  }
  
  public abstract Action getMove(BoardState board);

  

}
