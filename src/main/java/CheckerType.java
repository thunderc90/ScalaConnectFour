public enum CheckerType  {
  RED,
  BLACK,
  EMPTY;
  
  public static CheckerType switchType(CheckerType type) {
    CheckerType retVal = CheckerType.EMPTY;
    switch(type) {
      case RED:
        retVal = CheckerType.BLACK;
        break;
      case BLACK:
        retVal = CheckerType.RED;
        break;
    default:
      //wasn't necessary
      //retVal = CheckerType.EMPTY;
      break;
    }
    return retVal;
  }
}