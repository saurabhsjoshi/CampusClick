package com.sau.campusclick.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sau.campusclick.ImageViewerActivity;
import com.sau.campusclick.R;
import com.sau.campusclick.model.Picture;
import com.squareup.picasso.Picasso;

/**
 * Created by saurabh on 2017-04-13.
 */

public class ListAdapter extends ArrayAdapter<Picture> {

    //ViewHolder pattern to cache views in the list.
    private static class ViewHolder {
        ImageView imgPic;
        TextView txtLat;
    }


    public ListAdapter(Context context, Picture[] pictures) {
        super(context, 0, pictures);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Picture picture = getItem(position);
        ViewHolder viewHolder;
        final View result;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_picture, parent, false);
            viewHolder.txtLat = (TextView) convertView.findViewById(R.id.txt_lat);
            viewHolder.imgPic = (ImageView) convertView.findViewById(R.id.img_pic);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.txtLat.setText(picture.getLat() + "," + picture.getLon());
        viewHolder.imgPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), ImageViewerActivity.class);
                i.putExtra("pic", picture);
                getContext().startActivity(i);
            }
        });
        Picasso.with(getContext()).load("http://138.197.137.60/campusclick/uploads/" + picture.getId() + ".jpg").fit().centerCrop().into(viewHolder.imgPic);

        return result;

    }
}
