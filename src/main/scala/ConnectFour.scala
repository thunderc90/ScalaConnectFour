import net.ceedubs.ficus.Ficus._
import net.ceedubs.ficus.readers.ArbitraryTypeReader._
import com.typesafe.config.ConfigFactory

/**
 * Created by thunder on 12/21/2015.
 */
object ConnectFourScala {

  type ArgsMap = Map[Symbol, Any]

  def main(args: Array[String]): Unit = {
    val argsMap = parseArgs(args)
    println("Arguments Map:  " + argsMap)

    println("loading typesafe config")
    val config = ConfigFactory.load()

    val redPlayerType = config.as[PlayerSetting]("red-player")
    //val blackPlayer = config.as[PlayerSetting]("black-player")

    println("Red Player: \n" + redPlayerType)
    //println("Black Player \n" + blackPlayer)

  }

  def parseArgs(args: Array[String]): ArgsMap  = {
    val usage =
      """
        |Usage: ConnectFour [--red.type type] [--black.type type] [--red.depth int] [--black.depth int] [--red.prune bool]
        |   [--black.prune bool]
        |
        |Details:
        |   red.type [Computer/Human] - allows you to change red player from an ai player to a human player or vice-versa
        |		black.type [Computer/Human] - allows you to change black player from an ai player to a human player or vice-versa
        |
        |		red.depth [integer]	- 	allows you to adjust the depth the search goes to
        |								for the red player, does nothing if redType is human
        |		black.depth [integer] - 	allows you to adjust the depth the search goes to
        |								for the black player, does nothing if blackType is human
        |
        |		red.prune [True/False] - allows you to turn the alpha-beta pruning on (true) or off (false),
        |								this has no effect if redType is human
        |		black.prune [True/False]-allows you to turn the alpha-beta pruning on (true) or off (false),
        |								this has no effect if blackType is human
      """.stripMargin

    if (args.length == 0) {
      println(usage)
    }

    val arglist = args.toList

    def nextOption(map: ArgsMap, list: List[String]) : ArgsMap = {
      list match {
        case Nil => map
        case "--red.type" :: value :: tail =>
          nextOption(map ++ Map('redType-> value), tail)
        case "--black.type" :: value :: tail =>
          nextOption(map ++ Map('blackType-> value), tail)
        case "--red.depth" :: value :: tail =>
          nextOption(map ++ Map('redDepth-> value.toInt), tail)
        case "--black.depth" :: value :: tail =>
          nextOption(map ++ Map('blackDepth -> value.toInt), tail)
        case "--red.prune" :: value :: tail =>
          nextOption(map ++ Map('redPrune -> value.toBoolean), tail)
        case "--black.prune" :: value :: tail =>
          nextOption(map ++ Map('blackPrune -> value.toBoolean), tail)
        case option :: tail =>
          println("WARNING: Unknown Option: "+option)
          nextOption(map, tail)
      }
    }

    nextOption(Map(), arglist)
  }
}
