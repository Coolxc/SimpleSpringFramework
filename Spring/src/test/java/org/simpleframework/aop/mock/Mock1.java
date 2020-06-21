package org.simpleframework.aop.mock;

import org.simpleframework.aop.annotation.Order;
import org.simpleframework.aop.aspect.DefaultAspect;

@Order(3)
public class Mock1 extends DefaultAspect implements Mock {
}
