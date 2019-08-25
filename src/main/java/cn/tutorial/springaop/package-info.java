package cn.tutorial.springaop;
/*
* AOP源码解析
*
* 入口 @Aspect
*  AspectJAutoProxyBeanDefinitionParser的parse方法
*  1. 注册或者升级AnnotationAwareAspectJAutoProxyCreator
*  2. 处理proxy-target-class以及expose-proxy属性
*  3. 注册组件并通知，便于监听器进一步处理
*
*
* AOP
* */