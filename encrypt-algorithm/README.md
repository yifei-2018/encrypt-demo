# 1.单向加密
&emsp;&emsp;单向加密，又称不可逆加密，即明文经过加密后产生的密文不可被解密，目前常用的单向加密算法：[MD5][md5]、[SHA][sha]、[HMAC][hmac]。
## 1.1 [MD5][md5]加密
&emsp;&emsp;MD5信息摘要算法（英语：MD5 Message-Digest Algorithm），一种被广泛使用的密码散列函数，可以产生出一个128bit位（16字节）的散列值（hash value），用于确保信息传输完整一致。1996年后该算法被证实存在弱点，可以被加以破解，对于需要高度安全性的数据，专家一般建议改用其他算法，如SHA-2。2004年，证实MD5算法无法防止碰撞（collision），因此不适用于安全性认证，如SSL公开密钥认证或是数字签名等用途。
## 1.2 [SHA][sha]加密
&emsp;&emsp;安全散列算法（英语：Secure Hash Algorithm，缩写为SHA）是一个密码散列函数家族，是FIPS所认证的安全散列算法。能计算出一个数字消息所对应到的，长度固定的字符串（又称消息摘要）的算法。且若输入的消息不同，它们对应到不同字符串的机率很高。  
&emsp;&emsp;SHA家族的五个算法，分别是SHA-1、SHA-224、SHA-256、SHA-384和SHA-512，由美国国家安全局（NSA）所设计，并由美国国家标准与技术研究院（NIST）发布，是美国的政府标准。后四者有时并称为SHA-2。SHA-1在许多安全协定中广为使用，包括TLS和SSL、PGP、SSH、S/MIME和IPsec，曾被视为是MD5（更早之前被广为使用的杂凑函数）的后继者。但SHA-1的安全性如今被密码学家严重质疑；虽然至今尚未出现对SHA-2有效的攻击，它的算法跟SHA-1基本上仍然相似；因此有些人开始发展其他替代的杂凑算法。
## 1.3 [HMAC][hmac]加密
&emsp;&emsp;HMAC是密钥相关的哈希运算消息认证码（Hash-based Message Authentication Code）的缩写，由H.Krawezyk，M.Bellare，R.Canetti于1996年提出的一种基于Hash函数和密钥进行消息认证的方法，并于1997年作为RFC2104被公布，并在IPSec和其他网络协议（如SSL）中得以广泛应用，现在已经成为事实上的Internet安全标准。它可以与任何迭代散列函数捆绑使用。  
&emsp;&emsp;常用算法：HMACMD5、HMACSHA1、HMACSHA256、HMACSHA384和HMACSHA512。

######参考
[1].[常见加密算法之单向加密](https://cloud.tencent.com/developer/article/1608669)  
[2].[JAVA中的加密算法之单向加密](https://cloud.tencent.com/developer/article/1608733)  
[3].[MD4、MD5、SHA1、HMAC、HMAC_SHA1区别](https://blog.csdn.net/hero272285642/article/details/100032749)  
[4].[MAC与HMAC介绍](https://blog.51cto.com/xwandrew/2050973)  

###### 加解密jar推荐
    <dependency>
        <groupId>commons-lang</groupId>
        <artifactId>commons-lang</artifactId>
        <version>*.*</version>
    </dependency>
###### 在线加密工具推荐
* [在线加密工具](http://tool.chinaz.com/tools/md5.aspx)
* [1024tools](https://1024tools.com/hmac)

---
# 2.双向加密
&emsp;&emsp;双向加密又称为可逆加密，即生成密文后，在需要的时候可以反解为明文。
## 2.1 [对称加密][symmetricalEncryption]
&emsp;&emsp;采用单钥密码系统的加密方法，同一个密钥可以同时用作信息的加密和解密，这种加密方法称为对称加密，也称为单密钥加密。  
&emsp;&emsp;在对称加密中常用的算法：[DES][des]、[3DES][3des]、[AES][aes]等。
### 2.1.1 [DES][des]
&emsp;&emsp;DES全称为Data Encryption Standard，即数据加密标准，是一种使用密钥加密的块算法，1977年被美国联邦政府的国家标准局确定为联邦资料处理标准（FIPS），并授权在非密级政府通信中使用，随后该算法在国际上广泛流传开来。需要注意的是，在某些文献中，作为算法的DES称为数据加密算法（Data Encryption Algorithm,DEA），已与作为标准的DES区分开来。  
&emsp;&emsp;DES算法的入口参数有三个：Key、Data、Mode。其中Key为7个字节共56位，是DES算法的工作密钥；Data为8个字节64位，是要被加密或被解密的数据；Mode为DES的工作方式,有两种:加密或解密。
### 2.1.2 [3DES][3des]
&emsp;&emsp;3DES（或称为Triple DES）是三重数据加密算法（TDEA，Triple Data Encryption Algorithm）块密码的通称。它相当于是对每个数据块应用三次DES加密算法。由于计算机运算能力的增强，原版DES密码的密钥长度变得容易被暴力破解；3DES即是设计用来提供一种相对简单的方法，即通过增加DES的密钥长度来避免类似的攻击，而不是设计一种全新的块密码算法。  
&emsp;&emsp;3DES加密过程（加密-解密-加密）为：C=Ek3(Dk2(Ek1(M)))  
&emsp;&emsp;3DES解密过程（解密-加密-解密）为：M=Dk1(Ek2(Dk3(C)))
### 2.1.3 [AES][aes]
&emsp;&emsp;密码学中的高级加密标准（Advanced Encryption Standard，AES），又称Rijndael加密法，是美国联邦政府采用的一种区块加密标准。  
&emsp;&emsp;这个标准用来替代原先的[DES（Data Encryption Standard）][des]，已经被多方分析且广为全世界所使用。经过五年的甄选流程，高级加密标准由美国国家标准与技术研究院 （NIST）于2001年11月26日发布于FIPS PUB 197，并在2002年5月26日成为有效的标准。2006年，高级加密标准已然成为对称密钥加密中最流行的算法之一。
## 2.2 非对称加密
### 2.2.1 [RSA][rsa]
&emsp;&emsp;RSA加密算法是一种非对称加密算法。在公开密钥加密和电子商业中RSA被广泛使用。RSA是1977年由罗纳德·李维斯特（Ron Rivest）、阿迪·萨莫尔（Adi Shamir）和伦纳德·阿德曼（Leonard Adleman）一起提出的。当时他们三人都在麻省理工学院工作。RSA就是他们三人姓氏开头字母拼在一起组成的。  
&emsp;&emsp;对极大整数做因数分解的难度决定了RSA算法的可靠性。换言之，对一极大整数做因数分解愈困难，RSA算法愈可靠。假如有人找到一种快速因数分解的算法的话，那么用RSA加密的信息的可靠性就肯定会极度下降。但找到这样的算法的可能性是非常小的。今天只有短的RSA钥匙才可能被强力方式解破。世界上还没有任何可靠的攻击RSA算法的方式。只要其钥匙的长度足够长，用RSA加密的信息实际上是不能被解破的。  
### 2.2.2 [ECC][ecc]
&emsp;&emsp;椭圆加密算法（ECC）是一种公钥加密体制，最初由Koblitz和Miller两人于1985年提出，其数学基础是利用椭圆曲线上的有理点构成Abel加法群上椭圆离散对数的计算困难性。公钥密码体制根据其所依据的难题一般分为三类：大素数分解问题类、离散对数问题类、椭圆曲线类。有时也把椭圆曲线类归为离散对数类。  
&emsp;&emsp;优点:  
&emsp;&emsp;&emsp;&emsp;1.安全性高：有研究表示160位的椭圆密钥与1024位的RSA密钥安全性相同。  
&emsp;&emsp;&emsp;&emsp;2.处理速度快：a.在私钥的加密解密速度上，ecc算法比RSA、DSA速度更快;b.存储空间占用小;c.带宽要求低;  
&emsp;&emsp;ECC算法在jdk1.5后加入支持，目前仅仅只能完成密钥的生成与解析。 如果想要获得ECC算法实现，需要调用硬件完成加密/解密（ECC算法相当耗费资源，如果单纯使用CPU进行加密/解密，效率低下），涉及到Java Card领域，PKCS#11。  
### 2.2.3 [DSA][dsa]
&emsp;&emsp;Digital Signature Algorithm (DSA)是Schnorr和ElGamal签名算法的变种，被美国NIST作为DSS(DigitalSignature Standard)。
##### 参考
[1].[JAVA中的加密算法之双向加密(对称加密)](https://cloud.tencent.com/developer/article/1608323)  
[2].[JAVA中的加密算法之双向加密(非对称加密)](https://cloud.tencent.com/developer/article/1608852)  
[3].[Java实现DES加密解密](https://blog.csdn.net/gs12software/article/details/83899389)  
[4].[Java对称加密算法DES实例详解](https://www.jb51.net/article/169715.htm)  
[5].[java-信息安全（二）-对称加密算法工作模式ECB,CBC,CRT、DES,3DES,AES,Blowfish,RC2,RC4](https://www.cnblogs.com/bjlhx/p/6544766.html)  
[6].[JAVA中3DES加密示例](https://blog.csdn.net/qq_32133405/article/details/83112779)  
[7].[Java AES加密解密](https://www.cnblogs.com/li150dan/p/10921599.html)  
[8].[DES/3DES/AES 三种对称加密算法在 Java 中的实现](https://blog.csdn.net/s573626822/article/details/80596133)  
[9].[JAVA中使用RSA通过秘钥文件对字符串进行加密解密](https://www.cnblogs.com/cnndevelop/p/7137638.html)  
[10].[RSA加密、解密、签名、验签的原理及方法](https://www.cnblogs.com/pcheng/p/9629621.html)  
[11].[Java -- 生成RSA公钥私钥并对文件加密](https://blog.csdn.net/Aeve_imp/article/details/101217466)  
[12].[关于RSA使用的正确姿势](https://www.cnblogs.com/ppldev/p/5110667.html)  
[13].[java ECC 加密 解密算法](https://blog.csdn.net/qq_18206683/article/details/85066987)  
[14].[加解密篇 - 非对称加密算法 (RSA、DSA、ECC、DH)](https://blog.csdn.net/u014294681/article/details/86705999)  
[15].[不同证书格式Der 、Cer 、Pfx 、Pem区别](https://blog.csdn.net/chinahiphop/article/details/100145467)  
[16].[Cer Crt Pem Pfx 证书格式转换](https://www.cnblogs.com/aiqingqing/p/4521667.html)  
[17].[openssl命令目录](https://www.cnblogs.com/aixiaoxiaoyu/p/8650180.html)  
[18].[Windows下使用java调用OpenSSL(无需安装OpenSSL)](https://my.oschina.net/u/3695687/blog/1542125)  
[19].[使用openssl生成证书（含openssl详解）](https://blog.csdn.net/bbwangj/article/details/82503675)  
[20].[使用openssl生成证书](https://segmentfault.com/a/1190000019622898)  
[21].[使用OpenSSL生成自签名SSL证书](https://blog.csdn.net/nklinsirui/article/details/89432430?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.nonecase&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.nonecase)  
[22].[OpenSSL：证书格式的相互转换, 例如.pem，.crt，.cer，.pfx](https://blog.csdn.net/qq_18105691/article/details/83339101)
##### 在线工具推荐
* [在线生成公钥私钥对，RSA公私钥生成](http://www.metools.info/code/c80.html)
<!--全局链接-->
[md5]: https://baike.baidu.com/item/MD5/212708?fr=aladdin "md5"
[sha]: https://baike.baidu.com/item/SHA%E5%AE%B6%E6%97%8F/9849595?fr=aladdin "sha"
[hmac]: https://baike.baidu.com/item/hmac/7307543?fr=aladdin "hmac"
[symmetricalEncryption]: https://baike.baidu.com/item/%E5%AF%B9%E7%A7%B0%E5%8A%A0%E5%AF%86/2152944?fr=aladdin "对称加密"
[des]: https://baike.baidu.com/item/DES/210508?fr=aladdin "des"
[3des]: https://baike.baidu.com/item/3DES "3des"
[aes]: https://baike.baidu.com/item/%E9%AB%98%E7%BA%A7%E5%8A%A0%E5%AF%86%E6%A0%87%E5%87%86/468774?fromtitle=aes&fromid=5903 "aes"
[rsa]: https://baike.baidu.com/item/RSA%E7%AE%97%E6%B3%95/263310?fromtitle=RSA&fromid=210678&fr=aladdin "rsa"
[ecc]: https://baike.baidu.com/item/%E6%A4%AD%E5%9C%86%E5%8A%A0%E5%AF%86%E7%AE%97%E6%B3%95/10305582?fr=aladdin "ecc"
[dsa]: https://baike.baidu.com/item/DSA%E7%AE%97%E6%B3%95/10856660?fr=aladdin "dsa"