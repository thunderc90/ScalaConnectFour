import net.ceedubs.ficus.Ficus._
import net.ceedubs.ficus.readers.ArbitraryTypeReader._
import com.typesafe.config.ConfigFactory

/**
 * Contains basic configuration load logic, usage printout, and application configuration objects
 */
object ConnectFourScala {
  import ScalaPlayerType._

  //Configuration objects, loaded in as needed
  private lazy val config = ConfigFactory.load()
  lazy val redPlayerSettings = config.as[PlayerSetting]("red-player")
  lazy val blackPlayerSettings = config.as[PlayerSetting]("black-player")

  def main(args: Array[String]): Unit = {
    printWelcomeBanner(args)

    println("Red Player: " + redPlayerSettings)
    println("Black Player " + blackPlayerSettings)

  }

  def printWelcomeBanner(args: Array[String]): Unit  = {
    val welcome =
      """
        |Loading Application Configuration
      """.stripMargin
    val usage =
      """
        |Usage: ConnectFour
        |
        |ConnectFour is configured through the application.conf file
        |
        |Details:
        |   red-player.ptype [Computer/Human] - allows you to change red player from an ai player to a human player or vice-versa
        |		black-player.ptype [Computer/Human] - allows you to change black player from an ai player to a human player or vice-versa
        |
        |		red-player.depth [integer]	- 	allows you to adjust the depth the search goes to
        |								for the red player, does nothing if redType is human
        |		black-player.depth [integer] - 	allows you to adjust the depth the search goes to
        |								for the black player, does nothing if blackType is human
        |
        |		red-player.prune [True/False] - allows you to turn the alpha-beta pruning on (true) or off (false),
        |								this has no effect if redType is human
        |		black-player.prune [True/False]-allows you to turn the alpha-beta pruning on (true) or off (false),
        |								this has no effect if blackType is human
      """.stripMargin
    args.length match {
      case 0 => println(welcome)
      case _ =>
        println(usage)
        System.exit(0)
    }
  }
}
