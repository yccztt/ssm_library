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
