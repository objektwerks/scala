class Value(number: Int) {
  private val privateNumber = number

  private def privateDouble: Value = Value(number * 2)

  override def toString: String = s"${Value.name}: ${number * Value.multiplier}"
}

object Value {
  private val name = "Value"

  private def multiplier: Int = 1

  def apply(value: Int): Value = new Value(value)

  def double(value: Value): Value = value.privateDouble

  def number(value: Value): Int = value.privateNumber
}

val value = Value(3)
val double = Value.double(value)

/*
  No access is allowed to the class Value:
   1) constructor arg (number: Int)
  Bidirectional access is allowed between class and object Value:
   1) private val
   2) private def
*/