package com.github.snowdream.android.tests;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.github.snowdream.android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
        textView.setText("");
        FieldTest();
        MethodTest();
        ConstructTest();
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
        textView.append("\nFieldTest\n");
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


    public void MethodTest() {
        textView.append("\nMethodTest\n");
        Log.i("MethodTest");
        //normal
        {
            Person person = new Person();
            long start = System.currentTimeMillis();
            for (int i = 0; i < TEST_TIMES; i++) {
                person.setName("snowdream");
            }
            long end = System.currentTimeMillis();
            Log("normal method calls", start, end);
        }

        //reflect
        {
            try {
                Person person = new Person();
                Class<?> clazz = person.getClass();
                long start = System.currentTimeMillis();
                for (int i = 0; i < TEST_TIMES; i++) {
                    Method method = clazz.getDeclaredMethod("setName",String.class);
                    method.invoke(person,"snowdream");
                }
                long end = System.currentTimeMillis();
                Log("reflect method calls", start, end);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        //reflect cache the method
        {
            try {
                Person person = new Person();
                Class<?> clazz = person.getClass();
                Method method = clazz.getDeclaredMethod("setName", String.class);
                long start = System.currentTimeMillis();
                for (int i = 0; i < TEST_TIMES; i++) {
                    method.invoke(person, "snowdream");
                }
                long end = System.currentTimeMillis();
                Log("reflect cache method calls", start, end);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        //reflect cache the method and pass access check
        {
            try {
                Person person = new Person();
                Class<?> clazz = person.getClass();
                Method method = clazz.getDeclaredMethod("setName", String.class);
                method.setAccessible(true);
                long start = System.currentTimeMillis();
                for (int i = 0; i < TEST_TIMES; i++) {
                    method.invoke(person, "snowdream");
                }
                method.setAccessible(false);
                long end = System.currentTimeMillis();
                Log("reflect cache method and pass access check  calls", start, end);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public void ConstructTest() {
        textView.append("\nConstructTest\n");
        Log.i("ConstructTest");
        //normal
        {
            long start = System.currentTimeMillis();
            for (int i = 0; i < TEST_TIMES; i++) {
                Person person = new Person();
            }
            long end = System.currentTimeMillis();
            Log("normal Construct calls", start, end);
        }

        //reflect Person.class
        {
            try {
                long start = System.currentTimeMillis();
                for (int i = 0; i < TEST_TIMES; i++) {
                    Class<?> clazz = Person.class;
                    clazz.newInstance();
                }
                long end = System.currentTimeMillis();
                Log("reflect Construct Person.class calls", start, end);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            }
        }

        //reflect cache Person.class
        {
            try {
                Class<?> clazz = Person.class;
                long start = System.currentTimeMillis();
                for (int i = 0; i < TEST_TIMES; i++) {
                    clazz.newInstance();
                }
                long end = System.currentTimeMillis();
                Log("reflect cache Construct Person.class calls", start, end);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            }
        }

        //reflect Class.forName
        {
            try {
                long start = System.currentTimeMillis();
                for (int i = 0; i < TEST_TIMES; i++) {
                    Class<?> clazz = Class.forName("com.github.snowdream.android.tests.Person");
                    clazz.newInstance();
                }
                long end = System.currentTimeMillis();
                Log("reflect Construct  Class.forName calls", start, end);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        //reflect cache Class.forName
        {
            try {
                Class<?> clazz = Class.forName("com.github.snowdream.android.tests.Person");
                long start = System.currentTimeMillis();
                for (int i = 0; i < TEST_TIMES; i++) {
                    clazz.newInstance();
                }
                long end = System.currentTimeMillis();
                Log("reflect cache Construct Class.forName calls", start, end);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        //reflect getDeclaredConstructor
        {
            try {
                long start = System.currentTimeMillis();
                for (int i = 0; i < TEST_TIMES; i++) {
                    Class<?> clazz = Class.forName("com.github.snowdream.android.tests.Person");
                    Constructor constructor = clazz.getDeclaredConstructor(null);
                    constructor.newInstance();
                }
                long end = System.currentTimeMillis();
                Log("reflect Construct getDeclaredConstructor calls", start, end);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        //reflect cache Class.forName
        {
            try {
                Class<?> clazz = Class.forName("com.github.snowdream.android.tests.Person");
                Constructor constructor = clazz.getDeclaredConstructor(null);
                long start = System.currentTimeMillis();
                for (int i = 0; i < TEST_TIMES; i++) {
                    constructor.newInstance();
                }
                long end = System.currentTimeMillis();
                Log("reflect cache Construct getDeclaredConstructor  calls", start, end);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        //reflect cache getDeclaredConstructor
        {
            try {
                Class<?> clazz = Class.forName("com.github.snowdream.android.tests.Person");
                Constructor constructor = clazz.getDeclaredConstructor(null);
                constructor.setAccessible(true);
                long start = System.currentTimeMillis();
                for (int i = 0; i < TEST_TIMES; i++) {
                    constructor.newInstance();
                }
                long end = System.currentTimeMillis();
                Log("reflect cache Construct getDeclaredConstructor and pass access check  calls", start, end);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
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
