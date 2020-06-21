package org.simpleframework.aop.aspect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.simpleframework.aop.PointcutLocator;

@AllArgsConstructor
@Getter
public class AspectInfo {
    private int orderIndex; //Priority number of aspect
    private DefaultAspect aspectObject; //Aspect object
    private PointcutLocator pointcutLocator;
}
