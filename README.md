# SpringMVC：整合SSM

## 项目结构图

![](D:\Workspace\idea-workspace\ssm_books\technological-process.png)


## 1、环境要求

- IDEA
- MySQL 5.5
- Tomcat 8
- Maven 3.6

要求：

- 需要熟练掌握MySQL数据库
- Spring
- JavaWeb
- Mybatis
- 简单的前端知识

## 2、数据环境

### 创建一个存放书籍数据的数据库

```mysql
CREATE DATABASE `ssmlibrary`

USE `ssmlibrary`

CREATE TABLE `books`(
	`bookId` INT(10) NOT NULL AUTO_INCREMENT COMMENT '书ID',
	`bookName` VARCHAR(200) NOT NULL COMMENT '书名',
	`bookCounts` INT(11) NOT NULL COMMENT '数量',
	`detail` VARCHAR(200) NOT NULL COMMENT '描述',
	KEY `bookId` (`bookId`)
 ) ENGINE=INNODB DEFAULT CHARSET=utf8

INSERT INTO `books` (`bookId`,`bookName`,`bookCounts`,`detail`) VALUES
(1,'Java',1,'从入门到放弃'),
(2,'Mysql',5,'从删库到跑路'),
(3,'Linux',10,'从进门到入牢')`books`
```

## 3、基本环境搭建

### 1. 新建一个Maven项目！ssmlibrary，添加web支持

### 2. 导入相关的pom依赖！

   ```xml
<!--依赖：junit，数据库驱动，连接池，servlet，jsp，Mybatis，Mybatis-Spring，Spring-->
<dependencies>
    <!--junit-->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.13</version>
    </dependency>
    <!--数据库驱动-->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.37</version>
    </dependency>
    <!--数据库连接池：c3p0：dbcp-->
    <dependency>
        <groupId>com.mchange</groupId>
        <artifactId>c3p0</artifactId>
        <version>0.9.5.5</version>
    </dependency>

    <!--Servlet，JSP-->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>4.0.1</version>
    </dependency>
    <dependency>
        <groupId>javax.servlet.jsp</groupId>
        <artifactId>jsp-api</artifactId>
        <version>2.2</version>
    </dependency>
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>jstl</artifactId>
        <version>1.2</version>
    </dependency>

    <!--Mybatis-->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.5.4</version>
    </dependency>
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis-spring</artifactId>
        <version>2.0.4</version>
    </dependency>

    <!--Spring-->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.2.5.RELEASE</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-jdbc</artifactId>
        <version>5.2.5.RELEASE</version>
    </dependency>

    <!--aspectj事务-->
    <dependency>
        <groupId>org.aspectj</groupId>
        <artifactId>aspectjweaver</artifactId>
        <version>1.9.5</version>
    </dependency>

    <!--lombok-->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.10</version>
    </dependency>
</dependencies>
   ```

### 3. Maven资源过滤设置

```xml
<!--静态资源导出问题-->
<build>
    <resources>
        <resource>
            <directory>src/main/resources</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
            <filtering>true</filtering>
        </resource>
        <resource>
            <directory>src/main/java</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
            <filtering>true</filtering>
        </resource>
    </resources>
</build>

<properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>
```

### 4. 建立基本结构和配置框架！

   - com.ycztt.pojo

   - com.ycztt.dao/mapper

   - com.ycztt.service

   - com.ycztt.controller

   - mybaits-config.xml

     ```xml
     <?xml version="1.0" encoding="UTF-8" ?>
     <!DOCTYPE configuration
             PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
             "http://mybatis.org/dtd/mybatis-3-config.dtd">
     <configuration>
         
     </configuration>
     ```

   - appliczationContext.xml

     ```xml
     <?xml version="1.0" encoding="UTF-8"?>
     <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="
            http://www.springframework.org/schema/beans
            http://www.springframework.org/schema/beans/spring-beans.xsd">
     
     </beans>
     ```

## 4、Mybatis层编写

### 1. 数据库配置文件 database.properties

   ```java
jdbc.driver=com.mysql.jdbc.Driver
    jdbc.url=jdbc:mysql://localhost:3306/ssmlibrary?useSSL=true&useUnicode=true&characterEncoding=utf8&serverTimezone=UTC
jdbc.username=root
jdbc.password=123
   ```

### 2. IDEA关联数据库

### 3. 编写Mybaits的核心配置文件

   ```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <!--配置数据源，交给Spring去做-->
    
    <!--日志-->
    <settings>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
    </settings>

    <!--配置别名-->
    <typeAliases>
        <package name="com.yccztt.bean"/>
    </typeAliases>

    <!--绑定接口-->
    <mappers>
        <mapper class="com.yccztt.dao.BookMapper"/>
    </mappers>

</configuration>
   ```

### 4. 编写数据库对应的实体类	com.yccztt.pojo.Books

```java
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Books {
    private int bookId;
    private String bookName;
    private int bookCounts;
    private String detail;
}
```

### 5、编写Dao层的 Mapper 接口！

```java
public interface BookMapper {
    //新增一本书
    int addBooks(Books books);

    //删除一本书
    int deleteBook(@Param("bookId") int id);

    //修改一本书
    int updateBook(Books books);

    //查询一本书
    Books selectBookById(@Param("bookId") int id);

    //查询所有书
    List<Books> selectBooks();

    //根据书籍名称查询书籍
    Books queryBookByName(@Param("bookName") String bookName);
}
```

### 6、编写接口对应的 Mapper.xml 文件。需要导入Mybatis的包；

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC
        "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.yccztt.dao.BookMapper">
    <insert id="addBooks" parameterType="Books">
        insert into books (bookId,bookName,bookCounts,detail) values (#{bookId},#{bookName},#{bookCounts},#{detail})
    </insert>

    <delete id="deleteBook" parameterType="int">
        delete from books where bookId=#{bookId}
    </delete>

    <update id="updateBook" parameterType="Books">
        update books set bookName=#{bookName},bookCounts=#{bookCounts},detail=#{detail} where bookId=#{bookId}
    </update>

    <select id="selectBookById" parameterType="int" resultType="Books">
        select * from books where bookId=#{bookId}
    </select>

    <select id="selectBooks" resultType="Books">
        select * from books
    </select>

    <select id="queryBookByName" resultType="Books">
        select * from books where bookName = #{bookName}
    </select>
</mapper>
```

### 7、编写Service层的接口和实现类

接口：

```java
public interface BookService {
    //新增一本书
    int addBooks(Books books);

    //删除一本书
    int deleteBook(int id);

    //修改一本书
    int updateBook(Books books);

    //查询一本书
    Books selectBookById(int id);

    //查询所有书
    List<Books> selectBooks();

    //根据书籍名称查询书籍
    Books queryBookByName(String bookName);
}
```

实现类：

```java
public class BookServiceImpl implements BookService{

    //service层调dao层：组合Dao层，设置一个set接口，方便Spring管理
    private BookMapper bookMapper;
    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Override
    public int addBooks(Books books) {
        return bookMapper.addBooks(books);
    }

    @Override
    public int deleteBook(int id) {
        return bookMapper.deleteBook(id);
    }

    @Override
    public int updateBook(Books books) {
        return bookMapper.updateBook(books);
    }

    @Override
    public Books selectBookById(int id) {
        return bookMapper.selectBookById(id);
    }

    @Override
    public List<Books> selectBooks() {
        return bookMapper.selectBooks();
    }

    @Override
    public Books queryBookByName(String bookName) {
        return bookMapper.queryBookByName(bookName);
    }
}
```

**OK，到此，底层需求操作编写完毕！**

## 5、Spring层

### 1、配置Spring整合Mybatis，我们这里数据源使用c3p0连接池；

### 2、我们去编写Srping整合Mybatis的相关的配置文件；spring-dao.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       https://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 配置整合mybatis -->
    <!-- 1.关联数据库文件 -->
    <context:property-placeholder location="classpath:db.properties"/>

    <!-- 2.数据库连接池 -->
    <!--数据库连接池
        dbcp 半自动化操作 不能自动连接
        c3p0 自动化操作（自动的加载配置文件 并且设置到对象里面）
    -->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <!-- 配置连接池属性 -->
        <property name="driverClass" value="${jdbc.driver}"/>
        <property name="jdbcUrl" value="${jdbc.url}"/>
        <property name="user" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>

        <!-- c3p0连接池的私有属性 -->
        <property name="maxPoolSize" value="30"/>
        <property name="minPoolSize" value="10"/>
        <!-- 关闭连接后不自动commit -->
        <property name="autoCommitOnClose" value="false"/>
        <!-- 获取连接超时时间 -->
        <property name="checkoutTimeout" value="10000"/>
        <!-- 当获取连接失败重试次数 -->
        <property name="acquireRetryAttempts" value="2"/>
    </bean>

    <!-- 3.配置SqlSessionFactory对象 -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <!-- 注入数据库连接池 -->
        <property name="dataSource" ref="dataSource"/>
        <!-- 配置Mybatis全局配置文件:mybatis-config.xml -->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
    </bean>

    <!-- 4.配置扫描Dao接口包，动态实现Dao接口注入到spring容器中 -->
    <!--解释 ：https://www.cnblogs.com/jpfss/p/7799806.html-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!-- 给出需要扫描Dao接口包 -->
        <property name="basePackage" value="com.yccztt.dao"/>
        <!-- 注入sqlSessionFactory -->
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
    </bean>

</beans>
```

### 3、Spring整合service层

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context
                           https://www.springframework.org/schema/context/spring-context.xsd
                           http://www.springframework.org/schema/aop
                           https://www.springframework.org/schema/aop/spring-aop.xsd
                           http://www.springframework.org/schema/tx
                           http://www.springframework.org/schema/tx/spring-tx.xsd">

    <!-- 扫描service相关的bean -->
    <context:component-scan base-package="com.yccztt.service"/>

    <!--BookServiceImpl注入到IOC容器中-->
    <bean id="bookServiceImpl" class="com.yccztt.service.BookServiceImpl">
        <property name="bookMapper" ref="bookMapper"/>
    </bean>

    <!-- 配置事务管理器 -->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <!-- 注入数据库连接池 -->
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!--结合AOP实现事务的织入-->
    <!--配置事务通知：-->
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <!--给那些方法配置事务-->
        <tx:attributes>
            <tx:method name="*" propagation="REQUIRED"/>
        </tx:attributes>
    </tx:advice>

    <!--配置事务切入-->
    <aop:config>
        <aop:pointcut id="txPointcut" expression="execution(* com.yccztt.dao.*.*(..))"/>
        <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointcut"/>
    </aop:config>

</beans>
```

Spring层搞定！再次理解一下，Spring就是一个大杂烩，一个容器！对吧！

## 6、SpringMVC层

### 1、web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!--DispatcherServlet-->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <!--一定要注意:我们这里加载的是总的配置文件-->
            <param-value>classpath:applicationContext.xml</param-value>
        </init-param>
    </servlet>
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

    <!--encodingFilter-->
    <filter>
        <filter-name>encodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <!--Session过期时间-->
    <session-config>
        <session-timeout>15</session-timeout>
    </session-config>

</web-app>
```

### 2、spring-mvc.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/mvc
       https://www.springframework.org/schema/mvc/spring-mvc.xsd
       http://www.springframework.org/schema/context
       https://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 配置SpringMVC -->
    <!-- 1.开启SpringMVC注解驱动 -->
    <mvc:annotation-driven/>

    <!-- 2.静态资源默认servlet配置-->
    <mvc:default-servlet-handler/>

    <!-- 3.配置jsp 显示ViewResolver视图解析器 -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>

    <!-- 4.扫描web相关的bean -->
    <context:component-scan base-package="com.yccztt.controller"/>

</beans>
```

### 3、Spring整合配置文件，applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <import resource="classpath:spring-dao.xml"/>
    <import resource="classpath:spring-service.xml"/>
    <import resource="classpath:spring-mvc.xml"/>

</beans>
```

## 7、Controller 和 视图层编写

### 1、BookController 类，方法一：查询所有书籍

```java
@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    @Qualifier("bookServiceImpl")
    private BookService bookService;

    /**
     * 查询全部书籍，返回给页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/allBooks")
    public String getBooks(Model model) {
        List<Books> books = bookService.selectBooks();
        model.addAttribute("books", books);
        return "allBooks";
    }
}
```

### 2、编写首页 index.jsp

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>首页</title>
    <style>
      a{
        text-decoration: none;
        color: black;
        font-size: 18px;
      }
      h3{
        width: 180px;
        height: 38px;
        margin: 100px auto;
        text-align: center;
        line-height: 38px;
        background: skyblue;
        border-radius: 5px;
      }
    </style>
  </head>
  <body>
  <h3>
    <a href="${pageContext.request.contextPath}/book/allBooks">点击进入</a>
  </h3>
  </body>
</html>
```

### 3、书籍列表页面 allBooks.jsp

```html
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>书籍展示</title>
    <%--Bootstrap美化界面--%>
    <link href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>书籍列表 ———— 显示所有书籍</small>
                </h1>
            </div>
            <div class="row">
                <div class="col-md-4 column">
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/book/toAddBook">新增书籍</a>&nbsp;
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/book/allBooks">查看所有书籍</a>
                </div>
                <div class="col-md-4 column col-md-offset-4">
                    <%--查询功能--%>
                    <form action="${pageContext.request.contextPath}/book/queryBook" method="post" class="form-inline">
                        <span style="color: red;font-weight: bold">${error}</span>
                        <div class="form-group">
                            <input type="text" class="form-control" name="queryBookName" placeholder="请输入要查询的书籍名称">
                        </div>
                        <input type="submit" class="btn btn-default" value="查询"/>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-hover table-striped">
                <thead>
                    <tr>
                        <th>书籍编号</th>
                        <th>书籍名称</th>
                        <th>书籍数量</th>
                        <th>书籍详情</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <%--书籍从数据库查询出来，从这个List中遍历出来：foreach--%>
                <tbody>
                    <c:forEach var="book" items="${books}">
                        <tr>
                            <td>${book.bookId}</td>
                            <td>${book.bookName}</td>
                            <td>${book.bookCounts}</td>
                            <td>${book.detail}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/book/toUpdatePage?id=${book.bookId}">修改</a>&nbsp;|&nbsp;
                                <a href="${pageContext.request.contextPath}/book/deleteBook/${book.bookId}">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>

```

### 4、BookController 类编写，方法二：添加书籍

```java
    /**
     * 跳转到新增书籍页面
     *
     * @return
     */
    @RequestMapping("/toAddBook")
    public String getAddPage() {
        return "addBook";
    }

    /**
     * 添加书籍的请求
     *
     * @return
     */
    @RequestMapping("/addBook")
    public String addBook(Books books) {
        System.out.println("addBook=>" + books);
        bookService.addBooks(books);
        return "redirect:/book/allBooks"; //重定向到我们的@RequestMapping("/allBooks")请求
    }
```

### 5、添加书籍页面：addBook.jsp

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>新增</title>
    <%--Bootstrap美化界面--%>
    <link href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>新增书籍</small>
                </h1>
            </div>
        </div>
    </div>

    <form action="${pageContext.request.contextPath}/book/addBook" method="post">
        <div class="form-group">
            <label for="bkname">书籍名称</label>
            <input type="text" name="bookName" class="form-control" id="bkname" required>
        </div>
        <div class="form-group">
            <label for="bkcounts">书籍数量</label>
            <input type="text" name="bookCounts" class="form-control" id="bkcounts" required>
        </div>
        <div class="form-group">
            <label for="bkdetail">书籍介绍</label>
            <input type="text" name="detail" class="form-control" id="bkdetail" required>
        </div>
        <div class="form-group">
            <input type="submit" class="form-control" value="添加">
        </div>
    </form>

</div>
</body>
</html>
```

### 6、BookController 类编写，方法三：修改书籍

```java
	/**
     * 跳转到修改书籍页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/toUpdatePage")
    public String toUpdatePage(int id, Model model) {
        Books books = bookService.selectBookById(id);
        model.addAttribute("QBook", books);
        return "updateBook";
    }

    /**
     * 修改书籍的请求
     *
     * @param books
     * @return
     */
    @RequestMapping("/updateBook")
    public String updateBook(Books books) {
        System.out.println("updateBook=>" + books);
        int i = bookService.updateBook(books);
        if (i > 0) {
            System.out.println("修改成功：" + books);
        }
        return "redirect:/book/allBooks"; //重定向到我们的@RequestMapping("/allBooks")请求
    }
```

### 7、修改书籍页面 updateBook.jsp

```html
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改</title>
    <%--Bootstrap美化界面--%>
    <link href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>修改书籍</small>
                </h1>
            </div>
        </div>
    </div>

    <form action="${pageContext.request.contextPath}/book/updateBook" method="post">
        <input type="hidden" name="bookId"  value="${QBook.bookId}">
        <div class="form-group">
            <label for="bkname">书籍名称</label>
            <input type="text" name="bookName" class="form-control" id="bkname" value="${QBook.bookName}" required>
        </div>
        <div class="form-group">
            <label for="bkcounts">书籍数量</label>
            <input type="text" name="bookCounts" class="form-control" id="bkcounts" value="${QBook.bookCounts}" required>
        </div>
        <div class="form-group">
            <label for="bkdetail">书籍介绍</label>
            <input type="text" name="detail" class="form-control" id="bkdetail" value="${QBook.detail}" required>
        </div>
        <div class="form-group">
            <input type="submit" class="form-control" value="修改">
        </div>
    </form>

</div>
</body>
</html>
```

### 8、BookController 类编写，方法四：删除书籍

```java
    /**
     * 删除书籍的请求
     *
     * @param id
     * @return
     */
    @RequestMapping("/deleteBook/{bookId}")
    public String deleteBook(@PathVariable("bookId") int id) {
        int i = bookService.deleteBook(id);
        if (i > 0) {
            System.out.println("删除成功");
        }
        return "redirect:/book/allBooks"; //重定向到我们的@RequestMapping("/allBooks")请求
    }
```

### 9、BookController 类编写，方法五：查询书籍

```java
    /**
     * 查询书籍的请求
     *
     * @return
     */
    @RequestMapping("/queryBook")
    public String queryBookByName(String queryBookName, Model model) {
        Books bookByName = bookService.queryBookByName(queryBookName);
        System.err.println("queryBook=>" + bookByName);
        List<Books> books = new ArrayList<Books>();
        books.add(bookByName);
        if (bookByName == null) {
            books = bookService.selectBooks();
            model.addAttribute("error", "未查到相关书籍");
        } else {
            model.addAttribute("error", "");
        }
        model.addAttribute("books", books);
        return "allBooks";
    }
```

### 10、配置Tomcat，进行运行

