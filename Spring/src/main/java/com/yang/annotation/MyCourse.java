package com.yang.annotation;

@CourseInfoAnnotation(name = "IMOOC", profile = "great lesson")
public class MyCourse {

    @PersonInfoAnnotation(name = "Yang Youxiu", age = 23, language = {"Java", "Python"})
    String person;

    @CourseInfoAnnotation(name = "ChaoXing", profile = "bad lesson")
    public void getCourse(){}
}
