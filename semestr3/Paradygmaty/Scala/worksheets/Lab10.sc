class Box1[A](val v: Int) {}
class Box2[+A](val v: Int) {}
class Box3[-A](val v: Int) {}

class Csup
class C extends Csup
class Csub extends C

def print1(b: Box1[C]) : Unit = {println(b.v)}
def print2(b: Box2[C]) : Unit = {println(b.v)}
def print3(b: Box3[C]) : Unit = {println(b.v)}

//invariant
print1(new Box1[Csup](0))
print1(new Box1[C](0))
print1(new Box1[Csub](0))

//covariant
print2(new Box2[Csup](0))
print2(new Box2[C](0))
print2(new Box2[Csub](0))

//contravariant
print3(new Box3[Csup](0))
print3(new Box3[C](0))
print3(new Box3[Csub](0))