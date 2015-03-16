Automon Load Time Weaving (LTW) Config XML Files
========================

By configurming AspectJ LTW xml files you can tell Automon what parts of your program you would like to monitor simply by
 providing 2 pointcuts.  If you are unfamiliar with pointcuts please refer to AspectJ documentation. The pointcuts you provide
 follow:

 * **user_monitor** - Anything with this pointcut name will be monitored have Automon's 'start' method called when the code is
 entered and 'stop' called when the code is completed.  JAMon, JavaSimon and Yammer Metrics use this to monitor the
 performance of any classes or methods that you specify in the pointcut.
 * **user_exceptions** - Any code with this pointcut name will call Automon's code that specifies and exception has been thrown.
 Automon implementations of JAMon, JavaSimon, and Yammer Metrics use this capability to count exceptions.

 Typically the user_monitor and user_exceptions pointcuts use the same pointcut though they don't have to.

 See the xml files in this directory for some examples.  ajc-aop.xml also has many pointcuts defined that you
 can reuse that will simplify the process of writing pointcuts for you.

 Here is an example of how you would pass this xml configuration information to the AspectJ LTW:

 **java   -Dorg.automon=sysout -Dorg.aspectj.weaver.loadtime.configuration=file:config/ajc-aop.xml -javaagent:libs/aspectjweaver.jar -classpath libs/automon-1.0-SNAPSHOT.jar:libs/playground-1.0-SNAPSHOT.jar com.stevesouza.automon.annotations.AnnotationTester**
