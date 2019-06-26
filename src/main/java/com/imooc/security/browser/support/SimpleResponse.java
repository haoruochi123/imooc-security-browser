package com.imooc.security.browser.support;

/**
 * @ClassName: SimpleResponse  
 * @Description: 简单的响应提示
 * @author 郝若池
 * @date 2019年6月2日 下午5:19:26
 */
public class SimpleResponse {

	public SimpleResponse(Object object) {
		this.object = object;
	}

	private Object object;

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

}
