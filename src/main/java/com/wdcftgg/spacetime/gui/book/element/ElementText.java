package com.wdcftgg.spacetime.gui.book.element;

import com.wdcftgg.spacetime.gui.book.TextDataRenderer;
import com.wdcftgg.spacetime.gui.book.element.data.TextData;
import net.minecraft.client.gui.FontRenderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ElementText extends SizedBookElement{
    public TextData[] text;
    private List<String> tooltip = new ArrayList<String>();

    private boolean doAction = false;

    public ElementText(int x, int y, int width, int height, String text) {
        this(x, y, width, height, new TextData(text));
    }

    public ElementText(int x, int y, int width, int height, Collection<TextData> text) {
        this(x, y, width, height, text.toArray(new TextData[text.size()]));
    }

    public ElementText(int x, int y, int width, int height, TextData... text) {
        super(x, y, width, height);

        this.text = text;
    }

    @Override
    public int draw(int x, int y, int guiWidth, int guiHeight, int mouseX, int mouseY, float partialTicks, FontRenderer fontRenderer, boolean isHidden) {
//        for (TextData textData : text) {
//            System.out.println(textData.text);
//        }
        int h = TextDataRenderer.drawText(x + this.x, y + this.y, guiWidth, guiHeight, text, mouseX, mouseY, fontRenderer, tooltip);

        return h;
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
    }
}
