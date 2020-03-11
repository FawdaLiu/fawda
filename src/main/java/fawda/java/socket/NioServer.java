package fawda.java.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * <b>@Date:</b> <i>2018/8/15 22:02</i> 修订原因:初始化创建.详细说明:<br>
 * <b>@Desoription:</b> <br>
 *
 * <b>@Auther:</b> <i>Administrator</i>
 */
public class NioServer {
    public static void main(String[] args) throws IOException {
        //创建ServerSocketChannel，监听8080端口
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(8080));
        //设置为妃阻塞模式
        ssc.configureBlocking(false);
        //为ssc注册选择器
        Selector selector = Selector.open();
        //OP_ACCEPT接受请求操作
        //OP_CONNECT连接操作
        //OP_READ读操作
        //OP_WRITE写操作
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        //创建处理器
        Handler handler = new Handler(1024);
        while (true) {
            //等待请求，每次等待阻塞3s， 超过3后线程继续向下运行，如果传入0或者不传参数将一直阻塞
            if (selector.select(3000) == 0) {
                System.out.println("等待请求超时。。。");
                continue;
            }
            System.out.println("处理请求。。。");
            //获取待处理的SelectionKey
            Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();

            while (keyIter.hasNext()) {
                SelectionKey key = keyIter.next();
                try {
                    //接收到请求时
                    if (key.isAcceptable()) {
                        handler.HandleAccept(key);
                    }
                    if (key.isReadable()) {
                        handler.handleRead(key);
                    }
                } catch (IOException e) {
                    continue;
                } finally {
                    //处理完后，从待处理的SelectionKey迭代器中移除当前所使用的key
                    keyIter.remove();
                }
            }
        }

    }

    private static class Handler{
        private int bufferSize = 1024;
        private String localCharset = "UTF-8";
        public Handler(int bufferSize) {
            this(bufferSize, null);
        }

        public Handler(String localCharset) {
            this(-1, localCharset);
        }

        public Handler(int bufferSize, String localCharset) {
            if (bufferSize > 0) {
                this.bufferSize = bufferSize;
            }
            if (localCharset != null) {
                this.localCharset = localCharset;
            }
        }

        public void HandleAccept(SelectionKey key) throws IOException {
            SocketChannel sc = ((ServerSocketChannel) key.channel()).accept();
            sc.configureBlocking(false);
            sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufferSize));
        }

        public void handleRead(SelectionKey key) throws IOException {
            //获取chanel
            SocketChannel sc = (SocketChannel) key.channel();
            //获取ByteBuffer并重置
            ByteBuffer buffer = (ByteBuffer)key.attachment();
            buffer.clear();
            //没有读到内容则关闭
            if (sc.read(buffer) == -1) {
                sc.close();
            } else {
                //将buffer转换为读的状态
                buffer.flip();
                //将buffer中接收到的值按localCharset格式编码后保存到receivedString
                String receivedString = Charset.forName(localCharset).newDecoder().decode(buffer).toString();
                System.out.println("received from client: " + receivedString);

                //返回数据给客户端
                String sendString = "received data: " + receivedString;
                ByteBuffer.wrap(sendString.getBytes(localCharset));
                sc.write(buffer);
                //关闭socket
                sc.close();
            }
        }
    }
}
