implicit class StringOps(val value: String) {
  def <(length: Int): Boolean =
    if (value.nonEmpty) value.length < length else false
  def <=(length: Int): Boolean =
    if (value.nonEmpty) value.length <= length else false
  def ===(length: Int): Boolean =
    if (value.nonEmpty) value.length == length else false
  def >(length: Int): Boolean =
    if (value.nonEmpty) value.length > length else false
  def >=(length: Int): Boolean =
    if (value.nonEmpty) value.length >= length else false
}

val isLessThan = "abc" < "abcd"
val isLessThanEqual = "def" <= "def" && "def" <= "defg"
val isEqual = "xyz" === 3
val isGreaterThan = "mnlop" > 3
val isGreaterThanEqual = "def" >= 3 && "defg" >= 3