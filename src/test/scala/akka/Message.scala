package akka

sealed trait Mode

case object Tell extends Mode
case object TellDelegate extends Mode
case object Ask extends Mode

case class Message(mode: Mode, who: String, message: String)