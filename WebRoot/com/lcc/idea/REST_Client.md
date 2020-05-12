从postman到IDEA REST Client


真香定律的原因有如下几个：

1. 首先postman的所有功能IDEA REST Client都具备了，如REST Client控制台和历史请求记录

2. 其次如果能够在一个生产工具里完成开发和调试的事情，干嘛要切换到另一个工具呢

3. 然后IDEA REST Client还支持环境配置区分的功能，以及接口响应断言和脚本化处理的能力

4. IDEA REST Client的请求配置可以用文件配置描述，所以可以跟随项目和项目成员共享


IDEA REST Client控制台从顶层工具栏依次Tools -> HTTP Client -> Test RESTFUL Web Service 打开

特别说明下的是，如果请求的方式是Authorization :Basic这种方式认证的话，可以点击下锁的按钮，会弹出填充用户名和密码的窗口出来，
填完后会自动补充到Authorization 的header里面去。

历史请求记录

IntelliJ IDEA自动将最近执行的50个请求保存到http-requests-log.http 文件中，该文件存储在项目的.idea / httpRequests / 目录下。
使用请求历史记录，您可以快速导航到特定响应并再次发出请求。文件内容大如下图所示，再次发出请求只要点击那个运行按钮即可。
如果从请求历史记录再次发出请求，则其执行信息和响应输出的链接将添加到请求历史记录文件的顶部。

构建HTTP请求脚本

上面的历史记录就是一个完整的IDEA REST Client请求脚本，如果你是从控制台触发的，那么可以直接复制历史请求记录的文件放到项目里作为HTTP请求的脚本，
给其他成员共享，如果不是，也可以直接新建一个.http或者.rest结尾的文件，IDEA会自动识别为HTTP请求脚本。

语法部分
首先通过###三个井号键来分开每个请求体，然后请求url和header参数是紧紧挨着的，请求参数不管是POST的body传参还是GET的parameter传参，
都是要换行的

环境区分
首先在.http的脚本同目录下创建一个名为http-client.private.env.json的文件，然后内容如下，一级的key值时用来区分环境的，
比如，dev、uat、pro等，环境下的对象就是一次HTTP请求中能够获取到的环境变量了，你可以直接在请求的HTTP的脚本中通过{{xx}}占位符的方式获取到这里配置的参数。

结果断言

IDEA REST Client可以针对接口的响应值进行脚本化的断言处理，立马从一个接口调试工具上升到测试工具了，比如：
```
### Successful test: check response status is 200
GET https://httpbin.org/status/200

> {%
client.test("Request executed successfully", function() {
  client.assert(response.status === 200, "Response status is not 200");
});
%}
```

结果值暂存

试想下这样的场景，当一个系统需要通过认证才能访问的时候，如果用postman的时候，是不是先访问登录接口，然后获得token后，手动粘贴复制到新的调试接口的header参数里面去，
这太麻烦了，IDEA REST Client还有一个真香的功能，可以完美解决这个问题，请看下面的脚本：
```
### 演示POST请求
POST https://httpbin.org/post
Content-Type: application/json

{
  "user": "admin",
  "password": "123456"
}

> {% client.global.set("auth_token", response.body.json.token); %}
### 演示GET请求

GET https://httpbin.org/headers
Authorization: Bearer {{auth_token}}
```
在第一个认证的请求结束后，可以在response里拿到返回的token信息，然后我们通过脚本设置到了全局变量里，那么在接下来的接口请求中，
就可以直接使用双大括号占位符的方式获取到这个token了。
结语

postman有口皆碑，确实是一个非常不错的必备工具，之前给比人推荐这种工具时总是安利他postman。但是，IDEA REST Client也真的很不错，值得尝试一下，
后面安利这种工具就切换到IDEA REST Client了，postman反正被我丢掉了。和第三方做接口对接时，项目里必备一个rest-http.http接口请求文件，满足自己的同时也成方便了他人。

