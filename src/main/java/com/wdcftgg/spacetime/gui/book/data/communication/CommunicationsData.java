package com.wdcftgg.spacetime.gui.book.data.communication;

import com.wdcftgg.spacetime.gui.book.STBookGui;
import com.wdcftgg.spacetime.gui.book.TextDataRenderer;
import com.wdcftgg.spacetime.gui.book.data.BookData;
import com.wdcftgg.spacetime.gui.book.data.ChapterData;
import com.wdcftgg.spacetime.gui.book.data.PageData;
import com.wdcftgg.spacetime.gui.book.element.ElementTitle;
import net.minecraft.client.Minecraft;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommunicationsData extends PageData{
    public String id;
    public InformationPageData iData;
    public List<CommunicationData> showedList = new ArrayList<>();

    private final List<CommunicationData> communicationList = new ArrayList<>();

    public CommunicationsData(String title, InformationPageData informationPageData) {
        super(title);

        this.id = title;
        this.iData = informationPageData;
    }

    public CommunicationsData(String title) {
        super(title);

        this.id = title;
        this.iData = new InformationPageData();
    }

    public List<CommunicationData> getCommunicationList() {
        return communicationList;
    }

    public CommunicationsData initCommunication(CommunicationData data) {
        if (communicationList.isEmpty()) addShowingList(data);
        communicationList.add(data);
        return this;
    }

    public CommunicationsData initCommunications(CommunicationData... datas) {
        List<CommunicationData> dataList = Arrays.asList(datas);
        initCommunications(dataList);
        return this;
    }

    public CommunicationsData initCommunications(List<CommunicationData> datas) {
        if (communicationList.isEmpty() && !datas.isEmpty()) addShowingList(datas.get(0));
        communicationList.addAll(datas);
        return this;
    }

    public int getAllCommunicationNum() {
        return communicationList.size();
    }

    public List<CommunicationData> getShowingList() {
        return showedList;
    }

    public void addShowingList(CommunicationData data) {
        this.showedList.add(data);
    }

    public InformationPageData getInformationPageData() {
        return iData;
    }

    public CommunicationsData initInformationPageData(InformationPageData iData) {
        this.iData = iData;
        return this;
    }

    public CommunicationData getCommunicationFromId(String id) {

        for (CommunicationData data : communicationList) {
            if (data.id.equals(id)) {
                return data;
            }
        }

        return null;
    }
}
