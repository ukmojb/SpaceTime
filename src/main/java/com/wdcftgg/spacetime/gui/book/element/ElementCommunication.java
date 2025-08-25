package com.wdcftgg.spacetime.gui.book.element;

import com.wdcftgg.spacetime.gui.book.BookHelper;
import com.wdcftgg.spacetime.gui.book.BookRegistry;
import com.wdcftgg.spacetime.gui.book.TextDataRenderer;
import com.wdcftgg.spacetime.gui.book.data.communication.CommunicationData;
import com.wdcftgg.spacetime.gui.book.element.data.TextData;
import net.minecraft.client.gui.FontRenderer;

import java.util.ArrayList;
import java.util.List;

public class ElementCommunication extends SizedBookElement{
    public TextData[] text;
    private List<String> tooltip = new ArrayList<String>();

    private boolean doAction = false;
    private BookElement element = null;
    private CommunicationData data = null;

    public ElementCommunication(int x, int y, int width, int height, String text, CommunicationData data) {
        this(x, y, width, height, data, new TextData(text));
    }
    public ElementCommunication(int x, int y, int width, int height, String text, CommunicationData data, BookElement element) {
        this(x, y, width, height, data, new TextData(text));
        this.element = element;
    }
    public ElementCommunication(int x, int y, int width, int height, CommunicationData data) {
        this(x, y, width, height, data, data.text);
    }
    public ElementCommunication(int x, int y, int width, int height, CommunicationData data, BookElement element) {
        this(x, y, width, height, data, data.text);
        this.element = element;
    }

    public ElementCommunication(int x, int y, int width, int height, CommunicationData data, BookElement element, TextData... text) {
        super(x, y, width, height);

        this.text = text;
        this.data = data;
        this.element = element;
    }
    public ElementCommunication(int x, int y, int width, int height, CommunicationData data, TextData... text) {
        super(x, y, width, height);

        this.text = text;
        this.data = data;
    }

    @Override
    public int draw(int x, int y, int guiWidth, int guiHeight, int mouseX, int mouseY, float partialTicks, FontRenderer fontRenderer, boolean isHidden) {

        int h = TextDataRenderer.drawText(x + this.x, y + this.y, guiWidth, guiHeight, text, mouseX, mouseY, fontRenderer, tooltip);

        int height = h > 1 ? h + 1 : h;
        this.height = height * 18;
        this.width = (int) (guiWidth * 0.5);
        return height;
    }

    @Override
    public void drawOverlay(int mouseX, int mouseY, float partialTicks, FontRenderer fontRenderer) {
        super.drawOverlay(mouseX, mouseY, partialTicks, fontRenderer);
        if(tooltip.size() > 0){
            TextDataRenderer.drawTooltip(tooltip, mouseX, mouseY, fontRenderer);
            tooltip.clear();
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(mouseButton == 0) {
            doAction = true;
        }
        if (doAction) {
            BookHelper.getCommunicationsFromCommunication(data, BookRegistry.spaceTimeBook).getInformationPageData().addShowingList(element);
        }
    }
}