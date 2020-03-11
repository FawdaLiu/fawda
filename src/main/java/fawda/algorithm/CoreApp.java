package fawda.algorithm;

public class CoreApp {
    
    private String uuid;
    private String text;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CoreApp lnkUuid(String uuid) {
        setUuid(uuid);
        return this;
    }
    public CoreApp lnkText(String text) {
        setText(text);
        return this;
    }
}
