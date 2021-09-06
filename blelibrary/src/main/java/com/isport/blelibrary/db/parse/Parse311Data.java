package com.isport.blelibrary.db.parse;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.isport.blelibrary.db.CommonInterFace.WatchData;
import com.isport.blelibrary.db.action.W81Device.W81DeviceEexerciseAction;
import com.isport.blelibrary.db.action.bracelet_w311.Bracelet_W311_24HDataModelAction;
import com.isport.blelibrary.db.action.bracelet_w311.Bracelet_W311_AlarmModelAction;
import com.isport.blelibrary.db.action.bracelet_w311.Bracelet_W311_RealTimeDataAction;
import com.isport.blelibrary.db.action.bracelet_w311.Bracelet_W311_SettingModelAction;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_24HDataModel;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_AlarmModel;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_DisplayModel;
import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_RealTimeData;
import com.isport.blelibrary.db.table.watch_w516.Watch_W516_SedentaryModel;
import com.isport.blelibrary.deviceEntry.impl.BaseDevice;
import com.isport.blelibrary.deviceEntry.interfaces.IDeviceType;
import com.isport.blelibrary.entry.AlarmEntry;
import com.isport.blelibrary.entry.AutoSleep;
import com.isport.blelibrary.entry.DisplaySet;
import com.isport.blelibrary.entry.HeartRateData;
import com.isport.blelibrary.entry.HeartRateHistory;
import com.isport.blelibrary.entry.HeartTiming;
import com.isport.blelibrary.entry.HistorySportN;
import com.isport.blelibrary.entry.SedentaryRemind;
import com.isport.blelibrary.interfaces.BluetoothListener;
import com.isport.blelibrary.interfaces.W311BluetoothListener;
import com.isport.blelibrary.managers.BaseManager;
import com.isport.blelibrary.managers.BraceletW311BleManager;
import com.isport.blelibrary.utils.BleSPUtils;
import com.isport.blelibrary.utils.CommonDateUtil;
import com.isport.blelibrary.utils.DateUtil;
import com.isport.blelibrary.utils.Logger;
import com.isport.blelibrary.utils.ThreadPoolUtils;
import com.isport.blelibrary.utils.TimeUtils;
import com.isport.blelibrary.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Parse311Data {
    public static String formatTwoStr(int number) {
        String strNumber = String.format("%02d", number);
        return strNumber;
    }

    public static void processHeartRateHistoryData(boolean is288, Context context, String mac, List<byte[]> list, BluetoothListener bluetoothListener, String userId, String devcieId) {


        int year = 0, mon = 0, day = 0;
        try {
            List<byte[]> data = list;
            Log.e("WatchW311GattCallBack", "解析心率数据" + list.size());
            if (list != null && list.size() >= 1) {
                byte[] startBytes = data.get(0);
//        DE-02-10-FE-YY-MM-DD-总数(1bytes)-心率数据1-心率数据2-...心率数据48（历史数据为48个，当天按实际数据）
                year = startBytes[4] * 256 + (startBytes[5] & 0x00ff);
                mon = startBytes[6];
                day = startBytes[7];
                Calendar calendar = Calendar.getInstance();
                Calendar current = getCurrentCalendar();
                calendar.set(year, mon - 1, day, 0, 0, 0);
                String dateStr = DateUtil.dataToString(calendar.getTime(), "yyyy-MM-dd");
                int dataCount;
                if (is288) {
                    dataCount = byteArrayToInt(new byte[]{startBytes[8], startBytes[9]});//有效数据的长度
                } else {
                    dataCount = byteArrayToInt(new byte[]{startBytes[8]});//有效数据的长度
                }
                Log.e("CmdController", dataCount + "---");
                ArrayList<HeartRateData> heartDataList = new ArrayList<>();//所有数据数组
                ArrayList<Integer> dataList = new ArrayList<>();//有效数据数组
                int totalHeart = 0;
                int minHeart = 0;
                int maxHeart = 0;
                HeartRateHistory listHeartRecord = new HeartRateHistory();
                listHeartRecord.setStartDate(dateStr);
                listHeartRecord.setMac(mac);
                listHeartRecord.setCount(dataCount);

                //*****************************更简便的方式获取数据**************************************//

                byte[] resultBytes = new byte[dataCount];
                for (int i = 0; i < data.size(); i++) {
                    byte[] tmp = (byte[]) data.get(i);
                    if (tmp != null) {
                        if (i == 0) {
                            if (data.size() == 1) {
                                //只有一包的情况
                                if (is288) {
                                    System.arraycopy(tmp, 10, resultBytes, 0, dataCount);
                                } else {
                                    System.arraycopy(tmp, 9, resultBytes, 0, dataCount);
                                }
                            } else {
                                //有多包的情况
                                if (is288) {
                                    System.arraycopy(tmp, 10, resultBytes, 0, 10);
                                } else {
                                    System.arraycopy(tmp, 9, resultBytes, 0, 11);
                                }
                            }
                        } else {
                            //第N包 N>=2
                            int formIndex;
                            if (is288) {
                                formIndex = 10 + (i - 1) * 20;
                            } else {
                                formIndex = 11 + (i - 1) * 20;
                            }
                            if (i == data.size() - 1) {
                                //最后一包的情况
                                System.arraycopy(tmp, 0, resultBytes, formIndex, dataCount - (formIndex));
                            } else {
                                //中间包
                                System.arraycopy(tmp, 0, resultBytes, formIndex, 20);
                            }
                        }
                    }
                }


                //
                BleSPUtils.putString(context, BleSPUtils.Bracelet_LAST_HR_SYNCTIME, dateStr);


                if (year == current.get(Calendar.YEAR) && mon == current.get
                        (Calendar.MONTH) + 1 && day == current.get(Calendar.DAY_OF_MONTH)) {
                    if (bluetoothListener != null) {
                        BraceletW311BleManager.cacheRetrySyncHrDataInfo.clear();
                        ((W311BluetoothListener) bluetoothListener).onSyncHrDataComptelety();
                    }
                } else {
                    //同步下一天
                    String key = year + "_" + mon + "_" + day;
                    BraceletW311BleManager.cacheRetrySyncHrDataInfo.remove(key);
                    Calendar nextCalendar = getNextCalendar(year, mon, day);
                    Logger.myLog("同步心率数据时间year" + nextCalendar.get(Calendar.YEAR) + "month:" + nextCalendar.get(Calendar.MONTH) + 1 + "day:" + nextCalendar.get(Calendar.DAY_OF_MONTH));
                    if (bluetoothListener != null) {
                        ((W311BluetoothListener) bluetoothListener).onSysnHrDate(nextCalendar.get(Calendar.YEAR), nextCalendar.get(Calendar.MONTH) + 1, nextCalendar.get(Calendar.DAY_OF_MONTH));
                    }
                }

                for (int i = 0; i < resultBytes.length; i++) {
                    //确定最大值，最小值，平均值，所有数据
                    int rate = resultBytes[i] & 0xff;
                    if (rate != 0) {///过滤掉心率为0
                        totalHeart += rate;
                        if (minHeart == 0) {
                            minHeart = rate;
                        } else {
                            minHeart = minHeart < rate ? minHeart : rate;
                        }
                        maxHeart = maxHeart < rate ? rate : maxHeart;
                        dataList.add(rate);
                    }
                    //为0的数据也加入
                    heartDataList.add(new HeartRateData(rate));
                }
                listHeartRecord.setAvg(dataList.size() <= 0 ? 0 : (int) (totalHeart / (dataList.size() * 1.0)));
                listHeartRecord.setMax(maxHeart);
                listHeartRecord.setMin(minHeart);
                listHeartRecord.setHeartDataList(heartDataList);

                //Bracelet_W311_24HDataModel model=Bracelet_W311_24HDataModelAction.findBracelet_HDataModelByDeviceId(userId,dateStr,devcieId);
                Bracelet_W311_24HDataModel model = new Bracelet_W311_24HDataModel();
                model.setUserId(userId);
                model.setDeviceId(devcieId);
                model.setDateStr(dateStr);
                model.setAvgHR(dataList.size() <= 0 ? 0 : (int) (totalHeart / (dataList.size() * 1.0)));
                dataList.clear();
                for (int i = 0; i < heartDataList.size(); i++) {
                    dataList.add(heartDataList.get(i).getHeartRate());
                }
                Gson gson = new Gson();
                //bracelet_w311_24HDataModel.setStepArray(gson.toJson(stepList));
                // bracelet_w311_24HDataModel.setSleepArray(gson.toJson(sleepList));
                Logger.myLog(dateStr + "心率数据" + gson.toJson(dataList));
                model.setHrArray(gson.toJson(dataList));
                if (model.getAvgHR() == 0) {
                    model.setHasHR(WatchData.NO_HR);
                } else {
                    model.setHasHR(WatchData.HAS_HR);
                }
                Bracelet_W311_24HDataModelAction.updateBracelet24HHrDataModel(model, devcieId);
                //查询心率数据
            }

        } catch (Exception e) {
            Logger.myLog(e.toString());
            //解析错误
            if (bluetoothListener != null) {
                if (year == 0 && mon == 0 && day == 0) {
                    return;
                }
                ((W311BluetoothListener) bluetoothListener).onSysnHrDate(year, mon, day);
            }
        }

    }

    public static int byteArrayToInt(byte[] data) {
        int value = 0;
        if (data != null && data.length > 0) {
            int len = data.length - 1;
            for (int i = len; i >= 0; i--) {
                value += ((data[len - i] & 0x00ff) << (8 * i));
            }
        }
        return value;
    }


    public static void parseSportData(List<byte[]> m24HDATA, BluetoothListener bluetoothListener, BaseDevice baseDevice, Context context) {


        try {
            Log.e("parseSportData", "baseDevice:" + baseDevice.getDeviceName() + "baseDevice.mac" + baseDevice.getAddress());
            W81DeviceEexerciseAction action = new W81DeviceEexerciseAction();

            byte[] data = new byte[(m24HDATA.size()) * 20];//减去第一包剩下的数据集合
            byte[] data1 = new byte[(m24HDATA.size()) * 20 - 6];//减去第一包剩下的数据集合
            for (int i = 0; i < m24HDATA.size(); i++) {
                byte[] tmp = (byte[]) m24HDATA.get(i);
                if (tmp != null) {
                    System.arraycopy(tmp, 0, data, (i) * 20, 20);
                }
            }


            int sumLen = (Utils.byte2Int(data[4]) << 8)
                    + (Utils.byte2Int(data[5]));
            // Log.e("parseSportData", "sumLen:" + sumLen);

            //DE 02 16 FE 固定头
            //00 A8 数据的长度
            //一天数据的长度是24
            System.arraycopy(data, 6, data1, 0, data1.length);
            //24长度一个数据
            List<byte[]> datas = new ArrayList<>();
            for (int i = 0; i < sumLen / 24; i++) {
                if ((i) * 24 >= data1.length) {
                    break;
                }
                byte[] tmp = new byte[24];
                System.arraycopy(data1, (i) * 24, tmp, 0, 24);
                datas.add(tmp);
            }

            for (int j = 0; j < datas.size(); j++) {


                byte[] bytesFirst = datas.get(j);
                //0序号
                //1为模式
                //2,3年（高位在前）
                //4 月
                //5 日
                //6 开始小时
                //7 分
                //8 秒
                //9 10 11 运动总时间
                //12 平均心率
                //13 14 15配速
                // 16 17 总距离(0.01公里，高位在前)
                //18 19 20 总步数
                //21 22 23 总卡

                int index = Utils.byte2Int(bytesFirst[0]);
                //1   运动类型[1-8] 健走 跑步 骑行 登山 足球 篮球 乒乓球 羽毛球
                int sportType = Utils.byte2Int(bytesFirst[1]);

                if (sportType == 0 || sportType > 3) {
                    continue;
                }

                //   3   年(年份-2000)  运动开始时间
                int startyear = (Utils.byte2Int(bytesFirst[2]) << 8)
                        + (Utils.byte2Int(bytesFirst[3]));
                //4   运动开始时间 月
                int startmonth = Utils.byte2Int(bytesFirst[4]);
                //5   日
                int startday = Utils.byte2Int(bytesFirst[5]);
                //  6   时
                int starthour = Utils.byte2Int(bytesFirst[6]);
                //7   分
                int startMin = Utils.byte2Int(bytesFirst[7]);
                //8   秒
                int startSecd = Utils.byte2Int(bytesFirst[8]);
                //运动总时长
                int sumTime = (Utils.byte2Int(bytesFirst[9]) << 16)
                        + (Utils.byte2Int(bytesFirst[10]) << 8) + Utils.byte2Int(bytesFirst[11]);

                int avgHr = Utils.byte2Int(bytesFirst[12]);
                //
                //  18-20 运动总步数  低位在前

                int speed = (Utils.byte2Int(bytesFirst[13]) << 16)
                        + (Utils.byte2Int(bytesFirst[14]) << 8) + Utils.byte2Int(bytesFirst[15]);

                int dis = (Utils.byte2Int(bytesFirst[16]) << 8) + Utils.byte2Int(bytesFirst[17]);

                int step = (Utils.byte2Int(bytesFirst[18]) << 16)
                        + (Utils.byte2Int(bytesFirst[19]) << 8) + Utils.byte2Int(bytesFirst[20]);
                // 25-28 运动总距离

                // 29-32 运动总卡路里
                int cal = (Utils.byte2Int(bytesFirst[21]) << 16)
                        + (Utils.byte2Int(bytesFirst[22]) << 8) + Utils.byte2Int(bytesFirst[23]);


                Calendar endCalendar = Calendar.getInstance();
                endCalendar.set(startyear, startmonth - 1, startday, starthour, startMin, startSecd);
                long startTime = endCalendar.getTimeInMillis() / 1000 * 1000;
                long endTime = startTime + sumTime * 1000;
                // Log.e("parseSportData", "index=" + index + ",sportType=" + sportType + ",startyear=" + startyear + ",startmonth=" + startmonth + ",startday=" + startday + ",starthour=" + starthour + ",startMin" + startMin + ",startSecd" + startSecd + ",sumTime" + sumTime + ",avgHr" + avgHr + ",speed=" + speed + ",dis=" + dis + ",step=" + step + ",cal" + cal + ",startTime=" + startTime + ",endTime=" + endTime);
                action.saveW526DeviceExerciseData(BaseManager.mUserId, baseDevice.deviceName, "0", TimeUtils.getTimeByyyyyMMdd(startTime), startTime, endTime, sumTime, sportType, step, dis * 10, cal, avgHr, "");
            }
            bluetoothListener.onSyncSuccessPractiseData(-1);
        } catch (Exception e) {

        }


    }

    public static void parseW31124HData(List<byte[]> m24HDATA, BluetoothListener bluetoothListener, BaseDevice baseDevice, Context context) {
        Log.e("WatchW311GattCallBack", "baseDevice:" + baseDevice.getDeviceName() + "baseDevice.mac" + baseDevice.getAddress());

        if (baseDevice.deviceType == IDeviceType.TYPE_BRAND_W307J) {
            checkSum(m24HDATA, bluetoothListener, baseDevice.getDeviceName());
            return;
        }
        boolean isCurrentDay;
        if (m24HDATA != null && m24HDATA.size() > 0) {
            byte[] bytesFirst = m24HDATA.get(0);
//            00 00 27 26 00 50 00 00 00 00 B4 F9 F3 F3 F3 F3 F1 00 00 锻炼数据
            if (Utils.byte2Int(bytesFirst[12]) == 0xF3 && Utils.byte2Int(bytesFirst[13]) == 0xF3 && Utils.byte2Int
                    (bytesFirst[14]) == 0xF3 && Utils.byte2Int
                    (bytesFirst[15]) == 0xF3) {
                //锻炼数据
                Logger.myLog("锻炼数据");
            } else {
                //24H数据
                Logger.myLog("24H数据");
                int year = byteArrayToInt(new byte[]{bytesFirst[4], bytesFirst[5]});
                int non = byteArrayToInt(new byte[]{bytesFirst[6]});
                int day = byteArrayToInt(new byte[]{bytesFirst[7]});
                int totalStep = byteArrayToInt(new byte[]{bytesFirst[8], bytesFirst[9], bytesFirst[10], bytesFirst[11]});
                int totalCaloric = byteArrayToInt(new byte[]{bytesFirst[12], bytesFirst[13], bytesFirst[14], bytesFirst[15]});
                float totalDist = byteArrayToInt(new byte[]{bytesFirst[16], bytesFirst[17]}) / 100.0f;
                int totalSportTime = byteArrayToInt(new byte[]{bytesFirst[18], bytesFirst[19]});

                Logger.myLog("year = " + year + "non = " + non + "day = " + day + " step = " + totalStep + " cal = " + totalCaloric +
                        " dis = " + totalDist + " time = " + totalSportTime);
                byte[] data = new byte[(m24HDATA.size() - 1) * 19];//减去第一包剩下的数据集合

                for (int i = 1; i < m24HDATA.size(); i++) {
                    byte[] tmp = (byte[]) m24HDATA.get(i);
                    if (tmp != null) {
                        System.arraycopy(tmp, 1, data, (i - 1) * 19, 19);
                    }
                }


                String dateStr = year + "-" + formatTwoStr(non) + "-" + formatTwoStr(day);
                Bracelet_W311_24HDataModel bracelet_w311_24HDataModel = new Bracelet_W311_24HDataModel();
                bracelet_w311_24HDataModel.setUserId(BaseManager.mUserId);
                bracelet_w311_24HDataModel.setDeviceId(baseDevice.deviceName);

                Logger.myLog("BaseManager.mUserId" + BaseManager.mUserId + "baseDevice.deviceName:" + baseDevice.deviceName);
                bracelet_w311_24HDataModel.setDateStr(dateStr);
                bracelet_w311_24HDataModel.setTimestamp(System.currentTimeMillis());
                bracelet_w311_24HDataModel.setTotalSteps(totalStep);
                bracelet_w311_24HDataModel.setTotalDistance(totalDist);
                bracelet_w311_24HDataModel.setTotalCalories(totalCaloric);

                //            第 2 包到第 n 包是每分钟记录数据包，每个数据包括 2 个字节，定义如下
//            BYTE0 - 该时间段步数，若>=250 则为睡眠，睡眠分为 4 个等级，250 到 253 分别对深睡和浅睡 level 2，浅睡 level 1
//            和清醒。
//            BYTE1 - 该时间段平均心率，若为 1，表示没有开启心率功能，若为大于 1，小于 30，表示心率开启了，但是手表
//                    不在手腕上
//            Page 14 of 14
//            一个完整日历天总共有 1440 组记录数据，总数据长度为 1440 * 2 + 20 = 2900。
//            如果最后一个数据不在最后一包的末尾，后面的数据填入 0xFF
//            设备最多可以记录 7 天数据
                //每两byte为一组数据,总共1440数据,每分钟一条数据,24小时数据

                boolean haslPak = false;
                //判断是否是当天数据
                Calendar instance = Calendar.getInstance();

                if (year == instance.get(Calendar.YEAR) && non == instance.get
                        (Calendar.MONTH) + 1 && day == instance.get(Calendar.DAY_OF_MONTH)) {
                    //当天数据
                    //通过获取当前时间来判断包数，12:32  (12*60+32)/19   39.57  40包数据
                    // instance.add(Calendar.MINUTE, -1);
                    isCurrentDay = true;
                    //同步数据完成
                    int hourOfDay = instance.get(Calendar.HOUR_OF_DAY);//当前的小时
                    int minuteOfDay = instance.get(Calendar.MINUTE) - 1;//当前分钟
                    //时间长度   当天的数据
                    int pakNum = hourOfDay * 60 + minuteOfDay;
                    Logger.myLog("instance:" + DateUtil.dataToString(new Date(instance.getTimeInMillis()), "yyyy-MM-dd HH:mm:ss") + ",hourOfDay:" + hourOfDay + ",minuteOfDay:" + minuteOfDay + ",pakNum time lenth:" + pakNum + ",data.length:" + data.length);

                    if (data.length >= pakNum) {
                        Logger.myLog("pakNum == " + pakNum + "当天数据未丢包 data.length == " + data.length);
                    } else {
                        haslPak = true;
                        if (bluetoothListener != null) {
                            bluetoothListener.onSyncError();
                            Logger.myLog("cbluetoothListener.onSyncError:" + "year:" + year + "non:" + non + "day:" + day + "onSyncError");
                            // ((W311BluetoothListener) bluetoothListener).onSysnSportDate(year, non, day);

                        }
//                        Logger.myLog("pakNum == " + pakNum * 19 + "当天数据丢包 data.length == " + data.length);
                    }
                } else {
                    isCurrentDay = false;
                    //历史数据
                    //判断是否丢包,应该有152包数据  1440*2/19  151.57 除去了index位
                    if (data.length >= 1440) {
                        //没有丢包
                        Logger.myLog("历史数据未丢包 == " + data.length);

                    } else {
                        //丢包
                        haslPak = true;
                        Logger.myLog("历史数据丢包 == " + data.length);
                        if (bluetoothListener != null) {
                            bluetoothListener.onSyncError();
                            /*BraceletW311BleManager.cacheRetrySyncDataInfo.clear();
                            Logger.myLog("cbluetoothListener.onSyncError:" + "year:" + year + "non:" + non + "day:" + day);
                            ((W311BluetoothListener) bluetoothListener).onSysnSportDate(year, non, day);*/
                        }
//                        Logger.myLog("历史数据丢包 == " + data.length);
                    }
                }

                if (!haslPak) {
                    boolean isSleepData = false;
                    List<Integer> stepList = new ArrayList<>();
                    List<Integer> sleepList = new ArrayList<>();
                    bracelet_w311_24HDataModel.setHasHR(WatchData.NO_HR);
                    bracelet_w311_24HDataModel.setHasSleep(WatchData.NO_SLEEP);
                    //睡眠时长
                    int sleepTime = byteArrayToInt(new byte[]{data[0], data[1]});
                    bracelet_w311_24HDataModel.setTotalSleepTime(sleepTime + "");
                    Logger.myLog("data.length:" + data.length);
                    for (int i = 0; i < data.length; i++) {
                        //最大不超过1440个数据
                        if (i == 1442) {
                            break;
                        }
                        //最前面2byte是睡眠总时间
                        if (i == 0 || i == 1) {
                            continue;
                        }
                        byte byte0 = data[i];
                        //当天可能出现1197的情况
                       /* byte byte1;
                        if (i + 1 >= data.length) {
                            byte1 = 1;
                        } else {
                            byte1 = data[i + 1];
                        }*/
                        int int0 = Utils.byte2Int(byte0);

                        //Logger.myLog("收到的步数" + int0);
                        if (int0 == 255) {
                            //结尾包了
//                            Logger.myLog("结尾包了 == " + i);
                            break;
                        }
                        /**
                         * 计步或者睡眠
                         */
                        if (int0 >= 250) {
                            bracelet_w311_24HDataModel.setHasSleep(WatchData.HAS_SLEEP);
                            //为睡眠数据
                            if (int0 == 250) {
                                //深睡
//                                Logger.myLog("深睡");
                            } else if (int0 == 251) {
                                //浅睡 level 2
//                                Logger.myLog("浅睡 level 2");

                            } else if (int0 == 252) {
                                //浅睡 level 1
//                                Logger.myLog("浅睡 level 1");

                            } else if (int0 == 253) {
                                //清醒
//                                Logger.myLog("清醒");
                            }
                            stepList.add(0);
                            sleepList.add(int0);
                            isSleepData = true;
                        } else {
                            //步数数据
//                            Logger.myLog("步数数据 == " + int0);
                            stepList.add(int0);
                            sleepList.add(0);
                        }
                    }
                    //  bracelet_w311_24HDataModel.setAvgHR(Math.round(sum / (float) size));
//                    Logger.myLog("dateStr == " + (year + "-" + formatTwoStr(non) + "-" + formatTwoStr(day)) + "heartRateList == " + heartRateList.toString() + " heartRateList.size == " + heartRateList.size() + " AVG == " + Math.round(sum / (float) size));
                    Gson gson = new Gson();
                    /*int sumStep = 0;
                    for (int i = 0; i < stepList.size(); i++) {
                        sumStep += stepList.get(i);
                    }*/
                    //时间
                    //如果取到的总步数和详细数据对不上就把详细数据的数据加起来赋值给总步数
                    /*if (sumStep != bracelet_w311_24HDataModel.getTotalSteps()) {
                        bracelet_w311_24HDataModel.setTotalSteps(sumStep);
                    }*/
                    bracelet_w311_24HDataModel.setStepArray(gson.toJson(stepList));
                    bracelet_w311_24HDataModel.setSleepArray(gson.toJson(sleepList));
                    //区分是否有睡眠数据
                    if (isSleepData) {
                        bracelet_w311_24HDataModel.setHasSleep(WatchData.HAS_SLEEP);
                    } else {
                        bracelet_w311_24HDataModel.setHasSleep(WatchData.NO_SLEEP);
                    }
                    bracelet_w311_24HDataModel.setReportId("0");
                    //bracelet_w311_24HDataModel.setHrArray(gson.toJson(heartRateList));
                    Logger.myLog("获取到的时间" + dateStr);

                    BleSPUtils.putString(context, BleSPUtils.Bracelet_LAST_SYNCTIME, dateStr);
                    Bracelet_W311_24HDataModelAction.saveOrUpdateBracelet24HDataModel(bracelet_w311_24HDataModel, baseDevice.deviceName);
                    //同步成功


                    // Parse311Data parse311Data=new Parse311Data();
                    syncSuccess(isCurrentDay, bluetoothListener, year, non, day, baseDevice.getDeviceName(), (int) bracelet_w311_24HDataModel.getTotalSteps(), bracelet_w311_24HDataModel.getTotalDistance(), (int) bracelet_w311_24HDataModel.getTotalCalories(), dateStr, baseDevice.address);

                    /*if (isCurrentDay) {
                        // int cal = (int) StepArithmeticUtil.stepsConversionCaloriesFloat(BaseManager.mWeight * 1.0f, bracelet_w311_24HDataModel.getTotalSteps());
                        // float dis = StepArithmeticUtil.stepsConversionDistanceFloat(BaseManager.mHeight, BaseManager.mSex == 0 ? "Female" : "Male", bracelet_w311_24HDataModel.getTotalSteps());
                        Bracelet_W311_RealTimeData realTimeData = new Bracelet_W311_RealTimeData(1l, BaseManager.mUserId, baseDevice.deviceName, (int) bracelet_w311_24HDataModel.getTotalSteps(), bracelet_w311_24HDataModel.getTotalDistance(), (int) bracelet_w311_24HDataModel.getTotalCalories(), dateStr, baseDevice.address);
                        long id = Bracelet_W311_RealTimeDataAction.saveOrUpdateW311RealTimeData(BaseManager.mUserId, realTimeData, true);
                        if (bluetoothListener != null) {
                            ((W311BluetoothListener) bluetoothListener).onSyncCompte();
                        }

                    } else {
                        //获取到的当前日期+1天
                        String key = year + "_" + non + "_" + day;
                        BraceletW311BleManager.cacheRetrySyncDataInfo.remove(key);
                        Calendar nextCalendar = getNextCalendar(year, non, day);
                        Logger.myLog("同步数据时间year" + nextCalendar.get(Calendar.YEAR) + "month:" + (nextCalendar.get(Calendar.MONTH) + 1) + "day:" + nextCalendar.get(Calendar.DAY_OF_MONTH));
                        if (bluetoothListener != null) {
                            ((W311BluetoothListener) bluetoothListener).onSysnSportDate(nextCalendar.get(Calendar.YEAR), nextCalendar.get(Calendar.MONTH) + 1, nextCalendar.get(Calendar.DAY_OF_MONTH));
                        }
                    }
*/

                } else {
                    //丢包了  如果是在3次内就重新去请求否则就同步下一天
                    if (bluetoothListener != null) {
                        bluetoothListener.onSyncError();
                    }

                    String key = year + "_" + non + "_" + day;
                    int count = 1;
                    if (BraceletW311BleManager.cacheRetrySyncDataInfo.containsKey(key)) {
                        count = BraceletW311BleManager.cacheRetrySyncDataInfo.get(key);
                    }
                    if (count < 3) {
                        if (bluetoothListener != null) {
                            ((W311BluetoothListener) bluetoothListener).onSysnSportDate(year, non, day);
                        }
                    } else {
                        BraceletW311BleManager.cacheRetrySyncDataInfo.remove(key);
                        if (isCurrentDay) {
                            if (bluetoothListener != null) {
                                ((W311BluetoothListener) bluetoothListener).onSyncCompte();
                            }
                        } else {

                            Calendar nextCalendar = getNextCalendar(year, non, day);
                            Logger.myLog("同步数据时间year" + nextCalendar.get(Calendar.YEAR) + "month:" + nextCalendar.get(Calendar.MONTH) + 1 + "day:" + nextCalendar.get(Calendar.DAY_OF_MONTH) + "hasPak:" + haslPak);
                            if (bluetoothListener != null) {
                                ((W311BluetoothListener) bluetoothListener).onSysnSportDate(nextCalendar.get(Calendar.YEAR), nextCalendar.get(Calendar.MONTH) + 1, nextCalendar.get(Calendar.DAY_OF_MONTH));
                            }
                        }
                    }


                }
            }
            //                02-20 19:25:21.805 5660-5775/com.isport.isportblesdk E/MyLog:  ReceiverCmd
            // 00 26 27 00
            // 00 E3 07 02
// 13 00 A3 02 00 00 00 00 00 00 00 00  UUID 7658fd03-878a-4350-a93e-da553e719ed0
//                02-20 19:25:21.810 5660-5775/com.isport.isportblesdk E/MyLog:  ReceiverCmd 01 FA 3D FA 3E FA 3F FA
// 40 FA 41 FA 42 FA 43 FA 44 FA 45 FA  UUID 7658fd03-878a-4350-a93e-da553e719ed0
        }
    }

    /**
     * 获取下一个同步计步历史数据的日期
     *
     * @return
     */


    //同步成功处理
    public static void syncSuccess(boolean isCurrentDay, BluetoothListener bluetoothListener, int year, int non, int day, String deviceName, int tialSteps, float distance, int calories, String dateStr, String address) {
        if (isCurrentDay) {
            // int cal = (int) StepArithmeticUtil.stepsConversionCaloriesFloat(BaseManager.mWeight * 1.0f, bracelet_w311_24HDataModel.getTotalSteps());
            // float dis = StepArithmeticUtil.stepsConversionDistanceFloat(BaseManager.mHeight, BaseManager.mSex == 0 ? "Female" : "Male", bracelet_w311_24HDataModel.getTotalSteps());
            Bracelet_W311_RealTimeData realTimeData = new Bracelet_W311_RealTimeData(1l, BaseManager.mUserId, deviceName, tialSteps, distance, calories, dateStr, address);
            long id = Bracelet_W311_RealTimeDataAction.saveOrUpdateW311RealTimeData(BaseManager.mUserId, realTimeData, true);
            currentDay(bluetoothListener);

        } else {
            //获取到的当前日期+1天
            nextDay(bluetoothListener, year, non, day);
        }
    }


    public static void currentDay(BluetoothListener bluetoothListener) {
        if (bluetoothListener != null) {
            ((W311BluetoothListener) bluetoothListener).onSyncCompte();
        }
    }

    public static void nextDay(BluetoothListener bluetoothListener, int year, int non, int day) {
        String key = year + "_" + non + "_" + day;
        BraceletW311BleManager.cacheRetrySyncDataInfo.remove(key);
        Calendar nextCalendar = getNextCalendar(year, non, day);
        Logger.myLog("同步数据时间year" + nextCalendar.get(Calendar.YEAR) + "month:" + (nextCalendar.get(Calendar.MONTH) + 1) + "day:" + nextCalendar.get(Calendar.DAY_OF_MONTH));
        if (bluetoothListener != null) {
            ((W311BluetoothListener) bluetoothListener).onSysnSportDate(nextCalendar.get(Calendar.YEAR), nextCalendar.get(Calendar.MONTH) + 1, nextCalendar.get(Calendar.DAY_OF_MONTH));
        }
    }

    private static Calendar getNextCalendar(int startYear, int startMonth, int startDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.YEAR, startYear);
        calendar.set(Calendar.MONTH, startMonth - 1);
        calendar.set(Calendar.DAY_OF_MONTH, startDay);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar;
    }

    private static Calendar getCurrentCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * 解析闹钟信息
     *
     * @param data
     */
    public static void parserAlarmInfo(final byte[] data, final BaseDevice baseDevice) {
        try {
            byte[] tempBtype = new byte[data.length];
            for (int i = 0; i < data.length; i++) {
                tempBtype[i] = data[i];
            }
            if (data != null && data.length == 20) {
                List<Bracelet_W311_AlarmModel> models = new ArrayList<>();
                ArrayList<AlarmEntry> listAlarm = new ArrayList<>();
                int isOn = data[4];
                for (int i = 0; i < 5; i++) {
                    int startHour1 = data[3 * i + 5] & 0xff;
                    int startMin1 = data[3 * i + 6] & 0xff;
                    byte repeat1 = data[3 * i + 7];
                    listAlarm.add(new AlarmEntry(i, startHour1, startMin1, repeat1, (isOn) == 1));
                    long id = i;
                    if (startHour1 == 0 && startHour1 == 0 && repeat1 == 0) {

                    } else {
                        models.add(new Bracelet_W311_AlarmModel(id, i, BaseManager.mUserId, baseDevice.deviceName, repeat1, CommonDateUtil.formatTwoStr(startHour1) + ":" + CommonDateUtil.formatTwoStr(startMin1), "123", ((isOn >> i) & 0x01) == 1));
                    }
                }

                Logger.myLog("parserAlarmInfo baseDevice.deviceName" + baseDevice.deviceName + "BaseManager.mUserId:" + BaseManager.mUserId + "models:" + models.size());
                Bracelet_W311_AlarmModelAction.saveOrUpdateBraceletW311AlarmModele(models, baseDevice.deviceName, BaseManager.mUserId);
            }
        } catch (Exception e) {
            Logger.myLog("parserAlarmInfo:" + e.toString());

        }

           /* Intent intent = new Intent(Constants.ACTION_QUERY_ALARM);
            intent.putParcelableArrayListExtra(Constants.EXTRA_QUERY_ALARM, listAlarm);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);*/

    }


    /**
     * 解析睡眠时间设置
     *
     * @param data
     */
    public static void parserSleepInfo(byte[] data, Context mContext, BluetoothListener bluetoothListener) {
        if (data != null && data.length == 17) {
            AutoSleep autoSleep = AutoSleep.getInstance(mContext);
            boolean isOn = ((data[4] & 0x01) == 1);

            int[] tpb = new int[17];
            boolean isSleep = false;
            boolean isNap = false;
            boolean isSleepRemind;
            boolean isNapRemind;
            for (int i = 5; i < 17; i++) {
                tpb[i - 5] = (data[i] & 0xff);
            }
            int sleepStartHour = 0;
            int sleepStartMin = 0;
            int sleepEndHour = 0;
            int sleepEndMin = 0;
            int sleepRemindHour = 0;
            int sleepRemindMin = 0;

            int napStartHour = 0;
            int napStartMin = 0;
            int napEndHour = 0;
            int napEndMin = 0;
            int napRemindHour = 0;
            int napRemindMin = 0;

            int sleepRemindTime = 0;
            int napRemindTime = 0;

            if (tpb[2] == tpb[3] && tpb[2] == 0xfe) {
                isSleepRemind = false;
                sleepRemindTime = 15;
            } else {
                isSleepRemind = true;
                sleepRemindHour = tpb[2];
                sleepRemindMin = tpb[3];
            }

            if (tpb[10] == tpb[11] && tpb[10] == 0xfe) {
                isNapRemind = false;
                napRemindTime = 15;
            } else {
                napRemindHour = tpb[10];
                napRemindMin = tpb[11];
                isNapRemind = true;
            }

            if (tpb[0] == tpb[1] && tpb[0] == 0xfe && tpb[4] == tpb[5] && tpb[5] == 0xfe) {
                isSleep = false;

                autoSleep.setSleepStartHour(22);
                autoSleep.setSleepStartMin(0);
                autoSleep.setSleepEndHour(7);
                autoSleep.setSleepEndMin(0);
                autoSleep.setSleepRemindTime(15);


            } else {
                isSleep = true;
                sleepStartHour = tpb[0];
                sleepStartMin = tpb[1];
                sleepRemindHour = tpb[2];
                sleepRemindMin = tpb[3];
                sleepEndHour = tpb[4];
                sleepEndMin = tpb[5];
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR, sleepStartHour);
                calendar.set(Calendar.MINUTE, sleepStartMin);
                long mill = calendar.getTimeInMillis();
                calendar = Calendar.getInstance();
                if (sleepRemindHour > sleepStartHour) {
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                }
                calendar.set(Calendar.HOUR, sleepRemindHour);
                calendar.set(Calendar.MINUTE, sleepRemindMin);
                long tpppp = calendar.getTimeInMillis();
                if (sleepRemindTime != 15) {
                    sleepRemindTime = (int) ((mill - tpppp) / (1000 * 60));
                }
                autoSleep.setSleepStartHour(sleepStartHour);
                autoSleep.setSleepStartMin(sleepStartMin);
                autoSleep.setSleepEndHour(sleepEndHour);
                autoSleep.setSleepEndMin(sleepEndMin);
                autoSleep.setSleepRemindTime(sleepRemindTime);
            }

            if (tpb[6] == tpb[7] && tpb[8] == tpb[9] && tpb[6] == tpb[8] && tpb[6] == 0xfe) {
                isNap = false;
                autoSleep.setNapStartHour(13);
                autoSleep.setNaoStartMin(0);
                autoSleep.setNapEndHour(15);
                autoSleep.setNapEndMin(0);
                autoSleep.setNapRemindTime(15);
            } else {
                isNap = true;
                napStartHour = tpb[6];
                napStartMin = tpb[7];
                napEndHour = tpb[8];
                napEndMin = tpb[9];
                napRemindHour = tpb[10];
                napRemindMin = tpb[11];

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR, napStartHour);
                calendar.set(Calendar.MINUTE, napStartMin);
                long mill = calendar.getTimeInMillis();
                calendar = Calendar.getInstance();
                if (napRemindHour > napStartHour) {
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                }

                calendar.set(Calendar.HOUR, napRemindHour);
                calendar.set(Calendar.MINUTE, napRemindMin);
                long tttt = calendar.getTimeInMillis();
                if (napRemindTime != 15) {
                    napRemindTime = (int) ((mill - tttt) / (1000 * 60));
                }


                autoSleep.setNapStartHour(napStartHour);
                autoSleep.setNaoStartMin(napStartMin);
                autoSleep.setNapEndHour(napEndHour);
                autoSleep.setNapEndMin(napEndMin);
                autoSleep.setNapRemindTime(napRemindTime);
            }

            autoSleep.setAutoSleep(isOn);

            if (!isOn) {
                autoSleep.setSleepStartHour(22);
                autoSleep.setSleepStartMin(0);
                autoSleep.setSleepEndHour(7);
                autoSleep.setSleepEndMin(0);
                autoSleep.setSleepRemindTime(15);
                autoSleep.setNapStartHour(13);
                autoSleep.setNaoStartMin(0);
                autoSleep.setNapEndHour(15);
                autoSleep.setNapEndMin(0);
                autoSleep.setNapRemindTime(15);
                autoSleep.setSleep(false);
                autoSleep.setNap(false);
                autoSleep.setSleepRemind(false);
                autoSleep.setNapRemind(false);
            } else {


                autoSleep.setSleep(isSleep);
                autoSleep.setNap(isNap);
                autoSleep.setSleepRemind(isSleepRemind);
                autoSleep.setNapRemind(isNapRemind);
            }


            if (bluetoothListener != null) {
                bluetoothListener.successSleepData();
            }


           /* Intent intent = new Intent(Constants.ACTION_QUERY_SLEEP);
            intent.putExtra(Constants.EXTRA_QUERY_SLEEP, entry);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);*/
        }
    }


    /**
     * 解析display信息
     *
     * @param data
     */
    public static void parserDisplay(byte[] data, final BaseDevice baseDevice) {

        ThreadPoolUtils.getInstance().addTask(new Runnable() {
            @Override
            public void run() {

            }
        });

        byte[] tempBtype = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            tempBtype[i] = data[i];
        }
        if (data != null && data.length == 20) {
            List<Integer> tpDisp = new ArrayList<>();
            for (int i = 4; i < 20; i++) {
                tpDisp.add(data[i] & 0xff);
            }
            final DisplaySet displaySet = new DisplaySet();
            for (int i = 0; i < tpDisp.size(); i++) {
                int val = tpDisp.get(i);
                if (val == 0x00) {
                    displaySet.setShowLogo(true);
                } else if (val == 0x03) {
                    displaySet.setShowCala(true);
                } else if (val == 0x04) {
                    displaySet.setShowDist(true);
                } else if (val == 0x05) {
                    displaySet.setShowSportTime(true);
                } else if (val == 0x06) {
                    displaySet.setShowProgress(true);
                } else if (val == 0x07) {
                    displaySet.setShowEmotion(true);
                } else if (val == 0x08) {
                    displaySet.setShowAlarm(true);
                } else if (val == 0x0A) {
                    displaySet.setShowSmsMissedCall(true);///数量
                } else if (val == 0x0B) {
                    displaySet.setShowIncomingReminder(true);
                } else if (val == 0x0D || val == 0x1D) {
                    displaySet.setShowMsgContentPush(true);
                } else if (val == 0x0F) {///倒计时
                    displaySet.setShowCountDown(true);
                }
            }

            ThreadPoolUtils.getInstance().addTask(new Runnable() {
                @Override
                public void run() {
                    Bracelet_W311_DisplayModel model;
                    model = new Bracelet_W311_DisplayModel();
                    model.setDeviceId(baseDevice.deviceName);
                    model.setIsShowAlarm(displaySet.isShowAlarm());
                    model.setIsShowCal(displaySet.isShowCala());
                    model.setIsShowDis(displaySet.isShowDist());
                    model.setIsShowPresent(displaySet.isShowProgress());
                    model.setIsShowComplete(displaySet.isShowEmotion());
                    model.setIsShowSportTime(displaySet.isShowSportTime());
                    // mActPresenter.saveDisplayItem(model);
                    Bracelet_W311_SettingModelAction.saveOrUpdateBraceletDisplay(model);
                }
            });
            //
         /*   Intent intent = new Intent(Constants.ACTION_QUERY_DISPLAY);
            intent.putExtra(Constants.EXTRA_QUERY_DISPLAY, displaySet);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);*/

        }
    }


    /**
     * 解析久坐提醒
     *
     * @param data
     */
    public static void parserSedentaryInfo(byte[] data, final BaseDevice baseDevice) {
        ThreadPoolUtils.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                byte[] tempBtype = new byte[data.length];
                for (int i = 0; i < data.length; i++) {
                    tempBtype[i] = data[i];
                }
                if (data != null && data.length >= 19) {
                    int beginHour1 = data[5] & 0xff;
                    int beginMin1 = data[6] & 0xff;
                    int endHour1 = data[7] & 0xff;
                    int endMin1 = data[8] & 0xff;
                    SedentaryRemind sedentaryRemind1 = new SedentaryRemind(beginHour1 == 0 && beginMin1 == 0 && endHour1 == 0
                            && endMin1 == 0,
                            beginHour1, beginMin1, endHour1, endMin1);

                    int beginHour2 = data[9] & 0xff;
                    int beginMin2 = data[10] & 0xff;
                    int endHour2 = data[11] & 0xff;
                    int endMin2 = data[12] & 0xff;
                    SedentaryRemind sedentaryRemind2 = new SedentaryRemind(beginHour2 == 0 && beginMin2 == 0 && endHour2 == 0
                            && endMin2 == 0,
                            beginHour1, beginMin1, endHour1, endMin1);

                    int beginHour3 = data[13] & 0xff;
                    int beginMin3 = data[14] & 0xff;
                    int endHour3 = data[15] & 0xff;
                    int endMin3 = data[16] & 0xff;
                    int enble = data[4] & 0xff;
                    if (enble == 0) {
                        //未开启
                    } else {
                        //开启
                    }
                    SedentaryRemind sedentaryRemind3 = new SedentaryRemind(enble == 1 ? true : false,
                            beginHour1, beginMin1, endHour1, endMin1);

                    int noexcise = (data[17] & 0xff) * 60 + (data[18] & 0xff);
                    ArrayList<SedentaryRemind> list = new ArrayList<>();
                    list.add(sedentaryRemind1);
                    list.add(sedentaryRemind2);
                    list.add(sedentaryRemind3);
                    SedentaryRemind.noExerceseTime = noexcise;
                    try {
                        Watch_W516_SedentaryModel watch_w516_sedentaryModel = new Watch_W516_SedentaryModel();
                        watch_w516_sedentaryModel.setUserId(BaseManager.mUserId);
                        watch_w516_sedentaryModel.setDeviceId(baseDevice.getDeviceName());
                        //int timeLong = Utils.byte2Int(data[3]);
                        String startTime;
                        String endTime;
                        watch_w516_sedentaryModel.setIsEnable(sedentaryRemind1.isOn());
                        startTime = CommonDateUtil.formatTwoStr(beginHour1) + ":" + CommonDateUtil.formatTwoStr(beginMin1);
                        endTime = CommonDateUtil.formatTwoStr(endHour1) + ":" + CommonDateUtil.formatTwoStr(endMin1);
                        Logger.myLog("saveSedentary_time:startTime:" + startTime + ",endTime:" + endTime + "timeLong" + noexcise);
                        if (noexcise < 15) {
                            startTime = "9:00";
                            endTime = "17:00";
                            watch_w516_sedentaryModel.setIsEnable(false);
                        } else {
                            watch_w516_sedentaryModel.setIsEnable(true);
                        }
                        watch_w516_sedentaryModel.setLongSitEndTime(endTime);
                        watch_w516_sedentaryModel.setLongSitStartTime(startTime);
                        watch_w516_sedentaryModel.setLongSitTimeLong(noexcise);

                        ParseData.saveOrUpdateWatchW516Sedentary(watch_w516_sedentaryModel);
                    } catch (Exception e) {
                        Logger.myLog(e.toString());
                    }

                }
            }
        });
    }


    /**
     * 解析心率监测设置
     *
     * @param data
     */
    public static void parserTimingHeartDetect(byte[] data, final BaseDevice baseDevice) {
        byte[] tempBtype = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            tempBtype[i] = data[i];
        }
        if (data != null && data.length == 17) {

            boolean isEnable = ((data[4] & 0x01) == 1);
            boolean isFirst = true;
            boolean isSec = true;
            boolean isThird = true;

            int firstStartH = (data[5] & 0xff);
            int firstStartM = (data[6] & 0xff);
            int firstEndH = (data[7] & 0xff);
            int firstEndM = (data[8] & 0xff);
            int secStartH = (data[9] & 0xff);
            int secStartM = (data[10] & 0xff);
            int secEndH = (data[11] & 0xff);
            int secEndMin = (data[12] & 0xff);
            int thirdStartH = (data[13] & 0xff);
            int thirdStartM = (data[14] & 0xff);
            int thirdEndH = (data[15] & 0xff);
            int thirdEndM = (data[16] & 0xff);

            if (firstEndH == firstStartH && firstEndM == firstStartM && firstEndH == firstEndM && firstEndH == 0xff) {
                isFirst = false;
            }
            if (secEndH == secStartH && secEndH == secEndMin && secEndH == secStartM && secStartM == 0xff) {
                isSec = false;
            }
            if (thirdEndH == thirdStartH && thirdStartM == thirdEndM && thirdStartH == thirdEndM && thirdStartH ==
                    0xff) {
                isThird = false;
            }

            HeartTiming heartTiming = new HeartTiming(isEnable, isFirst, isSec, isThird, firstStartH, firstStartM,
                    firstEndH, firstEndM,
                    secStartH, secStartM, secEndH, secEndMin, thirdStartH,
                    thirdStartM, thirdEndH, thirdEndM);
           /* Intent intent = new Intent(Constants.ACTION_QUERY_TIMING_HEART_DETECT);
            intent.putExtra(Constants.EXTRA_QUERY_TIMING_HEART_DETECT, heartTiming);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);*/
        }
    }


    //W307J设备

    /**
     * 检查返回的byte总数是否对的上，看是否有丢包现象
     *
     * @param gatt
     * @param dtype
     * @param curCalendar
     * @param isError
     * @return
     */
    public static String TAG = "checkSum";
    public static boolean IS_DEBUG = true;
    public static int mCurrentYear, mCurrentMonth, mCurrentDay;
    static int isError;


    /**
     * 处理当天的数据
     *
     * @param
     * @return
     */


    public static void dealCurrentDay(List<byte[]> tempCache, int len, byte[] lastB, BluetoothListener listener, String deviceName) {
        {
            //请求的当天数据
            //查看当前时间的5分钟index和上一个5分钟的index，排查最后一包数据的最后一包的index位置
            Calendar instance = Calendar.getInstance();
            int hourOfDay = instance.get(Calendar.HOUR_OF_DAY);//当前的小时
            int minuteOfDay = instance.get(Calendar.MINUTE);
            //当的index是到哪儿了
            int index = hourOfDay * 12 + minuteOfDay / 5;
            if (IS_DEBUG)
                Log.e(TAG, "***00000当前小时和分钟" + "***hourOfDay***" + hourOfDay + "***minuteOfDay***" +
                        minuteOfDay + "***index***" + index);
            //最后一包数据的最近的index
            int length = 0;
            if (index > 255) {
                index = index - 255;
            }
            boolean isLast = false;
            for (int i = 0; i < lastB.length; i++) {
                if ((lastB[i] & 0xff) != 0x00) {
                    isLast = true;
                    break;
                }
            }
            if (isLast) {
                //最后一包有数据
                for (int i = lastB.length - 1; i >= 0; i--) {
                    //此时时间序号正好和
                    if (Utils.byteToInt(lastB[i]) == index) {
                        //这是最后一包的index
                        if (i == lastB.length - 1) {
                            length = 4;
                        } else {
                            if ((lastB[i + 1] & 0xff) == 0x80 || (lastB[i + 1] & 0xff) == 0x81 ||
                                    (lastB[i + 1] & 0xff) == 0x82 || (lastB[i + 1] & 0xff) ==
                                    0x83) {
                                // TODO: 2018/7/2
                                //可能当前正好等于睡眠位置，128、129、130、131，此时排查其之前的位置是否为128、129、130、131，
                                //可能会出现index和数据相等的情况，最极限的情况是，index=多个位置，而现有的逻辑只取了最先出现的那个位置，
                                //这样就会造成计算sum有误
                                //说明是睡眠包
                                length = i + 3;
                                if (IS_DEBUG)
                                    Log.e(TAG, "***是睡眠包，最后一包有数据的长度为***" + length);
                            } else {
                                //说明是计步包
                                length = i + 3;
                                if (IS_DEBUG)
                                    Log.e(TAG, "***是计步包，最后一包有数据的长度为***" + length);
                            }
                            break;
                        }

                    } else {

                    }
                }
                if (length == 0) {
                    //会存在临界点问题，手机时间已经到index 204 历史数据返回可能还在203
                    for (int i = lastB.length - 1; i >= 0; i--) {
                        if (Utils.byteToInt(lastB[i]) == index - 1) {
                            //这是最后一包的index
                            if (i == lastB.length - 1) {
                                length = 4;
                                break;
                            } else {
                                if ((lastB[i + 1] & 0xff) == 0x80 || (lastB[i + 1] & 0xff) == 0x81 ||
                                        (lastB[i + 1] & 0xff) == 0x82 || (lastB[i + 1] & 0xff) ==
                                        0x83) {
                                    //说明是睡眠包
                                    length = i + 3;
                                    if (IS_DEBUG)
                                        Log.e(TAG, "***是睡眠包，最后一包有数据的长度为***" + length);
                                } else {
                                    //说明是计步包
                                    length = i + 3;
                                    if (IS_DEBUG)
                                        Log.e(TAG, "***是计步包，最后一包有数据的长度为***" + length);
                                }
                                break;
                            }
                        }
                    }

                    if (length == 0) {
                        //适配出现两个index可能没有存储的情况
                        for (int i = lastB.length - 1; i >= 0; i--) {
                            if (Utils.byteToInt(lastB[i]) == index - 2) {
                                //这是最后一包的index
                                if (i == lastB.length - 1) {
                                    length = 4;
                                    break;
                                } else {
                                    if ((lastB[i + 1] & 0xff) == 0x80 || (lastB[i + 1] & 0xff) == 0x81 ||
                                            (lastB[i + 1] & 0xff) == 0x82 || (lastB[i + 1] & 0xff) ==
                                            0x83) {
                                        //说明是睡眠包
                                        length = i + 3;
                                        if (IS_DEBUG)
                                            Log.e(TAG, "***是睡眠包，最后一包有数据的长度为***" + length);
                                    } else {
                                        //说明是计步包
                                        length = i + 3;
                                        if (IS_DEBUG)
                                            Log.e(TAG, "***是计步包，最后一包有数据的长度为***" + length);
                                    }
                                }
                                break;
                            }
                        }
                    }
                }

                if (length == 0) {
                    //如果没有那么只能在倒数第二包去找这个index了
                    lastB = tempCache.get(tempCache.size() - 2);
                    isLast = false;
                    for (int i = lastB.length - 1; i >= 0; i--) {
                        if (Utils.byteToInt(lastB[i]) == index) {
                            if (i == lastB.length - 1) {
                                length = 4;
                                break;
                            } else {
                                //这是最后一包的index
                                if ((lastB[i + 1] & 0xff) == 0x80 || (lastB[i + 1] & 0xff) == 0x81 ||
                                        (lastB[i + 1] & 0xff) == 0x82 || (lastB[i + 1] & 0xff) ==
                                        0x83) {
                                    //说明是睡眠包
                                    length = i + 3;
                                    if (IS_DEBUG)
                                        Log.e(TAG, "***是睡眠包，最后一包有数据的长度为***" + length);
                                } else {
                                    //说明是计步包
                                    length = i + 3;
                                    if (IS_DEBUG)
                                        Log.e(TAG, "***是计步包，最后一包有数据的长度为***" + length);
                                }
                                break;
                            }
                        }
                    }
                    if (length == 0) {
                        //会存在临界点问题，手机时间已经到index 204 历史数据返回可能还在203
                        for (int i = lastB.length - 1; i >= 0; i--) {
                            if (Utils.byteToInt(lastB[i]) == index - 1) {
                                //这是最后一包的index
                                if (i == lastB.length - 1) {
                                    length = 4;
                                    break;
                                } else {
                                    if ((lastB[i + 1] & 0xff) == 0x80 || (lastB[i + 1] & 0xff) == 0x81 ||
                                            (lastB[i + 1] & 0xff) == 0x82 || (lastB[i + 1] & 0xff) ==
                                            0x83) {
                                        //说明是睡眠包
                                        length = i + 3;
                                        if (IS_DEBUG)
                                            Log.e(TAG, "***是睡眠包，最后一包有数据的长度为***" + length);
                                    } else {
                                        //说明是计步包
                                        length = i + 3;
                                        if (IS_DEBUG)
                                            Log.e(TAG, "***是计步包，最后一包有数据的长度为***" + length);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
                if (len <= ((tempCache.size() - (isLast ? 4 : 5)) * 20) + length) {
                    if (IS_DEBUG)
                        //  Log.e(TAG, "***当天相等***");
                        Log.e(TAG, "***当天相等***" + ((tempCache.size() - (isLast ? 4 : 5)) * 20) + "******" + len + "length" + length);
                    if (IS_DEBUG) {
                            /*if (logBuilder == null)
                                logBuilder = new StringBuilder();
                            logBuilder.append(DateUtil.dataToString(new Date(), "MM/dd HH:mm:ss") + " 设备第三包返回总byte数 " +
                                    len +
                                    " 相等 " +
                                    " App运算得到的总byte数 " + (((tempCache.size() - (isLast ? 4 : 5)) * 20) +
                                    length)).append("\r\n");*/

                    }
                    final List<byte[]> listCacheTp = new ArrayList<>();
                    listCacheTp.addAll(tempCache);
                    ThreadPoolUtils.getInstance().addTask(new Runnable() {
                        @Override
                        public void run() {
                            if (IS_DEBUG)
                                Log.e(TAG, "***进了11111***");
                            if (listener != null) {
                                ((W311BluetoothListener) listener).onSyncCompte();
                            }
                            processData(listCacheTp, deviceName);
                                    /*ParserData.processData(context, gatt.getDevice().getAddress(),
                                            callback, listCacheTp, dtype);*/
                        }
                    });
                } else {
                    //sync error
                    Log.e(TAG, ((tempCache.size() - (isLast ? 4 : 5)) * 20) + length + "***当天不相等***" + len + "---lenth" + length);
                    isError = syncFinishOrError(mCurrentYear, mCurrentMonth, mCurrentDay, 0);
//                                    isError = true;
                }
            } else {
                //最后一包是补的0,取倒数第二包
                lastB = tempCache.get(tempCache.size() - 2);
                for (int i = lastB.length - 1; i >= 0; i--) {
                    if (Utils.byteToInt(lastB[i]) == index) {
                        //这是最后一包的index
                        if (i == lastB.length - 1) {
                            length = i + 4;
                            break;
                        } else {
                            if ((lastB[i + 1] & 0xff) == 0x80 || (lastB[i + 1] & 0xff) == 0x81 ||
                                    (lastB[i + 1] & 0xff) == 0x82 || (lastB[i + 1] & 0xff) ==
                                    0x83) {
                                //说明是睡眠包
                                length = i + 3;
                                if (IS_DEBUG)
                                    Log.e(TAG, "***是睡眠包，最后一包有数据的长度为***" + length);
                            } else {
                                //说明是计步包
                                length = i + 3;
                                if (IS_DEBUG)
                                    Log.e(TAG, "***是计步包，最后一包有数据的长度为***" + length);
                            }
                        }
                        break;
                    }
                }
                if (length == 0) {
                    //会存在临界点问题，手机时间已经到index 204 历史数据返回可能还在203
                    for (int i = lastB.length - 1; i >= 0; i--) {
                        if (Utils.byteToInt(lastB[i]) == index - 1) {
                            //这是最后一包的index
                            if (i == lastB.length - 1) {
                                length = i + 4;
                                break;
                            } else {
                                if ((lastB[i + 1] & 0xff) == 0x80 || (lastB[i + 1] & 0xff) == 0x81 ||
                                        (lastB[i + 1] & 0xff) == 0x82 || (lastB[i + 1] & 0xff) ==
                                        0x83) {
                                    //说明是睡眠包
                                    length = i + 3;
                                    if (IS_DEBUG)
                                        Log.e(TAG, "***是睡眠包，最后一包有数据的长度为***" + length);
                                } else {
                                    //说明是计步包
                                    length = i + 3;
                                    if (IS_DEBUG)
                                        Log.e(TAG, "***是计步包，最后一包有数据的长度为***" + length);
                                }
                                break;
                            }
                        }
                    }
                }

                if (len <= ((tempCache.size() - 5) * 20) + length) {
                    if (IS_DEBUG)
                        Log.e(TAG, "***当天相等***" + ((tempCache.size() - 5) * 20) + length + "******" + len + "length" + length);

                    final List<byte[]> listCacheTp = new ArrayList<>();
                    listCacheTp.addAll(tempCache);
                    ThreadPoolUtils.getInstance().addTask(new Runnable() {
                        @Override
                        public void run() {
                            if (listener != null) {
                                ((W311BluetoothListener) listener).onSyncCompte();
                            }
                            processData(listCacheTp, deviceName);
                                  /*  ParserData.processData(context, gatt.getDevice().getAddress(),
                                            callback, listCacheTp, dtype);*/
                        }
                    });
                } else {
                    //sync error
                    if (IS_DEBUG)
                        Log.e(TAG, ((tempCache.size() - 5) * 20) + length + "***当天不相等***" + len + "length" + length);
                    isError = syncFinishOrError(mCurrentYear, mCurrentMonth, mCurrentDay, 0);

//                                    isError = true;
                }


            }
        }
    }

    /**
     * 处理其他天的数据
     *
     * @param
     * @return
     */
    private static void dealOtherDayData(List<byte[]> tempCache, int len, byte[] lastB, BluetoothListener listener, String deviceName) {
        int length = 0;
        boolean isLast = false;
        for (int i = 0; i < lastB.length; i++) {
            if ((lastB[i] & 0xff) != 0x00) {
                isLast = true;
                break;
            }
        }
        if (isLast) {
            //请求的历史数据，判断最后一把数据的结尾位置
            for (int i = lastB.length - 1; i >= 0; i--) {
                if ((lastB[i] & 0xff) == 0x20) {
                    Log.e(TAG, "***最后一包包含20***");
                    //这是最后一包的index
                    if (i == lastB.length - 1) {
                        length = i + 3;
                    } else {
                        if ((lastB[i + 1] & 0xff) == 0x80 || (lastB[i + 1] & 0xff) == 0x81 ||
                                (lastB[i + 1] & 0xff) == 0x82 || (lastB[i + 1] & 0xff) == 0x83) {
                            //说明是睡眠包
                            length = i + 2;
                            if (IS_DEBUG)
                                Log.e(TAG, "***是睡眠包，最后一包有数据的长度为***" + length);
                        } else {
                            //说明是计步包
                            length = i + 3;
                            if (IS_DEBUG)
                                Log.e(TAG, "***是计步包，最后一包有数据的长度为***" + length);
                        }
                    }
                    break;
                }
                if ((lastB[i] & 0xff) == 0x21) {
                    length = i + 3;
                    break;
                }
            }
            if (length == 0) {
                //倒数第一包没有index 20
                Log.e(TAG, "***倒数第二包包含20***");
                lastB = tempCache.get(tempCache.size() - 2);
                isLast = false;
                for (int i = lastB.length - 1; i >= 0; i--) {
                    if ((lastB[i] & 0xff) == 0x20) {
                        //这是最后一包的index
                        if (i == lastB.length - 1) {
                            Log.e(TAG, "***倒数第二包包含20，最后一个index是20***");
                            //虽然是倒数第二包，但是如果最后一个index为20说明是最后一包，
                            // 但是并不能确定最后一包是睡眠还是计步，所以不能直接+3，应该还要判断
                            byte[] lastC = tempCache.get(tempCache.size() - 1);
                            if ((lastC[0] & 0xff) == 0x80 || (lastC[0] & 0xff) == 0x81 ||
                                    (lastC[0] & 0xff) == 0x82 || (lastC[0] & 0xff) == 0x83) {
                                //说明是睡眠包
                                length = i + 2;
                            } else {
                                //否则是计步包
                                length = i + 3;
                            }
                        } else {
                            Log.e(TAG, "***倒数第二包包含20，最后一个index不是20，需要获取后一个index的数据来判断是睡眠还是计步***");
                            if ((lastB[i + 1] & 0xff) == 0x80 || (lastB[i + 1] & 0xff) == 0x81 ||
                                    (lastB[i + 1] & 0xff) == 0x82 || (lastB[i + 1] & 0xff) == 0x83) {
                                //说明是睡眠包
                                length = i + 2;
                                if (IS_DEBUG)
                                    Log.e(TAG, "***是睡眠包，最后一包有数据的长度为***" + length);
                            } else {
                                //说明是计步包
                                length = i + 3;
                                if (IS_DEBUG)
                                    Log.e(TAG, "***是计步包，最后一包有数据的长度为***" + length);
                            }
                        }
                        break;
                    }

                    if ((lastB[i] & 0xff) == 0x21) {
                        length = i + 3;
                        break;
                    }
                }
            }
            if (len <= ((tempCache.size() - (isLast ? 4 : 5)) * 20 + length)) {
                if (IS_DEBUG)
                    //  Log.e(TAG, "***历史相等***" + len);
                    Log.e(TAG, "***历史相等***" + (tempCache.size() - (isLast ? 4 : 5)) * 20 + length + "******" + len + "length" + length);

                final List<byte[]> listCacheTp = new ArrayList<>();
                listCacheTp.addAll(tempCache);
                ThreadPoolUtils.getInstance().addTask(new Runnable() {
                    @Override
                    public void run() {
                        processData(listCacheTp, deviceName);
                        nextDay(listener, mCurrentYear, mCurrentMonth, mCurrentDay);
                    }
                });
            } else {
                //sync error
                if (IS_DEBUG)
                    Log.e(TAG, ((tempCache.size() - 4) * 20 + length) + "***历史不相等000***" + len);
                isError = syncFinishOrError(mCurrentYear, mCurrentMonth, mCurrentDay, 0);

//                                    isError = true;
            }
        } else {
            lastB = tempCache.get(tempCache.size() - 2);
            //请求的历史数据，判断最后一把数据的结尾位置
            for (int i = lastB.length - 1; i >= 0; i--) {
                if ((lastB[i] & 0xff) == 0x20) {
                    //这是最后一包的index
                    if (i == lastB.length - 1) {
                        length = i + 3;
                    } else {
                        if ((lastB[i + 1] & 0xff) == 0x80 || (lastB[i + 1] & 0xff) == 0x81 ||
                                (lastB[i + 1] & 0xff) == 0x82 || (lastB[i + 1] & 0xff) == 0x83) {
                            //说明是睡眠包
                            length = i + 2;
                            if (IS_DEBUG)
                                Log.e(TAG, "***是睡眠包，最后一包有数据的长度为***" + length);
                        } else {
                            //说明是计步包
                            length = i + 3;
                            if (IS_DEBUG)
                                Log.e(TAG, "***是计步包，最后一包有数据的长度为***" + length);
                        }
                    }
                    break;
                }
                if ((lastB[i] & 0xff) == 0x21) {
                    length = i + 3;
                }
            }
            if (len <= ((tempCache.size() - 5) * 20 + length)) {
                if (IS_DEBUG)
                    Log.e(TAG, "***历史相等***" + len);
                Log.e(TAG, "***历史相等***" + (tempCache.size() - 5) * 20 + length + "******" + len + "length" + length);

                final List<byte[]> listCacheTp = new ArrayList<>();
                listCacheTp.addAll(tempCache);
                ThreadPoolUtils.getInstance().addTask(new Runnable() {
                    @Override
                    public void run() {
                        processData(listCacheTp, deviceName);
                        nextDay(listener, mCurrentYear, mCurrentMonth, mCurrentDay);
                                   /* ParserData.processData(context, gatt.getDevice().getAddress(),
                                            callback, listCacheTp, dtype);*/
                    }
                });
            } else {
                //sync error
                if (IS_DEBUG)
                    Log.e(TAG, ((tempCache.size() - 5) * 20 + length) + "***历史不相等1111***" + len);
                isError = syncFinishOrError(mCurrentYear, mCurrentMonth, mCurrentDay, 0);
//                                    isError = true;
            }
        }
    }

    private static synchronized int checkSum(List<byte[]> mCache, BluetoothListener listener, String deviceName) {


        boolean isCurrentDay;

        if (mCache.size() > 0) {
            byte[] data = (byte[]) mCache.get(0);
            mCurrentYear = byteArrayToInt(new byte[]{data[4], data[5]});
            mCurrentMonth = byteArrayToInt(new byte[]{data[6]});
            mCurrentDay = byteArrayToInt(new byte[]{data[7]});
        }
        List<byte[]> tempCache = new ArrayList<>();
        tempCache.addAll(mCache);
        try {
            byte[] bst = tempCache.get(2);
           /* int a1 = (bst[9] & 0xff) <<8;
            int a2 = (bst[10] & 0xff);*/
            int len = ((bst[9] & 0xff) << 8) + (bst[10] & 0xff);
            Log.e("checkSum", "checkSum len" + len);
            //第三包9 10位返回byte总数
            byte[] lastB = tempCache.get(tempCache.size() - 1);
            //判断是否请求的历史
            Calendar instance = Calendar.getInstance();
            if (mCurrentYear == instance.get(Calendar.YEAR) && mCurrentMonth == instance.get
                    (Calendar.MONTH) + 1 && mCurrentDay == instance.get(Calendar.DAY_OF_MONTH)) {
                dealCurrentDay(tempCache, len, lastB, listener, deviceName);
            } else {
                dealOtherDayData(tempCache, len, lastB, listener, deviceName);
            }

        } catch (Exception e) {
            if (IS_DEBUG)
                Log.e(TAG, e.toString());
            isError = syncFinishOrError(mCurrentYear, mCurrentMonth, mCurrentDay, 0);
        } finally {
            return isError;
        }

    }

    public static int syncFinishOrError(int year, int month, int day, int type) {

        return 0;
    }


    public static void processData(List<byte[]> tp, String deviceName) {


        ArrayList<HistorySportN> listdata = new ArrayList();
        // TODO: 2018/3/17 针对301H 307H由于数据存储是replace的，所以可以在处理历史数据时，在产生第一个睡眠数据时操作前8个数据未固定值
        //而对于实时产生的数据，将如何处理呢？
        /*List list = new ArrayList();
        if(tp != null){
            list.addAll(tp);
        }*/

        try {
            if (tp == null || tp.size() == 0) {
            }
            List<byte[]> list = tp;

            if (list != null && list.size() >= 3) {
                byte[] data = (byte[]) list.get(0);
                int year = byteArrayToInt(new byte[]{data[4], data[5]});
                int mon = byteArrayToInt(new byte[]{data[6]});
                int day = byteArrayToInt(new byte[]{data[7]});
                int totalStep = byteArrayToInt(new byte[]{data[8], data[9], data[10], data[11]});


//            BaseController.saveLog(new StringBuilder(DateUtil.dataToString(new Date(), "MM/dd HH:mm:ss") + "
// 第一包返回总数: "+totalStep +"\r\n"));
                int totalCaloric = byteArrayToInt(new byte[]{data[12], data[13], data[14], data[15]});
                float totalDist = byteArrayToInt(new byte[]{data[16], data[17]}) / 100.0f;// 带两位小数的千米单位

                if (IS_DEBUG)
                    Log.e(TAG, year + "-" + mon + "-" + day + "***总数***" + totalStep + "totalDist=" + totalDist);

                int totalSportTime = byteArrayToInt(new byte[]{data[18], data[19]});
                data = (byte[]) list.get(1);
                int totalSleepTime = byteArrayToInt(new byte[]{data[0], data[1]});
                int totalStillTime = byteArrayToInt(new byte[]{data[2], data[3]});
                int walkTime = byteArrayToInt(new byte[]{data[4], data[5]});
                int lowSpeedWalkTime = byteArrayToInt(new byte[]{data[6], data[7]});
                int midSpeedWalkTime = byteArrayToInt(new byte[]{data[8], data[9]});
                int larSpeedWalkTime = byteArrayToInt(new byte[]{data[10], data[11]});
                int lowSpeedRunTime = byteArrayToInt(new byte[]{data[12], data[13]});
                int midSpeedRunTime = byteArrayToInt(new byte[]{data[14], data[15]});
                int larSpeedRunTime = byteArrayToInt(new byte[]{data[16], data[17]});
                data = (byte[]) list.get(2);
                float strideLen = byteArrayToInt(new byte[]{data[0], data[1]}) / 100.0f;
                float weight = byteArrayToInt(new byte[]{data[2], data[3]}) / 100.0f;
                int targetStep = byteArrayToInt(new byte[]{data[4], data[5], data[6]});
                int targetSleepTime = byteArrayToInt(new byte[]{data[7], data[8]});
                String dataString = String.format("%04d", year) + "-" + String.format("%02d", mon) + "-" + String.format
                        ("%02d", day);


                if (IS_DEBUG)
                    Log.e(TAG, dataString + "---同步时更新实时----" + totalStep);

                int totalByteCount = byteArrayToInt(new byte[]{data[9], data[10]});
                if (IS_DEBUG)
                    Log.e(TAG, "---totalByteCount----" + totalByteCount);
                data = new byte[(list.size() - 3) * 20];
                for (int i = 3; i < list.size(); i++) {
                    byte[] tmp = (byte[]) list.get(i);
                    if (tmp != null) {
                        System.arraycopy(tmp, 0, data, (i - 3) * 20, 20);
                    }
                }
                /**
                 * jiao yan
                 */
                if (IS_DEBUG)
                    Log.e(TAG, "---totalByteCount----" + totalByteCount + "----data.length---" + data.length);
                if (totalByteCount > data.length) {
                    /*if (callback != null) {
                        callback.syncState(BaseController.STATE_SYNC_ERROR);
                    }*/
                } else {
                    int totalStepLog = 0;
                    int length = data.length;
                    Calendar calendar = Calendar.getInstance();
                    String todayStr = DateUtil.dataToString(calendar.getTime(), "yyyy-MM-dd");
                    calendar.set(year, mon - 1, day, 0, 0, 0);
                    String dateHist = DateUtil.dataToString(calendar.getTime(), "yyyy-MM-dd");
                    int i = 0;
                    int index = 0;
                    int lastFlag = -1;
                    //  DbHistorySport.getInstance(context).beginTransaction();
                    boolean hasData = false;
                    boolean has12Data = false;
                    try {
                        boolean isToday = todayStr.equals(dateHist);///
                        int mmmm = 0;
                        int nnnn = 0;
                        while (i < totalByteCount) {
                            nnnn++;
                            if (isToday) {
                                if (i + 2 >= totalByteCount)
                                    break;
                            } else {
                                /*修復最後一個五分鐘數據是睡眠數據不解析的情況*/
                                if (i + 1 >= totalByteCount)
                                    break;
                                int tttttpflag = Utils.byteToInt(data[i + 1]) & 0x0080;
                                if (tttttpflag == 0x80) {
                                    if (i + 1 >= totalByteCount)
                                        break;
                                } else {
                                    if (i + 2 >= totalByteCount)
                                        break;
                                }
                            }
                            mmmm++;

                            int tIndex = Utils.byteToInt(data[i]);//当前的序号
                            int dv = 1;
                            if (tIndex - index > 0) {
                                dv = tIndex - index;
                            } else if (tIndex - index < 0) {
                                dv = Utils.byteToInt((byte) 0xff) - index + tIndex;
                            }
                            int flag = Utils.byteToInt(data[i + 1]) & 0x0080;
                            // HistorySport sport = null;
                            if (IS_DEBUG)
                                Log.e(TAG, "---flag----" + flag);
                            if (flag == 0x80) {//sleep data
                                byte[] tdp = {data[i + 1]};
                                int sleepState = Utils.byteToInt(tdp[0]);
                                Log.e(TAG, sleepState + "***进了888888888***sleepState" + sleepState + "dv:" + dv);
                                for (int j = 1; j <= dv; j++) {
                                    String dateString = DateUtil.dataToString(calendar.getTime(), "yyyy-MM-dd HH:mm");
                                    if (IS_DEBUG)
                                        Log.e(TAG, sleepState + "***进了888888888***" + dateString);
                                    String detail = DateUtil.dataToString(calendar.getTime(), "HH:mm");
                                    if (IS_DEBUG)
                                        Log.e(TAG, dateString + "***当前时间***" + detail);

                                   /* HistorySport historySport = DbHistorySport.getInstance(context).
                                            findFirst(DbHistorySport.COLUMN_DATE + "=? and " + DbHistorySport.COLUMN_MAC
                                                    + "=?", new String[]{dateString, mac});
                                    if (historySport != null) {
                                        historySport.setSleepState(sleepState);
                                        historySport.setStepNum(0);
                                        historySport.setMac(mac);
                                    } else {
                                        historySport = new HistorySport(mac, dateString, 0, sleepState);
                                    }
                                    histList.add(historySport);*/
                                    HistorySportN historySportN = new HistorySportN("", dateString, 0, sleepState, 0, 0);
                                    listdata.add(historySportN);
                                    Log.e("sleepdata", "datestring = " + dateString + " , state = " + sleepState);
                                    calendar.add(Calendar.MINUTE, 5);
                                }

                                if (isToday) {
                                    i = i + 3;
                                } else {
                                    i = i + 2;
                                }
                            } else if (flag == 0) {//step data
                                byte[] tdp = {data[i + 1], data[i + 2]};//
                                int step = byteArrayToInt(tdp);
                                for (int j = 1; j <= dv; j++) {
                                    String dateString = DateUtil.dataToString(calendar.getTime(), "yyyy-MM-dd HH:mm");
                                    /*HistorySport historySport = DbHistorySport.getInstance(context).
                                            findFirst(DbHistorySport.COLUMN_DATE + "=? and " + DbHistorySport.COLUMN_MAC
                                                    + "=?", new String[]{dateString, mac});*/
                                    // Log.e(TAG, dateString + "***step***" + step);
                                        /*if (historySport != null) {
                                            historySport.setStepNum(step);
                                            historySport.setSleepState(0);
                                            historySport.setMac(mac);
                                            histList.add(historySport);
                                        } else {
                                            histList.add(new HistorySport(mac, dateString, step, 0));
                                        }*/
                                    HistorySportN historySportStep = new HistorySportN("", dateString, step, 0, 0, 0);
                                    listdata.add(historySportStep);
                                    totalStepLog += step;
//                                BaseController.saveLog(new StringBuilder(DateUtil.dataToString(new Date(), "MM/dd
// HH:mm:ss ") + dateString+" 步数: "+step +"\r\n"));
                                    Log.e("sleepdata", "datestring = " + dateString + " , state = " + step);
                                    calendar.add(Calendar.MINUTE, 5);
                                }
                                i = i + 3;
                            }
                            index = tIndex;
                            lastFlag = flag;
                        }
                        //DbHistorySport.getInstance(context).setTransactionSuccessful();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        // DbHistorySport.getInstance(context).endTransaction();
                    }
                    if (listdata != null && listdata.size() > 0) {
                        saveW307JData(listdata, deviceName, dateHist, totalStep, totalDist, totalCaloric, totalSleepTime);
                    }
                }
            } else {
            }


        } catch (Exception e) {

        }


    }


    public static void saveW307JData(ArrayList<HistorySportN> listdata, String deviceName, String dateStr, int totalStep, float totalDist, int totalCaloric, int sleepTime) {
        ArrayList<Integer> stepList = new ArrayList<>();
        ArrayList<Integer> sleepList = new ArrayList<>();
        Gson gson = new Gson();


        Bracelet_W311_24HDataModel bracelet_w311_24HDataModel = new Bracelet_W311_24HDataModel();
        bracelet_w311_24HDataModel.setUserId(BaseManager.mUserId);
        bracelet_w311_24HDataModel.setDeviceId(deviceName);

        Logger.myLog("BaseManager.mUserId" + BaseManager.mUserId + "baseDevice.deviceName:" + deviceName);
        bracelet_w311_24HDataModel.setDateStr(dateStr);
        bracelet_w311_24HDataModel.setTimestamp(System.currentTimeMillis());
        bracelet_w311_24HDataModel.setTotalSteps(totalStep);
        bracelet_w311_24HDataModel.setTotalDistance(totalDist);
        bracelet_w311_24HDataModel.setTotalCalories(totalCaloric);

        bracelet_w311_24HDataModel.setHasHR(WatchData.NO_HR);
        bracelet_w311_24HDataModel.setHasSleep(WatchData.NO_SLEEP);
        bracelet_w311_24HDataModel.setTotalSleepTime(sleepTime + "");


        //睡眠时长


        int state = 0;
        int step = 0;
        for (int j = 0; j < listdata.size(); j++) {
            state = listdata.get(j).getSleepState();
            step = listdata.get(j).getStepNum();
            if (state == 0) {
                //步数
                stepList.add(step);
                stepList.add(0);
                stepList.add(0);
                stepList.add(0);
                stepList.add(0);
                sleepList.add(0);
                sleepList.add(0);
                sleepList.add(0);
                sleepList.add(0);
                sleepList.add(0);
            } else {
                switch (state) {
                    //深睡
                    case 128:
                        state = 253;
                        break;
                    // 浅睡
                    case 129:
                        state = 252;
                        break;
                    //极浅睡
                    case 130:
                        state = 251;
                        break;
                    //清醒
                    case 131:
                        state = 250;
                        break;
                }
                sleepList.add(state);
                sleepList.add(state);
                sleepList.add(state);
                sleepList.add(state);
                sleepList.add(state);
                stepList.add(0);
                stepList.add(0);
                stepList.add(0);
                stepList.add(0);
                stepList.add(0);

            }
        }

        if (sleepList.size() != 1440) {

        }

        bracelet_w311_24HDataModel.setStepArray(gson.toJson(stepList));
        bracelet_w311_24HDataModel.setSleepArray(gson.toJson(sleepList));
        //区分是否有睡眠数据
        if (Collections.max(sleepList) >= 128) {
            bracelet_w311_24HDataModel.setHasSleep(WatchData.HAS_SLEEP);
        } else {
            bracelet_w311_24HDataModel.setHasSleep(WatchData.NO_SLEEP);
        }
        bracelet_w311_24HDataModel.setReportId("0");
        //bracelet_w311_24HDataModel.setHrArray(gson.toJson(heartRateList));
        Logger.myLog("获取到的时间" + dateStr + "bracelet_w311_24HDataModel=" + bracelet_w311_24HDataModel);
        BleSPUtils.putString(BaseManager.mContext, BleSPUtils.Bracelet_LAST_SYNCTIME, dateStr);
        Bracelet_W311_24HDataModelAction.saveOrUpdateBracelet24HDataModel(bracelet_w311_24HDataModel, deviceName);
    }

}
