package com.example.xiezi.contextmenu;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by 蝎子莱莱123 on 2015/12/13.
 */
class ImageV extends LinearLayout {


    private ImageView imageView;





    public void setImageView(int imageViewResource){
        this.imageView.setImageResource(imageViewResource);
        ViewGroup.LayoutParams imageViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageViewParams.width = 130;
        imageViewParams.height = 130;
        this.imageView.setLayoutParams(imageViewParams);
    }
    public void setBackgroundColor(int color){
        this.imageView.setBackgroundColor(color);
    }
    public void setPadding(int a,int b,int c,int d){

        this.imageView.setPadding(a,b,c,d);
    }
    public ImageV(Context context) {
        super(context);
        init(context);
    }

    public ImageV(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public ImageV(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }
    private void init(Context context){
        this.setOrientation(LinearLayout.VERTICAL);
        this.imageView=new ImageView(context);
        this.addView(imageView);
        View line=new View(context);
        ViewGroup.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.height=1;
        lp.width=130;
        line.setLayoutParams(lp);
        line.setBackgroundColor(Color.GRAY);
        ImageV.this.addView(line);
    }
}
