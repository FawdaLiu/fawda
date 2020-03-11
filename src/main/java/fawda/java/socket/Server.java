package fawda.java.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            //第一步：创建ServerSocket监听8080端口
            ServerSocket serverSocket = new ServerSocket(8080);
            //第二步：等待请求
            Socket socket = serverSocket.accept();
            //第三步：接受到请求后使用socket进行通信
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String readLine = bufferedReader.readLine();
            System.out.println("received from client: " + readLine);
            //第四步：发送数据
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println("received data: " + readLine);
            printWriter.flush();
            //第五步：关闭资源
            printWriter.close();
            bufferedReader.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
