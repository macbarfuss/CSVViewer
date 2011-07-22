package de.macbarfuss.kata.csvviewer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class CSVViewer {

    public static final String LINE_SEPARATOR =
            System.getProperty("line.separator");

    public static void main(String[] args) {
        switch (args.length) {
            case 0:
                startWithNoArgument();
                break;
            case 1:
                startWithFileArgument(args[0]);
                break;
            case 2:
                startWithFileAndSizeArgument(args[0], args[1]);
            default:
        }
    }

    private static CSVViewer instance;

    private File file;

    private String[] headers = new String[0];

    private List<String[]> data = new ArrayList<String[]>();

    private int[] columnWidth = new int[0];

    private int pagelength = 3;

    private int currentPage = 0;

    private BufferedReader filestream;

    protected CSVViewer() {
    }

    protected static void startWithNoArgument() {
        System.out.println("Please specify a file as first parameter.");
    }

    protected static void startWithFileArgument(String filename) {
        createInstance(filename);
        instance.readOutFile();
        String page = instance.getPage(1);
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
                    data.add(line.split(";"));
                    line = filestream.readLine();
                }
            }
            filestream.close();
            for (int i = 0; i < headers.length; i++) {
                columnWidth[i] = headers[i].length();
            }
            for (int j = 0; j < data.size(); j++) {
                for (int i = 0; i < columnWidth.length; i++) {
                    if (columnWidth[i] < data.get(j)[i].length()) {
                        columnWidth[i] = data.get(j)[i].length();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void readHeadersFromString(String line) {
        String[] tokens = line.split(";");
        initFields(tokens.length);
        for (int i = 0; i < tokens.length; i++) {
            headers[i] = tokens[i];
        }
    }

    private void initFields(int length) {
        headers = new String[length];
        columnWidth = new int[length];
        for (int i = 0; i < columnWidth.length; i++) {
            columnWidth[i] = 0;
        }
    }

    private String getPage(int pagenum) {
        StringBuffer result = new StringBuffer();
        int linecount = data.size();
        result.append(generateLine(headers));
        result.append(generateHorizontalRule());
        int firstline = (pagenum - 1) * pagelength;
        for (
                int i = firstline, j = 0;
                i < linecount && j < pagelength;
                i++, j++) {
            result.append(generateLine(data.get(i)));
        }
        return result.toString();
    }

    private String generateHorizontalRule() {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < columnWidth.length; i++) {
            for (int j = 0; j < columnWidth[i]; j++) {
                result.append("-");
            }
            result.append("+");
        }
        result.append(LINE_SEPARATOR);
        return result.toString();
    }

    private String generateLine(String[] items) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < columnWidth.length; i++) {
            result.append(items[i]);
            int spaces = columnWidth[i] - items[i].length();
            for (int j = 0; j < spaces; j++) {
                result.append(" ");
            }
            result.append("|");
        }
        result.append(LINE_SEPARATOR);
        return result.toString();
    }

    protected String[] getHeaders() {
        return headers;
    }

    private void printPageAndWaitForAnswer(String page) {
        System.out.println(page);
    }

    protected static void startWithFileAndSizeArgument(
            String args, String args2) {
    }
}
