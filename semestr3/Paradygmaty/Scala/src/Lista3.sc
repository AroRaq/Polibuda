def curry3[A, B, C, D](f: (A, B, C) => D)=



val plus = (x: Int, y: Int) => x + y

(plus curried 4)(5)