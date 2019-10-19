package uk.co.slightlymore.fpinscala

import uk.co.slightlymore.fpinscala.state.RNG
import uk.co.slightlymore.fpinscala.state.RNG.Rand

object Chapter6 {
  def main(args: Array[String]): Unit = {
//    val rng = RNG.Simple(42)    
    
    val rng = RNG.unit(1)
    val rng2 = RNG.unit(2)
    val x = (RNG.map2(rng, rng2)(_ + _))(RNG.Simple   (7))
    
    println(x._1)
    
    def rollDie: Rand[Int] = RNG.nonNegativeLessThan(6)
    val zero = rollDie(RNG.Simple(5))
    println(zero._1)
    
    def rollDieFixed = RNG.map(rollDie)(_ + 1)
    
    val nonZero = rollDieFixed(RNG.Simple(5))
    println(nonZero._1)
  }
}