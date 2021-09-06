package test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin
 * Date 2021/8/23
 */
public class Test {

    public static void main(String[] args) {


        List<byte[]> listByte = new ArrayList<>();

//        byte[] bt1 = new byte[]{ 00 ,01 ,0x5B, 15, 0x08, 17 ,0x0C ,0x2E, 29, 00 ,0x08, 04 ,15 ,0x08, 17 ,0x0C, 36 , (byte) 2D,32 ,01};
//
//        byte[] bt2 = new byte[]{ 00, 0x1F, 00, 00 ,00 , (byte) 0xDC, 00, 00, 00, 0x0E, 00, 00, 00, 00, 00, 00 ,00, 00, 00, 00};
        byte[] bt3 = new byte[]{ 01, 00 ,73 ,0x6F, 77, 78, 71, 77, 0x6B ,0x5C, 0x5A ,0x5A ,58 ,54, 54, 58, 0x5A ,59, 58, 58};
        byte[] bt4 = new byte[]{ 02, 0x58 , (byte) 0xFA, (byte) 0xFA,32, 01, (byte) 0xDC,00, 0x0F ,00, 0x4C, 0x52, 0x51, 51,0x4D, 0x4F,50 ,55 ,52 ,0x4F};
        byte[] bt5 = new byte[]{ 81 ,51, 50, (byte) 0xFA, (byte) 0xFA, 0x00, 00, 00, 00, 00, 00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};


//        listByte.add(bt1);
//        listByte.add(bt2);
        listByte.add(bt3);
        listByte.add(bt4);
        listByte.add(bt5);


        List<Byte> sourceList = new ArrayList<>();

        for(int i = 0;i<listByte.size();i++){
            byte[] currByte = listByte.get(i);

            for(int k = 1;k<currByte.length;k++){
                sourceList.add(currByte[k]);
            }
        }


        List<Integer> heartList = new ArrayList<>();
        List<Integer> stepList = new ArrayList<>();
        List<Integer> distanceList = new ArrayList<>();
        List<Integer> kcalList = new ArrayList<>();


        int position = sourceList.indexOf(0xFA);

        System.out.println("------position="+position);

//        for(int n = 0;n<sourceList.size();n++){
//
//            sourceList.indexOf(255);
//            int currValue = Utils.byte2Int(sourceList.get(n));
//
//            if(currValue == 255 && (Utils.byte2Int(sourceList.get(n+1)) == 255)){
//
//            }
//
//        }



    }
}
