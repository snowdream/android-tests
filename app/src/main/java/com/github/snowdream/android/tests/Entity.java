package com.github.snowdream.android.tests;

import java.io.Serializable;

/**
 * Created by hui.yang on 2014/8/28.
 */
public class Entity implements Serializable{
    /**
     * The name for the fragment
     *
     */
    private String name = "";

    /**
     * The class for the fragment
     */
    private Class<? extends BaseFragment> clazz = null;

    public Entity( String name,Class<? extends BaseFragment> clazz){
        this.name = name;
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<? extends BaseFragment> getClazz() {
        return clazz;
    }

    public void setClazz(Class<? extends BaseFragment> clazz) {
        this.clazz = clazz;
    }
}
