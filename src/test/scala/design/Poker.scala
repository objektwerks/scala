package design

// Cards
sealed trait Card {
  def suit: Suit
}
sealed trait Suit
sealed trait Clubs extends Suit
sealed trait Diamonds extends Suit
sealed trait Hearts extends Suit
sealed trait Spades extends Suit
case class Ace(suit: Suit) extends Card
case class King(suit: Suit) extends Card
case class Queen(suit: Suit) extends Card
case class Jack(suit: Suit) extends Card
case class Two(suit: Suit) extends Card
case class Three(suit: Suit) extends Card
case class Four(suit: Suit) extends Card
case class Five(suit: Suit) extends Card
case class Six(suit: Suit) extends Card
case class Seven(suit: Suit) extends Card
case class Eight(suit: Suit) extends Card
case class Nine(suit: Suit) extends Card
case class Ten(suit: Suit) extends Card
case class Deck(cards: Set[Card])

// Money
case class Value(value: Double) {
  implicit def +(other: Value): Value = Value(value + other.value)
  implicit def -(other: Value): Value = Value(value - other.value)
  implicit def ++(values: List[Value]): Value = values.foldLeft(Value(0.0))(_ + _)
}
sealed trait Money {
  def value: Value
}
case class $10(value: Value = Value(10.00)) extends Money
case class $50(value: Value = Value(50.00)) extends Money
case class $100(value: Value = Value(100.00)) extends Money
case class $500(value: Value = Value(500.00)) extends Money
case class $1000(value: Value = Value(1000.00)) extends Money

// Chips
sealed trait Chip {
  def money: Money
}
case class White(money: $10) extends Chip
case class Red(money: $50) extends Chip
case class Blue(money: $100) extends Chip
case class Green(money: $500) extends Chip
case class Black(money: $1000) extends Chip

// Dealer
case class Dealter(name: String)

// Player
case class Player(name : String)

// Game
sealed trait Action {
  def shuffle(cards: Set[Card])
  def deal(cards: Set[Card])
}
sealed trait Rule
sealed trait Game {
  def actions: Set[Action]
  def rules: Set[Rule]
}
case class StudPoker(actions: Set[Action], rules: Set[Rule]) extends Game

// Pot
case class Pot(chips : Set[Chip])

// Hand
case class Hand(game: Game, players: Set[Player], deck: Deck, pot: Pot)