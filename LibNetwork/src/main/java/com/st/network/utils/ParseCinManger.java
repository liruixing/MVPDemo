package com.st.network.utils;

import com.st.network.IOSetting.CINCallBack;
import com.st.network.IOSetting.CIn;
import com.st.network.IOSetting.CInType;

/**
 * Created by 18895.
 * Date: 2019/7/14 14:56
 * describle:
 */
public class ParseCinManger {

    public static final String zeroStr = "0000000000";
    private static CInType _InType = CInType.NONE;
    private static int _InPort = -1;
    private static int _Station = 0;
    private static int portCount = 0;
    private static String _Rfids = "";

    /**
     * 重置数据
     */
    private static void resetData() {
        _InType = CInType.NONE;
        _InPort = -1;
        portCount = 0;
        _Station = 0;
        _Rfids = "";
    }

    /**
     * 解析数据
     *
     * @param bytesCommand
     * @param ip
     * @param cinCallBack
     */
    public static synchronized void prase(byte[] bytesCommand, String ip, CINCallBack cinCallBack) {
        resetData();
        if (bytesCommand.length == 18) {// IN指令 光电
            if (bytesCommand[16] == 0x03) {
                _InType = CInType.IN_COMMAND;
                _Station = (int) bytesCommand[15];
                _InPort = bytesCommand[13];
                portCount = bytesCommand[14];
                if (cinCallBack != null) {
                    CIn cIn = new CIn(_InType, _InPort, _Station, _Rfids);
                    cIn.setPortCount(portCount);
                    cinCallBack.CINCallBack(cIn, ip);
                    return;
                }
            } else {
                _InType = CInType.NONE;
            }
            //#endregion
        } else if (bytesCommand.length == 28){// RFID指令
            byte[] bytesTemp = new byte[10];
            System.arraycopy(bytesCommand, 13, bytesTemp, 0, 10);
            String sTemp = new String(bytesTemp, 2, 8);
            int nTemp = 0;
            try {
                nTemp = Integer.valueOf(sTemp, 16);
            } catch (NumberFormatException x) {
                x.printStackTrace();
            }
            _Rfids = String.format("%010d", nTemp);

            if (bytesCommand[27] == 0x01) {
                _InType = CInType.RFID_1;
            } else if (bytesCommand[27] == 0x02) {
                _InType = CInType.RFID_2;
            } else if (bytesCommand[27] == 0x03) {
                _InType = CInType.RFID_3;
            } else if (bytesCommand[27] == 0x04) {
                _InType = CInType.RFID_4;
            } else {
                _InType = CInType.NONE;
            }
            _Station = (int) bytesCommand[26];
            if (cinCallBack != null) {
                cinCallBack.CINCallBack(new CIn(_InType, _InPort, _Station,
                        _Rfids), ip);
                return;
            }
            //#endregion
        } else {
            _InType = CInType.NONE;
        }
        if (cinCallBack != null) {
            CIn cin = new CIn(_InType, _InPort, _Station, _Rfids);
            cin.setBytesCommand(bytesCommand.length);
            cin.setBytesCommandStr(toHexString(bytesCommand));
            cinCallBack.CINCallBack(cin, ip);
        }
    }

    /**
     * 字节数组转成16进制表示格式的字符串
     *
     * @param byteArray 需要转换的字节数组
     * @return 16进制表示格式的字符串
     **/
    public static String toHexString(byte[] byteArray) {
        if (byteArray == null || byteArray.length < 1)
            throw new IllegalArgumentException("this byteArray must not be null or empty");

        final StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            if ((byteArray[i] & 0xff) < 0x10)//0~F前面不零
                hexString.append("0");
            hexString.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return hexString.toString().toLowerCase();
    }

    public static int bytesToInt(byte[] src, int offset) {
        int value;
        value = (int) ((src[offset] & 0xFF << 8)
                | ((src[offset + 1] & 0xFF)));
        return value;
    }

    /**
     * 将一个int数字转换为二进制的字符串形式。
     *
     * @param num    需要转换的int类型数据
     * @param digits 要转换的二进制位数，位数不足则在前面补0
     * @return 二进制的字符串形式
     */
    public static String toBinary(int num, int digits) {
        int value = 1 << digits | num;
        String bs = Integer.toBinaryString(value); //0x20 | 这个是为了保证这个string长度是6位数
        return bs.substring(1);
    }

}
