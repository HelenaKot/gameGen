package com.fancytank.gamegen.programming;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.fancytank.gamegen.R;
import com.fancytank.gamegen.programming.blocks.BlockActorPattern;
import com.fancytank.gamegen.programming.blocks.BlockCreateEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class BlockExpendableListAdapter extends BaseExpandableListAdapter {

    private ArrayList<BlockActorPattern[]> items;
    private BlockActorPattern[] itemsArray;
    private LayoutInflater inflater;
    private ArrayList<String> titleItems;

    public BlockExpendableListAdapter(ArrayList<String> parents, ArrayList<BlockActorPattern[]> childern) {
        this.titleItems = parents;
        this.items = childern;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        itemsArray = items.get(groupPosition);

        TextView textView = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.programming_list_item, null);
        }

        textView = (TextView) convertView.findViewById(R.id.item_title);
        textView.setText(itemsArray[childPosition].getName());

        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                BlockCreateEvent bce = new BlockCreateEvent(itemsArray[childPosition]);
                EventBus.getDefault().post(bce);
            }
        });

        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.programming_list_group, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.group_title);
        textView.setText(titleItems.get(groupPosition));

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
        return (items.get(groupPosition)).length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return titleItems.size();
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