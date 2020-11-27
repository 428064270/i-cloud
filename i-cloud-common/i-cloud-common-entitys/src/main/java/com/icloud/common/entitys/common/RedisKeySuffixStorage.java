package com.icloud.common.entitys.common;

/**
 * Redis内的Key后缀
 *
 * @author 42806
 */
public class RedisKeySuffixStorage {

    /**
     * 图形验证码
     */
    public static final String REDIS_IMAGE_CODE_KEY = "_redis_image_code";

    /**
     * 用户的API接口matcher对比对象
     */
    public static final String REDIS_AUTH_MATCHER_KEY = "_redis_auth_matcher";

}
