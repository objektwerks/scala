package classes

// Player
case class Player(name : String)

// Games
trait Action
trait Rule
abstract class Game (val actions: Set[Action], val rules: Set[Rule])
class StudPoker(actions: Set[Action], rules: Set[Rule]) extends Game (actions, rules)

// Hand
case class Pot(chips : Set[Chip])
case class Hand(game: Game, players: Set[Player], cards : Set[Card], pot: Pot)

// Cards
trait Card
trait Suit
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

// Deck
case class Deck(cards: Set[Card])

// Currency
abstract class Currency(value: Double)
class USD(value: Double) extends Currency(value)

case class OneDollar(currency: Currency)
case class FiveDollars(currency: Currency)
case class TenDollars(currency: Currency)
case class FiftyDollars(currency: Currency)
case class OneHundredDollars(currency: Currency)

// Chips
trait Chip

case class White(value: OneDollar) extends Chip
case class Red(value: FiveDollars) extends Chip
case class Blue(value: TenDollars) extends Chip
case class Green(value: FiftyDollars) extends Chip
case class Black(value: OneHundredDollars) extends Chip
