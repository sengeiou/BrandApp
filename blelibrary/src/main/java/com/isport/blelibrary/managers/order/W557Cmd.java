package com.isport.blelibrary.managers.order;

public class W557Cmd {


    //开始温度测量 01 39 01
    public byte[] startTempMeasure(boolean isStart) {
        byte[] cmds = new byte[]{0x01, 0x39, (byte) 01};
        return cmds;
    }


    //获取温度偏差

    public byte[] setTempSub(int value){
        byte[] cmds = new byte[]{0x05, 0x0e, (byte) 0xa1, (byte) 0xfe,0x74,0x69, (byte) value};
        return cmds;
    }

    //设置温度偏差
    public byte[] getTempSub(){
        byte[] cmds = new byte[]{0x01, 0x0f};
        return cmds;
    }

    //读取目标
    public byte[] readW560Goal(){
        return new byte[]{0x00,0x0C};
    }


    //设置音乐
    public byte[] setMusicStatus(){
        return new byte[]{0x01,0x13,};
    }





}
