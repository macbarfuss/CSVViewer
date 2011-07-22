package de.macbarfuss.kata.csvviewer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CSVViewerTest {

    public static final String TEST_FILE_NAME = "testfile.csv";

    private final ByteArrayOutputStream outContent =
            new ByteArrayOutputStream();

    public static final String LINE_SEPARATOR =
            System.getProperty("line.separator");

    @Before
    public void setUpStdOut() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStdOut() {
        System.setOut(null);
    }


    @Test
    public void startWithNoArgument() {
        CSVViewer.startWithNoArgument();
        String expected = "Please specify a file as first parameter."
                + LINE_SEPARATOR;
        Assert.assertEquals(
                "Output message not as expected.",
                expected,
                outContent.toString());
    }

    @Test
    public void startWithOneArgument() {
        CSVViewer.startWithFileArgument(TEST_FILE_NAME);
        String expected = "Name     |Age|City    |" + LINE_SEPARATOR
                + "---------+---+--------+" + LINE_SEPARATOR
                + "Peter    |42 |New York|" + LINE_SEPARATOR
                + "Paul     |57 |London  |" + LINE_SEPARATOR
                + "Mary     |35 |Munich  |" + LINE_SEPARATOR
                + "" + LINE_SEPARATOR
                + "N(ext page, P(revious page, F(irst page, L(ast page, eX(it"
                + LINE_SEPARATOR;
        Assert.assertEquals("First appearence is not as expected.",
                expected,
                outContent.toString());
    }

    @Test
    public void testReadHeadersFromString() {
        CSVViewer csvv = new CSVViewer();
        csvv.readHeadersFromString("Name;Age;City");
        String[] expected = new String[3];
        expected[0] = "Name";
        expected[1] = "Age";
        expected[2] = "City";
        Assert.assertEquals("Headers not parsed correctly.",
                Arrays.toString(expected),
                Arrays.toString(csvv.getHeaders()));
    }
}
