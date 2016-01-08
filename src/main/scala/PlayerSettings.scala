
/**
 * This class provides PlayerSettings that are loaded via typesafe/config (using the Ficus Scala adaptation)
 *
 * 2 player settings classes are loaded in, one for each player
 */
trait ScalaPlayerType
case object Human extends ScalaPlayerType
case object Computer extends ScalaPlayerType

//companion object for ScalaPlayerType trait that allows conversion from string
object ScalaPlayerType {

  import com.typesafe.config._
  import net.ceedubs.ficus.readers._

  /*
    Implicit value.. necessary to ensure an ValueReader is in scope to provide the answer
    to the question.. how do i translate this into a usable value...
   */
  implicit val scalaPlayerTypeReader: ValueReader[ScalaPlayerType] = new ValueReader[ScalaPlayerType]{
    override def read(config: Config, path: String): ScalaPlayerType = {
      val ptype = config.getString(path)
      fromString(ptype) match {
        case Some(x) => x
        case None => throw new InvalidArgumentDetectedException(
          "Invalid PlayerType Parameter in application.conf: Must use Human or Computer")
      }
    }
  }

  def fromString(ptype: String): Option[ScalaPlayerType] with Product with Serializable = {
    ptype match {
      case "Human" => Some(Human)
      case "Computer" => Some(Computer)
      case _ => None
    }
  }
}

case class PlayerSetting(pType: ScalaPlayerType, depth: Int, prune: Boolean)



