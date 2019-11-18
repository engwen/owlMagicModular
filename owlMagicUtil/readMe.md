owlMagicUtil工具介绍
-------

* Package name (包名)
com.owl.magicUtil
* The way of reference is (引用方式为)

```
<dependency>
   <groupId>com.github.engwen</groupId>
    <artifactId>owlMagicUtil</artifactId>
    <version>*.*.*</version>
</dependency>
```
#### *项目结构以及介绍如下*

* **model**  常用工具类集合

    1. ModelPrototype  序列化对象，正则中使用。使用 owlMagicComment 时，提供序列化功能
  
* **util**  常用工具类集合  
    1. DateCountUtil 日期工具类 日期格式化，两个日期的间隔小时，月份，年份等等
    2. MD5Util MD5工具类 计算文件或字符串的MD5
    3. ObjectAndByteArrayUtil 对象和数组变换工具
    4. PropertiesUtil 配置文件读取工具
    4. RandomUtil 随机数工具
    5. RegexUtil 正则工具
    6. ClassTypeUtil 类选择工具 
   
2.4.2

   推荐使用版本，新的版本中，监听者，返回基本数据类型，返回消息基础类 移动至本 jar 中，以方便后续使用
   为了简化使用标准，抛弃冗余，现将基础监听工具类删除，现在要实现监听机制，必须继承OwlObserved类，同时
   ，新加入了 lambda 参数化接口和基于非 lambda 的具体实现，现在监听时可以使用带参数或不带参数的 
   lambda，也可以使用 addListen(event,method) 添加监听，在抛出事件时，你可以选择更加方便的事件参数
   ——简而言之，现在你可以在抛出事件的同时传递参数
   
2.3.2-2.3.9
    
   将MVC功能移至 owlMagicComment 中。本工具类现在仅仅提供工具
   
2.3.1

   添加类选择工具ClassTypeUtil,优化reorderRule排序工具
   取消基于接口（MsgConstantUtil）以及枚举(MsgConstantEM)的msg常量，使用新的类MsgConstant，以方便拓展，该类支持继承与重写
