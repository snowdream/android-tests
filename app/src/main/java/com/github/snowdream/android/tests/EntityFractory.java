package com.github.snowdream.android.tests;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hui.yang on 2014/8/28.
 */
public class EntityFractory {
    public static final Entity createEntity(Class<? extends BaseFragment> clazz) {
        return new Entity(clazz.getSimpleName(), clazz);
    }

    public static final List<Entity> createEntities(Class<? extends BaseFragment> ...clazzes) {
        List<Entity> list = new ArrayList<Entity>();
        for (int i = 0; i<clazzes.length;i++){
            list.add(createEntity(clazzes[i]));
        }

        return list;
    }
}
