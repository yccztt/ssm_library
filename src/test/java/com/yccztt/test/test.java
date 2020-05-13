package com.yccztt.test;

import com.yccztt.bean.Books;
import com.yccztt.service.BookServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @author yccztt
 * @create 2020--12 11:34
 */
public class test {
    @Test
    public void test(){
        ApplicationContext as = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookServiceImpl bookServiceImpl = (BookServiceImpl) as.getBean("bookServiceImpl");
        List<Books> books = bookServiceImpl.selectBooks();
        for (Books book : books) {
            System.out.println(book);
        }
    }
}
