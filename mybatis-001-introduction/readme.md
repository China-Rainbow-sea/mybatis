# 开发我的第一个MyBatis 程序

1. resources 目录
> 放在这个目录当中，一般都是资源文件，配置文件，直接放到resources 目标下的资源，等
> 同于放到了类的根路径下。
>
2. 开发步骤
> 第一步：打包方式jar
> 第二步：引入依赖
>   mybatis依赖 ，mysql依赖
> 第三步：编写 mybatis核心配置文件,mybatis-config.xml
>   注意: 
>           第一,这个文件名不是必须叫 mybatis-config.xml,可以用其他的名字，只是大家都采用这个名字
>           第二,这个文件存放的位置也不是固定的，可以随意，但一般情况下，会放到类的根路径下
>第四步：编写XxxMapper.xml 文件
> 从这个配置文件当中编写SQL语句。
> 这个文件名也不是固定的，放的位置也不是固定，我们这里给它起个名字，叫做：CarMapper.xml
> 把它暂时放到类的根路径下
> 第五步： 在mybatis-config.xml 文件中指定XxxMapper.xml 文件的路径
> <mapper resource="CarMapper.xml">
> 注意：resource 属性会自动从类的根路径下开始查找资源。
> 第六步: 编写MyBatis 程序(使用MyBatis的类库，编写myBatis 程序，该数据库，做增删改查就行了)
```text
在MyBatis当中，负责执行SQL语句的哪个对象叫做什么呢？
SqlSession 
SqlSession 是专门来执行SQL语句的，是一个Java程序和数据库之间的一次会话
要想获得SqlSession对象，需要先获取SqlSessionFactory对象，通过SqlSessionFactory工厂来生产SqlSession对象。
怎么获取SqlSessionFactory对象呢？
    需要首先获取SqlSessionFactoryBuilder 对象。
    通过SqlSessionFactory对象的build方法，来获取一个SqlSessionFactory对象。
   
   
   
```

MyBatis的核心对象包括：
    SqlSessionFactoryBuilder
    SqlSessionFactory
    SqlSession

SqlSessionFactoryBuilder-->SqlSessionFactory-->SqlSession




3. 从xml中构建SqlSessionFactory
> 通过官方的这句话，你能想到什么呢？
>   第一: 在MyBatis中一定是有一个很重要的对象，这个对象是：SqlSessionFactory对象
>   第二: SqlSessionFactory 对象的创建需要xml
>  xml 是什么？它是一个配置文件
> 
4. MyBatis中有两个主要的配置文件
> 其中一个是: mybatis-config.xml ，这是核心配置文件，主要配置连接数据库的信息等(一般是一个数据库一个)
> 另一个是: XxxMapper.xml ，这个文件是专门用来编写SQL语句的配置文件（一个表一个）
>   t_user 表，一般会对应一个UserMapper.xml
>   t_student 表，一般会对应一个StudentMapper.xml



# 关于第一个MyBatis 程序的小细节
> 1.MyBatis 中SQL语句的结尾“；” 可以省略
> 2.Resource.getResourceAsStream
>   小技巧：以后凡是遇到resource 这个单词，大部分情况下，这种加载资源的方式就是从类根路径下开始加载。
>   优点: 采用这种方式，从类的路径当中加载资源，项目的移植性很强，项目从windows移植到Linux
> InputStream stream = new FileInputStream("E:\\mybatis-config.xml"); 采用这种方式也可以
> 缺点：可移植性差，程序不够健壮，可能会移植到其他的操作系统当中，导致以上路径无效，还需要修改Java代码
> 中的路径，这样违背了OCP原则。
> 3.已经验证了。mybatis核心配置文件的名字，不一定是：mybatis-config.xml 
>   mybatis核心配置文件存放的路径，也不一定是在类的根路径下，可以放到其他位置，但为了项目
> 的移植性，健壮性，最好将这个配置文件放到类路径下面。
> 
> InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("mybatis-config.xml")
> ClassLoader.getSystemClassLoader() 获取系统的类加载器。
> 系统类加载器有一个方法叫做：getResourceAsSteam
> 它就是从类路径当中加载资源的
> 通过源代码分析发现：
>   InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
>   底层的源代码其实现就是:
> InputStream is = ClasLoader.getSystemClassLoader().getResourceAsStream("mybatis-config.xml");
> 
> 
> 
> 4.CarMapper.xml 文件的名字是固定的吗？CarMapper.xml 文件的路径是固定的吗？
> 都不是固定的
> <mapper resource="CarMapper.xml"> resource属性；这种方式是从类路径当中加载资源
> <mapper url="file:///e:/C8arMapper.xml"> url 属性；这种方式是从绝对路径当中加载资源

> ````````5. 关于 mybatis 的事务管理机制（深度剖析）
> 在mybatis-config.xml 文件中，可以通过以下的配置进行mybatis的事务管理
> <transactionManager type="JDBC">
> type的属性的值包括两个: 
> JDBC (jdbc)
> MANGED(managed)
> type 后面的值，只有以上两个值可选择，不区分大小写
> 6. 在mybatis中提供了两种事务管理机制
>第一种: JDBC事务管理器
> 第二种: MANAGED 事务管理器
> JDBC 事务管理器
> mybatis 框架自己管理事务，自己采用原生的JDBC代码去管理事务
> conn.setAutoCommit(false);开启事务
> ...业务处理...
> conn.commit(); 手动提交事务
使用JDBC事务管理器的话，底层创建的事务管理器对象，jdbcTransaction对象。
```text
如果你编写的代码是下面的代码：
  SqlSession sqlSession = sqlSessionFactory.openSession(true);
  表示没有开启事务，因为这种方式压根不会执行，com.setAutoCommit(false)
  在JDBC事务中，没有执行com.setAutoCommit(false);那么autoCommit就是true.
  如果autoCommit 是 true ，就表示没有开启事务，只要执行任意一条DML语句就提交了一次
```
> 8. MANAGED 事务管理器；
> mybatis 不再负责事务的管理了，事务管理交给其他容器来负责。例如：spring.
> 我不管事务了，你来负责吧
> 对于我们当前的单纯的只有mybatis的情况下，如果配置为:MANAGED 那么事务着这块
> 是没人管的，没有人管理事务标识事务压根没有开启。后面我们可以配合使用Spring进行事务上的管理

9. JDBC中的事务
> 如果你没有在JDBC代码中执行，com.setAutoCommit(false)的话，默认的autoCommit是true.
> 
>
10.重点
> 以后注意了，只要你的autoCommit是true，就表示没有开启事务，只有你
> 的autoCommit 是false 的时候，就表示开启了事务。````````

7. 关于mybatis 集成日志组件，让我们调试起来更加方便。
mybatis常见的集成的日志组件有哪些呢？
SLF4J(沙拉风)
LOG4J
LOG$J2
STDOUT_LOGGING
...
注意：log4j log4j2 logback都是同一个作者开发的。
其中STDOUT_LOGGING 是标准日志，mybatis已经实现了这种标准日志，mybatis框架本身已经实现了这种
标准。只要开启即可，怎么开启呢？在mybatis-config.xml文件中使用settings标签进行配置开启。
<settings>
    <setting name="logImpl" value="STDOUT_LOGGING"/>
<settings>
这个标签在编写的时候要注意，它应该由现在environments标签之前，注意顺序，当然，不需要记忆这个顺序。
因为有dtd文件进行约束呢。我们只要参考did约束即可。


这种实现也是可以的，可以看到一些信息，比如：连接对象什么时候创建，什么时候关闭，sql语句是怎样的
但是没有详细的日期，线程名字，等，如果你想使用更加丰富的配置，可以集成第三方Log组件。

8. 集成logback日志框架:
```text
<!--        引入 logback的依赖，这个日志框架实现了slf4j 规范-->
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.2.11</version>
        </dependency>
```
> logback 日志框架实现了slf4j标准(沙拉风，日志门面，日志标准)
> 第一步: 引入logback的依赖
> 第二步: 一如logback所必须的xml 配置文件
>   这个配置文件的名字必须叫做： logback.xml 或者 logback-test.xml 不能是其他的名字
> 并且这个配置文件必须放到类的根路径下，不能是其他位置。

