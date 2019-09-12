### 常用命令
vagrant init [name [url]]        # 初始化当前目录到 vagrant 环境
vagrant up                       # 启动虚拟机
vagrant halt                     # 关闭虚拟机
vagrant reload                   # 重启虚拟机
vagrant status                   # 查看虚拟机运行状态
vagrant destroy [-f]              # 销毁当前虚拟机（但不删除Vagrantfile）
vagrant suspend                  # 挂起当前虚拟机
vagrant resume                   # 恢复被挂起的vm

vagrant ssh                      # SSH 登录至虚拟机

vagrant box add ADDRESS          # 安装box文件/url到本地
vagrant box list                 # 列出所有本地已安装的box列表
vagrant box outdated             # 检查已安装的box是否有更新的版本
vagrant box remove NAME          # 删除某个已安装的box

vagrant package         # 打包当前虚拟机环境为box文件以用于分发 --base=first-vm --output=first-vm-v1.box
vagrant plugin          # 安装卸载vagrant插件
vagrant provision       # 执行专门的环境初始化脚本
vagrant ssh-config      # 输出ssh连接的一些信息
vagrant version         # 获取vagrant的版本