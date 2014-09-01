package com.github.snowdream.android.tests;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.github.snowdream.android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by hui.yang on 2014/8/28.
 */
public class AnnotationTestFragment extends BaseFragment {
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
        AnnotationTest();
    }

    public void Log(final String tag, final long start, final long end) {
        textView.append(TEST_TIMES + " " + tag + ": " + (end - start) + " milliseconds." + "\n");
        Log.i(TEST_TIMES + " " + tag + ": " + (end - start) + " milliseconds.");
    }

    public void Log(final String message) {
        textView.append(message + "\n");
        Log.i(message);
    }

    public void AnnotationTest() {
        textView.append("\nAnnotationTest\n");
        Log.i("AnnotationTest");
        //isAnnotationPresent
        {
            try {
                Person person = new Person();
                Class<?> clazz = person.getClass();
                Method method = clazz.getDeclaredMethod("setName", String.class);
                long start = System.currentTimeMillis();
                for (int i = 0; i < TEST_TIMES; i++) {
                    method.isAnnotationPresent(Default.class);
                }
                long end = System.currentTimeMillis();
                Log("isAnnotationPresent calls", start, end);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        //getAnnotation
        {
            try {
                Person person = new Person();
                Class<?> clazz = person.getClass();
                Method method = clazz.getDeclaredMethod("setName", String.class);
                long start = System.currentTimeMillis();
                for (int i = 0; i < TEST_TIMES; i++) {
                    Default DefaultAnnoation = method.getAnnotation(Default.class);
                    DefaultAnnoation.value();
                }
                long end = System.currentTimeMillis();
                Log("isAnnotationPresent calls", start, end);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        //getAnnotation cache
        {
            try {
                Person person = new Person();
                Class<?> clazz = person.getClass();
                Method method = clazz.getDeclaredMethod("setName", String.class);
                Default DefaultAnnoation = method.getAnnotation(Default.class);

                long start = System.currentTimeMillis();
                for (int i = 0; i < TEST_TIMES; i++) {
                    DefaultAnnoation.value();
                }
                long end = System.currentTimeMillis();
                Log("isAnnotationPresent calls", start, end);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected List<Entity> createEntities() {
        return null;
    }
}
