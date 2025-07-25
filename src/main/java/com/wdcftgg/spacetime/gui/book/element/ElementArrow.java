package com.wdcftgg.spacetime.gui.book.element;

import com.wdcftgg.spacetime.gui.button.GuiArrow;
import net.minecraft.client.gui.FontRenderer;



public class ElementArrow extends ElementButton {

  protected final GuiArrow button;

  public ElementArrow(int id, IButtonClickHandler handler, int x, int y, GuiArrow.ArrowType arrowType, int arrowColor, int arrowColorHover) {
    super(id, x, y, arrowType.w, arrowType.h, handler);

    button = new GuiArrow(id, x, y, arrowType, arrowColor, arrowColorHover);
  }

  @Override
  public int draw(int x, int y, int guiWidth, int guiHeight, int mouseX, int mouseY, float partialTicks, FontRenderer fontRenderer, boolean isHidden) {
      button.drawButton(mc, mouseX, mouseY, partialTicks);
      return 10;
  }

}
