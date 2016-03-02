package design

// Cards
sealed trait Card {
  def suit: Suit
}
sealed trait Suit
trait Clubs extends Suit
trait Diamonds extends Suit
trait Hearts extends Suit
trait Spades extends Suit
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
trait Money {
  def value: Double
}
case class $1(value: Double = 1.00) extends Money
case class $5(value: Double = 5.00) extends Money
case class $10(value: Double = 10.00) extends Money
case class $50(value: Double = 50.00) extends Money
case class $100(value: Double = 100.00) extends Money

// Chips
sealed trait Chip {
  def currency: Money
}
case class White(currency: $1) extends Chip
case class Red(currency: $5) extends Chip
case class Blue(currency: $10) extends Chip
case class Green(currency: $50) extends Chip
case class Black(currency: $100) extends Chip

// Player
case class Player(name : String)

// Game
sealed trait Action
sealed trait Rule
sealed trait Game {
  def actions: Set[Action]
  def rules: Set[Rule]
}
case class StudPoker(actions: Set[Action], rules: Set[Rule]) extends Game

// Hand
case class Pot(chips : Set[Chip])
case class Hand(game: Game, players: Set[Player], deck: Deck, pot: Pot)