package uk.co.slightlymore.fpinscala

import uk.co.slightlymore.fpinscala.state.State

sealed trait Input
case object Coin extends Input
case object Turn extends Input

case class Machine(locked: Boolean, candies: Int, coins: Int)

object C6StateMachine {
  def update = (input: Input) => (state: Machine) => (input, state) match {
    case (_, Machine(_, 0, _)) => state
    case (Coin, Machine(false, _, _)) => state
    case (Turn, Machine(true, _, _)) => state
    case (Coin, Machine(true, candy, coin)) =>
      Machine(false, candy, coin + 1)
    case (Turn, Machine(false, candy, coin)) =>
      Machine(true, candy - 1, coin)
  }
  
  def simulate(inputs: List[Input]): State[Machine, (Int, Int)] = for {
    _ <- State.sequence(inputs.map(State.modify[Machine]_ compose update))
    s <- State.get[Machine]
  } yield (s.coins, s.candies)
  
  def main(args: Array[String]): Unit = {
    val s = simulate(List(Coin, Coin, Turn))
    val m = State.unit(Machine(false, 1, 2))
    // not immediately clear how to run a simulation _on_ a machine...
  }
}