package com.github.snowdream.android.tests;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hui.yang on 2014/8/28.
 */
public class EntityListAdapter extends BaseAdapter {
    private Context context = null;
    private List<Entity> entities = null;

    public EntityListAdapter(Context context, List<Entity> entities) {
        this.context = context;
        this.entities = entities;
    }

    @Override
    public int getCount() {
        return entities == null ? 0 : entities.size();
    }

    @Override
    public Entity getItem(int position) {
        if (position < getCount()) {
            return entities.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_main_item, parent, false);

        Entity entity = getItem(position);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(entity.getName());
        return textView;
    }
}
