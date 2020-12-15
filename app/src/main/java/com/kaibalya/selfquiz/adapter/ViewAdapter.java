package com.kaibalya.selfquiz.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.common.collect.BiMap;
import com.kaibalya.selfquiz.CategoryActivity;
import com.kaibalya.selfquiz.Question;
import com.kaibalya.selfquiz.R;
import com.kaibalya.selfquiz.model.Category;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewAdapter extends BaseAdapter {
    List<Category> lst;

    public ViewAdapter(List<Category> lst) {
        this.lst = lst;
    }

    @Override
    public int getCount() {
        return lst.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        CircleImageView img ;
        if(convertView == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_view, parent, false);
        }else{
            view = convertView;
        }

        img = view.findViewById(R.id.imageView);
        ((TextView)view.findViewById(R.id.textview)).setText(lst.get(position).getName());
        Glide.with(view.getContext()).load(lst.get(position).getUrl()).into(img);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", lst.get(position).getName());
                Intent intent = new Intent(view.getContext(), Question.class);
                intent.putExtra("name", lst.get(position).getName());
                view.getContext().startActivity(intent);
            }
        });
        return view;
    }
}
