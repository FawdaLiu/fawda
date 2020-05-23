+ virtualBox安装增强功能
    
 设置--》存储--》添加虚拟光驱
```
sudo mkdir --p /media/cdrom
sudo mount -t auto /dev/cdrom /media/cdrom/
cd /media/cdrom/
sudo sh VBoxLinuxAdditions.run
```

