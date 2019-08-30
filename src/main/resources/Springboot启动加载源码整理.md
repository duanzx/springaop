[参考链接](https://blog.csdn.net/u014044812/article/details/84256764) [链接2](https://blog.csdn.net/woshilijiuyi/article/details/82219585)
### 启动流程
```
public static ConfigurableApplicationContext run(Class<?>[] primarySources,
			String[] args) {
		return new SpringApplication(primarySources).run(args);
	}
```
* new SpringApplication()：创建SpringApplication实例，负责加载配置一些基本的环境变量、资源、构造器、监听器。     
* run(): 负责springboot的整个启动过程，包括加载创建环境、打印banner、配置文件、配置应用上下文、加载bean实例等。springboot整个生命周期都在run方法里。

### 创建SpringApplication实例
```
public SpringApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
        //使用的资源加载器
		this.resourceLoader = resourceLoader;
		//primarySources指的是主类Application.class，可以传多个，不允许为空，如果为空，抛出异常
		Assert.notNull(primarySources, "PrimarySources must not be null");
		//将启动类的可变数组转化为List集合，放在LinkedHashSet集合中
		this.primarySources = new LinkedHashSet<>(Arrays.asList(primarySources));
		/**
         * 创建应用类型，不同应用程序类型，创建不同的环境
         * springboot1.5 只有两种类型：web环境和非web环境
         * springboot2.0 有三种应用类型：WebApplicationType
         * NONE：不需要再web容器的环境下运行，也就是普通的工程
         * SERVLET：基于servlet的Web项目
         * REACTIVE：响应式web应用reactive web Spring5版本的新特性
         */
		this.webApplicationType = WebApplicationType.deduceFromClasspath();
		/**
		* 每一个initializer都是一个实现了ApplicationContextInitializer接口的实例。
		* ApplicationContextInitializer是Spring IOC容器中提供的一个接口，void initializer()
		* 这个方法会在ConfigurableApplicationContext的refresh()方法调动之前被调用。
		* prepareContext方法中调用
		*/
		setInitializers((Collection) getSpringFactoriesInstances(
				ApplicationContextInitializer.class));
		/**
		* SSpringboot整个生命周期在完成一个阶段的时候都会通过事件推送器(EventPublishingRunListener)产生一个事件(ApplicationEvent)，
        * 然后再遍历每个监听器(ApplicationListener)以匹配事件对象，这是一种典型的观察者设计模式的实现
		*/
		setListeners((Collection) getSpringFactoriesInstances(ApplicationListener.class));
		//指定main函数启动所在的类，即启动类BootApplication.class
		this.mainApplicationClass = deduceMainApplicationClass();
	}
	初始化类initializer , 监听器的实现类，都是在spring.factories文件中配置好的，在代码中通过getSpringFactoriesInstances方法获取      
	这种机制叫做：SPI机制，通过本地的注册发现获取到具体的实现类，实现轻松的可插拔。
```
### SpringApplication run 方法整理流程



### SPI机制实现
```
private <T> Collection<T> getSpringFactoriesInstances(Class<T> type,
			Class<?>[] parameterTypes, Object... args) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		// Use names and ensure unique to protect against duplicates
		Set<String> names = new LinkedHashSet<>(
				SpringFactoriesLoader.loadFactoryNames(type, classLoader));
		List<T> instances = createSpringFactoriesInstances(type, parameterTypes,
				classLoader, args, names);
		AnnotationAwareOrderComparator.sort(instances);
		return instances;
	}
	
private <T> List<T> createSpringFactoriesInstances(Class<T> type,
			Class<?>[] parameterTypes, ClassLoader classLoader, Object[] args,
			Set<String> names) {
		List<T> instances = new ArrayList<>(names.size());
		for (String name : names) {
			try {
				Class<?> instanceClass = ClassUtils.forName(name, classLoader);
				Assert.isAssignable(type, instanceClass);
				Constructor<?> constructor = instanceClass
						.getDeclaredConstructor(parameterTypes);
				T instance = (T) BeanUtils.instantiateClass(constructor, args);
				instances.add(instance);
			}
			catch (Throwable ex) {
				throw new IllegalArgumentException(
						"Cannot instantiate " + type + " : " + name, ex);
			}
		}
		return instances;
	}
```

### ApplicationContextInitializer initializer() 说明 , [参考链接](https://www.jianshu.com/p/3828e93be20d)

### Springboot的事件推送原理，[参考链接](https://www.cnblogs.com/ashleyboy/p/9566579.html)

### StopWatch原理分析,[参考链接](https://springboot.io/t/topic/315)
