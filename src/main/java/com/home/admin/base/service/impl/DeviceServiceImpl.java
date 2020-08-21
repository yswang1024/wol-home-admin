package com.home.admin.base.service.impl;

import com.home.admin.base.service.DeviceService;
import com.home.admin.base.util.WOLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 设备操作接口实现类
 */
@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 获取所有设备列表
     * @return
     */
    @Override
    public List<Map<String, Object>> getAllDevices() {
        String sql = "select * from device_list";
        return jdbcTemplate.queryForList(sql);
    }

    /**
     * 更新设备状态
     * @param deviceId
     * @param status
     */
    @Override
    public void updateDeviceStatus(Integer deviceId, Integer status) {
        String sql = "update device_list set status = " + status + " where id = " + deviceId;
        jdbcTemplate.update(sql);
    }

    /**
     * 开启设备
     * @param deviceId
     * @throws Exception
     */
    @Override
    public void openDevice(Integer deviceId) throws Exception{
        String sql = "select * from device_list where id = " + deviceId;
        Map result = jdbcTemplate.queryForMap(sql);
        String mac = result.get("mac").toString();
        WOLUtil.wakeUpDevice(mac);
    }

    /**
     * 关闭设备
     * @param deviceId
     * @throws Exception
     */
    @Override
    public void closeDevice(Integer deviceId) throws Exception {
        String sql = "select * from device_list where id = " + deviceId;
        Map result = jdbcTemplate.queryForMap(sql);
        String ip = result.get("ip").toString();
        WOLUtil.closeDevice(ip);
    }

    /**
     * 获取设备最新状态
     * @param id
     * @return
     */
    @Override
    public Integer getDeviceStatus(Integer id) {
        String sql = "select status from device_list where id = " + id;
        String status = jdbcTemplate.queryForMap(sql).get("status").toString();
        return Integer.valueOf(status);
    }

}
