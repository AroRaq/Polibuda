package Lab11.Philosophers

abstract sealed class PhilosopherState
case object Thinking extends PhilosopherState
case object Eating extends PhilosopherState
case object Waiting extends PhilosopherState
