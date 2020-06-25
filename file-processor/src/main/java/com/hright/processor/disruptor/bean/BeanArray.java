package com.hright.processor.disruptor.bean;

import com.lmax.disruptor.WorkHandler;
import lombok.Builder;
import lombok.Getter;
import org.eclipse.collections.api.block.function.Function0;
import org.eclipse.collections.impl.factory.Lists;

@Getter
@Builder
public class BeanArray<T> {

    WorkHandler<T>[] handler;

    public static <T> BeanArray<T> createBeanArray(Function0<WorkHandler<T>> handler, int size) {
        WorkHandler<T>[] workHandlers = Lists.mutable.withNValues(size, handler).toArray(new WorkHandler[size]);
        return BeanArray.<T>builder().handler(workHandlers).build();
    }
}
