package com.wdcftgg.spacetime.gui.book.data.communication;

import com.wdcftgg.spacetime.gui.book.element.data.TextData;

public class OptionData {

    public TextData text;

    public String nextCommunication = null;

    public OptionData(String text) {
        this.text = new TextData(text);
    }

    public String getNextCommunication() {
        return nextCommunication;
    }

    public OptionData setNextCommunication(String nextCommunication) {
        this.nextCommunication = nextCommunication;
        return this;
    }
}
