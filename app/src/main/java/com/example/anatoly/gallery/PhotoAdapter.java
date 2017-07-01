package com.example.anatoly.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Anatoly on 1.07.2017.
 */

public class PhotoAdapter extends BaseAdapter {

    private List<Bitmap> items = new LinkedList<>();
    private final LayoutInflater layoutInflater;
    private final Context context;

    public PhotoAdapter(Context context) {
        items = new LinkedList<>();
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = (ImageView) convertView;
        if (imageView == null) {
            imageView = (ImageView) layoutInflater.inflate(R.layout.photo_layout, parent, false);
        }
        imageView.setImageBitmap((Bitmap) getItem(position));
        return imageView;
    }

    public void add_item(Bitmap img) {
        items.add(0, img);
    }
}
