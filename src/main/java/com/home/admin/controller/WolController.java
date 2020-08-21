package com.home.admin.controller;

import com.home.admin.base.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 设备操作相关
 */
@RestController
@RequestMapping("/wol")
public class WolController{

    @Autowired
    private DeviceService deviceService;

    /**
     * 设备列表
     * @param request
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView wolIndex(HttpServletRequest request) {
        request.setAttribute("devices", deviceService.getAllDevices());
        return new ModelAndView("wol/device");
    }

    /**
     * 开启/关闭设备
     * @param id
     * @param action
     * @return
     */
    @RequestMapping("/open")
    public String openDevice(@RequestParam Integer id, @RequestParam Integer action) {
        try {
            if (action == 1) {
                deviceService.closeDevice(id);
                return "succeedClose";
            } else if (action == 0) {
                deviceService.openDevice(id);
                return "succeedOpen";
            } else {
                return "fail";
            }
        } catch (Exception e) {
            return "fail";
        }
    }

    /**
     * 检查设备状态
     * @param id
     * @return
     */
    @RequestMapping("/check")
    public String checkDeviceStatus(@RequestParam Integer id) {
        return deviceService.getDeviceStatus(id).toString();
    }

    /**
     * 更新设备状态
     * @param id
     * @param action
     * @return
     */
    @RequestMapping("/upd")
    public String updateStatus(@RequestParam Integer id, @RequestParam Integer action) {
        if (action == 1) {
            deviceService.updateDeviceStatus(id, 3);
        } else if (action == 0) {
            deviceService.updateDeviceStatus(id, 2);
        }
        return "succeed";
    }
}
