package com.github.snowdream.android.tests;

import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.util.TimingLogger;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.github.snowdream.android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Timer;

/**
 * Created by hui.yang on 2014/8/28.
 */
public class DynamicProxyTestFragment extends BaseFragment {
    private TextView textView = null;
    private static final int TEST_TIMES = 100000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reflect, container, false);
        textView = (TextView) view.findViewById(R.id.textview);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        textView.setText("");
        DynamicTest();
    }

    public void Log(final String tag, final long start, final long end) {
        textView.append(TEST_TIMES + " " + tag + ": " + (end - start) + " milliseconds." + "\n");
        Log.i(TEST_TIMES + " " + tag + ": " + (end - start) + " milliseconds.");
    }

    public void Log(final String message) {
        textView.append(message + "\n");
        Log.i(message);
    }

    public void DynamicTest() {
        textView.append("\nDynamicTest\n");
        Log.i("DynamicTest");
        //ProxyBuilder
        {
            ISubject iSubject = ProxyBuilder.create(ISubject.class);
            long start = System.currentTimeMillis();
            for (int i = 0; i < TEST_TIMES; i++) {
                iSubject.printHashCode();
            }
            long end = System.currentTimeMillis();
            Log("iSubject.printHashCode() calls: "+ iSubject.printHashCode() , start, end);
        }
    }

    @Override
    protected List<Entity> createEntities() {
        return null;
    }


    public static class ProxyBuilder{

        public static <T extends ISubject> T create (Class<T> type) {
            if (type == null) {
                return null;
            }

            return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    String tag = method.getName();
                    String label = method.getName();
                    TimingLogger timings = new TimingLogger(tag, label);
                   // method.invoke(proxy, args);

                    timings.dumpToLog();
                    return proxy;
                }
            });
        }
    }
}
