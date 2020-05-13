package com.yccztt.service;

import com.yccztt.bean.Books;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yccztt
 * @create 2020--12 10:44
 */
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
