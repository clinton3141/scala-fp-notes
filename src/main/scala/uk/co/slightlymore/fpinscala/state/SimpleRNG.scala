package uk.co.slightlymore.fpinscala.state

trait RNG {
  def nextInt: (Int, RNG)
}

object RNG {
  type Rand[+A] = RNG => (A, RNG)

  case class Simple(seed: Long) extends RNG {
    def nextInt: (Int, RNG) = {
      val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL
      val nextRNG = Simple(newSeed)
      val n = (newSeed >>> 16).toInt
      (n, nextRNG)
    }    
  }

  // Ex 6.1
  def nonNegativeInt(rng: RNG): (Int, RNG) = {
    val (n, r) = rng.nextInt
    if (n == Int.MinValue) (Int.MaxValue, r)
    else if (n < 0) (-n, r)
    else (n, r)
  }
  
  // Ex 6.2
  def double(rng: RNG): (Double, RNG) = {
    val (n, r) = rng.nextInt
    (n / (Int.MaxValue.toDouble), r)
  }
  
  // Ex 6.3
  def intDouble(rng: RNG): ((Int, Double), RNG) = {
    val (i, r1) = rng.nextInt
    val (d, r2) = double(r1)
    ((i, d), r2)
  }

  def doubleInt(rng: RNG): ((Double, Int), RNG) = {
    val ((i, d), r) = intDouble(rng)
    ((d, i), r)
  }

  def double3(rng: RNG): ((Double, Double, Double), RNG) = {
    val (d1, r1) = double(rng)
    val (d2, r2) = double(r1)
    val (d3, r3) = double(r2)
    ((d1, d2, d3), r3)
  }
  
  // Ex 6.4
  def ints(count: Int)(rng: RNG): (List[Int], RNG) = count match {
    case 0 => (List[Int](), rng)
    case n => {
      val (i, r1) = rng.nextInt
      val (is, r2) = ints(n - 1)(r1)
      ((i :: is), r2)
    }
  }
  
  val int: Rand[Int] = _.nextInt
  
  def unit[A](a: A): Rand[A] = rng => (a, rng)
  
  def map[A, B](s: Rand[A])(f: A => B): Rand[B] = rng => {
      val (a, rng2) = s(rng)
      (f(a), rng2) 
    }
    
  def nonNegativeEven: Rand[Int] = 
    map(nonNegativeInt)(i => i - i % 2)
    
  def double2: Rand[Double] = 
    map(int)(_ / (Int.MaxValue.toDouble))
    
  def map2[A, B, C](ra: Rand[A], rb: Rand[B])(f: (A, B) => C): Rand[C] = rng => {
    val (a, rng2) = ra(rng)
    val (b, rng3) = rb(rng2)
    (f(a, b), rng3)
  }
  
  def both[A, B](ra: Rand[A], rb: Rand[B]): Rand[(A, B)] = map2(ra, rb)((_, _))
  
  def randIntDouble: Rand[(Int, Double)] = both(int, double)
  
  def randDoubleInt: Rand[(Double, Int)] = both(double, int)

  def sequence2[A](fs: List[Rand[A]]): Rand[List[A]] = 
    fs.foldRight(unit(List[A]()))((rng, acc) => map2(rng, acc)((next, list) => (next :: list)))
    // below is tempting... but I don't know if future me would go on a murdering spree 
    // fs.foldRight(unit(List[A]()))(map2(_, _)((_ :: _)))
    
  def nonNegativeLessThan(n: Int): Rand[Int] = {
    flatMap(nonNegativeInt) { i =>
      val mod = i % n
      if (i + (n-1) - mod >= 0) unit(mod) else nonNegativeLessThan(n)
    }
  }
  
  def flatMap[A, B](f: Rand[A])(g: A => Rand[B]): Rand[B] = rng => {
    val (a, r) = f(rng)
    g(a)(r)
  }
  
  def mapV2[A, B](s: Rand[A])(f: A => B): Rand[B] =
    flatMap(s)(a => unit(f(a)))
  
  def map2V2[A, B, C](ra: Rand[A], rb: Rand[B])(f: (A, B) => C): Rand[C] =
    flatMap(ra)(a => map(rb)(b => f(a, b)))
    // attempt 1:
    // flatMap(ra)(a => flatMap(rb)(b => unit(f(a, b))))
}