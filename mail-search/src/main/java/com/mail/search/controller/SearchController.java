package com.mail.search.controller;

import com.mail.search.service.MailSearchService;
import com.mail.search.vo.SearchParam;
import com.mail.search.vo.SearchResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class SearchController {

    @Resource
    private MailSearchService mailSearchService;

    /**
     * 自动将页面提交过来的所有请求参数封装成我们指定的对象
     * @param param
     * @return
     */
    @GetMapping(value = "/list.html")
    public String listPage(SearchParam param, Model model, HttpServletRequest request) {

        // 原生 HttpServletRequest 中的 getQueryString 可以拿到 PathVariable(路径中的请求变量)
        param.set_queryString(request.getQueryString());

        //1、根据传递来的页面的查询参数，去es中检索商品
        SearchResult result = mailSearchService.search(param);
        model.addAttribute("result",result);

        return "list";
    }

}
