package com.home.admin.base.tasks;

import com.home.admin.base.enums.DeviceStatus;
import com.home.admin.base.service.DeviceService;
import com.home.admin.base.util.ValueUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 设备状态检测定时任务
 */
@Configuration
@EnableScheduling
@Slf4j
public class CheckStatusTask {

    @Autowired
    private DeviceService deviceService;

    /**
     * 检测设备状态并更新
     */
    @Scheduled(fixedRate = 2000)
    private void checkStatus() {
        try {
            List<Map<String, Object>> deviceList = deviceService.getAllDevices();
            for (Map device : deviceList) {
                Integer deviceId = ValueUtils.safeToInteger(device.get("id"),  0);
                String deviceIpAddr = ValueUtils.safeToString(device.get("ip"), "");
                Integer deviceStatus = ValueUtils.safeToInteger(device.get("status"), 0);
                Integer currentDeviceStatus = DeviceStatus.CLOSED.value;
                boolean isOpened = ping(deviceIpAddr, 1, 100);
                if (isOpened) {
                    if (deviceStatus == DeviceStatus.CLOSING.value) {
                        currentDeviceStatus = DeviceStatus.CLOSING.value;
                    }else {
                        currentDeviceStatus = DeviceStatus.OPENED.value;
                    }
                } else {
                    if (deviceStatus == DeviceStatus.OPENING.value) {
                        currentDeviceStatus = DeviceStatus.OPENING.value;
                    } else {
                        currentDeviceStatus = DeviceStatus.CLOSED.value;
                    }
                }
                if (deviceStatus != currentDeviceStatus) {
                    deviceService.updateDeviceStatus(deviceId, currentDeviceStatus);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用ping命令检测目标主机是否开启
     * @param ipAddress
     * @param pingTimes
     * @param timeOut
     * @return
     */
    public boolean ping(String ipAddress, int pingTimes, int timeOut) {
        BufferedReader in = null;
        Runtime r = Runtime.getRuntime();
        String pingCommand = "ping " + ipAddress + " -n " + pingTimes    + " -w " + timeOut;
        try {
            Process p = r.exec(pingCommand);
            if (p == null) {
                return false;
            }
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            int connectedCount = 0;
            String line = null;
            while ((line = in.readLine()) != null) {
                connectedCount += getCheckResult(line);
            }
            return connectedCount == pingTimes;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 解析ping返回的结果
     * @param line
     * @return
     */
    private static int getCheckResult(String line) {
        Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)",    Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            return 1;
        }
        return 0;
    }

}
