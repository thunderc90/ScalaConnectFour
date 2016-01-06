import java.util.List;


abstract class MiniMaxHelper {

  private MiniMaxAlgorithm algorithm = null;
  
  public void setMiniMaxAlgorithm(MiniMaxAlgorithm alg) {
    algorithm = alg;
  }
  
  MiniMaxAlgorithm getMiniMaxAlgorithm() {
    return algorithm;
  }
  
  public abstract int minValue(BoardState board, int maxDepth, CheckerType checker);
  
  public abstract int maxValue(BoardState board, int maxDepth, CheckerType checker);
  
  public List<Action> actions(BoardState board, CheckerType checkerType) {
    return board.getValidActions(checkerType);
  }
}
