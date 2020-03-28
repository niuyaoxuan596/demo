package com.example.demo.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

public class TestPipe {
    @Test
    void test1() throws IOException {
        //1.获取管道
        Pipe pipe = Pipe.open();

        //2.将缓冲区中的数据写入管道
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //sink用于发送数据
        Pipe.SinkChannel sinkChannel = pipe.sink();
        buf.put("通过单向管道发送数据".getBytes());
        buf.flip();
        sinkChannel.write(buf);

        //3.读取缓冲区中的数据
        //source用于接收数据
        Pipe.SourceChannel sourceChannel = pipe.source();
        buf.flip();
        sourceChannel.read(buf);
        System.out.println(new String(buf.array(),0,buf.limit()));

        sourceChannel.close();
        sinkChannel.close();
    }
}
