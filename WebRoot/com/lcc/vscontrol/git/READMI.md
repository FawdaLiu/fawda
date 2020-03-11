###### [git book](https://git-scm.com/book/en/v2)
###### [linux](https://git-scm.com/download/linux)
###### [win](https://git-scm.com/download/win)

##### 简述
1、什么是ｇｉｔ
    
    ３Ｗ: who what when
    版本控制工具
    
2、开源协议
    
    开源项目的选型，是会涉及到开源协议的，那么开源协议有哪些呢？
    LGPL、BSD、GPL

3、图形化gui客户端：sourceTree

4、仓库，git可以管理的目录

##### 使用

1、配置用户信息

    $ git config --global user.name "liuyl"
    $ git config --global user.email "15516662880@163.com"
    $ git config --list
2、全局变量
+ 添加
    git remote --global set-url origin "https://..." \
    git config --global remote.origin.url "https://..."

+ 删除 \
    vim  ~/.gitconfig \
    # 删除 origin  的 参数 [remote "origin"]
    
3、初始化

    $ git init
    
4、添加文件到仓库

    $ git add xxx.txt
    $ git commit -m "xxx" //-m:描述信息
    
5、仓库状态

    $ git status

6、查看提交记录

    git log
    git log --graph

7、回退
    
    $ git reset --hard HEAD^(commitId) //回退本地,返回到指定的版本,会在重置 HEAD 和branch的同时,重置stage区和工作目录
    $ git reset --soft HEAD^(commitId) //会在重置 HEAD 和 branch 时，保留工作目录和暂存区中的内容，并把重置 HEAD 所带来的新的差异放进暂存区。
    $ git reset /--mixed HEAD^ xx.txt //无参数的reset或者带--mixed参数，工作目录的内容和 --soft 一样会被保留，但和 --soft 的区别在于，它会把暂存区清空,并把原节点和reset节点的差异的文件放在工作目录，总而言之就是，工作目录的修改、暂存区的内容以及由 reset 所导致的新的文件差异，都会被放进工作目录
    
8、$ git push -f origin master //强制覆盖git服务器
    
9、从工作区到本地

    $ git checkout -- xx.txt
    
10、远程仓库配置

    //添加key
    github->setting->SSH and GPG keys->
    $ ssh-keygen -t rsa -C "15516662880@163.com"
    /c/Users/Admin/.ssh/
    id_rsa id_rsa.pub known_hosts
    cat id_rsa.pub
    //测试是否连通
    $ ssh -T git@github.com
11、//仓库路径查询
    
    git remote -v
12、添加远程仓库

    //关联
    $ git remote add origin git@github.com:StevenLyl/demo_liuyl.git
    //完成第一次提交
    $ git pull origin master --allow-unrelated-histories
    $ git push -u origin master
    
13、删除指定的远程
    
    git remote rm origin
14、克隆仓库

    git clone https://github.com/StevenLyl/demo_repo.git
15、标签管理
+ 标记，通过标签来回滚 \
    $ git tag //查看标签 \
    $ git tag name //创建标签 \
    $ git tag -a name -m "comment" //指定提交信息 \
    $ git tag -d name //删除本地标签 \
    $ git push origin :refs/tags/标签名 //删除远程标签 \
    $ git push origin name
    
16、分支管理

    $ git branch //查看分支
    $ git branch name //创建分支
    $ git checkout name //切换分支
    $ git checkout -b name //创建新分支
    $ git merge 分支branchName //合并分支
    $ git branch -d name //删除分支
    $ git merge upstream/master //解决冲突后合并
17、栈

    $ git stash
18、合并
    
    $ git merge
19、可以对某一段线性提交历史进行编辑、删除、复制、粘贴；因此，合理使用rebase命令可以使我们的提交历史干净、简洁！
    
    $ git rebase
    
20、团队管理
    
    $ git-flow

gitlabs
https://bitnami.com/stack/gitlab/virtual-machine
https://github.com/gitlabhq/gitlabhq
https://about.gitlab.com/dowloads/#centos7

在.git目录中可以看到
git hooks
client side
server side