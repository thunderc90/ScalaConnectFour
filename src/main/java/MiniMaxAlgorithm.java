public class MiniMaxAlgorithm {
  
   private final MiniMaxHelper helper;
   
   private int maxDepthReached;
   private int actionsExamined;

  private int maxDepth;
  
   public MiniMaxAlgorithm(MiniMaxHelper helper) {
     this.helper = helper;
     helper.setMiniMaxAlgorithm(this);
   }
   
   public void resetCounters() {
     maxDepthReached = 0;
     actionsExamined = 0;
   }
   
   public void adjustMaxDepthCount(int maxDepth) {
     maxDepth = this.maxDepth - maxDepth;
     if(maxDepth > maxDepthReached) {
       maxDepthReached = maxDepth;
     }
   }
   
   public void incrementActionsCount() {
     actionsExamined++;
   }
   
   public int getMaxDepth() {
     return maxDepthReached;
   }
   
   public int getActionsCount() {
     return actionsExamined;
   }
   
   public boolean hasPruning() {
     boolean retVal = false;
     if (helper instanceof AlphaBetaPruneHelper) {
       retVal = true;
     }
     return retVal;
   }

   public Action miniMaxDecision(BoardState board, int maxDepth, CheckerType nextMove) {
    Action retVal = null;
    
    switch(nextMove) {
    case BLACK:
      retVal = maxActionSearch(board, maxDepth, nextMove);
      break;
    case RED:
      retVal = minActionSearch(board, maxDepth, nextMove);
      break;
    default:
      break;
    }
    
    return retVal;
  }

  private Action minActionSearch(BoardState board, int maxDepth,
      CheckerType nextMove) {
    Action retVal = null;
    int retValUtility = Integer.MAX_VALUE;
    
    for(Action a : helper.actions(board, nextMove)) {
      int util = helper.maxValue(a.applyAction(board), maxDepth, CheckerType.switchType(nextMove));
//      System.out.println("Col: " + a.getDropValue() + " util: " + util);
      if(util < retValUtility) {
        retVal = a;
        retValUtility = util;
      }
      
      if(retValUtility==1)
        break;
    }
    
//    System.out.println("Eval: " + retValUtility);
    return retVal;
  }

  private Action maxActionSearch(BoardState board, int maxDepth,
      CheckerType nextMove) {
    Action retVal = null;
    int retValUtility = Integer.MIN_VALUE;
    
    for(Action a: helper.actions(board, nextMove)) {
      int util = helper.minValue(a.applyAction(board), maxDepth, CheckerType.switchType(nextMove));
//      System.out.println("Col: " + a.getDropValue() + " util: " + util);
      if(util > retValUtility) {
        retVal = a;
        retValUtility = util;
      }
      
      if(retValUtility==1023)
        break;
    }
    
//    System.out.println("Eval: " + retValUtility);
    return retVal;
  }

  public void setMaxDepth(int maxDepth) {
    this.maxDepth = maxDepth;
  }

  

}
