package com.guanguan.map.map.controller;

import com.alibaba.fastjson.JSON;
import com.guanguan.map.map.domain.DataDomain;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: map
 * @description: 一句话说明该类
 * @author: 杨永平
 * @create: 2018年01月23日 17:10
 **/
@Component
public class MapServerHandler  extends IoHandlerAdapter  {

    public static String START = "start";
    public static String END = "end";

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        System.out.println("mina连接已经创建");
        // TODO Auto-generated method stub
        SocketSessionConfig cfg = (SocketSessionConfig) session.getConfig();
        cfg.setReceiveBufferSize(2 * 1024 * 1024);
        cfg.setReadBufferSize(2 * 1024 * 1024);
        cfg.setKeepAlive(true);
        cfg.setSoLinger(0); //这个是根本解决问题的设置
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
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        IoBuffer bbuf = (IoBuffer) message;

        byte[] byten = new byte[bbuf.limit()];
        bbuf.get(byten, bbuf.position(), bbuf.limit());
        String msg = new String(byten,"UTF-8");
        System.out.println("客户端收到消息" + msg);
        String[] messages = msg.split("\n");
        DataDomain dataDomain = null;
        System.out.println(JSON.toJSONString(messages));
        for (String msg1 :messages) {
            //保持

            if (msg1.contains(START)) {
                dataDomain = new DataDomain();
                session.setAttribute("dataDomain",dataDomain);
            } else if (msg1.contains(END)) {
                dataDomain = (DataDomain)session.getAttribute("dataDomain");
                System.out.println(JSON.toJSONString(dataDomain));
                try {
                    IndexWebSocket.sendInfo(JSON.toJSONString(dataDomain));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                dataDomain = (DataDomain)session.getAttribute("dataDomain");
                if (msg1.contains("Temperature")) {
                    dataDomain.setTemperature(msg1.split(":")[1]);
                }
                if (msg1.contains("Humidity")) {
                    dataDomain.setHumidity(msg1.split(":")[1]);
                }
                if (msg1.contains("Lo")) {
                    dataDomain.setLongitude(msg1.split(":")[1]);
                }
                if (msg1.contains("La")) {
                    dataDomain.setLatitude(msg1.split(":")[1]);
                }
//            if (msg.contains("Temperature")){
//
//            }
//            if (msg.contains("Temperature")){
//
//            }
                session.setAttribute("dataDomain",dataDomain);
            }
        }


    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(session, cause);
    }
}
