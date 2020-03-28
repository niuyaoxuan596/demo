package com.example.demo.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 一.使用NIO完成网络通信的三个核心(非阻塞式是针对网络通信的概念)
 * 1.通道（Channel）：负责链接
 *      java.nio.channels.Channel接口：
 *          --SelectableChannel
 *              --SocketChannel
 *              --ServerSocketChannel
 *              --DatagramChannel
 *
 *              --Pipe.SinkChannel
 *              --Pipe.SourceChannel
 * 2.缓冲区（Buffer）：负责数据的存取
 *
 * 3.选择器（Selector）：是SelectableChannel的多路复用器。用于监控SelectableChannel的IO状况
 *
 */
public class TestBlockingNIO {

    //客户端
    @Test
    void client() throws IOException {
        //1.获取通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

        FileChannel inChannel = FileChannel.open(Paths.get("E:\\project\\demo\\src\\main\\resources\\1.jpg"), StandardOpenOption.READ);

        //2.分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //3.读取本地文件，并发送到服务端
        while (inChannel.read(buf) != -1){

        }

        //4.关闭通道
        inChannel.close();
        sChannel.close();

    }
    //服务端
    @Test
    void server() throws IOException {
        //1.获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();

        FileChannel outChannel = FileChannel.open(Paths.get("E:\\project\\demo\\src\\main\\resources\\2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        //2.绑定连接
        ssChannel.bind(new InetSocketAddress(9898));

        //3.获取客户端连接的通道
        SocketChannel sChannel = ssChannel.accept();

        //4.分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //5.接收客户端的数据，并保存在本地
        while(sChannel.read(buf) != -1){
            buf.flip();
            outChannel.write(buf);
            buf.clear();
        }

        //6.关闭通道
        sChannel.close();
        outChannel.close();
        ssChannel.close();
    }
}
