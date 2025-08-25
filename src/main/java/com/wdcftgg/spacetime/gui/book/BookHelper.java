package com.wdcftgg.spacetime.gui.book;

import com.wdcftgg.spacetime.gui.book.data.BookData;
import com.wdcftgg.spacetime.gui.book.data.ChapterData;
import com.wdcftgg.spacetime.gui.book.data.PageData;
import com.wdcftgg.spacetime.gui.book.data.communication.CommunicationData;
import com.wdcftgg.spacetime.gui.book.data.communication.CommunicationsData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BookHelper {
    public static final List<BookData> books = new ArrayList<>();

    public static BookData getBookFromId(String id) {
        for (BookData data : books) {
            if (Objects.equals(data.id, id)) return data;
        }
        return null;
    }

    public static ChapterData getChapterFromId(String id, String bookId) {
        BookData bookData = getBookFromId(bookId);
        if (bookData != null) {
            for (ChapterData data : bookData.getChapterDataList()) {
                if (Objects.equals(data.id, id)) return data;
            }
        }
        return null;
    }
    public static ChapterData getChapterFromPageId(String pageId, String bookId) {
        BookData bookData = getBookFromId(bookId);
        if (bookData != null) {
            for (ChapterData data : bookData.getChapterDataList()) {
                List<String> list = data.getPageDataList().stream().map(PageData::getTitle).collect(Collectors.toList());

                if (list.contains(pageId)) return data;
            }
        }
        return null;
    }

    public static ChapterData getChapter(String id, BookData bookData) {
        if (bookData != null) {
            for (ChapterData data : bookData.getChapterDataList()) {
                if (Objects.equals(data.id, id)) return data;
            }
        }
        return null;
    }

    public static PageData getPageFromBookId(int num, String bookId) {
        BookData bookData = getBookFromId(bookId);
        if (bookData != null) {
            if (!bookData.pages.isEmpty()) {
                if (bookData.getAllPageNum() >= num)
                    return bookData.pages.get(num);
                else
                    return null;
            }
        }
        return null;
    }

    public static PageData getPageFromChapterId(int num, String bookId, String chapterId) {
        BookData bookData = getBookFromId(bookId);
        if (bookData != null) {
            ChapterData chapterData = getChapterFromId(chapterId, bookId);
            if (chapterData != null) {
                if (chapterData.getAllPageNum() >= num)
                    return chapterData.getPageDataList().get(num);
                else
                    return null;
            }
        }
        return null;
    }

    public static PageData getPageFromBook(int num, BookData bookData) {
        if (bookData != null && !bookData.pages.isEmpty()) {
            if (bookData.pages.size() <= (num - 1)) {
                return null;
            }
            try {
                return bookData.pages.get(num - 1);
            } catch (ArrayIndexOutOfBoundsException e) {
                return null;
            }
        }
        return null;
    }

    public static PageData getPageFromChapter(int num, BookData bookData, String chapterId) {
        if (bookData != null) {
            ChapterData chapterData = getChapter(chapterId, bookData);
            if (chapterData != null) {
                if (chapterData.getAllPageNum() >= num)
                    return chapterData.getPageDataList().get(num);
                else
                    return null;
            }
        }
        return null;
    }

    public static BookData getBookFromChapter(ChapterData chapterData) {
        for (BookData bookData1 : books) {
            List<ChapterData> chapterDataList = bookData1.getChapterDataList();
            if (chapterDataList.contains(chapterData)) return bookData1;
        }
        return null;
    }

    public static BookData getBookFromChapterId(String chapterId) {
        for (BookData bookData1 : books) {
            List<String> list = bookData1.getChapterDataList().stream().map(c -> c.id).collect(Collectors.toList());

            if (list.contains(chapterId)) return bookData1;
        }
        return null;
    }

    public static BookData getBookFromPageId(String pageId) {
        for (BookData bookData1 : books) {
            for (ChapterData chapterData : bookData1.getChapterDataList()) {
                List<String> list = chapterData.getPageDataList().stream().map(PageData::getTitle).collect(Collectors.toList());

                if (list.contains(pageId)) return bookData1;
            }

        }
        return null;
    }

    public static BookData getBookFromPage(PageData pageData) {
        for (BookData bookData1 : books) {
            for (ChapterData chapterData : bookData1.getChapterDataList()) {
                if (chapterData.getPageDataList().contains(pageData)) return bookData1;
            }

        }
        return null;
    }

    public static CommunicationsData getCommunicationsFromCommunicationId(String CommunicationId, BookData bookData) {
        if (bookData != null) {
            for (ChapterData chapterData : bookData.getChapterDataList()) {
                for (PageData pageData : chapterData.getPageDataList()) {
                    if (pageData instanceof CommunicationsData) {
                        CommunicationsData communicationsData = (CommunicationsData) pageData;
                        List<String> list = communicationsData.getCommunicationList().stream().map(c -> c.id).collect(Collectors.toList());

                        if (list.contains(CommunicationId)) return communicationsData;
                    }
                }
            }
        }
        return null;
    }

    public static CommunicationsData getCommunicationsFromCommunication(CommunicationData communicationData, BookData bookData) {
        if (bookData != null) {
            for (ChapterData chapterData : bookData.getChapterDataList()) {
                for (PageData pageData : chapterData.getPageDataList()) {
                    if (pageData instanceof CommunicationsData) {
                        CommunicationsData communicationsData = (CommunicationsData) pageData;

                        if (communicationsData.getCommunicationList().contains(communicationData)) return communicationsData;
                    }
                }
            }
        }
        return null;
    }


}
