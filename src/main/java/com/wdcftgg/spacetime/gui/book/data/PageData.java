package com.wdcftgg.spacetime.gui.book.data;

import com.wdcftgg.spacetime.gui.book.element.BookElement;

import java.util.ArrayList;
import java.util.List;

public class PageData {
    private String title;
    private final List<BookElement> bookElements = new ArrayList<>();

    public PageData(String title) {
        this.title = title;
    }

    public List<BookElement> getBookElements() {
        return bookElements;
    }

    public PageData initElement(BookElement element) {
        bookElements.add(element);
        return this;
    }

    public PageData initElements(List<BookElement> elements) {
        bookElements.addAll(elements);
        return this;
    }

    public String getTitle() {
        return title;
    }
}
