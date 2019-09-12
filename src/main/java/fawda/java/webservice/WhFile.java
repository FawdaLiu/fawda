package fawda.java.webservice;

/**
 * @author Administrator
 *
 */
public class WhFile {
    /** 应用系统标识 */
    private String appID;

    /** 应用系统名称 */
    private String appName;

    /** 公文ID,一个公文包括一个表单，一个正文，若干附件，公文有一个唯一的公文ID，应用系统中定义的，跟xml中的formid一致 */
    private String docID;

    /** 文件的类型1:正文 0:附件；2：回复表单；3：对回复表单进行回复时回复数据组成的xml */
    private Integer fileType;

    /** 文件名称，不带路径 */
    private String fileName;

    /** 正文和附件的分片数据 (已编码的数据,客户端调用时可以是byte[]也可以编码后的String) */
    private String file;

    /** 0：第一个数据包；1：中间数据包；2：结束包。 */
    private Integer flag;

    /** 第一个包的时候，该参数为空，由服务端产生文件存放路径，并返回给客户端，客户端在传输后续包的时候把路径通过这个参数传递 */
    private String pathDir;

    private String operUri;
    private String operName;
    private String extInfo1;
    private String extInfo2;

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getPathDir() {
        return pathDir;
    }

    public void setPathDir(String pathDir) {
        this.pathDir = pathDir;
    }

    public String getOperUri() {
        return operUri;
    }

    public void setOperUri(String operUri) {
        this.operUri = operUri;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public String getExtInfo1() {
        return extInfo1;
    }

    public void setExtInfo1(String extInfo1) {
        this.extInfo1 = extInfo1;
    }

    public String getExtInfo2() {
        return extInfo2;
    }

    public void setExtInfo2(String extInfo2) {
        this.extInfo2 = extInfo2;
    }

    @Override
    public String toString() {
        return "WhFile [appID=" + appID + ", appName=" + appName + ", docID=" + docID + ", fileType=" + fileType + ", fileName=" + fileName
                + ", file=" + file + ", flag=" + flag + ", pathDir=" + pathDir + ", operUri=" + operUri + ", operName=" + operName + ", extInfo1="
                + extInfo1 + ", extInfo2=" + extInfo2 + ", toString()=" + super.toString() + "]";
    }

}
