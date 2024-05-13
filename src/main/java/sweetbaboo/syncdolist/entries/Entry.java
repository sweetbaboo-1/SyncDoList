package sweetbaboo.syncdolist.entries;

import sweetbaboo.syncdolist.network.Locks.Lock;

public abstract class Entry {
  protected String text;
  protected String metaData;
  protected Lock textLock, metaDataLock;

  public Entry(String text, String metaData) {
    this.text=text;
    this.metaData = metaData;
  }

  public Entry() {
    this.text = "";
    this.metaData = "";
  }

  public String getText() {
    return text;
  }

  public String getMetaData() {
    return metaData;
  }

  public void setMetaData(String metaData) {
    this.metaData=metaData;
  }

  public void setText(String text) {
    this.text=text;
  }

  public Lock getTextLock() {
    return textLock;
  }

  public void setTextLock(Lock textLock) {
    this.textLock=textLock;
  }

  public Lock getMetaDataLock() {
    return metaDataLock;
  }

  public void setMetaDataLock(Lock metaDataLock) {
    this.metaDataLock=metaDataLock;
  }
}
