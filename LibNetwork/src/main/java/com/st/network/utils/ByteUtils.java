package com.st.network.utils;

import java.math.BigInteger;

/**
 * create by Dennis
 * on 2019-12-26
 * description：
 **/
public class ByteUtils {

    /**
     * 编码逻辑
     * @param cardNO
     * @return
     */
    public static String encodeRFIDData(String cardNO){
        try{
            int in10 = Integer.parseInt(cardNO,10);
            String hex = Integer.toHexString(in10);
            hex = hex.toUpperCase();
            String preFix = "";
            if(hex.length()<8){
                for(int i=0;i<(8-hex.length());i++){
                    preFix = preFix+"0";
                }
            }
            hex = preFix + hex;
            String result = convertStringToHex(hex);
            result = "08"+result;
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<result.length()/2;i++){
                sb.append(result.substring(i*2,i*2+2));
                if(i<result.length()/2-1){
                    sb.append(" ");
                }
            }
            return sb.toString();
        }catch (Exception e){
            return "";
        }
//        System.out.println("转换后："+sb.toString());
    }

    /**
     * 编码逻辑
     * @param data
     * @return
     */
    public static String encodeDataTo16(String data){
        int in10 = Integer.parseInt(data,10);
        String hex = Integer.toHexString(in10);
        hex = hex.toUpperCase();
        String result = "";
        if(hex.length()==1){
            result = "0"+hex +" 00";
        }else if(hex.length()==2){
            result = hex+" 00";
        }else if(hex.length() == 3){
            result = hex.substring(0,2)+" "+hex.substring(2,hex.length())+"0";
        }else if(hex.length() == 4){
            result = hex.substring(0,2)+" "+hex.substring(2,hex.length());
        }else{
            result = "00 00";
        }

        return result;
    }


    private static String convertStringToHex(String str){

        char[] chars = str.toCharArray();

        StringBuffer hex = new StringBuffer();
        for(int i = 0; i < chars.length; i++){
            hex.append(Integer.toHexString((int)chars[i]));
        }

        return hex.toString();
    }
    private static String convertHexToString(String hex){

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for( int i=0; i<hex.length()-1; i+=2 ){

            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char)decimal);

            temp.append(decimal);
        }

        return sb.toString();
    }

    /**
     * 解码程序逻辑
     * @param data
     * @return
     */
    public static String decodePLCData(String data){
        String result = "";
        String str16 = data.replaceAll(" ","");
        String hex = convertHexToString(str16);

        String intResult = Integer.parseInt(hex,16)+"";

        if(intResult.length()<10){
            for(int i=0;i<(10-intResult.length());i++){
                result = result+"0";
            }
            result = result+intResult;
        }else{
            result = intResult;
        }
        return result;
    }

    /**
     * 十六进制内容转成十进制
     * @param hexs
     * @return
     */
    public static int decodeHEX(String hexs) {
        BigInteger bigint = new BigInteger(hexs, 16);
        int numb = bigint.intValue();
        return numb;
    }

    /**
     * 题外话，我都不知道自己在写啥
     * 算法跳来跳去，担心是否共性的问题
     * @param arg
     */
    public static void main(String[] arg) {
//        String a = "0003932372";
//        String bytes1 = encodeRFIDData(a);//编码得出  范工扫描出来的byte[]
//
//        StringBuilder sb = new StringBuilder();//模拟出站成功，范工回传的byte[]字符串
//        sb.append("30 ").append("30 ").append("30 ").append("30 ").append("33 ").append("43 ").append("30 ").append("30 ").append("44 ").append("34");

//        String result = decodePLCData(sb.toString());//对范工回传消息进行解码 得出最后的字符串

        String result = encodeDataTo16("250");
        System.out.println("_Rfids:" + result);


        String s1 = encodeDataTo16("1000");
        System.out.println("_Rfids:" + s1);

        String s2 = encodeDataTo16("0");
        System.out.println("_Rfids:" + s2);

        String s3 = encodeDataTo16("2208");
        System.out.println("_Rfids:" + s3);
    }
}
