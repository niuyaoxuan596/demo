package com.example.demo.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

public class TestNonBlockingNIO2 {
    //发送端
    @Test
    void send() throws IOException {
        //1.获取通道
        DatagramChannel dc = DatagramChannel.open();

        //2.切换非阻塞模式
        dc.configureBlocking(false);

        //3.分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //4.发送数据给服务端
        Scanner scan = new Scanner(System.in);

        while (scan.hasNext()){
            String str = scan.next();
            buf.put((new Date().toString()+"\n"+str).getBytes());
            buf.flip();
            dc.send(buf,new InetSocketAddress("127.0.0.1",9898));
            buf.clear();
        }

        //5.关闭通道
        dc.close();
    }
    //接收端
    @Test
    void receive() throws IOException {
        //1.获取通道
        DatagramChannel dc = DatagramChannel.open();

        //2.切换非阻塞模式
        dc.configureBlocking(false);

        //3.绑定连接
        dc.bind(new InetSocketAddress(9898));

        //4.获取选择器
        Selector selector = Selector.open();

        //5.将通道注册到选择器上,并且指定“监听接收事件”
        dc.register(selector, SelectionKey.OP_READ);

        //6.轮询式的获取选择器上已经“准备就绪”的事件
        while(selector.select()>0){
            //7.获取当前选择器中所有注册的“选择键（已就绪的监听事件）”
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();

            while (it.hasNext()){
                //8.获取准备就绪的事件
                SelectionKey sk = it.next();

               if(sk.isReadable()){
                    //9.读取数据
                    ByteBuffer buf = ByteBuffer.allocate(1024);

                    dc.receive(buf);
                    buf.flip();
                    System.out.println(new String(buf.array(),0,buf.limit()));

                }
                //10.取消选择键SelctionKey
                it.remove();
            }
        }
    }
}
