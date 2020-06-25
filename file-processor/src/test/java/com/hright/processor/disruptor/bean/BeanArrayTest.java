package com.hright.processor.disruptor.bean;

import com.hright.test.BaseTest;
import com.lmax.disruptor.WorkHandler;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

public class BeanArrayTest extends BaseTest {

    @Test
    public void testBeanArray() {
        BeanArray beanArray = BeanArray.builder().handler(new WorkHandler[1]).build();
        assertNotNull(beanArray);
        assertThat(beanArray.getHandler(), equalTo(new WorkHandler[1]));
    }
}