package uk.co.slightlymore.fpinscala

import main.scala.uk.co.slightlymore.fpinscala.datastructures.Stream

object Chapter5 {
  def main(args: Array[String]): Unit = {
    println(Stream(1, 2, 3, 4) take(0) toList)
    println(Stream(1) take(1) toList)
    println(Stream(1, 2, 3, 4, 5) take(2) toList)
    println(Stream(1, 2, 3, 4, 5) takeViaUnfold(2) toList)
    
    println(Stream(1, 2, 3, 4, 5) drop(2) toList)
    
    println("takeWhile")
    println(Stream(1, 2, 3, 4, 5).takeWhile(_ < 3).toList)
    println(Stream(1, 2, 3, 4, 5).takeWhileViaFoldRight(_ < 3).toList)
    println(Stream(1, 2, 3, 4, 5).takeWhile(_ > 3).toList)
    println(Stream(1, 2, 3, 4, 5).takeWhileViaFoldRight(_ > 3).toList)
    
    println(Stream(1, 2, 3, 4, 5).forAll(_ > 0))
    println(Stream(1, 2, 3, 4, 5).forAll(_ < 0))
    
    println(Stream(1, 2, 3, 4, 5).headOptionViaFoldRight)
    println(Stream().headOptionViaFoldRight)
    
    println(Stream.constant("Hello").take(3).toList)
    
    println(Stream.ones.take(5).toList)
    println(Stream.from(0).take(5).toList)
    
    println(Stream.fibs.take(1).toList)
    println(Stream.fibs.take(5).toList)
    
    println(Stream.unfold(0)(x => Some((x, x + 1))).take(4).toList)
    
    println(Stream.fibsViaUnfold.take(10).toList)
    
    println(Stream.fromViaUnfold(5).take(4).toList)
    
    println(Stream.constantViaUnfold(5).take(4).toList)
    
    println(Stream(1, 2, 3).zipWith(Stream("One", "Two", "Three", "Four"))((n, s) => s"$n: $s") toList)
    
    println(Stream(1, 2, 3, 4).zipAll(Stream(1, 2)) toList)
    println(Stream(1, 2).zipAll(Stream(1, 2, 3, 4)) toList)
    
    assert((Stream(1, 2, 3, 4) startsWith Stream(1, 2)) == true, "1234 starts with 12")
    assert((Stream(1, 2, 3, 4) startsWith Stream(2, 3)) == false, "1234 starts with 23")
    assert((Stream(1, 2, 3, 4) startsWith Stream()) == true, "1234 starts with empty")
    assert((Stream() startsWith Stream()) == true, "empty starts with empty")
    assert((Stream() startsWith Stream(1, 2, 3)) == false, "empty starts with 123")
  }
}