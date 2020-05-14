case class State[S, A](run: S => (S, A)) {
  def flatMap[B](f: A => State[S, B]): State[S, B] = State { (currentState: S) =>
    val (newState, a) = run(currentState)
    f(a).run(newState)
  }

  def map[B](f: A => B): State[S, B] = flatMap(a => State.point(f(a)))
}

object State {
  def point[S, A](value: A): State[S, A] = State(run = state => (state, value))
}

case class Distance(distance: Int)

def move(distance: Int): State[Distance, Int] = State { (currentDistance: Distance) =>
  val newDistance = currentDistance.distance + distance
  (Distance(newDistance), newDistance)
}

val moveDistance: State[Distance, Int] = for {
  _ <- move(10)
  _ <- move(20)
  totalDistance <- move(30)
} yield totalDistance

val initalDistance = Distance(0)
val result: (Distance, Int) = moveDistance.run(initalDistance)
