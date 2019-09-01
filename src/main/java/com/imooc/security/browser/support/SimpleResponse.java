package com.imooc.security.browser.support;

/**
 * @author 郝若池
 * @ClassName: SimpleResponse
 * @Description: 简单的响应提示
 * @date 2019年6月2日 下午5:19:26
 */
public class SimpleResponse {

    private Object object;

    public SimpleResponse(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

}
