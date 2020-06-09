# https请求响应
### 1. SSL建立连接过程：  
![image](https://github.com/yifei-2018/img-repository/blob/master/encrypt-demo/quote_https-requestProcess.png?raw=true)

流程说明：  
1. client向server发送请求（如：https://*.yifei.com），连接到server的443端口，发送的信息主要是随机值1和客户端支持的加密算法;  
2. server接收到信息之后给予client响应握手信息，包括随机值2和匹配好的协商加密算法，这个加密算法一定是client发送给server加密算法的子集;  
3. 随即server给client发送第二个响应报文是数字证书。服务端必须要有一套数字证书，可以自己制作，也可以向组织申请。区别就是自己颁发的证书需要客户端验证通过，才可以继续访问，而使用受信任的公司申请的证书则不会弹出提示页面，这套证书其实就是一对公钥和私钥。传送证书，这个证书其实就是公钥，只是包含了很多其它信息，如证书的颁发机构，过期时间、服务端的公钥，第三方证书认证机构(CA)的签名，服务端的域名信息等内容;  
4. 客户端解析证书，这部分工作是由客户端的TLS来完成的，首先会验证公钥是否有效，比如颁发机构，过期时间等等，如果发现异常，则会弹出一个警告框，提示证书存在问题。如果证书没有问题，那么就生成一个随机值（预主秘钥）;  
5. 客户端认证证书通过之后，接下来是通过随机值1、随机值2和预主秘钥组装会话秘钥。然后通过证书的公钥加密会话秘钥;  
6. 传送加密信息，这部分传送的是用证书加密后的会话秘钥，目的就是让服务端使用秘钥解密得到随机值1、随机值2和预主秘钥;  
7. 服务端解密得到随机值1、随机值2和预主秘钥，然后组装会话秘钥，跟客户端会话秘钥相同;  
8. 客户端通过会话秘钥加密一条消息发送给服务端，主要验证服务端是否正常接受客户端加密的消息;  
9. 同样服务端也会通过会话秘钥加密一条消息回传给客户端，如果客户端能够正常接受的话表明SSL层连接建立完成了;  
### 2. https数据传输过程：  
![image](https://raw.githubusercontent.com/yifei-2018/img-repository/master/encrypt-demo/https_dataTransmission.png)

#### 拓展点1：如何判断浏览器收到的数字证书是否受信任、证书被篡改或证书被替换？
1. 判断证书是否受信任：根据证书内容去寻找浏览器/操作系统预置的CA机构根证书。若未找到根证书，代表此机构是不受信任的，此时浏览器会警告用户。
2. 判断证书是否被篡改：若找到根证书后，会在根证书中获取根公钥，然后去解密收到的证书指纹。获取到指纹中的hash值，然后根据收到证书的内容再次生成一个hash值，判断信息是否被篡改。
3. 判断证书是否被替换：若信息未被篡改，则验证是否为目标服务器发送的证书，检查证书的<使用者>属性是否和我们请求的url相同。

##### 参考
[1].[图解HTTPS的一些小动作](https://www.jianshu.com/p/51cc23843756)  
[2].[图解HTTPS](https://blog.csdn.net/lucky52529/article/details/98094621)  
[3].[白话图解HTTPS原理](https://www.cnblogs.com/ghjbk/p/6738069.html)  
[4].[HTTPS详解一：附带最精美详尽的HTTPS原理图](https://developer.51cto.com/art/202001/609520.htm)  
[5].[HTTP和HTTPS协议，看一篇就够了](https://blog.csdn.net/xiaoming100001/article/details/81109617)  
[6].[https数字证书验证原理](https://www.jianshu.com/p/221ea040006f)

##### todo
1. http升级至https操作流程；  
[让你的网站免费支持 HTTPS 及 Nginx 平滑升级](https://www.cnblogs.com/mafly/p/https_nginx.html)  
[将网站从http转换成https](https://www.cnblogs.com/angrybb/p/8540838.html)
