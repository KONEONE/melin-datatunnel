# Java ORM 学习
[Hibernate 教程](https://www.cnblogs.com/wuwuyong/p/11623352.html)  
[Hibernate 快速开始](https://docs.jboss.org/hibernate/orm/6.6/quickstart/html_single/)  
[Hibernate ORM 用户指南](https://docs.jboss.org/hibernate/stable/orm/userguide/html_single/Hibernate_User_Guide.html)  
___ 
Dubbo SPI 功能如下：  
约定在Classpath下的META-INF/services/目录中创建一个服务接口命名的文件，然后文件里面记录的次Jar包提供的具体实现类的全限定名；需要去实现类的加载和实例化时，就可以根据该文件去指定具体类的实现。  
下面是一个Java SPI的例子（非Dubbo SPI）
```Java
public interface Aobing{ void say(); }

public class NuanNanAobing implements Aobing {
    @Override
    public void say(){
        System.out.println("太暖了！");
    }
}

public class ShuaiAobing implements Aobing {
    @Override
    public void say(){
        System.out.println("太帅了！");
    }
}

public class Main{
    public static void main(String[] args){
        ServiceLoader<Aobing> serviceLoader = ServiceLoader.load(Aobing.class);
        Iterator<Aobing> iter = serviceLoader.iterator();
        while(iter.hasNext()){
            Aobing aobing = iter.next();
            aobing.say();
        }
    }
}
```
在<font color=yellow>META-INF/services/</font>目录下构建以接口全限定名的文件，具体内容如下：
```xml
com.demo.spi.NuanNanAobing
com.demo.spi.ShuaiAobing
```
运行结果：
```text
太暖了！
太帅了！
```
Dubbo SPI的META-INF/dubbo/目录下的文件是一个KV文件，如下
```xml
NuanNanAobing=com.demo.spi.NuanNanAobing
ShuaiAobing=com.demo.spi.ShuaiAobing
```
这样做就可以根据名字按照需求去动态加载该接口的实现类。  下面是一个dubbo的例子
```Java
@SPI("englist")  // 默认使用english实现
public interface HelloService {
    String say();
}

public class EnglishHelloService implements HelloService {
    @Override
    public String say(){
        return "Hello!";
    }
}

public class ChineseHelloService implements HelloService {
    @Override
    public String say(){
        return "你好！";
    }
}
```
配置文件如下：（META-INF/dubbo/org.apache.dubbo.common.extension..HelloService）  
```xml
english=com.example.EnglishHelloService
chinese=com.example.ChineseHelloService
```
使用
```Java
public class Demo {
    public static void main(String[] args) {
        // 获取默认扩展（english）
        HelloService defaultService = ExtensionLoader
            .getExtensionLoader(HelloService.class)
            .getDefaultExtension();
        System.out.println(defaultService.say());

        // 获取指定扩展
        HelloService chineseService = ExtensionLoader
            .getExtensionLoader(HelloService.class)
            .getExtension("chinese");
        System.out.println(chineseService.say());
    }
}
```
输出
```text
hello!
你好！
```
而在datatunnel项目中，所使用的bee库，使用Java SPI实现了自定义的SPI机制。比如：
```Java
@SPI
public interface DataTunnelSink extends Serializable {
    ...
}

public class DorisDataTunnelSink implements DataTunnelSink {
    @Override
    ...
}
```
在配置文件中(META-INF/services/com.kone.datatunnel.api.DataTunnelSink)
```xml
doris=com.kone.datatunnel.plugin.doris.DorisDataTunnelSink
```  
[Dubbo SPI 机制](https://juejin.cn/post/6872138926216511501)  
