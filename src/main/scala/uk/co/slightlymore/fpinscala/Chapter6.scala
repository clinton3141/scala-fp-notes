package uk.co.slightlymore.fpinscala

import uk.co.slightlymore.fpinscala.state.RNG

object Chapter6 {
  def main(args: Array[String]): Unit = {
//    val rng = RNG.Simple(42)    
    
    val rng = RNG.unit(1)
    val rng2 = RNG.unit(2)
    val x = (RNG.map2(rng, rng2)(_ + _))(RNG.Simple   (7))
    
    println(x._1)
  }
}