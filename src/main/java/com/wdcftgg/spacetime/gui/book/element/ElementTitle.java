package com.wdcftgg.spacetime.gui.book.element;


import com.wdcftgg.spacetime.gui.book.TextDataRenderer;
import com.wdcftgg.spacetime.gui.book.element.data.TextData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ElementTitle extends ElementText{
    public ElementTitle(String text) {
        super(0, 0, 50, 50, text);
    }

    @Override
    public int draw(int x, int y, int guiWidth, int guiHeight, int mouseX, int mouseY, float partialTicks, FontRenderer fontRenderer, boolean isHidden) {

//        for (TextData textData : this.text) {
//            int stringWidth = mc.fontRenderer.getStringWidth(textData.text);

//            this.mc.fontRenderer.drawString(textData.text, (guiWidth - stringWidth) / 2, this.y, 0xFFFFFF);
        int h = TextDataRenderer.drawText(x, y, guiWidth, guiHeight, text, mouseX, mouseY, fontRenderer, new ArrayList<String>());
//            TextDataRenderer.drawText((this.x - stringWidth) / 2, this.y, guiWidth, guiHeight, Collections.singletonList(textData).toArray(new TextData[0]), mouseX, mouseY, fontRenderer, new ArrayList<String>());
//        }

        return h;
    }
}
