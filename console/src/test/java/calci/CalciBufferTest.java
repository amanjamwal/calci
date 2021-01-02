package calci;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalciBufferTest {

    @Test
    void checkEmptyInput() {
        String line = "    ";
        CalciBuffer calciBuffer = new CalciBuffer(line);
        assertFalse(calciBuffer.hasMoreElements());
    }

    @Test
    void checkSingleInput() {
        String line = "  4  ";
        CalciBuffer calciBuffer = new CalciBuffer(line);
        assertTrue(calciBuffer.hasMoreElements());
        assertEquals("4", calciBuffer.nextElement());
        assertEquals(3, calciBuffer.getCurrentPosition());

        assertFalse(calciBuffer.hasMoreElements());
    }

    @Test
    void checkMultiInput() {
        String line = "  4  5 + sqrt";
        CalciBuffer calciBuffer = new CalciBuffer(line);
        assertTrue(calciBuffer.hasMoreElements());
        assertEquals("4", calciBuffer.nextElement());
        assertEquals(3, calciBuffer.getCurrentPosition());

        assertTrue(calciBuffer.hasMoreElements());
        assertEquals("5", calciBuffer.nextElement());
        assertEquals(6, calciBuffer.getCurrentPosition());

        assertTrue(calciBuffer.hasMoreElements());
        assertEquals("+", calciBuffer.nextElement());
        assertEquals(8, calciBuffer.getCurrentPosition());

        assertTrue(calciBuffer.hasMoreElements());
        assertEquals("sqrt", calciBuffer.nextElement());
        assertEquals(10, calciBuffer.getCurrentPosition());

        assertFalse(calciBuffer.hasMoreElements());
    }
}
