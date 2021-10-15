sealed interface Stack {
    companion object

    val size: Int
    val isEmpty: Boolean
    val top: Int

    fun push(element: Int)
    fun pop(): Int
    fun find(element: Int): Int?

    sealed class Error(message: String) : Throwable(message) {
        object Overflow : Error("Stack is pushed past it's capacity.")
        object Underflow : Error("Stack is popped beyond 0 size.")
        object IllegalCapacity : Error("Stack can't have negative capacity.")
        object Empty : Error("Stack is empty.")
    }
}

operator fun Stack.Companion.invoke(capacity: Int): Stack =
    when {
        capacity < 0 -> throw Stack.Error.IllegalCapacity
        capacity == 0 -> ZeroCapacityStack
        else -> BoundedStack(capacity)
    }

private object ZeroCapacityStack : Stack {
    override val size: Int = 0
    override val isEmpty: Boolean = true
    override val top: Int
        get() = throw Stack.Error.Empty

    override fun push(element: Int) = throw Stack.Error.Overflow
    override fun pop(): Int = throw Stack.Error.Underflow
    override fun find(element: Int): Int = throw Stack.Error.Empty
}

private class BoundedStack(
    private val capacity: Int,
) : Stack {
    private val elements = IntArray(capacity)

    private var _size = 0
    override val size: Int
        get() = _size

    override val isEmpty: Boolean
        get() = size == 0

    override val top: Int
        get() =
            if (isEmpty) throw Stack.Error.Empty
            else elements[size - 1]

    override fun push(element: Int) {
        if (size == capacity) throw Stack.Error.Overflow
        elements[_size++] = element
    }

    override fun pop(): Int {
        if (isEmpty) throw Stack.Error.Underflow
        return elements[--_size]
    }

    override fun find(element: Int): Int? {
        val index = elements.indexOf(element).takeIf { it != -1 } ?: return null
        return (size - 1) - index
    }
}
