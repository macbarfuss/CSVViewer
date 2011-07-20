package de.macbarfuss.kata.csvviewer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public final class CSVViewer {

    public static void main(String[] args) {
        switch (args.length) {
            case 1:
                startWithNoArgument();
                break;
            case 2:
                startWithFileArgument(args[1]);
                break;
            case 3:
                startWithFileAndSizeArgument(args[1], args[2]);
            default:
        }
    }

    private static CSVViewer instance;

    private File file;

    private final List<String> headers = new ArrayList<String>();

    private BufferedReader filestream;

    protected CSVViewer() {
    }

    protected static void startWithNoArgument() {
        System.out.println("Please specify a file as first parameter.");
    }

    protected static void startWithFileArgument(String filename) {
        createInstance(filename);
        instance.readOutFile();
        String page = instance.getFirstPage();
        instance.printPageAndWaitForAnswer(page);
    }

    private static void createInstance(String filename) {
        instance = new CSVViewer();
        instance.setFile(filename);
    }

    private void setFile(String filename) {
        file = new File(filename);
    }

    private void readOutFile() {
        try {
            filestream = new BufferedReader(
                    new FileReader(file));
            String line = filestream.readLine();
            if (line != null) {
                readHeadersFromString(line);
                line = filestream.readLine();
                while (line != null) {
                    // TODO do something here
                    line = filestream.readLine();
                }
            }
            filestream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void readHeadersFromString(String line) {
        String[] result = line.split(";");
        for (int x = 0; x < result.length; x++) {
            headers.add(result[x]);
        }
    }

    private String getFirstPage() {
        return null;
    }

    protected List<String> getHeaders() {
        return headers;
    }

    private void printPageAndWaitForAnswer(String page) {
    }

    protected static void startWithFileAndSizeArgument(
            String args, String args2) {
    }
}
