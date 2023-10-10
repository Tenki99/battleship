/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.tq22.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;

class AppTest {

    @Disabled
    @Test
    public void test_main() throws IOException {
        test_main_helper("test_main_input1.txt", "test_main_output1.txt");
        test_main_helper("test_main_input2.txt", "test_main_output2.txt");
    }

    @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
    public void test_main_helper(String expectInputPath, String expectOutputPath) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true);
        InputStream input = getClass().getClassLoader().getResourceAsStream(expectInputPath);
        assertNotNull(input);
        InputStream oldIn = System.in;
        PrintStream oldOut = System.out;
        try {
            System.setIn(input);
            System.setOut(out);
            App.main(new String[0]);
        } finally {
            System.setIn(oldIn);
            System.setOut(oldOut);
        }
        String actual = bytes.toString();
        InputStream expectedStream = getClass().getClassLoader().getResourceAsStream(expectOutputPath);
        String expected = new String(expectedStream.readAllBytes());
        assertEquals(expected, actual);
    }

}
