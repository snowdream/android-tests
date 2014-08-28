package com.github.snowdream.android.tests;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

/**
 * Created by hui.yang on 2014/8/28.
 */
public abstract class BaseFragment extends Fragment implements
        AdapterView.OnItemClickListener {
    private List<Entity> entities = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        entities = createEntities();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (entities == null) {
            return;
        }

        int length = entities.size();
        if (id < length) {
            openEntity(entities.get(position));
        }
    }

    protected abstract List<Entity> createEntities();

    protected final List<Entity> getEntities() {
        return entities;
    }

    protected void openEntity(Entity entity) {
        if (entity != null && entity.getClazz() != null) {
            openFragment(entity.getClazz());
        }
    }

    protected void openFragment(Class<? extends BaseFragment> clazz) {
        try {
            Fragment f = (Fragment) clazz.newInstance();

            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().replace(R.id.container, f, null).addToBackStack(null).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
