package com.home.admin.base.util;

import com.home.admin.base.wol.WOLNode;

/**
 * 设别开启关闭工具类
 */
public class WOLUtil {

    /**
     * 唤醒设备
     * @param macAddress
     * @throws Exception
     */
    public static void wakeUpDevice(String macAddress) throws Exception{
        WOLNode node = new WOLNode(macAddress);
        node.wakeUP();
    }

    /**
     * 关闭设备
     * @param ipAddress
     * @throws Exception
     */
    public static void closeDevice(String ipAddress) throws Exception {
        Runtime r = Runtime.getRuntime();
        String command = "shutdown -s -t 0 -f -m " + ipAddress;
        r.exec(command);
    }

}
