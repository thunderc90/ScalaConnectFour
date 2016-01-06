public class ComputerPlayer extends Player {

  private final int maxDepth;
  private final MiniMaxAlgorithm alg;  
  
  public ComputerPlayer (CheckerType type, int maxDepth, MiniMaxAlgorithm alg) {
    super(type);
    this.maxDepth = maxDepth;
    this.alg = alg;
    alg.setMaxDepth(maxDepth);
  }
  
  @Override
  public Action getMove(BoardState board) {
    alg.resetCounters();
    Action retVal = alg.miniMaxDecision(board, maxDepth, getCheckerType());
    printStats();
    return retVal;
  }
  
  public String toString() {
    return getCheckerType() + " Computer using " 
      + (alg.hasPruning()?"pruned ":"") + "search depth: " + maxDepth;
  }
  
  private void printStats() {
    System.out.println("Computer Player search report:");
    System.out.println("Maximum Depth Reached: " + alg.getMaxDepth());
    System.out.println("Actions Examined: " + alg.getActionsCount());
    System.out.println("Effective Branching Factor: " + Math.pow(alg.getActionsCount(), 1.0/alg.getMaxDepth()));
  }
}
