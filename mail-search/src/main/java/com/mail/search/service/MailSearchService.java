package com.mail.search.service;

import com.mail.search.vo.SearchParam;
import com.mail.search.vo.SearchResult;
import org.springframework.stereotype.Service;

public interface MailSearchService {

    /**
     * @param param 检索的所有参数
     * @return  返回检索的结果，里面包含页面需要的所有信息
     */
    SearchResult search(SearchParam param);
}
