package sweetbaboo.syncdolist.widgets;

import fi.dy.masa.malilib.gui.widgets.WidgetStringListEntry;
import fi.dy.masa.malilib.render.RenderUtils;
import net.minecraft.client.gui.DrawContext;

public class WidgetColorfulStringListEntry extends WidgetStringListEntry {
  private boolean isOdd;
  int color;
  public WidgetColorfulStringListEntry(int x, int y, int width, int height, boolean isOdd, String entry, int listIndex, int color) {
    super(x, y, width, height, isOdd, entry, listIndex);
    this.isOdd = isOdd;
    this.color = color;
  }

  @Override
  public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
    RenderUtils.color(1f, 1f, 1f, 1f);

    // Draw a lighter background for the hovered and the selected entry
    if (selected || this.isMouseOver(mouseX, mouseY))
    {
      RenderUtils.drawRect(this.x, this.y, this.width, this.height, 0xA0707070);
    }
    else if (this.isOdd)
    {
      RenderUtils.drawRect(this.x, this.y, this.width, this.height, 0xA0101010);
    }
    // Draw a slightly lighter background for even entries
    else
    {
      RenderUtils.drawRect(this.x, this.y, this.width, this.height, 0xA0303030);
    }

    if (selected)
    {
      RenderUtils.drawOutline(this.x, this.y, this.width, this.height, 0xFF90D0F0);
    }

    int yOffset = (this.height - this.fontHeight) / 2 + 1;
    this.drawStringWithShadow(this.x + 2, this.y + yOffset, color, this.entry, drawContext);
  }

  public void setColor(int color) {
    this.color=color;
  }

  public void setPos(int i) {
    this.setY(i);
  }
}
