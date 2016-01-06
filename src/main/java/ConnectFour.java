public class ConnectFour {
  
  private static final int DEFAULT_DEPTH = 7;
  private static final boolean DEFAULT_PRUNE = true;
  private static final PlayerType DEFAULT_BLACK_TYPE = PlayerType.HUMAN;
  private static final PlayerType DEFAULT_RED_TYPE = PlayerType.COMPUTER;
  
  private int moveCount;
  private BoardState currentBoard;
  private final long[] moveTime = new long[2];
  private final Player[] players = new Player[2];
  /**
   * @param args various arguments are passed in here, see README for details
   */
  public static void main(String[] args) {
    
    boolean blackPrune = DEFAULT_PRUNE;
    boolean redPrune = DEFAULT_PRUNE;
    
    int blackDepth = DEFAULT_DEPTH;
    int redDepth = DEFAULT_DEPTH;
    
    PlayerType blackType = DEFAULT_BLACK_TYPE;
    PlayerType redType = DEFAULT_RED_TYPE;
    
    if( args.length != 0 ) {
      try {
        for(int i = 0; i < args.length; i++) {
          if(args[i].equalsIgnoreCase("REDTYPE")) {
            if(i < args.length-1) { 
              i++;
              redType = getType(args[i]);
            } else {
              throw new InvalidArgumentDetectedException(
                  args[i] + " expects an argument (Computer, or Human)");
            }
          } else if(args[i].equalsIgnoreCase("BLACKTYPE")) {
            if(i < args.length-1) {
              i++;
              blackType = getType(args[i]);
            } else {
              System.err.println(args[i] + " expects an argument (Computer, or Human)");
              throw new InvalidArgumentDetectedException(
                  args[i] + " expects an argument (Computer, or Human)");
            }
          } else if (args[i].equalsIgnoreCase("REDDEPTH")) {
            if(i < args.length-1) {
              i++;
              redDepth = getDepth(args[i]);
            } else {
              throw new InvalidArgumentDetectedException(
                  args[i] + " requires an integer argument");
            }
          } else if (args[i].equalsIgnoreCase("BLACKDEPTH")) {
            if(i < args.length-1) {
              i++;
              blackDepth = getDepth(args[i]);
            } else {
              throw new InvalidArgumentDetectedException(
                  args[i] + " requires an integer argument");
            }
          } else if (args[i].equalsIgnoreCase("REDPRUNE")) {
            if(i < args.length-1) {
              i++;
              redPrune = getPrune(args[i]);
            } else {
              throw new InvalidArgumentDetectedException(
                  args[i] + " requires a boolean argument (True or False)");
            }
          } else if (args[i].equalsIgnoreCase("BLACKPRUNE")) {
            if(i < args.length-1) {
              i++;
              blackPrune = getPrune(args[i]);
            } else {
              throw new InvalidArgumentDetectedException(
                  args[i] + "requires a boolean argument (True or False)");
            }
          } else {
            throw new InvalidArgumentDetectedException(
                args[i] + " is not a valid argument");
          }
        }
      } catch (InvalidArgumentDetectedException e) {
        System.err.println("Invalid Input Occured");
        e.printStackTrace();
      }
    }
    
    //initialize player objects
    Player blackPlayer = Player.createPlayer(CheckerType.BLACK, 
        blackType, blackDepth, blackPrune);
    Player redPlayer = Player.createPlayer(CheckerType.RED,
        redType, redDepth, redPrune);
    
    ConnectFour game = new ConnectFour(blackPlayer, redPlayer);
    
    game.play();
  }
  
  private static boolean getPrune(String string) 
      throws InvalidArgumentDetectedException {
    boolean retVal;
    
    if(string.equalsIgnoreCase("FALSE")) {
      retVal = false;
    } else if (string.equalsIgnoreCase("TRUE")) {
      retVal = true;
    } else {
      throw new InvalidArgumentDetectedException(
          string + " is invalid. Expected True or False");
    }
    
    return retVal;
  }

  private static int getDepth(String string) 
      throws InvalidArgumentDetectedException {
   
    int retVal;
    
    try {
      retVal = Integer.parseInt(string);
    } catch (NumberFormatException e) {
      throw new InvalidArgumentDetectedException(
          "Depth value invalid (integer > 0)");
    }
    return retVal;
  }

  private static PlayerType getType(String string)
      throws InvalidArgumentDetectedException { 
    
    PlayerType retVal;
    
    if(string.equalsIgnoreCase("COMPUTER")){
      retVal = PlayerType.COMPUTER;
    } else if (string.equalsIgnoreCase("HUMAN")){
      retVal = PlayerType.HUMAN;
    } else {
      throw new InvalidArgumentDetectedException(
          string + " is invalid. Expected Human or Computer.");
    }
    return retVal;
  }

  private void play() {
    //print starting information
    System.out.println("Player 1: " + players[0]);
    System.out.println("Player 2: " + players[1]);
    System.out.println();
    
    //complete indicates win for red, win for black, or tie
    while(!currentBoard.complete()) {
//      currentBoard.printBoard();
      play(nextPlayer(), currentBoard);
      moveCount++;
    }
    
    currentBoard.printBoard();
    currentBoard.printCompletion();
    
    
    System.out.println(moveCount + " total moves.");
    for(int i = 0 ; i < 2; i++) {
      System.out.println(players[i].getCheckerType() +
          " player avg move time: " + moveTime[i]/(moveCount/2));
    }
  }
  
  private void play(Player player, BoardState currentBoard) {
    long startTime = System.currentTimeMillis();
    Action act = player.getMove(currentBoard);
     long endTime = System.currentTimeMillis();
    if(act != null) {
      this.currentBoard = act.applyAction(currentBoard);
      moveTime[moveCount%2] += (endTime-startTime);
      System.out.println("Player: " + player.getCheckerType() +
          " Moved: " + act.getDropValue() + 
          " Time: " + (endTime - startTime));
    }
  }

  /**
   * assumes checkertype assigned to players is unique
   * didn't add error checking for it
   * @param p0 player 0
   * @param p1 player 1
   */
  private ConnectFour(Player p0, Player p1) {
    if(p0.getCheckerType() == CheckerType.BLACK) {
      players[0]=p0;
      players[1]=p1;
    } else {
      players[0]=p1;
      players[1]=p0;
    }
      
    moveTime[0]=0;
    moveTime[1]=0;
    
    currentBoard = new BoardState();
  }

  private Player nextPlayer(){
    return players[moveCount%2];
  }
}
