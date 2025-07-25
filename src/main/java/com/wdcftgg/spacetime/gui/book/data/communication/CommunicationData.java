package com.wdcftgg.spacetime.gui.book.data.communication;

import com.wdcftgg.spacetime.gui.book.STBookGui;
import com.wdcftgg.spacetime.gui.book.element.BookElement;
import com.wdcftgg.spacetime.gui.book.element.ElementCommunication;
import com.wdcftgg.spacetime.gui.book.element.data.TextData;

import java.util.ArrayList;
import java.util.List;

public class CommunicationData {

    public String id;
    public TextData text;
    public CommunicationsData communicationsData;
    public List<OptionData> optionList = new ArrayList<>();

    private final List<BookElement> bookElements = new ArrayList<>();

    public List<BookElement> getBookElements() {
        return bookElements;
    }

    public CommunicationData initElement(BookElement element) {
        bookElements.add(element);
        return this;
    }

    public CommunicationData initElements(List<BookElement> elements) {
        bookElements.addAll(elements);
        return this;
    }

    public CommunicationData(String id, String text, CommunicationsData communicationsData) {
        this.id = id;
        this.text = new TextData(text);
        this.communicationsData = communicationsData;

        initElement(new ElementCommunication(0, 0, STBookGui.PAGE_WIDTH,  STBookGui.PAGE_HEIGHT, text, this));

        communicationsData.initCommunication(this);
    }

    public CommunicationData(String id, String text, CommunicationsData communicationsData, BookElement element) {
        this.id = id;
        this.text = new TextData(text);
        this.communicationsData = communicationsData;

        initElement(new ElementCommunication(0, 0, STBookGui.PAGE_WIDTH,  STBookGui.PAGE_HEIGHT, text, this));
//        initElement(new ElementCommunication(0, 0, STBookGui.PAGE_WIDTH,  STBookGui.PAGE_HEIGHT, text, communicationsData, element));

        communicationsData.initCommunication(this);
    }

    public List<OptionData> getOptionList() {
        return optionList;
    }

    public void addOptions(List<OptionData> optionList) {
        this.optionList.addAll(optionList);
    }

    public void addOption(OptionData option) {
        this.optionList.add(option);
    }
}
