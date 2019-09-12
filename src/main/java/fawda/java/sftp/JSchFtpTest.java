// package fawda.java.sftp;
//
// import com.jcraft.jsch.*;
// import org.junit.Test;
//
// /**
//  * <b>时间:</b> <i>2019-08-02 22:38</i> 修订原因:初始化创建.详细说明:<br>
//  * <br>
//  *     ssh-keyscan -H -t rsa example.org >> known_hosts
//  *     需要将本地的id_rsa.pub复制到服务器的authorized_keys中
//  *     http://www.jcraft.com/jsch/examples/
//  * Action类{@linkplain fawda.java.security}使用<br>
//  *
//  * <b>时间:</b> *年*月*日 *时*分*秒 修订原因:比如BUG修复或业务变更.详细说明:<br>
//  *
//  * @author Fawda liuyl @since 1.0
//  **/
// public class JSchFtpTest {
//     private String remoteHost = "192.168.10.26";//HOST_NAME_HERE
//     private String username = "root";//USERNAME_HERE
//     private String password = "Risen@2019";//PASSWORD_HERE
//
//     //JSch 支持密码或公共密钥认证访问远程服务器，下面的示例采用密码认证：
//     private ChannelSftp setupJsch() throws JSchException {
//         JSch jsch = new JSch();
//         jsch.setKnownHosts("/home/fawda/.ssh/known_hosts");
//         Session jschSession = jsch.getSession(username, remoteHost);
//
//         //this introduces insecurities and should only be used for testing purposes
//         // java.util.Properties config = new java.util.Properties();
//         // config.put("StrictHostKeyChecking", "no");
//         // jschSession.setConfig(config);
//         jschSession.setPassword(password);
//         jschSession.connect();
//         return (ChannelSftp) jschSession.openChannel("sftp");
//     }
//
//     //上传
//     @Test
//     public void whenUploadFileUsingJsch_thenSuccess() throws JSchException, SftpException {
//         ChannelSftp channelSftp = setupJsch();
//         channelSftp.connect();
//         String localFile = "src/main/resources/sample.txt";
//         String remoteDir = "/root/";
//         channelSftp.put(localFile, remoteDir + "jschFile.txt");
//         channelSftp.exit();
//     }
//     //下载
//     @Test
//     public void whenDownloadFileUsingJsch_thenSuccess() throws JSchException, SftpException {
//         ChannelSftp channelSftp = setupJsch();
//         channelSftp.connect();
//         String remoteFile = "/root/jschFile.txt";
//         String localDir = "src/main/resources/";
//         channelSftp.get(remoteFile, localDir + "jschFile.txt");
//         channelSftp.exit();
//     }
// }
