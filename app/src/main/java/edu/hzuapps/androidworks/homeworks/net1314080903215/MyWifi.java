package edu.hzuapps.androidworks.homeworks.net1314080903215;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by Administrator on 2016/4/12.
 */
public class MyWifi {
    private WifiManager wifiManager;
    private WifiInfo wifiInfo;
    private DhcpInfo dhcpInfo;

    public MyWifi(Context context){
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiInfo = wifiManager.getConnectionInfo();
        dhcpInfo = wifiManager.getDhcpInfo();
    }
    //得到本机ip
    public String getLocalIp(){
        return FormatString(dhcpInfo.ipAddress);
    }
    //得到服务器ip（热点ip）
    public String getServerIp(){
        return FormatString(dhcpInfo.serverAddress);
    }
    //转换ip格式为*.*.*.*
    public String FormatString(int value){
        String strValue = "";
        byte[] ary = intToByteArray(value);
        for(int i = ary.length - 1; i>=0; i--){
            strValue += (ary[i]&0xFF);
            if(i>0){
                strValue += ".";
            }
        }
        return strValue;
    }

    private byte[] intToByteArray(int value) {
        byte[] b = new byte[4];
        for(int i=0; i<4; i++){
            int offset = (b.length - 1 - i)*8;
            b[i] = (byte) ((value>>>offset)&0xFF);
        }
        return b;
    }
}
