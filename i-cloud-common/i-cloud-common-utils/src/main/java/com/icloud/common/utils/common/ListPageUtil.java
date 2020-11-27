package com.icloud.common.utils.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 42806
 */
public final class ListPageUtil {

    /**
     * @param list     进行分页的list
     * @param pageNo   页码
     * @param pageSize 每页显示条数
     * @return 分页后数据
     */
    public static <T> List<T> listPaging(List<T> list, Integer pageNo, Integer pageSize) {
        if (list == null) {
            list = new ArrayList<T>();
        }
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        if (pageNo <= 0) {
            pageNo = 1;
        }

        int totalitems = list.size();
        List<T> pagingList = new ArrayList<T>();

        int totalNum = ((pageNo - 1) * pageSize) + pageSize > totalitems ? totalitems : ((pageNo - 1) * pageSize) + pageSize;
        for (int i = (pageNo - 1) * pageSize; i < totalNum; i++) {
            pagingList.add(list.get(i));
        }
        return pagingList;
    }

}

