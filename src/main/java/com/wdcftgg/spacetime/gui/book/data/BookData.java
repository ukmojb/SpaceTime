package com.wdcftgg.spacetime.gui.book.data;

import com.wdcftgg.spacetime.gui.book.element.ElementTitle;

import java.util.ArrayList;
import java.util.List;

public class BookData {
    public String id;

    public BookData(String id) {
        this.id = id;
    }

    private final List<ChapterData> chapterDataList = new ArrayList<>();

    public final List<PageData> pages = new ArrayList<>();

    public List<ChapterData> getChapterDataList() {
        return chapterDataList;
    }

    public BookData initChapter(ChapterData data) {
        chapterDataList.add(data);
        return this;
    }

    public BookData initChapters(List<ChapterData> datas) {
        chapterDataList.addAll(datas);
        return this;
    }

    public int getAllChapterNum() {
        return chapterDataList.size();
    }

    public int getAllPageNum() {
        return pages.size();
    }

    public BookData registry() {
        pages.add(new PageData(this.id).initElement(new ElementTitle(this.id)));
        for (ChapterData chapterData : chapterDataList) {
            pages.addAll(chapterData.getPageDataList());
        }

        return this;
    }
}
