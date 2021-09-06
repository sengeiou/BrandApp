package com.isport.blelibrary.managers.order;

public class S002Cmd {


    /**
     * 发送最大心率报警
     */
    public byte[] setMaxHr(int hr){
        byte[] cmds = new byte[]{0x01, (byte) 0x1E, (byte) hr};
        return cmds;
    }

    /**
     * 清除数据指令
     */
    public byte[] clearHistoryData() {
        byte[] cmds = new byte[]{0x01, (byte) 0x29, 0x01, (byte) 0xA1, (byte) 0xFE, 0x74, 0x69};
        return cmds;
    }


    //= 0X20,         // HOST		设置跳绳类型
    //05 20 type min sec c1 c2

    /**
     * type [0,1,2] 分别代表[自由,倒计时,倒计数]
     * min sec 为倒计时时间 分 秒
     * c1 c2 为倒计数 低8位 高8位
     * pk pk类型(0 正常跳绳,
     * 1 普通pk(pk类型由type决定),
     * 2 限时pk(倒计数类型type=2, 在c1 c2规定的时间内完成))
     */
    public byte[] setRopeType(int type, int min, int sec, int number, int pk) {
        byte[] cmds = new byte[]{0x06, 0x20, (byte) type,(byte) min, (byte) sec, (byte) number, (byte) (number >> 8), (byte) pk};
        return cmds;
    }

    /**
     * CMD_GET_SKIP_STATE              = 0X23,         // HOST     获取跳绳开始&停止
     * 	0   4
     *     1   0xFF
     *     2   CMD_GET_SKIP_STATE
     * 	3   1 为停止 2 为开始跳绳
     * 	4   跳绳类型
     * 	5   0 为无数据，1 为有数据(有数据时需要执行 24 25 26 命令)
     * 	    取数据前，先执行这条命令
     * @param
     * @return
     */
    public byte[] getRopeStartOrEnd(){
        byte[] cmds = new byte[]{0x00, 0x23};
        return cmds;
    }

    public byte[] setRopeType(int type) {
        byte[] cmds = new byte[]{0x01, 0x28, (byte) type};
        return cmds;
    }

    //= 0X21,         // HOST
    public byte[] getRopeType() {
        byte[] cmds = new byte[]{0x01, 0x21};
        return cmds;
    }
    //CMD_SET_SKIP_STATE              = 0X22,         // HOST		设置跳绳开始&停止
    //	2 为开始   1 为停止并保存数据

    public byte[] startOrStopRope(int type) {
        byte[] cmds = new byte[]{0x01, 0x22, (byte) type};
        return cmds;
    }

    //0X24  // HOST		获取跳绳总数据
    //0X25,         // HOST		获取跳绳绊绳详情
    //0X26,         // HOST		获取跳绳心率&频次

    public byte[] getSumRopeData() {
        byte[] cmds = new byte[]{0x01, 0x24};
        return cmds;
    }

    public byte[] getSumRopeDetailData() {
        byte[] cmds = new byte[]{0x01, 0x25};
        return cmds;
    }

    public byte[] getSumRopeDetailHrAndBPMData() {
        byte[] cmds = new byte[]{0x01, 0x26};
        return cmds;
    }


}
