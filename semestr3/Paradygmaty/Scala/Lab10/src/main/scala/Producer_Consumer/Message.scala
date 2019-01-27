package Producer_Consumer

abstract sealed class Message
case object Put extends Message
case object Take extends Message
case object Wake extends Message
