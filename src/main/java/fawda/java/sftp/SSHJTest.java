// package fawda.java.sftp;
//
// import net.schmizz.sshj.SSHClient;
// import net.schmizz.sshj.sftp.SFTPClient;
// import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
// import org.bouncycastle.jce.provider.BouncyCastleProvider;
// import org.junit.Test;
//
// import java.io.IOException;
// import java.security.Security;
// import java.util.Properties;
//
// /**
//  * <b>时间:</b> <i>2019-08-02 22:39</i> 修订原因:初始化创建.详细说明:<br>
//  * TODO java.lang.SecurityException: class "org.bouncycastle.asn1.x9.X9ECParametersHolder"'s signer information does not match signer information of other classes in the same package
//  *
//  * Action类{@linkplain fawda.java.security}使用<br>
//  *
//  * <b>时间:</b> *年*月*日 *时*分*秒 修订原因:比如BUG修复或业务变更.详细说明:<br>
//  *
//  * @author Fawda liuyl @since 1.0
//  **/
// public class SSHJTest {
//
//     private String remoteHost = "192.168.10.26";
//     private String username = "root";
//     private String password = "Risen@2019";
//
//     private String localFile = "src/main/resources/sample.txt";
//     private String remoteDir = "/root/";
//
//     private String remoteFile = "welcome.txt";
//     private String localDir = "src/main/resources/";
//
//     //SSHJ 同样支持密码或公共密钥认证访问远程服务器。下面的示例采用密码认证：
//     private SSHClient setupSshj() throws IOException {
//         Security.addProvider(new BouncyCastleProvider());
//
//         SSHClient client = new SSHClient();
//         client.addHostKeyVerifier(new PromiscuousVerifier());
//         client.connect(remoteHost);
//         client.authPassword(username, password);
//         return client;
//     }
//
//     @Test
//     public void whenUploadFileUsingSshj_thenSuccess() throws IOException {
//         SSHClient sshClient = setupSshj();
//         SFTPClient sftpClient = sshClient.newSFTPClient();
//         sftpClient.put(localFile, remoteDir + "sshjFile.txt");
//         sftpClient.close();
//         sshClient.disconnect();
//     }
//
//     @Test
//     public void whenDownloadFileUsingSshj_thenSuccess() throws IOException {
//         SSHClient sshClient = setupSshj();
//         SFTPClient sftpClient = sshClient.newSFTPClient();
//         sftpClient.get(remoteFile, localDir + "sshjFile.txt");
//         sftpClient.close();
//         sshClient.disconnect();
//     }
// }
