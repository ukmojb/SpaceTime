package com.wdcftgg.spacetime.gui.book.data.communication;

import com.wdcftgg.spacetime.gui.book.data.PageData;
import com.wdcftgg.spacetime.gui.book.element.BookElement;

import java.util.ArrayList;
import java.util.List;

public class InformationPageData extends PageData {
    public List<BookElement> showedList = new ArrayList<>();

    public InformationPageData() {
        super("");
    }

    @Override
    public List<BookElement> getBookElements() {
        return showedList;
    }

    @Override
    public PageData initElements(List<BookElement> elements) {
        showedList.addAll(elements);
        return this;
    }

    public void addShowingList(BookElement data) {
        this.showedList.clear();
        this.showedList.add(data);
    }
}
