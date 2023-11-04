package unit_tests

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun subtraction_isCorrect() {
        // testing out test files in CI workflow
        assertEquals(4, 2 + 2);
    }
}