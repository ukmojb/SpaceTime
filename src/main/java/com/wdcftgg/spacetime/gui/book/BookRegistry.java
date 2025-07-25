package com.wdcftgg.spacetime.gui.book;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.gui.book.data.BookData;
import com.wdcftgg.spacetime.gui.book.data.ChapterData;
import com.wdcftgg.spacetime.gui.book.data.PageData;
import com.wdcftgg.spacetime.gui.book.data.communication.CommunicationData;
import com.wdcftgg.spacetime.gui.book.data.communication.CommunicationsData;
import com.wdcftgg.spacetime.gui.book.data.communication.OptionData;
import com.wdcftgg.spacetime.gui.book.element.ElementText;
import com.wdcftgg.spacetime.gui.book.json.JsonFileLoader;

public class BookRegistry {
    public static BookData spaceTimeBook = new BookData(SpaceTime.MODID + "_book");

    public static void initBook() {
        ChapterData chapterData = new ChapterData("aaaaaaa");
        PageData pageData = new PageData("bbbbb");
        ElementText elementText = new ElementText(0, 0, 50, 50, "vvvvvvvvvvvvvvvvvv");
        pageData.initElement(elementText);
        chapterData.initPage(pageData);


        ElementText elementText1 = new ElementText(0, 0, 50, 50, "sdnbfkhsbfkjand");
        CommunicationsData cs_data = new CommunicationsData("test");
        CommunicationData c1_data = new CommunicationData("test1", "你是谁?\nmenmemememememememmefmdnmabdjs电话局噶备受打击发货的随机发你哈会计出纳号v金卡储蓄等等上不封顶女大三科技吧v的表达刷卡机办法科技大是吧发卡机办法科技大巴萨饭卡卡缴纳", cs_data);
        CommunicationData c2_data = new CommunicationData("test2", "那我怎么知道?", cs_data);
        CommunicationData c3_data = new CommunicationData("test3", "我猜你个锤子", cs_data);
        CommunicationData c4_data = new CommunicationData("test4", "不嘻嘻", cs_data, elementText1);

        OptionData optionData1 = new OptionData("不知道").setNextCommunication("test2");
        OptionData optionData2 = new OptionData("你猜?").setNextCommunication("test3");
        OptionData optionData3 = new OptionData("嘻嘻").setNextCommunication("test4");

        c1_data.addOption(optionData1);
        c1_data.addOption(optionData2);
        c2_data.addOption(optionData3);
        c3_data.addOption(optionData3);

//        cs_data.initCommunications(c1_data, c2_data, c3_data, c4_data);

        chapterData.initCommunications(cs_data);

        spaceTimeBook.initChapter(chapterData);

        BookHelper.books.add(spaceTimeBook);

        JsonFileLoader.loadAllJsons("books");

        spaceTimeBook.registry();
    }

    public static void reloadBook() {
        for (BookData bookData : BookHelper.books) {
            bookData.getChapterDataList().clear();
        }
        initBook();
    }

}
