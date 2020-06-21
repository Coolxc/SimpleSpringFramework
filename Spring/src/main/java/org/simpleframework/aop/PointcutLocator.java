package org.simpleframework.aop;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.ShadowMatch;

import java.lang.reflect.Method;

public class PointcutLocator {

    private PointcutParser pointcutParser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingContextClassloaderForResolution(
            PointcutParser.getAllSupportedPointcutPrimitives()
    );

    private PointcutExpression pointcutExpression;

    public PointcutLocator(String expression){
        this.pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }

    //Determine whether the class object is the aim proxy class according to the pointcut expression
    //Just can verify the [within] pointcut expression, need a accurateMatches to verify the [execution] pointcut expression
    public boolean roughMatches(Class<?> targetClass){
        //Determine if the [within] pointcut expression is right
        return pointcutExpression.couldMatchJoinPointsInType(targetClass);
    }

    //Determine whether the method is the aim proxy method
    //TODO, I have no idea to accurate do AOP on method
    public boolean accurateMatches(Method method){
        ShadowMatch shadowMatch = pointcutExpression.matchesMethodExecution(method);
        if (shadowMatch.alwaysMatches()){
            return true;
        }else {
            return false;
        }
    }
}
