import util.Random

def whileLoop(cond: () => Boolean, expr: () => Unit) : Unit = {
  if (cond()) {
    expr()
    whileLoop(cond, expr)
  }
}




def quickSort(tab: Array[Int]) : Unit = {
  def swap(tab: Array[Int], i: Int, j: Int): Unit = {
    val temp = tab(i)
    tab(i) = tab(j)
    tab(j) = temp
  }

  def partition(tab: Array[Int], l: Int, r: Int): (Int, Int) = {
    val pivot = tab((l + r) / 2)
    var i = l
    var j = r
    while (i <= j) {
      while (tab(i) < pivot) i += 1
      while (pivot < tab(j)) j -= 1
      if (i <= j) {
        swap(tab, i, j)
        i += 1
        j -= 1
      }
    }
    (i, j)
  }

  def quick(tab: Array[Int], l: Int, r: Int): Unit = {
    if (l < r) {
      val (i, j) = partition(tab, l, r)
      if (j - l < r - i) {
        quick(tab, l, j)
        quick(tab, i, r)
      }
      else {
        quick(tab, i, r)
        quick(tab, l, j)
      }
    }
  }

  quick(tab, 0, tab.length - 1)
}





val tab = Array.fill(10)(Random.nextInt(100))
quickSort(tab)
tab