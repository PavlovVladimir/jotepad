package com.company;

import java.util.ArrayList;

class PositionFinder {
    private String text;
    private String patternString;
    private boolean isStringRegexp;
    private boolean isTextChanged = false;
    private boolean isPatternChanged = false;
    private ArrayList<Indexies> arrayOfIndexies = new ArrayList<Indexies>();

    public void find() {

        if (isSearchNotCompleted()) {
            try {
                Finder thread = new Finder(text, patternString);
                thread.start();
                thread.join();
                arrayOfIndexies = thread.getArrayOfIndexies();
            } catch (InterruptedException e) {
                System.out.println(e.toString());
            }
        }
    }

    Indexies getIndex(int caretPosition) {
        int arraySize = arrayOfIndexies.size();
        if (isStringRegexp) {
            return arrayOfIndexies.get(0);
        }

        while (caretPosition < 0) {
            caretPosition = (arraySize + caretPosition);
        }

        while (caretPosition >= arraySize) {
            caretPosition -= arraySize;
        }

        return arrayOfIndexies.get(caretPosition);
    }

    public boolean isSearchNotCompleted() {
        if (arrayOfIndexies.isEmpty() || isTextChanged || isPatternChanged) {
            return true;
        }
        return false;
    }

    public void setText(String text) {
        if (text.equals(this.text)) {
            isTextChanged = false;
        } else {
            isTextChanged = true;
        }

        this.text = text;
    }

    public void setPatternString(String patternString) {
        if (patternString.equals(this.patternString)) {
            isPatternChanged = false;
        } else {
            isPatternChanged = true;
        }

        this.patternString = patternString;
    }

    public void setIsRegexp(boolean isRegexp) {
        isStringRegexp = isRegexp;
    }
}
