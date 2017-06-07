package com.seeker.tony.myapplication.proxy;

import com.meiyou.jet.annotation.JImplement;

/**
 * 测试接口
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/5/22
 */

@JImplement("com.seeker.tony.myapplication.proxy.TestImpl")
public interface ITest {
    public void  test();
    
    public String getValue();
}
