import kotlin.test.*

class StackTest {
    private lateinit var stack: Stack

    @BeforeTest
    fun initStack() {
        stack = Stack(2)
    }

    @Test
    fun `newly created stack should be empty`() {
        assertTrue(stack.isEmpty)
        assertTrue(stack.size == 0)
    }

    @Test
    fun `push should increase stack size by 1`() {
        stack.push(1)
        assertTrue(stack.size == 1)
        assertFalse(stack.isEmpty)
    }

    @Test
    fun `after one push and pop stack should be empty`() {
        stack.push(1)
        stack.pop()

        assertTrue(stack.size == 0)
        assertTrue(stack.isEmpty)
    }

    @Test
    fun `popping empty stack throws an error`() {
        assertFailsWith<Stack.Error.Underflow> {
            stack.pop()
        }
    }

    @Test
    fun `pushing past limit should overflow the stack`() {
        assertFailsWith<Stack.Error.Overflow> {
            stack.push(1)
            stack.push(1)
            stack.push(1)
        }
    }

    @Test
    fun `when 1 is pushed 1 is popped`() {
        stack.push(1)

        val popped = stack.pop()

        assertEquals(1, popped)
    }

    @Test
    fun `when 1 and 2 are pushed, 2 and 1 are popped`() {
        stack.push(1)
        stack.push(2)

        assertEquals(2, stack.pop())
        assertEquals(1, stack.pop())
    }

    @Test
    fun `creating stack with negative capacity should throw error`() {
        assertFailsWith<Stack.Error.IllegalCapacity> {
            Stack(-1)
        }
    }

    @Test
    fun `when creating stack with 0 capacity any push should overflow`() {
        val stack = Stack(0)
        assertFailsWith<Stack.Error.Overflow> {
            stack.push(1)
        }
    }

    @Test
    fun `when 1 is pushed, 1 is on top`() {
        stack.push(1)
        assertEquals(1, stack.top)
    }

    @Test
    fun `when stack is empty, stack should throw Empty`() {
        assertFailsWith<Stack.Error.Empty> {
            stack.top
        }
    }

    @Test
    fun `ZeroCapacityStack top throws Empty`() {
        val stack = Stack(0)
        assertFailsWith<Stack.Error.Empty> {
            stack.top
        }
    }

    @Test
    fun `with 1 and 2 pushed find 1`() {
        stack.push(1)
        stack.push(2)

        assertEquals(1, stack.find(1))
        assertEquals(0, stack.find(2))
    }

    @Test
    fun `given a stack without 2 returns null`() {
        stack.push(1)
        stack.push(1)

        assertNull(stack.find(2))
    }
}