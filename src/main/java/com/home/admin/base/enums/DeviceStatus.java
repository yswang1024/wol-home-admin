package com.home.admin.base.enums;

import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * 设备状态枚举
 * 0 ->> 设备已关闭
 * 1 ->> 设备已开启
 * 2 ->> 设备开启中
 * 3 ->> 设备关闭中
 */
@ToString
@AllArgsConstructor
public enum DeviceStatus {

    CLOSED(0), OPENED(1), OPENING(2), CLOSING(3);

    public final Integer value;

}
