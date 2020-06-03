package com.company;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PositionFinder {
    private String text;
    private String patternString;
    private boolean isStringRegexp;
    private ArrayList<Indexies> arrayOfIndexies = new ArrayList<Indexies>();

    public void find() {

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            int startIndex = matcher.start();
            int endIndex = matcher.end();
            arrayOfIndexies.add(new Indexies(startIndex, endIndex));
        }
    }

    Indexies getIndex(int caretPosition) {
        if (isStringRegexp) {
            return arrayOfIndexies.get(0);
        } else {
            return arrayOfIndexies.get(caretPosition);
        }
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPatternString(String patternString) {
        this.patternString = patternString;
    }

    public void setIsRegexp(boolean isRegexp) {
        isStringRegexp = isRegexp;
    }
}
