
public class DefaultMiniMaxHelper extends MiniMaxHelper {

  public int minValue(BoardState board, int maxDepth, CheckerType nextMove) {
    int retVal = Integer.MAX_VALUE;
    
    getMiniMaxAlgorithm().adjustMaxDepthCount(maxDepth);
    getMiniMaxAlgorithm().incrementActionsCount();
    
    if(!board.complete() && maxDepth > 0) {
      for(Action a : actions(board, nextMove)) {
        retVal = Math.min(retVal, maxValue(a.applyAction(board), maxDepth-1, CheckerType.switchType(nextMove)));
      }
    } else {
      retVal = board.utility(nextMove);
      
    }
    
    return retVal;
  }
  
  public int maxValue(BoardState board, int maxDepth, CheckerType nextMove) {
    int retVal = Integer.MIN_VALUE;
    
    getMiniMaxAlgorithm().adjustMaxDepthCount(maxDepth);
    getMiniMaxAlgorithm().incrementActionsCount();
    
    if(!board.complete() && maxDepth > 0) {
      for(Action a : actions(board, nextMove)) {
        retVal = Math.max(retVal, minValue(a.applyAction(board), maxDepth-1, CheckerType.switchType(nextMove)));
      }
    } else {
      retVal = board.utility(nextMove);
    }
      
    return retVal;
  }
}
