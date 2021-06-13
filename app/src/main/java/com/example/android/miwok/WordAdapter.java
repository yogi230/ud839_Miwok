package com.example.android.miwok;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter  extends ArrayAdapter<word> {
    private int mcolor_res;
    private MediaPlayer mediaPlayer;

    public WordAdapter(Context context, ArrayList<word> words,int color_res)
    {
        super(context,0,words);
        mcolor_res=color_res;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        word Word=getItem(position);
        if(convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);

        }
        TextView miwok=(TextView)convertView.findViewById(R.id.miwok_text_view);
        miwok.setText(Word.getMiwokTranslation());
        TextView defa=(TextView)convertView.findViewById(R.id.default_text_view);
        defa.setText(Word.getDefaultTranslation());
        ImageView img=(ImageView)convertView.findViewById(R.id.image_view);
        if(Word.hasId())
        {
            int val=Word.getImageResource();
            img.setImageResource(val);
            img.setVisibility(View.VISIBLE);
        }
        else
        {
            img.setVisibility(View.GONE);
        }
        View mtext_list=convertView.findViewById(R.id.text_list);
        mtext_list.setBackgroundColor(ContextCompat.getColor(getContext(), mcolor_res));


        return convertView;
    }
}
