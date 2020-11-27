package com.icloud.common.entitys.params;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 分页查询参数
 *
 * @author 42806
 */
@Data
public class QueryPageParam implements Serializable {

    /**
     * 查询参数
     */
    private Map<String, Object> query;

    /**
     * 分页参数
     */
    private PageParam page;


    public Object getQueryValue(String key) {
        if (this.query == null) {
            return "";
        }
        Object value = this.query.get(key);
        return value != null ? value : "";
    }

    public Integer getPageNo() {
        return this.page.getPageNo();
    }

    public Integer getPageSize() {
        return this.page.getPageSize();
    }

    /**
     * 分页参数
     */
    @Data
    public static class PageParam implements Serializable {

        /**
         * 当前页
         */
        private Integer pageNo = 1;

        /**
         * 页大小
         */
        private Integer pageSize = 20;

    }

}
