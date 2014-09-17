package com.github.snowdream.android.tests;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

/**
 * Created by hui.yang on 2014/8/28.
 */
public class EntityListFragment extends BaseFragment {
    protected ListView listView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        listView = (ListView) view.findViewById(R.id.main_listview);

        Context context = inflater.getContext();
        EntityListAdapter adapter = new EntityListAdapter(context, getEntities());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    protected List<Entity> createEntities() {
        return EntityFractory.createEntities(ReflectTestFragment.class,AnnotationTestFragment.class,DynamicProxyTestFragment.class);
    }
}
