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
1. 创建一个应用上下文引用 ConfigurableApplicationContext
2. 异常收集，报告启动异常
3. 获取事件推送监听器集合，负责产生事件，并掉哦那个某类事件的监听器。getRunListeners     
4. 发布一个启动事件     

```
public ConfigurableApplicationContext run(String... args) {
	/**
	 *  StopWatch: 简单的秒表，允许定时的一些任务，公开每个指定任务的总运行时间和运行时间。
	 *  这个对象的设计不是线程安全的，没有使用同步。SpringApplication是在单线程环境下，使用安全。
	 */
	StopWatch stopWatch = new StopWatch();
	// 设置当前启动的时间为系统时间startTimeMillis = System.currentTimeMillis();
	stopWatch.start();
	// 创建一个应用上下文引用
	ConfigurableApplicationContext context = null;
	// 异常收集，报告启动异常
	Collection<SpringBootExceptionReporter> exceptionReporters = new ArrayList<>();
	/**
	 * 系统设置headless模式（一种缺乏显示设备、键盘或鼠标的环境下，比如服务器），
	 * 通过属性：java.awt.headless=true控制
	 */
	configureHeadlessProperty();
	/*
	 * 获取事件推送监器，负责产生事件，并调用支某类持事件的监听器
	 * 事件推送原理看上面的事件推送原理图
	 */
	SpringApplicationRunListeners listeners = getRunListeners(args);
	/**
	 * 发布一个启动事件(ApplicationStartingEvent)，通过上述方法调用支持此事件的监听器
	 */
	listeners.starting();
	try {
		// 提供对用于运行SpringApplication的参数的访问。取默认实现
		ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
		/**
		 * 构建容器环境，这里加载配置文件
		 */
		ConfigurableEnvironment environment = prepareEnvironment(listeners, applicationArguments);
		// 对环境中一些bean忽略配置
		configureIgnoreBeanInfo(environment);
		// 日志控制台打印设置
		Banner printedBanner = printBanner(environment);
		// 创建容器
		context = createApplicationContext();
		exceptionReporters = getSpringFactoriesInstances(SpringBootExceptionReporter.class, new Class[] { ConfigurableApplicationContext.class }, context);
		/**
		 * 准备应用程序上下文
		 * 追踪源码prepareContext（）进去我们可以发现容器准备阶段做了下面的事情：
		 * 容器设置配置环境，并且监听容器，初始化容器，记录启动日志，
		 * 将给定的singleton对象添加到此工厂的singleton缓存中。
		 * 将bean加载到应用程序上下文中。
		 */
		prepareContext(context, environment, listeners, applicationArguments, printedBanner);
		/**
		 * 刷新上下文
		 * 1、同步刷新，对上下文的bean工厂包括子类的刷新准备使用，初始化此上下文的消息源，注册拦截bean的处理器，检查侦听器bean并注册它们，实例化所有剩余的(非延迟-init)单例。
		 * 2、异步开启一个同步线程去时时监控容器是否被关闭，当关闭此应用程序上下文，销毁其bean工厂中的所有bean。
		 * 。。。底层调refresh方法代码量较多
		 */
		refreshContext(context);
		afterRefresh(context, applicationArguments);
		// stopwatch 的作用就是记录启动消耗的时间，和开始启动的时间等信息记录下来
		stopWatch.stop();
		if (this.logStartupInfo) {
			new StartupInfoLogger(this.mainApplicationClass).logStarted(getApplicationLog(), stopWatch);
		}
		// 发布一个已启动的事件
		listeners.started(context);
		callRunners(context, applicationArguments);
	}
	catch (Throwable ex) {
		handleRunFailure(context, ex, exceptionReporters, listeners);
		throw new IllegalStateException(ex);
	}
	try {
		// 发布一个运行中的事件
		listeners.running(context);
	}
	catch (Throwable ex) {
		// 启动异常，里面会发布一个失败的事件
		handleRunFailure(context, ex, exceptionReporters, null);
		throw new IllegalStateException(ex);
	}
	return context;
}
```


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
**ApplicatonListener**:观察者模式的监听器接口，用来监听各种ApplicationEvent       
**ParentContextCloserApplicationListener**:当父Context关闭时候，关闭当前ApplicationContext     
**FileEncodingApplicationListener**: 当系统属性file.encoding与Spring配置的encode不一致时，打印出错信息，并中止程序的启动。        
**AnsiOutputApplicationListener**: 根据属性spring.output.ansi.enabled配置ANSI输出       
**ConfigFileApplicationListener**: 搜索并加载配置文件，根据配置文件设置Environment和SpringApplication      
**DelegatingApplicationListener**: 加载并转发事件至context.listener.classes中配置的ApplicationListener      
**LiquibaseServiceLocatorApplicationListener**: 如果classpath中存在类liquibase.servicelocator.serviceLocator,那么将其替换成一个适用于springboot的版本。       
**ClasspathLoggingApplicationListener**: 当程序正常启动成功时，将classpath打印到debug日志；当程序启动失败时，将classpath打印到info日志。      
**LoggingApplicationListener**: 根据配置，在合适的时候初始化和配置日志系统       
     






### StopWatch原理分析,[参考链接](https://springboot.io/t/topic/315)
