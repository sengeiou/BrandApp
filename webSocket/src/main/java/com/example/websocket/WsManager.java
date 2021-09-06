package com.example.websocket;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.example.websocket.bean.PKType;
import com.example.websocket.bean.ResponeBean;
import com.example.websocket.bean.SendGiftData;
import com.example.websocket.bean.SendRealData;
import com.example.websocket.im.JWebSocketClient;
import com.example.websocket.observable.TimeOutObservable;

import org.java_websocket.enums.ReadyState;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class WsManager {
    public JWebSocketClient client;
    private static WsManager mInstance;
    protected static Handler mHandler;
    protected Context context;

    volatile int connectCount = 0;

    static ParsePkInfo parse;

    private String mUserId = "";
    private String mRoomId = "";
    private String mToken = "";

    private final String TAG = this.getClass().getSimpleName();

    private WsManager() {
    }

    public static WsManager getInstance() {
        if (mInstance == null) {
            synchronized (WsManager.class) {
                if (mInstance == null) {
                    parse = new ParsePkInfo();
                    mInstance = new WsManager();
                    mHandler = new Handler(Looper.getMainLooper()) {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            switch (msg.what) {

                            }
                        }
                    };
                }
            }
        }
        return mInstance;
    }


    public boolean isConn() {
        if (client != null) {
            if (client.isClosed()) {
                return false;
            } else {
                return true;
            }
        } else {
            //如果client已为空，重新初始化连接
            return false;
        }
    }


    public void disConnect() {
        // mHandler.removeCallbacks(heartBeatRunnable);

        Log.e("disConnect", "disConnect" + client);

        Util.pkEnd = true;
        if (client != null && client.isOpen()) {
            try {
                client.closeBlocking();
                client = null;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static StringBuilder logBuilder;

    public void setContext(Context context) {
        this.context = context;
    }

    public void connetSocket(String userId, String roomId, String token) {
        mUserId = userId;
        mRoomId = roomId;
        mToken = token;

        if (client != null && client.isOpen()) {
            try {
                //  mHandler.removeCallbacks(heartBeatRunnable);
                client.closeBlocking();
                client = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            //  URI uri = URI.create(Util.ws + roomId + "/" + userId + "?token=" + token);
            URI uri = URI.create("wss://test.gateway.spinning.fitalent.com.cn/spinning-websocket/pk/1/1");
            Log.e("JWebSocketClientService", "心跳包检测websocket连接状态" + (Util.ws + roomId + "/" + userId));
            client = new JWebSocketClient(uri) {
                @Override
                public void onError(Exception ex) {
                    super.onError(ex);
                    Log.e("JWebSocketClientService", "onError：" + ex.toString());
                    //errorreconnectWs();
                    isConn = false;
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    super.onClose(code, reason, remote);
                    Log.e("JWebSocketClientService", "onClose：" + reason + ",code=" + code);
                    isConn = false;
                  /*  switch (code) {
                        case 1:
                            Util.pkEnd = true;
                            reconnectWs();
                            break;
                        case 2:
                            Util.pkEnd = true;
                            reconnectWs();
                            break;
                        case 3://手动断开连接

                            break;
                        case 4:
                            Util.pkEnd = true;
                            reconnectWs();
                            break;

                        case 5://网络断开连接
                            TimeOutObservable.getInstance().sendTimeOut();
                            Util.pkEnd = true;
                            break;
                    }
*/
                }

                @Override
                public void onMessage(String message) {
                    isConn = true;
                    Log.e("JWebSocketClientService", "收到的消息：messageId=" + message);
                    if (logBuilder == null) {
                        logBuilder = new StringBuilder();
                    }
                    logBuilder.append(new StringBuilder(dataToString(new Date(), "HH:mm:ss")) + "--message" + message);

                }

                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    super.onOpen(handshakedata);
                    sendMessage();
                    TimeOutObservable.getInstance().sendConnTime();
                    Util.pkEnd = false;
                    connectCount = 0;
                    isConn = true;
                    Log.e("JWebSocketClientService", "websocket连接成功");
                }
            }

            ;

            connect();

        } catch (
                Exception e) {
            e.printStackTrace();
        }
        // mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);//开启心跳检测
    }

    public static String dataToString(Date date, String format) {
        try {
            SimpleDateFormat format1 = new SimpleDateFormat(format);
            return format1.format(date);
        } catch (Exception e) {
            return System.currentTimeMillis() + "";
        }

    }

    /**
     * {"type":2001,"pkId":"1","userId":1,"distance":1,"durationMillis":1,"pkStatus":1}
     */
    public void sendRealData(String pkId, String userId, int distance, String pkStatus) {
        SendRealData sendRealData = new SendRealData();
        sendRealData.setType(PKType.REPORT_DATA.getValue());
        sendRealData.setPkId(pkId);
        sendRealData.setUserId(userId);
        sendRealData.setDistance("" + distance);
        sendRealData.setPkStatus(pkStatus);
        sendValue(parse.sendRealDataBeanToString(sendRealData));

    }


    //发送心跳包
    public void sendHeartData() {
        sendValue("{\"type\": 101}");
    }

    /**
     * {"type":2002,"pkId":"1","giftCode":"demoData","fromUserId":1,"toUserId":2}
     *
     * @return
     */
    public void sendGiftData(String pkId, String giftCode, String fromUserId, String toUserId) {
        SendGiftData sendRealData = new SendGiftData();
        sendRealData.setType(PKType.GIVE_GIFT.getValue());
        sendRealData.setPkId(pkId);
        sendRealData.setGiftCode(giftCode);
        sendRealData.setFromUserId(fromUserId);
        sendRealData.setToUserId(toUserId);
        Log.e("JWebSocketClientService", "sendGiftData=" + sendRealData);
        sendValue(parse.sendGiftbeanToStr(sendRealData));

    }

    //
    volatile boolean isConn = false;


    public void cheacConn() {
        Log.e("cheacConn", "cheacConn------");


        if (!isConn) {
            Util.pkEnd = true;
            disConnect();
            reconnectWs();
        }
        isConn = false;
        if (client != null) {
            if (!client.isOpen()) {
                if (client.getReadyState().equals(ReadyState.NOT_YET_CONNECTED)) {
                    try {
                        reconnectWs();
                    } catch (IllegalStateException e) {
                    }
                } else if (client.getReadyState().equals(ReadyState.CLOSING) || client.getReadyState().equals(ReadyState.CLOSED)) {
                    reconnectWs();
                }
            } else {
                sendHeartData();
            }
        } else {
            //如果client已为空，重新初始化连接
            client = null;
            connetSocket(mUserId, mRoomId, mToken);
        }
    }


    public void responce(String messageId, String pkId, int type) {
        ResponeBean sendRealData = new ResponeBean();
        sendRealData.setType(type);
        sendRealData.setPkId(pkId);
        sendRealData.setMessageId(messageId);
        sendValue(parse.sendResponse(sendRealData));

    }

    public void sendValue(String value) {
        if (TextUtils.isEmpty(value)) {
            return;
        }
        addQueuryData(value);

    }

    String value;
    boolean isStart = false;

    private void sendMessage() {
        if (isStart) {
            return;
        }
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (!Util.pkEnd) {
                            isStart = true;
                            //connectBlocking多出一个等待操作，会先连接再发送，否则未连接发送会报错
                            if (client != null && client.isOpen()) {
                                if (getQueuryLenth() > 0) {
                                    value = pollQueuryData();
                                    Log.e("JWebSocketClientService", " sendMessage sendValue=" + value + "isOpen=" + client.isOpen());
                                    if (!TextUtils.isEmpty(value)) {
                                        client.send(value);
                                    }

                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }.start();
    }

    protected BlockingQueue<String> cmds = new LinkedBlockingQueue<>();


    public int getQueuryLenth() {
        return cmds.size();
    }

    public void clearQueuryData() {
        cmds.clear();
    }

    public void addQueuryData(String data) {
        cmds.offer(data);
    }

    public String pollQueuryData() {

        return cmds.poll();
    }


    /**
     * 开启重连
     */
    public void reconnectWs() {

        // mHandler.removeCallbacks(heartBeatRunnable);
        connectCount++;
        new Thread() {
            @Override
            public void run() {
                try {
                    Log.e("JWebSocketClientService", "开启重连");
                    client.reconnectBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    /**
     * 开启重连
     */
    private void errorreconnectWs() {
        cheacConn();
    }

    /**
     * 连接websocket
     */
    private void connect() {
        new Thread() {
            @Override
            public void run() {
                try {
                    //connectBlocking多出一个等待操作，会先连接再发送，否则未连接发送会报错
                    client.connectBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
