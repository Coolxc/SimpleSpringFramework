package org.simpleframework.aop.mock;

import org.simpleframework.aop.annotation.Order;
import org.simpleframework.aop.aspect.DefaultAspect;

@Order(1)
public class Mock3 extends DefaultAspect implements Mock {
}
