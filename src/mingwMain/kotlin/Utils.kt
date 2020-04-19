// IMPLY (->)
infix fun Boolean.implies(other: Boolean) = !this or other

// NOT-AND (↑)
infix fun Boolean.nand(other: Boolean) = !(this and other)

// NOR (↓)
infix fun Boolean.nor(other: Boolean) = (!this) and (!other)

// NOT-OR (←)
infix fun Boolean.converseImplies(other: Boolean) = !other or this

// Difference (\)
infix fun Boolean.differs(other: Boolean) = this and !other

// Symmetric Difference (∆)
infix fun Boolean.symDiffers(other: Boolean) = (this and !other) or (!this and other)
