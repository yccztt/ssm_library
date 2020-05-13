package com.yccztt.controller;

import com.yccztt.bean.Books;
import com.yccztt.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yccztt
 * @create 2020--12 13:24
 */
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

}
