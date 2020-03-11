[#macro NTKOEVENT
 id="TANGER_OCX"
 AfterOpenFromURL=false
 AfterPublishAsPDFToURL=false
 OnDocumentOpened=true
 OnDocumentClosed=false
 OnDocActivated=false
 OnScreenModeChanged=false
 AfterPreviewClosed=false
 OnFileCommand=false
 OnCustomMenuCmd2=false
 OnCustomFileMenuCmd=false
 BeforeOriginalMenuCommand=false
 OnClickCustomContextMenu=false
]
[#-- **** SSSS文档相关事件SSSS **** --]

[#--
 * <b>时间:</b> <i>2018-7-5 上午8:15:53</i> <br>
 * 该事件在BeginOpenFromURL方法执行完毕之后被触发, 可通过定义forAfterOpenFromURL来回调 <br>
 * 
 * @author 刘彦龙 liuyl@risencn.com @since 1.0
 * @param TANGER_OCX_obj:当前打开的文档对象, statusCode:状态值
          statusCode:0: 成功 , 1:文件错误, 2:网络错误, 3: 内存错误, 4:参数错误, 5:从ODBC数据库读写数据错误, 6：从Variant读写数据错误, 100：其他错误
 --]
[#if AfterOpenFromURL ]
<script language="JScript" for="${id}" event="AfterOpenFromURL(TANGER_OCX_obj, StatusCode)">
    try {
        forAfterOpenFromURL(TANGER_OCX_obj, StatusCode);
    } catch (e){
        
    } finally {
        
    }
</script>
[/#if]

[#--
 * <b>时间:</b> <i>2018-7-5 上午8:23:34</i> <br>
 * 该事件在PublishAsPDFToURL方法执行完毕之后被触发, 可通过定义forAfterPublishAsPDFToURL来回调 <br>
 * 
 * @author 刘彦龙 liuyl@risencn.com @since 1.0
 * @param retData:后台自定义返回字符串
          errorCode:错误状态值  
 --]
[#if AfterPublishAsPDFToURL]
<script language="JScript" for="${id}" event="AfterPublishAsPDFToURL(RetData, ErrorCode)">
    try {
        forAfterPublishAsPDFToURL(RetData, ErrorCode);
    } catch (e){
        
    } finally {
        
    }
</script>
[/#if]

[#--
 * <b>时间:</b> <i>2018-7-5 上午8:24:03</i> <br>
 * 该事件在文档打开完毕时执行。 可通过定义forOnDocumentOpened来回调 <br>
 * 
 * @author 刘彦龙 liuyl@risencn.com @since 1.0
 * @param file:当前打开的文档路径或URL
          TANGER_OCX_obj:当前打开的文档对象
 --]
[#if OnDocumentOpened]
<script language="JScript" for="${id}" event="OnDocumentOpened(File, TANGER_OCX_obj)">
    try {
        forOnDocumentOpened(File, TANGER_OCX_obj);
    } catch (e){
        
    } finally {
        
    }
</script>
[/#if]

[#--
 * <b>时间:</b> <i>2018-7-5 上午8:27:01</i> <br>
 * 该事件在文档关闭之后执行。 可通过定义forOnDocumentClosed来回调 <br>
 * 
 * @author 刘彦龙 liuyl@risencn.com @since 1.0
 --]
[#if OnDocumentClosed]
<script language="JScript" for="${id}" event="OnDocumentClosed()">
    try {
        forOnDocumentClosed();
    } catch (e){
        
    } finally {
        
    }
</script>
[/#if]

[#--
 * <b>时间:</b> <i>2018-7-5 上午8:30:07</i> <br>
 * 当控件被激活或者不激活时触发此事件。比如，切换窗口将会出发此事件。 可通过定义forOnDocActivated来回调 <br>
 * 
 * @author 刘彦龙 liuyl@risencn.com @since 1.0
 * @param IsActivated:是否激活
 --]
[#if OnDocActivated]
<script language="JScript" for="${id}" event="OnDocActivated(IsActivated)">
    try {
        forOnDocActivated(IsActivated);
    } catch (e){
        
    } finally {
        
    }
</script>
[/#if]

[#--
 * <b>时间:</b> <i>2018-7-5 上午8:30:07</i> <br>
 * 此事件当控件在全屏/非全屏状态切换时被触发。 可通过定义forOnScreenModeChanged来回调 <br>
 * 
 * @author 刘彦龙 liuyl@risencn.com @since 1.0
 * @param IsFullScreenMode:是否处于全屏状态
 --]
[#if OnScreenModeChanged]
<script language="JScript" for="${id}" event="OnScreenModeChanged(IsFullScreenMode)">
    try {
        forOnScreenModeChanged(IsFullScreenMode);
    } catch (e){
        
    } finally {
        
    }
</script>
[/#if]

[#--
 * <b>时间:</b> <i>2018-7-5 上午8:30:07</i> <br>
 * 此事件当控件在退出打印预览模式后触发。 可通过定义forAfterPreviewClosed来回调 <br>
 * 
 * @author 刘彦龙 liuyl@risencn.com @since 1.0
 * @param IsFullScreenMode:是否处于全屏状态
 --]
[#if AfterPreviewClosed]
<script language="JScript" for="${id}" event="AfterPreviewClosed()">
    try {
        forAfterPreviewClosed();
    } catch (e){
        
    } finally {
        
    }
</script>
[/#if]
[#-- **** EEEE文档相关事件EEEE **** --]


[#-- **** SSSS菜单相关事件SSSS **** --]

[#--
 * <b>时间:</b> <i>2018-7-5 上午8:53:41</i> <br>
 * 该事件在用户单击文件菜单或者工具栏的相关按钮时发生。 可通过定义forOnFileCommand来回调 <br>
 * 
 * @author 刘彦龙 liuyl@risencn.com @since 1.0
 * @param Item:文件菜单命令索引, bool:是否取消命令
 --]
[#if OnFileCommand]
<script language="JScript" for="TANGER_OCX" event="OnFileCommand(Item, bool)">
    try {
        forOnFileCommand(Item, bool);
    } catch (e){
        
    } finally {
        
    }
</script>
[/#if]
[#--
 * <b>时间:</b> <i>2018-7-5 上午8:56:51</i> <br>
 * 该事件在用户单击自定义主菜单中的项目时执行。 可通过定义forOnCustomMenuCmd2来回调 <br>
 * 
 * @author 刘彦龙 liuyl@risencn.com @since 1.0
 * @param menuPos:一级菜单位置, submenuPos:二级菜单位置
          subsubmenuPos:三级菜单位置, menuCaption:自定义菜单名称
          myMenuID:自定义菜单ID
 --]
[#if OnCustomMenuCmd2]
<script language="JScript" for="TANGER_OCX" event="OnCustomMenuCmd2(menuPos, submenuPos, subsubmenuPos, menuCaption, myMenuID)">
    try {
        forOnCustomMenuCmd2(menuPos, submenuPos, subsubmenuPos, menuCaption, myMenuID);
    } catch (e){
        
    } finally {
        
    }
</script>
[/#if]
[#--
 * <b>时间:</b> <i>2018-7-5 上午8:56:51</i> <br>
 * 该事件在用户单击自定义文件菜单中的项目时执行。 可通过定义forOnCustomFileMenuCmd来回调 <br>
 * 
 * @author 刘彦龙 liuyl@risencn.com @since 1.0
 * @param menuIndex:自定义文件菜单位置索引, menuCaption:自定义文件菜单名称
          menuID:自定义文件菜单ID
 --]
[#if OnCustomFileMenuCmd]
<script language="JScript" for="TANGER_OCX" event="OnCustomFileMenuCmd(menuIndex, menuCaption, menuID)">
    try {
        forOnCustomFileMenuCmd(menuIndex, menuCaption, menuID);
    } catch (e){
        
    } finally {
        
    }
</script>
[/#if]
[#--
 * <b>时间:</b> <i>2018-7-5 上午9:02:55</i> <br>
 * 在用户执行文档菜单命令之前执行。 可通过定义forBeforeOriginalMenuCommand来回调 <br>
 * 
 * @author 刘彦龙 liuyl@risencn.com @since 1.0
 * @param menuTitle:菜单标题, IsCancel:是否取消命令
 --]
[#if BeforeOriginalMenuCommand]
<script language="JScript" for="TANGER_OCX" event="BeforeOriginalMenuCommand(menuTitle, IsCancel)">
    try {
        forBeforeOriginalMenuCommand(menuTitle, IsCancel);
    } catch (e){
        
    } finally {
        
    }
</script>
[/#if]
[#--
 * <b>时间:</b> <i>2018-7-5 上午9:02:55</i> <br>
 * 在用户点击通过CustomMenuStr属性自定义的右键菜单项就会触发本事件。 可通过定义forOnClickCustomContextMenu来回调 <br>
 * 
 * @author 刘彦龙 liuyl@risencn.com @since 1.0
 * @param MenuIndex:自定义右键菜单索引
 --]
[#if OnClickCustomContextMenu]
<script language="JScript" for="TANGER_OCX" event="OnClickCustomContextMenu(MenuIndex)">
    try {
        forOnClickCustomContextMenu(MenuIndex);
    } catch (e){
        
    } finally {
        
    }
</script>
[/#if]
[#-- **** EEEE菜单相关事件EEEE **** --]
[/#macro]