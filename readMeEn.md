Use my code, please note the source, thank you.

Author engwen            
Email xiachanzou@outlook.com            
Time 2018/07/16

Thank JetBrains Community Support Team for its support to this project, which provides IntelliJ idea tools
for my faster development and iteration. <url>https://www.jetbrains.com/?From=owlMagicComment

En details ,please click <url>https://github.com/engwen/owlMagicComment/blob/master/readMeEn.md

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

# OwlMagicComment

-----

# To simple principles

### use the jar,you can get like this

##### check params   (@OwlCheckParams  notNull,notAllNull,canNull)

        @RequestMapping("/signin")
        public MsgResultVO signin(User user) {
            MsgResultVO result = new MsgResultVO();
            if(null==user.getPassword || "" == user.getPassword ){
                result.errorMsg("The password must not be empty.");                \ \      @RequestMapping("/signin")
            } else if(null==user.getAccount || ""==user.getAccount ){      -------- \ \     @OwlCheckParams(notNull={"account","password"})
                result.errorMsg("Account can not be empty.");              -------- / /     public MsgResultVO signin(User user) {
            } else {                                                               / /           return userService.signin(user);
                result = userService.signin(user);                                          }
            }
            return result;
        }

##### change result type to your want (@OwlBackToObject  PS: you can use @OwlBackToMsgResult change result type to MsgResultVO)

    @RequestMapping("test")
    public Object test() {
        MsgResultVO<String> msgResultVO = MsgResultVO.getInstanceSuccess("aaaa");
        TestVO testVO = new TestVO();                                                \ \    @RequestMapping("test")
        testVO.setCode(msgResultVO.getResultCode());                        --------- \ \   @OwlBackToObject(classPath = "com.owl.shiro.vo.TestVO",msg = "msg",code = "code",data = "data")
        testVO.setMsg(msgResultVO.getResultMsg());                          --------- / /   public Object test() {
        testVO.setData(msgResultVO.getResultData());                                 / /        return MsgResultVO.getInstanceSuccess("aaaa");
        return testVO;                                                                      }
    }

##### count the method use time (@OwlCountTime)

    @RequestMapping("/signin")
    @OwlCheckParams(notNull={"account","password"})                               \ \       @RequestMapping("/signin")   
    public MsgResultVO signin(OwlUser user) {                           -----------\ \      @OwlCountTime
        Date startTime = new Date();                                    -----------/ /      @OwlCheckParams(notNull={"account","password"})     
        MsgResultVO<OwlUser> result = owlAuthService.signin(user);                / /       public MsgResultVO signin(OwlUser user) {
        Double second = (double)((new Date()).getTime() - startTime.getTime()) / 1000.0D;       return owlAuthService.signin(user);
        logger.info("cost time is " + second);                                              }
        return result;
    }

##### set params are null or set return data some value are null (@OwlSetNullData)

    @RequestMapping("/signin")
    @OwlCheckParams(notNull={"account","password"})
    public MsgResultVO signin(OwlUser user) {                                      \ \      @RequestMapping("/signin")
        user.setId(null);                                                -----------\ \     @OwlSetNullData(backValue = {"password"},paramsValue = {"id"})
        MsgResultVO<OwlUser> result = owlAuthService.signin(user);       -----------/ /     @OwlCheckParams(notNull={"account","password"})
        result.getResultData().setPassword(null);                                  / /      public MsgResultVO signin(OwlUser user) {
        return result;                                                                          return owlAuthService.signin(user);
    }                                                                                       }

### or like this

(use Listening event like use Flex AS)

        //build class
        public UserTest extend OwlObserved{
            public void name(){
                print("test")
            }
        }
        // build event     
        OwlObserverEvent HH= new OwlObserverEvent("HH");   
        //add event listen 
        UserTest lili = new UserTest();
        lili.addEventListen(HH,()->{System.out.println("test");});//or  lili.addEventListen(HH,"name")
        lili.dispatchEvent(HH);//or OwlObserverAB.dispatchEvent(HH);        
        //remove
        lili.removeEventListen(HH);

### or like this

(build a class extends OwlMemento ,so you can restore the class to the specified state)

     public class UserTest extends OwlMemento {
         private String name;
         private Integer age;
         public String getName() {
             return name;
         }
         public void setName(String name) {
             this.name = name;
         }
         public Integer getAge() {
             return age;
         }
         public void setAge(Integer age) {
             this.age = age;
         }
     }
 
    //in other class you can do this :
  
          UserTest lili = new UserTest();
          lili.setName("lili");
          lili.setAge(12);
             //save state to OwlMemento
          lili.saveToMemento();
          lili.setAge(13);
            //save state to OwlMemento
          lili.saveToMemento();
            //get first 
          System.out.println(ObjectUtil.toJSON(lili.getMementoFirst()));//{"name":"lili","age":"10"}
            //get last
          System.out.println(ObjectUtil.toJSON(lili.getMementoLast()));//{"name":"lili","age":"13"}
            //get history
          System.out.println(ObjectUtil.toJSON(lili.getMementoHistory()));//[{"name":"lili","age":"10"},{"name":"lili","age":"13"}]
          UserTest wade = lili.transferMemento(new UserTest());
            //transfer history
          System.out.println(ObjectUtil.toJSON(wade.getMementoHistory(0)));//[{"name":"lili","age":"10"},{"name":"lili","age":"13"}]

[Basic addition, deletion, modification and query function](https://github.com/engwen/owlMagicComment/blob/master/readMeEn.md/#CRUD)

> Custom Annotations

1. @OwlCheckParams

Method Notes. This annotation can be used to validate request parameters at the controller layer, including notAllNull (
not all empty), notNull (not empty), and canNull.

(can be empty) three attributes, in order to facilitate the writing of interface documents and post-query code, the
three attributes should be combined to be acceptable to the interface.

With parameters, the parameters in notAllNull cannot all be null, otherwise the interface will return "Request
parameters** cannot all be null" in notNull.

The parameter cannot be null, otherwise the interface will return "Request parameter** cannot be null" and the parameter
in canNull can be null.

Use this annotation to set the default returned object to MsgResultVO
For example:

Original code:

    @RequestMapping("signin")        
    public MsgResultVO signin(OwlUser user) {
        MsgResultVO result = new MsgResultVO();
        if(null == user.getAccount() && null == user.getEmail() && user.getMobile() == null){
            return result.errorResult("account、email、mobile not be all null");
        }
        if(null== user.getPassword()){
            return result.errorResult("password not be all null");
        }
        return owlAuthService.signin(user);
    }

Using annotation Code:

    @RequestMapping("signin")
    @OwlCountTime
    @OwlCheckParams(notAllNull = {"account", "email", "mobile"}, notNull = {"password"})
    public Object signin(@RequestBody OwlUser user) {
        return owlAuthService.signin(user);
    }

it will return {"result":false,"resultCode":"0002","resultMsg":"request params account,email,mobile cannot be all
null.","resultData":null,"params":{}}
or {"result":false,"resultCode":"0002","resultMsg":"request params password can`t be null.","resultData":null,"
params":{}}
or {"result":true,"resultCode":"0000","resultMsg":"request success.","resultData":null,"params":{}}
Save time for writing code.

1. @OwlLogInfo

   Class and method annotations. Print the information entering the interface at the beginning of the interface. "Now
   in%s"

   For example:

   Request auth/signin

            @OwlLogInfo
            @RestController
            @RequestMapping(value = "auth", method = RequestMethod.POST, consumes = "application/json")
            public class AuthController {
           
                @RequestMapping("signin")
                @OwlCheckParams(notAllNull = {"account", "email", "mobile"}, notNull = {"password"})
                public MsgResultVO signin(@RequestBody OwlUser user) {
                    return owlAuthService.signin(user);
                }
            }

   Will print

        [INFO] [2019-06-1009:24:23] [com.owl.shiro.controller.AuthController] - now in signin

1. @OwlCountTime
   Method Notes. The time difference used to calculate the start and end

            @RequestMapping("signin")
            @OwlCheckParams(notAllNull = {"account", "email", "mobile"}, notNull = {"password"})
            @OwlCountTime
            public MsgResultVO signin(@RequestBody OwlUser user) {
                return owlAuthService.signin(user);
            }

   Printing

        [INFO][2019-06-10 10:59:58][com.owl.shiro.controller.AuthController] - method name: signin cost: 0.21 seconds

1. @OwlSetNullData

   Method Notes. Used to set request parameters or return parameters to null
   For example:

            @RequestMapping(value = "list")
            @OwlSetNullData(paramsValue = {"id"}, backValue = {"password"})
            public MsgResultVO<List<OwlUser>> list(@RequestBody OwlUser user) {
                 return owlUserService.list(user);
            }

1. @Owltry
   Method Notes. Using it is equivalent to adding a try catch method to the outermost layer of the method. His return
   value is MsgResultVO.
1. @OwlBackToObjectAS
   Method Notes. When you don't want to use MsgResultVO, you can use it to change MsgResultVO to other types of return
   values.
   You only need to provide the path classPath and the existing result values, result codes, result information, result
   objects, etc. to change the return value into what you want.
   object

                @RequestMapping("signin")
                @OwlCheckParams(notAllNull = {"account", "email", "mobile"}, notNull = {"password"})
                @OwlBackToObject(classPath = "com.owl.shiro.model.TestVO",msg = "msg",code = "mdg",data = "data")
                public Object signin(@RequestBody OwlUser user) {
                    return owlAuthService.signin(user);
                }

   Return to TestVO:

   {"mdg":"0000","msg":"请求成功","data":{"id":1,"name":"系统管理员","password":null,"account":"admin",
   "email":"admin@admin.com","mobile":"18812345678","isBan":false,"status":true,"lastSigninTime":1558060991000}

   The previous return value object was MsgResultVO:

        {"result":true,"resultCode":"0000","resultMsg":"请求成功","resultData":{"id":1,"name":"系统管理员",
        "password":null,"account":"admin","email":"admin@admin.com","mobile":"18812345678","isBan":false,
        "status":true,"lastSigninTime":1558060991000}

   Remember that you need to modify the return value to be an object object object when using this annotation

1. @OwlBackToMsgResultAS

   Method Notes. When you want to use MsgResultVO as a return value, you can use it to change other types of return
   values to MsgResultVO. Usage and method
   @OwlBackToObjectAS

   Almost the same. But you don't need to specify classPath

> observer model The observer model is inspired by the event monitoring mechanism in Flex AS

The goal is to execute the specified listening event code when a specified event is received, and one observer can
listen for multiple events.

1. Create OwlObserverEvent events

1. Create the Observed class and inherit the OwlObserved class

1. Adding event listeners to the observer and writing the logical process of listening event post-processing in a custom
   method

1. Throw the OwlObserverEvent event at the appropriate time

   Note!!!! Because the observer described here will execute the code when listening to the specified event, the lamda
   expression is used.

   For example:

        public class TestOb extends OwlObserved {
                // Code to be executed by the observer
                public Consumer<OwlObserved> SystemOutYYYYY() {
                // Do whatever you want to do. Don't forget that SpringContextUtil in this package can help you get beans here.
                    return  (obj)-> System.out.println("yyyyyyyy");
                }
                // Code to be executed by the observer
                public Consumer<OwlObserved> SystemOutHHHH() {
                // Do whatever you want to do. Don't forget that SpringContextUtil in this package can help you get beans here.
                    return  (obj)-> System.out.println("hhhhhhh");
                }
                //also, you can use this
                public static void SystemOutYYYYY(OwlObserved owlObserved) {
                  //ode to be executed by the observer，Do whatever you want to do
                    System.out.println("----------yyy---------");
                }
        }

   after

        @Test
        public void test() {
            TestOb testOb = new TestOb();
            OwlObserverEvent Y_EVENT = new OwlObserverEvent("SystemOutYYYYY");
            OwlObserverEvent H_EVENT = new OwlObserverEvent("SystemOutHHHH");
            OwlObserverEvent Y_LAMDA1_EVENT = new OwlObserverEvent("lamda1");
            testOb.addEventListen(Y_EVENT, testOb.SystemOutYYYYY());
            testOb.addEventListen(H_EVENT, testOb.SystemOutHHHH());
            //---------------------------------------------------
            testOb.addEventListen(Y_LAMDA_EVENT, TestOb::SystemOutYYYYY);
            OwlObserverAB.dispatchEvent(Y_EVENT, testOb.getClass());// For a specified event, the specified class accepts the event
            OwlObserverAB.dispatchEvent(H_EVENT);// For a specified event, all the classes that listen for that event
            testOb.dispatchEvent(H_EVENT);// == OwlObserverAB.dispatchEvent(H_EVENT); For a specified event, all the classes that listen for that event
            testOb.removeListen(Y_EVENT);
            OwlObserverAB.dispatchEvent(Y_EVENT);
            testOb.addEventListen(Y_EVENT, testOb.SystemOutYYYYY());
            OwlObserverAB.removeEventListen(Y_EVENT,testOb);
            OwlObserverAB.dispatchEvent(Y_EVENT);
        }


1. You can also use OwlObserver Util directly. The listening events he declares will also be triggered by the above
   methods, but please note that

   It's just trying to prevent you from listening to and throwing events when classes are no longer inheritable.

   My advice to you is that inheritance is the best way to achieve this, which is more efficient (you don't need to
   traverse the data), but you can use OwlObserver Util's throw event function

<div id="CRUD"></div>
> MVC abbreviation section (common CRUD usage)

1. Spring ContextUtil
   I provide this tool to facilitate you to quickly get the specified beans in multithreading
1. Dao
   The Dao class interface in this template needs to inherit CellBaseDao < T > or RelationBaseDao < T > and you can
   write your own method after inheritance.
   For example:

        public interface OwlMenuDao extends CellBaseDao<OwlMenu> {
            Set<OwlMenu> menuListByRole(IdListSO idListSO);
        }

1. XML
   CellDemo. XML and RelationDemo. XML in this template are packaged in jar. You can directly copy the contents into
   your own XML file.
   Specify <mapper namespace="com.owl.dao.OwlUser Mapper"> and modify the configuration properties in it. Table_ID_Name
   is the ID name of the current table.
   Fuzzy queries represented by SQL Select_Life, Select_Exact for exact queries
   Note <! - The following does not need to be changed - > below is the code that does not need to be modified. But
   before that, you need to modify the attributes to make it and your database tables
   One-to-one correspondence
1. Service
   The Service layer needs to inherit CellBaseService Ab < T > or RelationBaseService Ab < T >.
   And through

              @Resource
                private OwlMenuDao owlMenuDao;
              @Autowired
                public void setCellBaseDao() {
                    super.setCellBaseDao(owlMenuDao);
                }
   Or:

               @Resource
               private OwlPageMenuDao owlPageMenuDao;
           
               @Autowired
               public void setRelationBaseDao() {
                   super.setRelationBaseDao(owlPageMenuDao);
               }    

   The method injects the Dao class into the template, and you can rewrite the method in this class so that it can
   process the business in your way.

1. Controller
   The CellBaseController class defines basic add-delete checks

            MsgResultVO<T> create(T model);

            MsgResultVO<?> createList(List<T> list);

            MsgResultVO delete(T model);

            MsgResultVO deleteList(DeleteDTO deleteDTO);

            MsgResultVO banOrLeave(BanDTO banDTO);

            MsgResultVO banOrLeaveList(BanListDTO banListDTO);

            MsgResultVO<?> update(T model);

            MsgResultVO<T> details(T model);

            MsgResultVO<PageVO<T>> list(PageDTO<T> pageDTO);
        
            MsgResultVO<List<T>> getAll(T model);
        
            MsgResultVO<?> isExist(T model);

   After inheriting CellBaseController Ab, you can quickly invoke the methods defined in the interface.

### The jar packages introduced in this package include

    
-------

```
        <dependency>
               <groupId>com.github.engwen</groupId>
               <artifactId>owlMagicUtil</artifactId>
               <version>2.3</version>
        </dependency>
        <!--aop 核心依赖以及完成本功能所需依赖 -->
       <dependency>
                  <groupId>javax</groupId>
                  <artifactId>javaee-api</artifactId>
                  <version>7.0</version>
              </dependency>
      
              <dependency>
                  <groupId>org.springframework</groupId>
                  <artifactId>spring-web</artifactId>
                  <version>4.2.5.RELEASE</version>
              </dependency>
      
              <dependency>
                  <groupId>org.aspectj</groupId>
                  <artifactId>aspectjrt</artifactId>
                  <version>1.9.1</version>
              </dependency>
      
              <!-- 添加日志相关jar包 -->
              <dependency>
                  <groupId>log4j</groupId>
                  <artifactId>log4j</artifactId>
                  <version>1.2.17</version>
              </dependency>
```

### History Upgrade Record

 -------
1.1

- add

@OwlLogInfo

The annotation will print "now in class% s, method% s" where it is used. Note that this doesn't give you any additional
useful information, just with
To output some logs to help you quickly locate the interface being invoked.

1.1.1

- Upgrading

Fixed some BUGs and optimized some code

1.1.2

- Upgrading

Remove the []from the return value and optimize some code

- add

@OwlSetNullData (value={""}) or @OwlSetNullData ("")@OwlSetNullData ({""})

This annotation will clear the specified return value where it is used. Sometimes some attributes don't want to be
returned to the front desk, but it's very difficult to modify them later.
Changes to SQL or service often lead to unexpected events in other places where they are used. Based on these
considerations, I added this annotation for the purpose of
Return value is removed.
Note: This method only supports encapsulated objects in MsgResultVO, that is, like the OwlCheckParams interface, you
need to set the return value
For MsgResultVO<T>, you can put the returned object in resultData, but it must be a custom object or a Map, and no other
object will be supported.
The attributes it supports are just wrapper classes, String, Long, Integer, Float, Double, List and Date.

For example:

        @RequestMapping("/signin")
        @OwlCheckParams(notNull={"account","password"})
        @OwlSetNullData("password")
        public MsgResultVO signin(User user) {
            return userService.signin(user);
        }
        返回值
        {"result":true,"resultCode":"0000","resultMsg":"请求成功","resultData":{"id":1,"name":"admin",
        "password":null,"account":"admin","email":"admin@admin.com","mobile":null,"status":true,
        "lastSigninTime":1541668514000,"createTime":1541668514000,"updateTime":1542622682000},"params":{}}

1.1.3

- Upgrading（@OwlSetNullData）

change @OwlSetNullData(value={})  to @OwlSetNullData (paramsValue={""}) or @OwlSetNullData (backValue={"})

Add the set request parameter set paramsValue to null, and set the set of return parameters to nul to backValue.

1.1.4

-add

@OwlBackToObject(msg="",code="",data="",classPath="com.*.*.TestVO")

It's use the returns value type, the msg code dataset to the returns value type(classPath="com.*.*.*.TestVO"),
In the returning type, the methods returns value to Objectobject

For example:

        @RequestMapping("/test1")
        @OwlBackToObject(msg = "msg",code = "code",data = "data",classPath = "com.owl.shiro.util.TestVO")
        public Object test1() {
            MsgResultVO result = new MsgResultVO();
            result.errorResult(MsgConstant.REQUEST_NO_SIGNIN);
            result.setResultData(new Date());
            return result;
        }

In example, returns value types to the MsgResultVO to TestVO, contents: {"msg":"User is登录","code","0004","data":
1545889878416}

-add

@OwlBackToMsgResult(msg="",code="",data="")

It's use the returns value type, and the msg code dataset to MsgResultVO,
In the returning type, the methods returns value to Objectobject

For example:

        @RequestMapping("/test2")
        @OwlBackToMsgResult(msg = "msg",code = "code",data = "data")
        public Object test2() {
               TestVO<Date> result = new TestVO();
               result.setMsg("test");
               result.setCode("0000");
               result.setData(new Date());
               return result;
        }

In example, returns value types to the TestVO to MsgResultVO, content to: {"result":null,"resultCode","0000","
resultMsg","test","resultData":1545890084447,"params": {}}

1.1.5 - 1.1.7

-add.
@OwlTry.
> now you can stop writing try catch, and of course, the default return object is still MsgResultVO, which is really a
> pity if you can't use it.

MVC abstraction module.
> add controller service dao base classes and their utility class implementations, normal classes now you just need to
> inherit CellBaseControllerAb CellBaseServiceAb.
> And CellBaseDao can use the corresponding tool class CellBaseControllerUtil CellBaseServiceUtil tool class to complete
> the basic curd functionality.
> List object support Pagination. The relational class, which provides RelationBaseControllerAb and RelationBaseServiceAb,
> as well as the corresponding xml at the bottom of the Util, has also been templated.
> For consistency, it is recommended that you use the object that receives the data for DTO, to return the object for the
> VO, database corresponding to model.

Tool acquisition for Bean.
> SpringContextUtil can get bean objects directly from class or injected names, making it more friendly to multithreaded
> programs.

-Optimization.
> now checks the annotation of the parameter to check the parent class of the object.
> to resolve exception checking, all try catch in the MVC schema will be open in version 1.1.7

1.1.8

- Optimization

> On the basis of the previous version, the implementations of CellBaseService Util and RelationBaseService Util tool
> classes are removed, and their work is moved to abstract classes.
> Now you can inherit CellBaseServiceAb and RelationBaseServiceAb using the service class to provide good CRUDs among
> them. I won't recommend you
> Use these two tool classes any more, but you can still use them

> Adding IdListSO to handle ID collection problems

> Now you need to use the set * Dao method when inheriting CellBaseService Ab and RelationBaseService Ab to make the
> abstract class understand what you are going to use
> Which is the Dao, for example:

     @Resource
     Private OwlMenuDao owlMenuDao;
     @Autowired
     Public void setCellBaseDao (){
        Super.setCellBaseDao(owlMenuDao);
     }

> @OwlSetNullDataAS can now solve the property setting problem of the contained object when it contains the object.

> @OwlCheckParamsAS can now solve the property setting problem of the contained object when it contains the object.

> @OwlBackToObjectAS can now solve the property setting problem of the contained object when it contains the object.

> The query corresponding to the underlying database now sorts by name

> Now MsgResultVO supports getInstance Success to get objects

> Adding ModelSO to handle accepting models has solved the problem that underlying XML cannot be unified

> RelationBaseDao adds delete single operation

> @OwlTry now provides value for easy output when used

> Lots of code structure optimization

- Add

> Adding Observer Mode



1.1.9

- Add

> Memorandum mode OwlMemento

Inheriting OwlMemento's classes will give you the memo function, which will restore class attribute data to the time
when the memo function is used after executing the code
At the same time, it provides a collection of historical data that you can restore to any time you use the memo
function. Provide a clear method to facilitate
You clear the cached data after using the memo

- Add

> Simplified version of event mechanism tool class OwlObserver Util

Although it provides almost the same functionality as inheritance classes, and has been basically connected with the
functionality of the reason - and the inheritance classes can respond to each other, I recommend that you implement it
through inheritance methods.
It's just a variant of how the target class can still listen if it can't use extension again.

- Optimizing

> Now the Observer mode uses thread-safe Concurrent HashMap to store data

> After the observer receives the event, it opens a new thread to process all the observer's code to be executed.

> To prevent conflicts between different versions of loggers, now all loggers in jar are in a format similar to log4j
> based on System. out

> Several ways to modify the observer are now easier to use

> Modify code delivery to custom lambda delivery

> Added a cache thread pool

> Now even with array objects, toJSON still outputs the results correctly

## How To Use?

* Package name
  com.owl.comment.annotations
* The way of reference is

```
<dependency>
    <groupId>com.github.engwen</groupId>
    <artifactId>owlMagicComment</artifactId>
    <version>*.*.*</version>
</dependency>
```

This package relies on my other project, the OwlMagicUtil package, is available
at https://github.com/engwen/owlMagicUtil.

    <context: component-scan base-package="com.owl.comment.annotations"/>Spring MVC project needs to add the following configuration in the configuration file of the spring MVC Servlet

    <aop: aspectj-autoproxy/>

SpringBoot users need to configure scans on project startup classes

    @ComponentScan (base Packages = {"***", "com. owl", "***"})
