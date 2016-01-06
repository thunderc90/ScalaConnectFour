/**
 * Created by thunder on 12/22/2015.
 */
sealed trait ScalaPlayerType
case object Human extends ScalaPlayerType
case object Computer extends ScalaPlayerType

case class PlayerSetting(
                        pType: ScalaPlayerType,
                        depth: Int,
                        prune: Boolean
                           )

//sealed class PlayerSettings (pType: ScalaPlayerType, depth: Int, prune: Boolean)

//sealed trait ApplicationSetting
//sealed case class RedPlayerSettings (pType: ScalaPlayerType, depth: Int,prune: Boolean)
//  extends PlayerSettings(pType, depth, prune) with ApplicationSetting
//sealed case class BlackPlayerSettings (pType: ScalaPlayerType, depth: Int,prune: Boolean)
//  extends PlayerSettings(pType, depth, prune) with ApplicationSetting

