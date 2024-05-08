package sweetbaboo.syncdolist.Tasks;

public class Entry {
  public String text;
  public boolean state;
  public int color;

  public int getColor() {
    return color;
  }

  public void setColor(int color) {
    this.color=color;
  }

  public Entry(String text, boolean state) {
    this.text=text;
    this.state=state;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text=text;
  }

  public boolean getState() {
    return state;
  }

  public void setState(boolean state) {
    this.state=state;
  }
}
