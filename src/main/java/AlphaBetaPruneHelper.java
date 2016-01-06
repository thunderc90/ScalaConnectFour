
public class AlphaBetaPruneHelper extends MiniMaxHelper {

  @Override
  public int minValue(BoardState board, int maxDepth, CheckerType nextMove) {
    return minValueAlphaBeta(board, maxDepth, nextMove,
        Integer.MIN_VALUE,
        Integer.MAX_VALUE);
  }

  @Override
  public int maxValue(BoardState board, int maxDepth, CheckerType nextMove) {
    return maxValueAlphaBeta(board, maxDepth, nextMove,
        Integer.MIN_VALUE, 
        Integer.MAX_VALUE);
  }
  
  private int minValueAlphaBeta(BoardState board, int maxDepth,
      CheckerType nextMove, int alpha, int beta) {
    int retVal = Integer.MAX_VALUE;
    
    getMiniMaxAlgorithm().adjustMaxDepthCount(maxDepth);
    getMiniMaxAlgorithm().incrementActionsCount();
    
    if(!board.complete() && maxDepth > 0) {
      for(Action a : actions(board, nextMove)) {
        retVal = Math.min(retVal, maxValueAlphaBeta(a.applyAction(board),
            maxDepth-1,CheckerType.switchType(nextMove),alpha,beta));
        if(retVal <= alpha)
          break;
        beta = Math.min(beta, retVal);
      }
    } else {
      retVal = board.utility(nextMove);
    }
    
    return retVal;
  }
  
  private int maxValueAlphaBeta(BoardState board, int maxDepth,
      CheckerType nextMove, int alpha, int beta) {
    int retVal = Integer.MIN_VALUE;
    
    getMiniMaxAlgorithm().adjustMaxDepthCount(maxDepth);
    getMiniMaxAlgorithm().incrementActionsCount();
    
    if(!board.complete() && maxDepth > 0) {
      for(Action a : actions(board, nextMove)) {
        retVal = Math.max(retVal, minValueAlphaBeta(a.applyAction(board),
            maxDepth - 1, CheckerType.switchType(nextMove), alpha, beta));
        
        if(retVal >= beta) {
          break;
        }
        alpha = Math.max(alpha, retVal);
      }
    } else {
      retVal = board.utility(nextMove);
    }
    
    return retVal;
  }
}
