## AOP源码解析

### 入口 @Aspect
  AspectJAutoProxyBeanDefinitionParser的parse方法
```
    public BeanDefinition parse(Element element, ParserContext parserContext) {
            AopNamespaceUtils.registerAspectJAnnotationAutoProxyCreatorIfNecessary(parserContext, element);
            extendBeanDefinition(element, parserContext);
            return null;
    }
    
	public static void registerAspectJAnnotationAutoProxyCreatorIfNecessary(
			ParserContext parserContext, Element sourceElement) {
        //注册或者升级AutoProxyCreator为 AnnotationAwareAspectJAutoProxyCreator
		BeanDefinition beanDefinition = AopConfigUtils.registerAspectJAnnotationAutoProxyCreatorIfNecessary(
				parserContext.getRegistry(), parserContext.extractSource(sourceElement));
        //对于proxy-target-class以及expose-proxy属性的处理
		useClassProxyingIfNecessary(parserContext.getRegistry(), sourceElement);
		//注册组件并通知，便于监听器作进一步处理
		registerComponentIfNecessary(beanDefinition, parserContext);
	}
```
#### 注册或者升级AnnotationAwareAspectJAutoProxyCreator
  ```
    public static BeanDefinition registerAspectJAnnotationAutoProxyCreatorIfNecessary(
                     BeanDefinitionRegistry registry, @Nullable Object source) {
         
                 return registerOrEscalateApcAsRequired(AnnotationAwareAspectJAutoProxyCreator.class, registry, source);
    }
    
    private static BeanDefinition registerOrEscalateApcAsRequired(
    			Class<?> cls, BeanDefinitionRegistry registry, @Nullable Object source) {
    
    		Assert.notNull(registry, "BeanDefinitionRegistry must not be null");
            //如果已经存在了自动代理创建器，并且AnnotationAwareAspectJAutoProxyCreator和当前代理创建器不一致，则根据优先级来决定优先使用哪个
    		if (registry.containsBeanDefinition(AUTO_PROXY_CREATOR_BEAN_NAME)) {
    			BeanDefinition apcDefinition = registry.getBeanDefinition(AUTO_PROXY_CREATOR_BEAN_NAME); //org.springframework.aop.config.internalAutoProxyCreator
    			if (!cls.getName().equals(apcDefinition.getBeanClassName())) {
    				int currentPriority = findPriorityForClass(apcDefinition.getBeanClassName());
    				int requiredPriority = findPriorityForClass(cls);
    				if (currentPriority < requiredPriority) {
    					apcDefinition.setBeanClassName(cls.getName());
    				}
    			}
    			return null;
    		}
    
    		RootBeanDefinition beanDefinition = new RootBeanDefinition(cls);
    		beanDefinition.setSource(source);
    		beanDefinition.getPropertyValues().add("order", Ordered.HIGHEST_PRECEDENCE);
    		beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
    		registry.registerBeanDefinition(AUTO_PROXY_CREATOR_BEAN_NAME, beanDefinition);
    		return beanDefinition;
    }
  ```
  
 #### 处理proxy-target-class以及expose-proxy属性       
 1. proxy-target-class : Spring AOP部分使用JDK动态代理或者CGLIB来为目标对象创建代理。       
 如果被代理的目标对象实现了至少一个接口，则会使用JDK动态代理。所有该目标类型实现的接口都将被代理。     
 2. 如果目标对象没有实现任何接口，则创建一个CGLIB代理。        
 3. 使用CGLIB代理，无法对Final修饰的方法使用Advice,因为它们不能被覆写。强制使用CGLIB代理需要设置 app config : proxy-target-class= true     
 4. JDK动态代理：其代理对象必须是实现了某个接口，它通过在运行期间创建一个接口的实现类来完成对目标对象的代理。      
 5. CGLIB代理：在运行期间生成的代理对象是针对目标类扩展的子类。CGLIB性能比JDK强。       
 6. expose-proxy，有时候目标对象内部的自我调用无法实施切面中的增强，需要设置expose-proxy=true 
 #### 注册组件并通知，便于监听器进一步处理
### AOP代理的创建