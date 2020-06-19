package com.st.network.utils;

import android.util.Log;

import java.util.Locale;

/**
 * kang do filter log for debug log level
 */
public class LogUtil {

    private static int LOG_LEVEL = 5;

    public static void v(String tag, String mess) {
        if (LOG_LEVEL > 0) {
            Log.v(tag, mess);
        }
    }

    public static void d(String tag, String mess) {
        if (LOG_LEVEL > 1) {
            Log.d(tag, mess);
        }
    }

    public static void i(String tag, String mess) {
        if (LOG_LEVEL > 2) {
            Log.i(tag, mess);
        }
    }

    public static void w(String tag, String mess) {
        if (LOG_LEVEL > 3) {
            Log.w(tag, mess);
        }
    }

    public static void e(String tag, String mess) {
        if (LOG_LEVEL > 4) {
            Log.e(tag, mess);
        }
    }

    /**
     * 获取到调用者的类名
     *
     * @return 调用者的类名
     */
    private static String getTag() {
        StackTraceElement[] trace = new Throwable().fillInStackTrace()
                .getStackTrace();
        String callingClass = "";
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(LogUtil.class)) {
                callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass
                        .lastIndexOf('.') + 1);
                break;
            }
        }
        return "TAG "+callingClass;
    }

    /**
     * 获取线程ID，方法名和输出信息
     *
     * @param msg
     * @return
     */
    private static String buildMessage(String msg) {
        StackTraceElement[] trace = new Throwable().fillInStackTrace()
                .getStackTrace();
        String caller = "";
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(LogUtil.class)) {
                caller = trace[i].getMethodName();
                break;
            }
        }
        return String.format(Locale.US, "[%d] %s: %s", Thread.currentThread()
                .getId(), caller, msg);
    }

    public static void v(String mess) {
        if (LOG_LEVEL > 0) {
            Log.v(getTag(), buildMessage(mess));
        }
    }

    public static void d(String mess) {
        if (LOG_LEVEL > 1) {
            Log.d(getTag(), buildMessage(mess));
        }
    }

    public static void i(String mess) {
        if (LOG_LEVEL > 2) {
            Log.i(getTag(), buildMessage(mess));
        }
    }

    public static void w(String mess) {
        if (LOG_LEVEL > 3) {
            Log.w(getTag(), buildMessage(mess));
        }
    }

    public static void e(String mess) {
        if (LOG_LEVEL > 4) {
            Log.e(getTag(), buildMessage(mess));
        }
    }

    public static void v1(String mess) {
        if (LOG_LEVEL > 0) {
            Log.v(getTag(), getMsgFormat(mess));
        }
    }

    public static void d1(String mess) {
        if (LOG_LEVEL > 1) {
            Log.d(getTag(), getMsgFormat(mess));
        }
    }

    public static void i1(String mess) {
        if (LOG_LEVEL > 2) {
            Log.i(getTag(), getMsgFormat(mess));
        }
    }

    public static void w1(String mess) {
        if (LOG_LEVEL > 3) {
            Log.w(getTag(), getMsgFormat(mess));
        }
    }

    public static void e1(String mess) {
        if (LOG_LEVEL > 4) {
            Log.e(getTag(), getMsgFormat(mess));
        }
    }

    /**
     * 获取相关数据:类名,方法名,行号等.用来定位行
     *
     * @return
     */
    private static String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts != null) {
            for (StackTraceElement st : sts) {
                if (st.isNativeMethod()) {
                    continue;
                }
                if (st.getClassName().equals(Thread.class.getName())) {
                    continue;
                }
                if (st.getClassName().equals(LogUtil.class.getName())) {
                    continue;
                }
                return "[ Thread:" + Thread.currentThread().getName() + ", at " + st.getClassName() + "." + st.getMethodName()
                        + "(" + st.getFileName() + ":" + st.getLineNumber() + ")" + " ]";
            }
        }
        return null;
    }

    /**
     * 输出格式定义
     *
     * @param msg
     * @return
     */
    private static String getMsgFormat(String msg) {
        return msg + " ;" + getFunctionName();
    }

    public static void setLog(Boolean isLog) {
        if(isLog){
            LOG_LEVEL = 5;
        }else{
            LOG_LEVEL = 0;
        }
    }
}

