package com.wdcftgg.spacetime.gui.book.data;

import com.wdcftgg.spacetime.gui.book.data.communication.CommunicationsData;
import com.wdcftgg.spacetime.gui.book.data.communication.InformationPageData;
import com.wdcftgg.spacetime.gui.book.element.ElementTitle;

import java.util.ArrayList;
import java.util.List;

public class ChapterData {
    public String id;

    public ChapterData(String id) {
        this.id = id;
        registry();
    }
    private final List<PageData> pageDataList = new ArrayList<>();

    public List<PageData> getPageDataList() {
        return pageDataList;
    }

    public ChapterData initCommunications(CommunicationsData data) {
        if (pageDataList.size() % 2 != 0) {
            pageDataList.add(new PageData(""));
        }
        InformationPageData informationPageData = new InformationPageData();
        pageDataList.add(data.initInformationPageData(informationPageData));
        pageDataList.add(informationPageData);
        return this;
    }

    public ChapterData initPage(PageData data) {
        if (data instanceof CommunicationsData) return this;
        pageDataList.add(data);
        return this;
    }

    public ChapterData initPages(List<PageData> datas) {
        for (PageData data : datas) {
            initPage(data);
        }
//        pageDataList.addAll(datas);
        return this;
    }

    public int getAllPageNum() {
        return pageDataList.size();
    }

    public void registry() {
        pageDataList.add(new PageData(id).initElement(new ElementTitle(id)));
    }
}
