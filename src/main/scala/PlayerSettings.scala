
/**
 * Created by thunder on 12/22/2015.
 */
trait ScalaPlayerType
case object Human extends ScalaPlayerType
case object Computer extends ScalaPlayerType
case object InvalidType extends ScalaPlayerType

//companion object for ScalaPlayerType trait that allows conversion from string
object ScalaPlayerType {

  import com.typesafe.config._
  import net.ceedubs.ficus.readers._
  import net.ceedubs.ficus.Ficus._

  /*def fromString(s: String): ScalaPlayerType = s match {
    case "Human" => Human
    case "Computer" => Computer
    case "InvalidType" => InvalidType
  }

  def apply(x: Object): ScalaPlayerType = fromString(x.asInstanceOf[String])*/

  implicit val scalaPlayerTypeReader: ValueReader[ScalaPlayerType] = new ValueReader[ScalaPlayerType]{
    override def read(config: Config, path: String): ScalaPlayerType = {
      val ptype = config.getString(path)
      ptype match {
        case "Human" => Human
        case "Computer" => Computer
        case _ => InvalidType
      }
    }
  }

}

case class PlayerSetting(pType: ScalaPlayerType, depth: Int, prune: Boolean)



