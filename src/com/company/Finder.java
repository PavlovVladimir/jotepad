package com.company;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Finder extends Thread {
    private String text;
    private String patternString;
    private ArrayList<Indexies> arrayOfIndexies = new ArrayList<Indexies>();

    public Finder(String text, String patternString) {
        this.text = text;
        this.patternString = patternString;
    }

    @Override
    public void run() {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            int startIndex = matcher.start();
            int endIndex = matcher.end();
            arrayOfIndexies.add(new Indexies(startIndex, endIndex));
        }
    }

    public ArrayList<Indexies> getArrayOfIndexies() {
        return arrayOfIndexies;
    }
}
