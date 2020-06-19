package com.st.network.IOSetting;

import android.text.TextUtils;

import com.st.network.utils.LogUtil;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by 18895.
 * Date: 2019/12/18 15:37
 * describle:
 */
public class CoutPrase {


    public static byte[] OpenOUT(COut cOut){
        byte[] reCommand = null;
        switch (cOut.getPort()){
            case 31://发送队列
                reCommand = sendListToPLC(cOut);
                break;
            case 32://发送标记队列
                reCommand = sendFlagListToPLC(cOut);
                break;
            case 33://发送延时停止点击时间
                reCommand = sendOutStationToPLC(cOut);
                break;
            case 34://不能下放
                reCommand = sendOutStationToPLC(cOut);
                break;
            case 35://出站延时时间
                reCommand = sendOutStationToPLC(cOut);
                break;
            case 36://一个格子间隔时间
                reCommand = sendOutStationToPLC(cOut);
                break;
            case 37://计算偏移量
                reCommand = sendOutStationToPLC(cOut);
                break;
            case 38://计算偏移量
                reCommand = sendOutStationToPLC(cOut);
                break;
        }
        return reCommand;
    }

    private static byte[] sendOutStationToPLC(COut cOut) {
        String outSetListData = "";
        LogUtil.e("star3","====此时的数据是："+cOut.toString());
        switch (cOut.getPort()){
            case 33:
                outSetListData = OpenCommand.SendOutStation33+cOut.getCommand();
                break;
            case 34:
                outSetListData = OpenCommand.SendOutStation34+cOut.getCommand();
                break;
            case 35:
                outSetListData = OpenCommand.SendOutStation35+cOut.getCommand();
                break;
            case 36:
                outSetListData = OpenCommand.SendOutStation36+cOut.getCommand();
                break;
            case 37:
                outSetListData = OpenCommand.SendOutStation37+cOut.getCommand();
                break;
            case 38:
                outSetListData = OpenCommand.SendOutStation38+cOut.getCommand();
                break;
        }

        LogUtil.e("star3",cOut.getPort()+"sendOutStationToPLC=====发送的数据是："+outSetListData);
        return HexCommandtoByte(outSetListData);
    }

    /**
     * 发送标记队列给PLC
     * @param cOut
     * @return
     */
    private static byte[] sendFlagListToPLC(COut cOut) {
        String outSetListData = "";
        int arraySize = 90;//长度

        String command = "";//FlagListUtil.getFlagListCommand();
        cOut.setCommand(command);

        String[] outCommond = cOut.getCommand().split(" ");
        String[] resultCommond = new String[arraySize];
        if(outCommond.length < arraySize){
            for(int i=0;i<resultCommond.length;i++){
                if(i>=outCommond.length){
                    resultCommond[i] = "00";
                }else{
                    resultCommond[i] = outCommond[i];
                }
            }
        } else {
            resultCommond = outCommond;
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<resultCommond.length;i++){
            sb.append(resultCommond[i]);
            if(i< resultCommond.length-1){
                sb.append(" ");
            }
        }
        outSetListData = OpenCommand.SendFlagList+sb.toString();
        LogUtil.e("star","sendFlagListToPLC=====发送的数据是："+outSetListData);
        return HexCommandtoByte(outSetListData);
    }

    /**
     * 发送入站队列到PLC
     * @param cOut
     * @return
     */
    private static byte[] sendListToPLC(COut cOut) {
        String outSetListData = "";

        String[] outCommond = cOut.getCommand().split(" ");

        int arraySize = 90;//长度

        String[] resultCommond = new String[arraySize];
        if(outCommond.length < arraySize){
            for(int i=0;i<resultCommond.length;i++){
                if(i>=outCommond.length){
                    resultCommond[i] = "00";
                }else{
                    resultCommond[i] = outCommond[i];
                }
            }
        } else {
            resultCommond = outCommond;
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<resultCommond.length;i++){
            sb.append(resultCommond[i]);
            if(i< resultCommond.length-1){
                sb.append(" ");
            }
        }

        String strTou = OpenCommand.SendTargetList[cOut.getIndex()];

        outSetListData = strTou+sb.toString();

        outSetListData = outSetListData.replaceAll("  "," ");
        LogUtil.e("star","sendListToPLC===="+cOut.getIndex()+"=发送的数据是："+outSetListData);

        return HexCommandtoByte(outSetListData);
    }

    public static byte[] OpenOUT(int nOutPort, boolean beOpenOrClose) {
        byte[] retCommand = null;
        switch (nOutPort) {
            case 1:
                if (beOpenOrClose)
                    retCommand = HexCommandtoByte(OpenCommand.Out1);
                else
                    retCommand = HexCommandtoByte(CloseCommand.Out1);
                break;
            case 2:
                if (beOpenOrClose)
                    retCommand = HexCommandtoByte(OpenCommand.Out2);
                else
                    retCommand = HexCommandtoByte(CloseCommand.Out2);
                break;
            case 3:
                if (beOpenOrClose)
                    retCommand = HexCommandtoByte(OpenCommand.Out3);
                else
                    retCommand = HexCommandtoByte(CloseCommand.Out3);
                break;
            case 4:
                if (beOpenOrClose)
                    retCommand = HexCommandtoByte(OpenCommand.Out4);
                else
                    retCommand = HexCommandtoByte(CloseCommand.Out4);
                break;
            case 5:
                if (beOpenOrClose)
                    retCommand = HexCommandtoByte(OpenCommand.Out5);
                else
                    retCommand = HexCommandtoByte(CloseCommand.Out5);
                break;
            case 6:
                if (beOpenOrClose)
                    retCommand = HexCommandtoByte(OpenCommand.Out6);
                else
                    retCommand = HexCommandtoByte(CloseCommand.Out6);
                break;
            case 7:
                if (beOpenOrClose)
                    retCommand = HexCommandtoByte(OpenCommand.Out7);
                else
                    retCommand = HexCommandtoByte(CloseCommand.Out7);
                break;
            case 8:
                if (beOpenOrClose)
                    retCommand = HexCommandtoByte(OpenCommand.Out8);
                else
                    retCommand = HexCommandtoByte(CloseCommand.Out8);
                break;
            case 9:
                if (beOpenOrClose)
                    retCommand = HexCommandtoByte(OpenCommand.Out9);
                else
                    retCommand = HexCommandtoByte(CloseCommand.Out9);
                break;
            case 10:
                if (beOpenOrClose)
                    retCommand = HexCommandtoByte(OpenCommand.Out10);
                else
                    retCommand = HexCommandtoByte(CloseCommand.Out10);
                break;
            case 11:
                if (beOpenOrClose)
                    retCommand = HexCommandtoByte(OpenCommand.Out11);
                else
                    retCommand = HexCommandtoByte(CloseCommand.Out11);
                break;
            case 12:
                if (beOpenOrClose)
                    retCommand = HexCommandtoByte(OpenCommand.Out12);
                else
                    retCommand = HexCommandtoByte(CloseCommand.Out12);
                break;
            case 13:
                retCommand = HexCommandtoByte(OpenCommand.Re_Read_Rfid1);
                break;
            case 14://扫描RFID
//                LogUtil.e("star","==========Re_Read_Rfid2:"+OpenCommand.Re_Read_Rfid2);
                retCommand = HexCommandtoByte(OpenCommand.Re_Read_Rfid2);
                break;
            case 15:
                retCommand = HexCommandtoByte(OpenCommand.Re_Read_Rfid3);
                break;
            case 16:
                retCommand = HexCommandtoByte(OpenCommand.Re_Read_Rfid4);
                break;
            case 18:
                retCommand = HexCommandtoByte(OpenCommand.DisconnectPLC);
                break;
            case 19:
                retCommand = HexCommandtoByte(OpenCommand.InitPLC);
                break;
            case 20:
                retCommand = GetLocalIPAddress();
                break;
        }

        return retCommand;
    }

    private static byte[] HexCommandtoByte(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] strings = str.split(" ");
        int nLength = strings.length;
        byte[] data = new byte[nLength];
        for (int i = 0; i < nLength; i++) {
            if (strings[i].length() != 2) {
                data[i] = 00;
                continue;
            }
            try {
                data[i] = (byte) Integer.parseInt(strings[i], 16);
            } catch (Exception e) {
                data[i] = 00;
                continue;
            }
        }
        return data;
    }

    public static byte[] GetLocalIPAddress() {
        String OutSetPADIP = "00 00 00 00 00 0B 01 10 06 04 00 04 04 ";
        String sIP = getLocalIpAddress();
        try {
            if (sIP != null) {
                //String sFields = sIP.substring(sIP.lastIndexOf(".") + 1);
                String[] sFields = sIP.split("\\.");
                if (sFields.length == 4) {
                    //int nLastIP = Integer.parseInt(sFields);
                    //OutSetPADIP = OutSetPADIP + Integer.toHexString(nLastIP).toUpperCase();

                    OutSetPADIP = OutSetPADIP + String.format("%02X %02X %02X %02X",
                            Integer.parseInt(sFields[0]), Integer.parseInt(sFields[1]),
                            Integer.parseInt(sFields[2]), Integer.parseInt(sFields[3]));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return HexCommandtoByte(OutSetPADIP);
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = (NetworkInterface) en.nextElement();
                for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public interface OpenCommand {
//        String Out1 = "00 00 00 00 00 06 01 05 00 00 FF 00";
//        String Out2 = "00 00 00 00 00 06 01 05 00 01 FF 00";
//        String Out3 = "00 00 00 00 00 06 01 05 00 02 FF 00";
//        String Out4 = "00 00 00 00 00 06 01 05 00 03 FF 00";
//        String Out5 = "00 00 00 00 00 06 01 05 00 04 FF 00";
//        String Out6 = "00 00 00 00 00 06 01 05 00 05 FF 00";
//        String Out7 = "00 00 00 00 00 06 01 05 00 06 FF 00";
//        String Out8 = "00 00 00 00 00 06 01 05 00 07 FF 00";
//        String Out9 = "00 00 00 00 00 06 01 05 00 08 FF 00";
//        String Out10 = "00 00 00 00 00 06 01 05 00 09 FF 00";
//        String Out11 = "00 00 00 00 00 06 01 05 00 0A FF 00";
//        String Out12 = "00 00 00 00 00 06 01 05 00 0B FF 00";
//        String Re_Read_Rfid1 = "00 00 00 00 00 0B 01 10 06 09 00 04 04 AA 00 00 00";
//        String Re_Read_Rfid2 = "00 00 00 00 00 0B 01 10 06 09 00 04 04 00 AA 00 00";
//        String Re_Read_Rfid3 = "00 00 00 00 00 0B 01 10 06 09 00 04 04 00 00 AA 00";
//        String Re_Read_Rfid4 = "00 00 00 00 00 0B 01 10 06 09 00 04 04 00 00 00 AA";
//        String Heartbeat = "00 00 00 00 00 06 01 05 00 FF 00 00";
//        String InitPLC = "00 00 00 00 00 09 01 10 06 07 00 02 02 11 11";
//        String DisconnectPLC = "00 00 00 00 00 09 01 10 06 07 00 02 02 22 22";

        String Out1 = "00 00";
        String Out2 = "00 00";
        String Out3 = "00 00";
        String Out4 = "00 00";
        String Out5 = "00 00";
        String Out6 = "00 00";
        String Out7 = "00 00";
        String Out8 = "00 00";
        String Out9 = "00 00";
        String Out10 = "00 00";
        String Out11 = "00 00";
        String Out12 = "00 00";
        String Re_Read_Rfid1 = "00 00";
        String Re_Read_Rfid2 = "00 00";
        String Re_Read_Rfid3 = "00 00";
        String Re_Read_Rfid4 = "00 00";
        String Heartbeat = "00 00 00 00 00 06 01 05 00 FF 00 00";
        String InitPLC = "00 00 00 00 00 09 01 10 06 07 00 02 02 11 11";
        String DisconnectPLC = "00 00 00 00 00 09 01 10 06 07 00 02 02 22 22";


        String SendTargetList1 = "00 00 00 00 00 61 01 10 14 14 00 5A 5A ";//入站队列 9位（1个衣架）
        String SendTargetList2 = "00 00 00 00 00 61 01 10 14 41 00 5A 5A ";//入站队列 9位（1个衣架）
        String SendTargetList3 = "00 00 00 00 00 61 01 10 14 6E 00 5A 5A ";//入站队列 9位（1个衣架）
        String SendTargetList4 = "00 00 00 00 00 61 01 10 14 8C 00 5A 5A ";//入站队列 9位（1个衣架）
        String SendTargetList5 = "00 00 00 00 00 61 01 10 14 B4 00 5A 5A ";//入站队列 9位（1个衣架）

        String[] SendTargetList = new String[]{SendTargetList1,SendTargetList2,SendTargetList3,SendTargetList4,SendTargetList5};



        String SendFlagList = "00 00 00 00 00 61 01 10 08 5C 00 5A 5A ";//标记队列

        String SendOutStation33 = "00 00 00 00 00 09 01 10 23 C3 00 02 02 ";//出站参数
        String SendOutStation34 = "00 00 00 00 00 09 01 10 23 BE 00 02 02 ";//出站参数
        String SendOutStation35 = "00 00 00 00 00 09 01 10 23 C8 00 02 02 ";//出站参数
        String SendOutStation36 = "00 00 00 00 00 09 01 10 23 CD 00 01 02 ";//出站参数
        String SendOutStation37 = "00 00 00 00 00 09 01 10 23 CE 00 02 02 ";//出站参数


        String SendOutStation38 = "00 00 00 00 00 09 01 10 23 B9 00 02 02 ";//完成事件
    }

    public interface CloseCommand {
        String Out1 = "00 00";
        String Out2 = "00 00";
        String Out3 = "00 00";
        String Out4 = "00 00";
        String Out5 = "00 00";
        String Out6 = "00 00";
        String Out7 = "00 00";
        String Out8 = "00 00";
        String Out9 = "00 00";
        String Out10 = "00 00";
        String Out11 = "00 00";
        String Out12 = "00 00";
        String Out13 = "00 00";
        String Out14 = "00 00";
        String Out15 = "00 00";
        String Out16 = "00 00";
    }

    //延迟时间关闭电机
    private static final String closeDian = "00 00 00 00 00 09 01 10 10 2C 00 02 02 ";

    /**
     * 新增数据接口
     *
     * @param port
     * @param deplayTime
     * @return
     */
    public static byte[] OpenOUT(int port, int deplayTime) {
        switch (port) {
            case 21://自动开关电机
                if (deplayTime > 0) {
                    String sixteen = Integer.toHexString(deplayTime);
                    if (sixteen.length() > 4) {
                        LogUtil.e("sixteen.length() > 4:" + sixteen);
                        return HexCommandtoByte((closeDian + "00 00"));
                    } else {
                        while (true) {
                            switch (sixteen.length()) {
                                case 0:
                                    sixteen = "00 00";
                                    break;
                                case 1:
                                    sixteen = "00 0" + sixteen;
                                    break;
                                case 2:
                                    sixteen = "00 " + sixteen;
                                    break;
                                case 3:
                                    sixteen = "0" + sixteen.substring(0, 1) +
                                            " " + sixteen.substring(1, 3);
                                    break;
                                case 4:
                                    sixteen = sixteen.substring(0, 2) + " " + sixteen.substring(2
                                            , 4);
                                    break;
                            }
                            if (sixteen.length() >= 4) {
                                break;
                            }
                        }
//                        LogUtil.d("sixteen:" + sixteen);
                        return HexCommandtoByte((closeDian + sixteen));
                    }
                } else {
                    LogUtil.e("deplayTime:" + deplayTime);
                    return HexCommandtoByte((closeDian + "00 00"));
                }
        }
        return null;
    }

}
