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

"abc" < "abcd"
"def" <= "def" && "def" <= "defg"
"xyz" === 3
"mnlop" > 3
"def" >= 3 && "defg" >= 3