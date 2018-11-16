package com.seeker.tony.myapplication.proxy;

import com.jet.jet.annotation.JImplement;

/**
 * 测试接口
 * @author zhengxiaobin
 * @since 17/5/22
 */

@JImplement("com.seeker.tony.myapplication.proxy.TestImpl")
public interface ITest {
    public void  test();
    
    public String getValue();
}
