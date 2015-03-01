package org.automon.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.automon.monitors.NullImp;
import org.automon.monitors.OpenMon;

/**
 * aspectj compile time weaving using ajc
 *  cd /Users/stevesouza/gitrepo/testproject/playground/src/main/javastyle
 *  ajc -source 5 com/stevesouza/aspectj/*.javastyle
 *  javastyle com.stevesouza.aspectj.MessageCommunicator
 *
 * for nonaspectj version:
 *  javac com/stevesouza/aspectj/*.javastyle
 *
 */
@Aspect
public abstract class AutomonAspect  {

    private OpenMon openMon = new NullImp();
    protected static boolean enable = true;

    public static boolean isEnabled() {
        return enable;
    }

    public static void enable(boolean shouldEnable) {
        enable = shouldEnable;
//        openMon.enable(enable);
    }



    @Around("monitor()")
    public Object monitorPerformance(ProceedingJoinPoint pjp) throws Throwable {
        Object mon = openMon.start("com.stevesouza.myMethod");
        Object retVal = pjp.proceed();
        openMon.stop(mon);
        return retVal;
    }

    @AfterThrowing(pointcut = "exceptions()", throwing = "e")
    public void monitorExceptions(JoinPoint joinPoint, Throwable e) {
            System.out.println();
            System.out.println("Exception: "+e);
            System.out.println(" jp.getKind()=" + joinPoint.getKind());
            System.out.println(" jp.getStaticPart()="+joinPoint.getStaticPart());
            Object[] argValues = joinPoint.getArgs();
            Signature signature = joinPoint.getSignature();
            System.out.println(" jp.getSignature().getClass()="+signature.getClass());
            openMon.exception(e.getClass().getName()); // java.lang.RuntimeException

        // Note would have to look at all the special cases here.
            if (signature instanceof MethodSignature) {
                MethodSignature methodSignature =  (MethodSignature) signature;
                String[]argNames = methodSignature.getParameterNames();
                //for (int i = 0; i < argNames.length; i++) {
                //    printMe("  argName, argValue", argNames[i] + ", " + argValues[i]);
                //}
            }
    }


    public OpenMon getOpenMon() {
        return openMon;
    }

    public void setOpenMon(OpenMon openMon) {
        this.openMon = openMon;
    }



//    public boolean isEnabled() {
//        return isEnabled && openMon.isEnabled();
//    }



    @Pointcut("user_monitor1() && user_monitor2() && user_monitor3() && sys_monitor1()")
    public void monitor() {

    }

    @Pointcut()
    public abstract void sys_monitor1();

    @Pointcut()
    public abstract void user_monitor1();

    @Pointcut()
    public abstract void user_monitor2();

    @Pointcut()
    public abstract void user_monitor3();

    @Pointcut("user_exceptions1() && user_exceptions2() && user_exceptions3() && sys_exceptions1()")
    public void exceptions() {
    }

    @Pointcut()
    public abstract void sys_exceptions1();

    @Pointcut()
    public abstract void user_exceptions1();

    @Pointcut()
    public abstract void user_exceptions2();

    @Pointcut()
    public abstract void user_exceptions3();

}