package com.comnet.CNProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HtmlReader {
    private static String html_path
            = System.getProperty("user.dir") + "/src/main/resources/static/";

    BufferedReader reader;

    public HtmlReader(String filename) throws IOException {
        this.reader = new BufferedReader(new FileReader(html_path + filename));
    }

    public HtmlReader(String filename, String path) throws IOException {
        this.reader = new BufferedReader(new FileReader(path + filename));
    }


    public BufferedReader getReader() { return this.reader; }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("");
        String tmp;

        while(true) {

            try { tmp = reader.readLine(); }
            catch (IOException e) { break; }

            if (tmp == null) break;

            res.append(tmp).append("\n");
        }

        return res.toString();
    }
}
