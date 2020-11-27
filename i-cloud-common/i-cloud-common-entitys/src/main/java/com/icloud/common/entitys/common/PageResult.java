package com.icloud.common.entitys.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 分页响应
 *
 * @author 42806
 */
@Data
@AllArgsConstructor
@ToString
public class PageResult<T> implements Serializable {

    private long total;

    private List<T> records;

}
