package Philosophers

abstract sealed class Message
case object Think extends Message
case object Eat extends Message
case object Request extends Message
