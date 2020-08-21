# WOL(Wake On Lan) 远程唤醒

### 实现功能
* 远程开机
* 远程关机

### 背景说明
远程办公时也经常会需要用到家里的电脑，但是电脑从早开到晚也会产生不少电费，于是想远程控制电脑的开关机。

### 实现原理及运行平台
远程唤醒利用了WOL发送唤醒数据包进行开机，远程关机则是使用***shutdown***命令来操作。

代码中使用了***ping***命令检查设备状态及***shutdown***命令进行关闭设备，因此服务部署平台应为Windows，Linux平台请自行兼容。

### 关于安全性
考虑到映射到外网的访问安全性，但又懒得做登录模块，于是偷懒使用了Google GoogleAuthenticator来做登录操作。

关于使用方法请见***GoogleAuthenticatorUtil*** 中 ***main*** 函数。

### 其他说明
初版Demo做的很粗糙，以能快速投入使用为目标，设备需要手动在数据库中配置（-。-其实自己用也没有必要做个增删改查），后续有机会的话会继续完善。

### 运行效果
![image](https://github.com/yswang1024/wol-home-admin/blob/master/screenshot/wol_login_1.png)
![image](https://github.com/yswang1024/wol-home-admin/blob/master/screenshot/wol_login_2.png)
![image](https://github.com/yswang1024/wol-home-admin/blob/master/screenshot/wol_index.png)
![image](https://github.com/yswang1024/wol-home-admin/blob/master/screenshot/wol_device.png)

### WOL核心代码来源
[https://github.com/rmrodrigues/wol4j](https://github.com/rmrodrigues/wol4j)
