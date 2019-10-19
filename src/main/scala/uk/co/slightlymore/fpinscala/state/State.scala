package uk.co.slightlymore.fpinscala.state

case class State[S, +A](run: S => (A, S)) {
  def map[B](f: A => B): State[S, B] = 
    flatMap(a => State.unit(f(a)))

  def flatMap[B](f: A => State[S, B]): State[S, B] = {
    State(s => {
      val (a, s2) = run(s)
      f(a).run(s2)
    })
  }
    
  def map2[B, C](sb: State[S, B])(f: (A, B) => C): State[S, C] =
    flatMap(a => sb.map(b => f(a, b)))
}

object State {
  def unit[S, A](a: A): State[S, A] = State(s => (a, s))
  
  def sequence[S, A](fs: List[State[S, A]]): State[S, List[A]] = 
    fs.foldRight(unit[S, List[A]](List[A]()))((s, acc) => s.map2(acc)(_ :: _))
}