package com.guonima.wxapp.util;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.HashMap;
import java.util.Map;

/**
 * SecurityUtil Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>���� 13, 2017</pre>
 */
public class SecurityUtilTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: createToken(Map<String, Object> srcData)
     */
    @Test
    public void testCreateTokenSrcData() throws Exception {
        Map map = new HashMap();
        map.put("str1", "1");
        map.put("str2", "2");
        map.put("str3", "3");
        map.put("str4", "4");
        System.out.println(SecurityUtil.createToken(map));
    }

    /**
     * Method: createToken(String str)
     */
    @Test
    public void testCreateTokenStr() throws Exception {
        System.out.println(SecurityUtil.createToken("3333"));
    }


} 
