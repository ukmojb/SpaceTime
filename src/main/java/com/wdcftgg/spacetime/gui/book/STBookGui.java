package com.wdcftgg.spacetime.gui.book;

import com.wdcftgg.spacetime.SpaceTime;
import com.wdcftgg.spacetime.gui.book.data.BookData;
import com.wdcftgg.spacetime.gui.book.data.PageData;
import com.wdcftgg.spacetime.gui.book.data.communication.CommunicationData;
import com.wdcftgg.spacetime.gui.book.data.communication.CommunicationsData;
import com.wdcftgg.spacetime.gui.book.data.communication.OptionData;
import com.wdcftgg.spacetime.gui.book.element.BookElement;
import com.wdcftgg.spacetime.gui.button.GuiCommunicationButton;
import com.wdcftgg.spacetime.gui.button.GuiSlotButton;
import com.wdcftgg.spacetime.network.MessageBookUpdate;
import com.wdcftgg.spacetime.network.PacketHandler;
import com.wdcftgg.spacetime.util.IBook;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class STBookGui extends GuiScreen {
    public static final ResourceLocation BOOK_GUI_TEXTURES = new ResourceLocation(SpaceTime.MODID, "textures/gui/book_background.png");

    private int guiLeft;
    private int guiTop;
    private int xSize;
    private int ySize;
    public static final int BUTTON_PAGE_UP = 0;
    public static final int BUTTON_PAGE_DOWN = 1;
    public static final int BUTTON_C_1 = 2;
    public static final int BUTTON_C_2 = 3;
    public static final int BUTTON_C_3 = 4;
    private GuiButton buttonPageUp;
    private GuiButton buttonPageDown;
    private GuiCommunicationButton buttonCommunication_1 = null;
    private GuiCommunicationButton buttonCommunication_2 = null;
    private GuiCommunicationButton buttonCommunication_3 = null;

    public static int PAGE_WIDTH = 256;
    public static int PAGE_HEIGHT = 180;

    private BookData bookData;
    private IBook book;
    private ItemStack bookItem;
    private PageData pageLeft = null;
    private PageData pageRight = null;

    private boolean delay = false;

    public STBookGui(IBook book, ItemStack itemStack) {
        this.book = book;
        this.bookData = book.getBookData();
        this.bookItem = itemStack;
    }

    @Override
    public void initGui() {
        super.initGui();

        ScaledResolution resolution = new ScaledResolution(mc);

        this.width = resolution.getScaledWidth();
        this.height = resolution.getScaledHeight();

        this.xSize = (int) (this.width * 0.75);
        this.ySize = (int) (this.height * 0.75);

        this.guiLeft = (this.width - PAGE_WIDTH) / 2;
        this.guiTop = (this.height - PAGE_HEIGHT) / 2 - 32;

        buttonPageUp = new GuiSlotButton(BUTTON_PAGE_UP, (int) guiLeft - 14, (int) (guiTop + PAGE_HEIGHT * 0.4), 14, 22, false);
        buttonPageDown = new GuiSlotButton(BUTTON_PAGE_DOWN, (int) (guiLeft + PAGE_WIDTH), (int) (guiTop + PAGE_HEIGHT * 0.4), 14, 22, true);
        buttonCommunication_1 = new GuiCommunicationButton(BUTTON_C_1, (int) (guiLeft - PAGE_WIDTH * 0.5), (int) (guiTop + PAGE_HEIGHT * 0.9), (int) (PAGE_WIDTH * 1.2), (int) (PAGE_HEIGHT * 0.05), null, false);
        buttonCommunication_2 = new GuiCommunicationButton(BUTTON_C_2, (int) (guiLeft - PAGE_WIDTH * 0.5), (int) (guiTop + PAGE_HEIGHT * 0.95), (int) (PAGE_WIDTH * 1.2), (int) (PAGE_HEIGHT * 0.05), null, false);
        buttonCommunication_3 = new GuiCommunicationButton(BUTTON_C_3, (int) (guiLeft - PAGE_WIDTH * 0.5), (int) (guiTop + PAGE_HEIGHT), (int) (PAGE_WIDTH * 1.2), (int) (PAGE_HEIGHT * 0.05), null, false);

        if (pageLeft instanceof CommunicationsData) {
            CommunicationsData communicationsData = (CommunicationsData) pageLeft;
            checkButtonCommunication(communicationsData);
        }

        this.buttonList.add(buttonPageUp);
        this.buttonList.add(buttonPageDown);
        this.buttonList.add(buttonCommunication_1);
        this.buttonList.add(buttonCommunication_2);
        this.buttonList.add(buttonCommunication_3);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(BOOK_GUI_TEXTURES);
        this.drawTexturedModalRect(guiLeft, this.guiTop, 0, 0, PAGE_WIDTH, PAGE_HEIGHT);

        pageLeft = BookHelper.getPageFromBook(book.getPageNum(bookItem), bookData);
        pageRight = BookHelper.getPageFromBook(book.getPageNum(bookItem) + 1, bookData);

        if (pageLeft instanceof CommunicationsData) {
            CommunicationsData data = (CommunicationsData) pageLeft;

            CommunicationsData communicationsData = (CommunicationsData) pageLeft;
            checkButtonCommunication(communicationsData);

            int h = 0;
            for (int x = 0; x < data.showedList.size(); x++) {
                CommunicationData data1 = data.showedList.get(x);

                for (BookElement element : data1.getBookElements()) {
                    h += element.draw((int) (guiLeft + PAGE_WIDTH * 0.05), (int) (guiTop + PAGE_HEIGHT * 0.05) + h * 8, (int) (xSize * 0.31), ySize, mouseX, mouseY, partialTicks, fontRenderer);
                }
            }

        } else if (pageLeft != null) {
            clearButtonCommunication();


            for (int x = 0; x < pageLeft.getBookElements().size(); x++) {
                BookElement element = pageLeft.getBookElements().get(x);

                element.draw((int) (guiLeft + PAGE_WIDTH * 0.05), (int) (guiTop + PAGE_HEIGHT * 0.05), (int) (xSize * 0.33), ySize, mouseX, mouseY, partialTicks, fontRenderer);
            }

            for (int v = 0; v < pageLeft.getBookElements().size(); v++) {
                BookElement element = pageLeft.getBookElements().get(v);

                element.drawOverlay(guiLeft, this.guiTop, partialTicks, fontRenderer);
            }

        }

        if (pageRight != null) {
            for (int x = 0; x < pageRight.getBookElements().size(); x++) {
                BookElement element = pageRight.getBookElements().get(x);

                element.draw((int) (guiLeft + PAGE_WIDTH * 0.55), (int) (guiTop + PAGE_HEIGHT * 0.05), xSize / 2, ySize, mouseX, mouseY, partialTicks, fontRenderer);
            }

            for (int v = 0; v < pageRight.getBookElements().size(); v++) {
                BookElement element = pageRight.getBookElements().get(v);

                element.drawOverlay(guiLeft + PAGE_WIDTH / 2, (int) (guiTop + PAGE_HEIGHT * 0.05), partialTicks, fontRenderer);
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);

    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if (delay) return;
        switch(button.id){
            case BUTTON_PAGE_UP:
                lastPage();
                break;
            case BUTTON_PAGE_DOWN:
                nextPage();
                break;
            case BUTTON_C_1:
                turnToNext(buttonCommunication_1.data);
                break;
            case BUTTON_C_2:
                turnToNext(buttonCommunication_2.data);
                break;
            case BUTTON_C_3:
                turnToNext(buttonCommunication_3.data);
                break;
            default:
                break;
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (pageLeft != null && pageRight != null) {
            for (BookElement element : pageLeft.getBookElements()) {
                element.mouseClicked(mouseX, mouseY, mouseButton);
            }

            for (BookElement element : pageLeft.getBookElements()) {
                element.mouseClicked(mouseX + 146, mouseY, mouseButton);
            }
        }
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        if (pageLeft != null && pageRight != null) {
            for (BookElement element : pageLeft.getBookElements()) {
                element.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
            }

            for (BookElement element : pageLeft.getBookElements()) {
                element.mouseClickMove(mouseX + 146, mouseY, clickedMouseButton, timeSinceLastClick);
            }
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        if (pageLeft != null && pageRight != null) {
            for (BookElement element : pageLeft.getBookElements()) {
                element.mouseReleased(mouseX, mouseY, state);
            }

            for (BookElement element : pageLeft.getBookElements()) {
                element.mouseReleased(mouseX + 146, mouseY, state);
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }

    private void turnToNext(OptionData data) {
        if (data == null) return;
        if (book != null) {
            String next = data.nextCommunication;

            if (pageLeft instanceof CommunicationsData) {
                delay = true;
                CommunicationsData communicationsData = (CommunicationsData) pageLeft;
                communicationsData.addShowingList(new CommunicationData(data.text.text + "_option", data.text.text, communicationsData));

                Timer timer1 = new Timer();
                TimerTask task1 = new TimerTask(){
                    public void run(){
                        communicationsData.addShowingList(communicationsData.getCommunicationFromId(next));
                        delay = false;
                    }
                };
                timer1.schedule(task1,2000);
            }
        }
    }

    private void nextPage() {
        if (book != null) {
            int pageNum = book.getPageNum(bookItem);
            if ((pageNum + 1) <= bookData.pages.size()) {
                book.setPageNum(bookItem, pageNum + 2);
            }
            PacketHandler.INSTANCE.sendToServer(new MessageBookUpdate(bookItem, pageNum));
        }
    }

    private void lastPage() {
        if (book != null) {
            int pageNum = book.getPageNum(bookItem);
            if ((pageNum - 2) < 0) {
                book.setPageNum(bookItem, 0);
            } else book.setPageNum(bookItem, pageNum - 2);

            PacketHandler.INSTANCE.sendToServer(new MessageBookUpdate(bookItem, pageNum));
        }
    }

    private void clearButtonCommunication() {
        if (buttonCommunication_1 != null) buttonCommunication_1.setDataAndShow(null, false);
        if (buttonCommunication_2 != null) buttonCommunication_2.setDataAndShow(null, false);
        if (buttonCommunication_3 != null) buttonCommunication_3.setDataAndShow(null, false);
    }

    private void checkButtonCommunication(CommunicationsData data) {
        List<OptionData> list = data.showedList.get(data.showedList.size() - 1).optionList;

        if (list.isEmpty()) clearButtonCommunication();
        if (!list.isEmpty()) buttonCommunication_1.setDataAndShow(list.get(0), true);
        if (list.size() > 1) {
            buttonCommunication_2.setDataAndShow(list.get(1), true);
        } else {
            buttonCommunication_2.setDataAndShow(null, false);
        }
        if (list.size() > 2) {
            buttonCommunication_3.setDataAndShow(list.get(2), true);
        } else {
            buttonCommunication_3.setDataAndShow(null, false);
        }
    }
}