sealed trait Exprr
case class Value(value: Int) extends Exprr
case class Add(left: Exprr, right: Exprr) extends Exprr
case class Negative(expr: Exprr) extends Exprr


def calculate(expr: Exprr) : Int = {
  expr match {
    case Value(v) => v
    case Add(l, r) => calculate(l) + calculate(r)
    case Negative(v) => -calculate(v)
  }
}

calculate (Add(Negative(Value(10)), Value(100)));;
