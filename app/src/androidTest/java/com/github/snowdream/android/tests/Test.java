package com.github.snowdream.android.tests;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import junit.framework.TestCase;
import android.test.AndroidTestCase;
import android.util.Log;

public class Test extends AndroidTestCase {
	public void testAndroidAnnotations() throws Exception {
//	    Field field = Foo.class.getDeclaredField("field");
//	    MyAnnotation myAnnotation = field.getAnnotation(MyAnnotation.class);
	    long before = System.currentTimeMillis();
	    for (int i = 0; i < 1000; i++){
		    Field field = Foo.class.getDeclaredField("field");
		    MyAnnotation myAnnotation = field.getAnnotation(MyAnnotation.class);
	    	myAnnotation.foo();
	    }
	    Log.i("test", "in " + (System.currentTimeMillis() - before) + "ms");
	}
	
	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private static @interface MyAnnotation {
	    String foo();
	}
	
	private static class Foo {
	    @MyAnnotation(foo = "bar")
	    String field;
	}
}
