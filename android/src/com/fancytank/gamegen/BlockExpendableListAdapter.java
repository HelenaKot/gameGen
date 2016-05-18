package com.fancytank.gamegen;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.fancytank.gamegen.programming.blocks.BlockActorPattern;
import com.fancytank.gamegen.programming.blocks.BlockCreateEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class BlockExpendableListAdapter extends BaseExpandableListAdapter {

    private Activity activity;
    private ArrayList<BlockActorPattern[]> childtems;
    private BlockActorPattern[] childsArray;
    private LayoutInflater inflater;
    private ArrayList<String> parentItems;

    public BlockExpendableListAdapter(ArrayList<String> parents, ArrayList<BlockActorPattern[]> childern) {
        this.parentItems = parents;
        this.childtems = childern;
    }

    public void setInflater(LayoutInflater inflater, Activity activity) {
        this.inflater = inflater;
        this.activity = activity;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        childsArray = childtems.get(groupPosition);

        TextView textView = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.drawer_list_item, null);
        }

        textView = (TextView) convertView.findViewById(R.id.item_title);
        textView.setText(childsArray[childPosition].getName());

        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                BlockCreateEvent bce = new BlockCreateEvent(childsArray[childPosition]);
                EventBus.getDefault().post(bce);
            }
        });

        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.drawer_list_group, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.group_title);
        textView.setText(parentItems.get(groupPosition));

        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return (childtems.get(groupPosition)).length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return parentItems.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}