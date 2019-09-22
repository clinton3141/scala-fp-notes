package uk.co.slightlymore.fpinscala

import uk.co.slightlymore.fpinscala.datastructures._

object Chapter3 {
  def main(args: Array[String]): Unit = {
    val intList: List[Int] = List(1, 2, 3)
    println(List.sum(intList))
    
    val doubleList: List[Double] = List(1, 2, 3)
    println(List.product(doubleList))
    
    // because List[+A] is covariant, Nil can be a list of any type,
    // so has all methods
    val nilList = Nil
    println(List.sum(nilList))
    println(List.product(nilList))
    
    // ex 3.1: what's the output?
    val x = List(1, 2, 3, 4, 5) match {
      case Cons(x, Cons(2, Cons(4, _))) => x // no match - missing 3
      case Nil => 42 // no match - not nil
      case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y // match - x = 1, y = 2, _ = Cons(5, Nil)
      case Cons(h, t) => h + List.sum(t) // no match - already matched. But would do so if no previous match
      case _ => 101 // no match - already matched. But would do so if no previous match
    }
    println(x)
    
    val x3_2 = List(1, 2, 3)
    println(List.tail(x3_2))
    
    // should be List("new head", "tail1", "tail2")
    println(List.setHead("new head", List("old head", "tail1", "tail2")))
    
    // should be Nil because we can't replace a head which doesn't exist
    println(List.setHead("new head", List()))
    
    // ex 3.4
    println("Ex 3.4");
    println(List.drop(Nil, 5)) // should be Nil
    println(List.drop(List(1), 2)) // should be Nil
    println(List.drop(List(1, 2, 3), 2)) // should be List(3)
    println(List.drop(List(1, 2, 3), 3)) // should be Nil
    
    // ex 3.5
    println("Ex 3.5");
    println(List.dropWhile(List(), (x: Int) => x > 2)) // should be Nil
    println(List.dropWhile(List(1,2,3,4), (x: Int) => x > 2)) // should be List(1, 2, 3, 4)
    println(List.dropWhile(List(1,2,3,4), (x: Int) => x <= 2)) // should be List(3, 4)
    println(List.dropWhile2(List(1, 2, 3, 4))(_ > 2))
    
    println("Ex 3.6")
    println(List.init(List(1))) // should be Nil
    println(List.init(List(1, 2))) // should be List(1)
    println(List.init(List(1, 2, 3))) // should be List(1, 2)
    
    println("Ex 3.8")
    // calling with Nil and Cons returns the original list
    println(List.foldRight(List(1, 2, 3), Nil:List[Int])(Cons(_, _)))
    
    println("Ex 3.9")
    println(List.length(Nil))
    println(List.length(List(1, 2, 3)))
    
    println("Ex 3.10")
    println(List.foldLeft(List(1, 2, 3), 0)(_ + _))
    
    println("Ex 3.11")
    println(List.sumL(List()))
    println(List.sumL(List(1, 2, 3, 4)))
    println(List.productL(List()))
    println(List.productL(List(1, 2, 3, 4)))
    println(List.lengthL(List(1, 2, 3)))

    println("Ex 3.12")
    println(List.reverse(List(1, 2, 3)))

    println("Ex 3.13")
    println(List.foldRight(List(1, 2, 3), List[Int]())(Cons(_, _)))
    println(List.foldRightUsingLeft(List(1, 2, 3), List[Int]())(Cons(_, _)))
    
    println("Ex 3.14")
    println(List.append(List(1, 2, 3), 4))
    
    println("Ex 3.15")
    println(List.concat(List(List(1, 2, 3), List(4, 5, 6))))
    
    println("Ex 3.16")
    println(List.add1(List(1, 2, 3)))
    
    println("Ex 3.17")
    println(List.doubleToString(List(1.0, 2.0, 3.142)))
    
    println("Ex 3.18")
    println(List.map(List(1, 2, 3))(_ + 1))
    
    println("Ex 3.19")
    println(List.filter(List(1, 2, 3, 4, 5))(_ % 2 == 0))
    
    println("Ex 3.20")
    println(List.flatMap(List(1, 2, 3))(i => List(i, i)))
    
    println("Ex 3.21")
    println(List.filterViaFlatMap(List(1, 2, 3, 4, 5, 6))(_ > 2))
    
    println("Ex 3.22")
    println(List.addListsPairwise(List(1, 2, 3), List(4, 5, 6)))
    
    println("Ex 3.23")
    println(List.zipWith(List(1, 2, 3), List(4, 5, 6))(_.toString() + _.toString()))
    
    println("Ex 3.24")
    // should all be true
    println(List.hasSubsequence(List(1, 2, 3), List(1,2, 3)))
    println(List.hasSubsequence(List(1, 2, 3), List(1, 2)))
    println(List.hasSubsequence(List(1, 2, 3), List(2, 3)))
    println(List.hasSubsequence(List(1, 2, 3), List(1)))
    println(List.hasSubsequence(List(1, 2, 3), List(2)))
    println(List.hasSubsequence(List(1, 2, 3), List(3)))
    // should be false
    println(List.hasSubsequence(List(1, 2, 3), List(1, 3)))
    println(List.hasSubsequence(List(1, 2, 3), List(4)))
    
    println("Ex 3.25")
    println(Tree.size(Leaf(1)))
    println(Tree.size(Branch(Leaf(1), Leaf(1))))
    println(Tree.size(Branch(Leaf(1), Branch(Leaf(1), Leaf(2)))))
    
    println("Ex 3.26")
    println(Tree.maximum(Branch(Leaf(1), Branch(Leaf(10), Branch(Leaf(12), Leaf(42))))))
    println(Tree.maximum(Branch(Leaf(1), Branch(Leaf(42), Branch(Leaf(12), Leaf(4))))))
    println(Tree.maximum(Branch(Branch(Leaf(42), Branch(Leaf(12), Leaf(4))), Leaf(1))))
    
    println("Ex 3.27")
    println(Tree.depth(Leaf(1)))
    println(Tree.depth(Branch(Leaf(1), Leaf(1))))
    println(Tree.depth(Branch(Leaf(1), Branch(Leaf(1), Leaf(1)))))
    
    println("Ex 3.28")
    println(Tree.map(Branch(Leaf(1), Leaf(2)))(i => i + 1))
    
    println("Ex 3.29")
    println(Tree.size2(Leaf(1)))
    println(Tree.size2(Branch(Leaf(1), Leaf(1))))
    println(Tree.size2(Branch(Leaf(1), Branch(Leaf(1), Leaf(2)))))
    
    println(Tree.maximum2(Branch(Leaf(1), Branch(Leaf(10), Branch(Leaf(12), Leaf(42))))))
    println(Tree.maximum2(Branch(Leaf(1), Branch(Leaf(42), Branch(Leaf(12), Leaf(4))))))
    println(Tree.maximum2(Branch(Branch(Leaf(42), Branch(Leaf(12), Leaf(4))), Leaf(1))))
    
    println(Tree.depth2(Leaf(1)))
    println(Tree.depth2(Branch(Leaf(1), Leaf(1))))
    println(Tree.depth2(Branch(Leaf(1), Branch(Leaf(1), Leaf(1)))))
    
    println(Tree.map2(Branch(Leaf(1), Leaf(2)))(i => i + 1))
  }
}