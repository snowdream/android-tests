package com.github.snowdream.android.tests;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.github.snowdream.android.util.Log;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by hui.yang on 2014/8/28.
 */
public class ReflectTestFragment extends BaseFragment {
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
        FieldTest();
    }

    public void Log(final String tag, final long start, final long end) {
        textView.append(TEST_TIMES + " " + tag + ": " + (end - start) + " milliseconds." + "\n");
        Log.i(TEST_TIMES + " " + tag + ": " + (end - start) + " milliseconds.");
    }
    public void Log(final String message) {
        textView.append(message+"\n");
        Log.i(message);
    }

    public void FieldTest() {
        Log.i("FieldTest");
        //normal
        {
            Person person = new Person();
            long start = System.currentTimeMillis();
            for (int i = 0; i < TEST_TIMES; i++) {
                person.name = "snowdream";
            }
            long end = System.currentTimeMillis();
            Log("normal field calls", start, end);
        }

        //reflect
        {
            try {
                Person person = new Person();
                Class<?> clazz = person.getClass();
                long start = System.currentTimeMillis();
                for (int i = 0; i < TEST_TIMES; i++) {
                    Field field = clazz.getDeclaredField("name");
                    field.set(person, "snowdream");
                }
                long end = System.currentTimeMillis();
                Log("reflect field calls", start, end);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        //reflect cache the field
        {
            try {
                Person person = new Person();
                Class<?> clazz = person.getClass();
                Field field = clazz.getDeclaredField("name");
                long start = System.currentTimeMillis();
                for (int i = 0; i < TEST_TIMES; i++) {
                    field.set(person, "snowdream");
                }
                long end = System.currentTimeMillis();
                Log("reflect cache field calls", start, end);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        
        //reflect cache the field and pass access check
        {
            try {
                Person person = new Person();
                Class<?> clazz = person.getClass();
                Field field = clazz.getDeclaredField("name");
                field.setAccessible(true);
                long start = System.currentTimeMillis();
                for (int i = 0; i < TEST_TIMES; i++) {
                    field.set(person, "snowdream");
                }
                field.setAccessible(false);
                long end = System.currentTimeMillis();
                Log("reflect cache field and pass access check  calls", start, end);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected List<Entity> createEntities() {
        return null;
    }
}
