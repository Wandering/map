package com.guanguan.map.map;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @program: map
 * @description: 一句话说明该类
 * @author: 杨永平
 * @create: 2018年01月23日 17:17
 **/
public class MinaTest  extends IoHandlerAdapter {

    private IoConnector connector;
    private static IoSession session;
    public MinaTest() {
        connector = new NioSocketConnector();
        connector.setHandler(this);
        ConnectFuture connFuture = connector.connect(new InetSocketAddress("54.144.138.156", 9123));
//        ConnectFuture connFuture = connector.connect(new InetSocketAddress("localhost", 9123));
        connFuture.awaitUninterruptibly();
        session = connFuture.getSession();
        System.out.println("TCP 客户端启动");
    }
    public static void main(String[] args) throws Exception {
        MinaTest client = new MinaTest();
        for(int j=0;j<2;j++){ // 发送两遍
            session.write(IoBuffer.wrap("start\n".getBytes()));
            session.write(IoBuffer.wrap("Temperature:1°C\n".getBytes()));
            session.write(IoBuffer.wrap("Humidity:2%\n".getBytes()));
            session.write(IoBuffer.wrap("Lo:114.11052\n".getBytes()));
            session.write(IoBuffer.wrap("La:10.4000\n".getBytes()));
            session.write(IoBuffer.wrap("end\n".getBytes()));
            Thread.sleep(2000);
        }
        // 关闭会话，待所有线程处理结束后
        client.connector.dispose(true);
    }
    @Override
    public void messageReceived(IoSession iosession, Object message)
            throws Exception {
        IoBuffer bbuf = (IoBuffer) message;
        byte[] byten = new byte[bbuf.limit()];
        bbuf.get(byten, bbuf.position(), bbuf.limit());
        System.out.println("客户端收到消息" +new String(byten,"UTF-8"));
    }
    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {
        System.out.println("客户端异常");
        super.exceptionCaught(session, cause);
    }
    @Override
    public void messageSent(IoSession iosession, Object obj) throws Exception {
        System.out.println("客户端消息发送");
        super.messageSent(iosession, obj);
    }
    @Override
    public void sessionClosed(IoSession iosession) throws Exception {
        System.out.println("客户端会话关闭");
        super.sessionClosed(iosession);
    }
    @Override
    public void sessionCreated(IoSession iosession) throws Exception {
        System.out.println("客户端会话创建");
        super.sessionCreated(iosession);
    }
    @Override
    public void sessionIdle(IoSession iosession, IdleStatus idlestatus)
            throws Exception {
        System.out.println("客户端会话休眠");
        super.sessionIdle(iosession, idlestatus);
    }
    @Override
    public void sessionOpened(IoSession iosession) throws Exception {
        System.out.println("客户端会话打开");
        super.sessionOpened(iosession);
    }
}