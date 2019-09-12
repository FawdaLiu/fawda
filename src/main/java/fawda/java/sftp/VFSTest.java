package fawda.java.sftp;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.VFS;
import org.junit.Test;

import java.io.IOException;

/**
 * <b>时间:</b> <i>2019-08-05 15:56</i> 修订原因:初始化创建.详细说明:<br>
 * <br>
 * Action类{@linkplain fawda.java.sftp}使用<br>
 *
 * <b>时间:</b> *年*月*日 *时*分*秒 修订原因:比如BUG修复或业务变更.详细说明:<br>
 *
 * @author Fawda liuyl @since 1.0
 **/
public class VFSTest {
    private String remoteHost = "192.168.5.93";
    private String username = "root";
    private String password = "Risen@2019";

    private String localFile = "src/main/resources/sample.txt";
    private String remoteDir = "/root/test/";

    private String remoteFile = "welcome.txt";
    private String localDir = "src/main/resources/";
    @Test
    public void whenUploadFileUsingVfs_thenSuccess() throws IOException {
        FileSystemManager manager = VFS.getManager();
        FileObject local = manager.resolveFile(
                System.getProperty("user.dir") + "/" + localFile);
        FileObject remote = manager.resolveFile(
                "sftp://" + username + ":" + password + "@" + remoteHost + "/" + remoteDir + "vfsFile.txt");
        remote.copyFrom(local, Selectors.SELECT_SELF);
        local.close();
        remote.close();
    }


    @Test
    public void whenDownloadFileUsingVfs_thenSuccess() throws IOException {
        FileSystemManager manager = VFS.getManager();
        FileObject local = manager.resolveFile(
                System.getProperty("user.dir") + "/" + localDir + "vfsFile.txt");
        FileObject remote = manager.resolveFile(
                "sftp://" + username + ":" + password + "@" + remoteHost + "/" + remoteFile);
        local.copyFrom(remote, Selectors.SELECT_SELF);
        local.close();
        remote.close();
    }
}
