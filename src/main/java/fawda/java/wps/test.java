package fawda.java.wps;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class test {
    private final static String fileName = "C:\\Users\\ThinkPad\\Desktop\\12.doc";

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        saveAs();
    }

    public static boolean saveAs() {
        ActiveXComponent app = new ActiveXComponent("WPS.Application");
        app.setProperty("Visible", new Variant(false)); // 设置word不可见
        Dispatch words = app.getProperty("Documents").toDispatch();
        Dispatch doc = null;
        if (doc != null) {
            System.out.println("当前文件未关闭");
            return false;
        }
        boolean readOnly = false;
        doc = Dispatch.invoke(words, "Open", Dispatch.Method, new Object[] { fileName, new Variant(false), new Variant(readOnly) }, new int[1])
                .toDispatch();
        System.out.println("打开文件" + fileName + (readOnly ? " ReadOnly" : " Writable"));
        try {
            saveAs("C:\\Users\\ThinkPad\\Desktop\\123.doc", doc);
            app.invoke("Quit", new Variant[] {});
        } catch (Exception e) {
            System.out.print(e);
        }
        return true;

    }

    /**
     * 另存为
     * 
     * @param fileName
     *            目标文件名，为绝对路径
     * @return
     */
    public static boolean saveAs(String fileName, Dispatch doc) throws Exception {
        if (doc == null) {
            System.out.println("当前无文件");
            return false;
        } else {
            Dispatch.call(doc, "SaveAs", fileName);
            System.out.println("另存为" + fileName);
            return true;
        }
    }
}
