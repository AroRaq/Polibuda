class Time1(private var h: Int) {
  require(h<24, "value must be in 24-hour range")
  if (h < 0) h = 0

  def hour: Int = h
  def hour_= (x: Int) {
    require(x<24, "value must be in 24-hour range")
    hour = if (x < 0) 0 else x
  }
  override def toString = h.toString
}
object Time1 {
  def apply(h: Int) = new Time1(h)
}


class Time2a (private var h: Int, private var m: Int) {
  require(0 <= h && h < 24 && 0 <= m && m < 60)

  def before(other: Time2a) = {
    if (h == other.h) m < other.m
    else h < other.h
  }
  def hour: Int = h
  def hour_= (x: Int): Unit = {
    require(0 <= x && x < 24, "value must be in 24-hour range")
    h = x
  }
  def minute: Int = m
  def minute_= (x: Int): Unit = {
    require(0 <= x && x < 60, "value must be in 60-minute range")
    m = x
  }
  override def toString = h + ":" + m
}


class Time2b(h: Int, m: Int) {
  require(0 <= h && h < 24, "value must be in 24-hour range")
  require(0 <= m && m < 60, "value must be in 60-minute range")

  private var minutesAfterMidnight = 60*h + m

  def before(other: Time2b) =
    minutesAfterMidnight < other.minutesAfterMidnight
  def hour: Int = minutesAfterMidnight / 60
  def hour_= (x: Int): Unit = {
    require(0 <= x && x < 24, "value must be in 24-hour range")
    minutesAfterMidnight = 60*x + minute
  }
  def minute: Int = minutesAfterMidnight % 60
  def minute_= (x: Int): Unit = {
    require(0 <= x && x < 60, "value must be in 60-minute range")
    minutesAfterMidnight = 60*hour + x
  }
  override def toString = hour + ":" + minute
}


class Pojazd(private val p: String, private val m: String,
             private val r: Int = -1, private var n: String = "") {

  def this(p: String, m: String, n: String) = this(p,m,-1,n)
  def producent= p
  def model = m
  def rokProdukcji = r
  def nrRejestracyjny = n
  def nrRejestracyjny_= (x: String) = {
    n = x
  }
}
object Pojazd {
  def apply(p: String, m: String, r: Int = -1, n: String = "") = new Pojazd(p,m,r,n)
  def apply(p: String, m: String, n: String) = new Pojazd(p,m,-1,n)
}