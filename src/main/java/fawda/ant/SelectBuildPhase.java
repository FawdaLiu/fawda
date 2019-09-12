package fawda.ant;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>@Date:</b> <i>2018/8/15 8:27</i> 修订原因:初始化创建.详细说明:<br>
 * <b>@Desoription:</b> <br>
 *
 * <b>@Auther:</b> <i>Administrator</i>
 */
class BuildPhase{
    private String name;
    private String dependsList;
    private String refDirList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDependsList() {
        return dependsList;
    }

    public void setDependsList(String dependsList) {
        this.dependsList = dependsList;
    }

    public String getRefDirList() {
        return refDirList;
    }

    public void setRefDirList(String refDirList) {
        this.refDirList = refDirList;
    }
}
public class SelectBuildPhase extends Task {
    private String configFile = "";
    private String deltaFile = "";
    private String destFile = "";
    private ArrayList<BuildPhase> myBuildPhaseArray = new ArrayList<BuildPhase>();

    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    public String getDeltaFile() {
        return deltaFile;
    }

    public void setDeltaFile(String deltaFile) {
        this.deltaFile = deltaFile;
    }

    public String getDestFile() {
        return destFile;
    }

    public void setDestFile(String destFile) {
        this.destFile = destFile;
    }

    @Override
    public void execute() throws BuildException {
        try {
            parseConfig();
            IsNeedBuild();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        }
    }

    private void IsNeedBuild() {
        //识别过程
    }

    private void parseConfig() throws IOException, JDOMException {
        SAXBuilder mySAXBuilder = new SAXBuilder();
        FileInputStream myInputStream = new FileInputStream(configFile);
        Document myDocument = mySAXBuilder.build(myInputStream);
        Element root = myDocument.getRootElement();
        List<Element> elementList = root.getChildren();
        StringBuffer sb = new StringBuffer();
        for (Element outerTemp : elementList) {
            List<Element> innerList  = outerTemp.getChildren();
            BuildPhase bp = new BuildPhase();
            bp.setName(outerTemp.getAttributeValue("name"));
            bp.setDependsList(outerTemp.getAttributeValue("depends"));
            for (Element inner : innerList) {
                sb.append(inner.getAttributeValue("name"));
                sb.append(";");
            }
            bp.setRefDirList(sb.toString());
            myBuildPhaseArray.add(bp);
            sb.delete(0, sb.toString().length());
        }
    }

    public static void main(String[] args) {
        SelectBuildPhase sb = new SelectBuildPhase();
        sb.setConfigFile("D:\\config.xml");
        sb.setDeltaFile("D:\\delta.xml");
        sb.setDestFile("D:\\buildPhase.property");
        sb.execute();
    }

}
