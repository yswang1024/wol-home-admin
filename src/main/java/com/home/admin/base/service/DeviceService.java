package com.home.admin.base.service;


import java.util.List;
import java.util.Map;

/**
 * 设备操作接口
 */
public interface DeviceService {

    /**
     * 获取所有设备列表
     * @return
     */
    List<Map<String, Object>> getAllDevices();

    /**
     * 更新设备状态
     * @param deviceId
     * @param status
     */
    void updateDeviceStatus(Integer deviceId, Integer status);

    /**
     * 开启设备
     * @param deviceId
     * @throws Exception
     */
    void openDevice(Integer deviceId) throws Exception;

    /**
     * 关闭设备
     * @param deviceId
     * @throws Exception
     */
    void closeDevice(Integer deviceId) throws Exception;

    /**
     * 获取某设备的最新状态
     * @param id
     * @return
     */
    Integer getDeviceStatus(Integer id);
}
