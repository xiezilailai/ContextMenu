package com.example.xiezi.contextmenu;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by 蝎子莱莱123 on 2015/12/9.
 */
public class Dialog extends android.app.DialogFragment implements View.OnClickListener {
    private static int cu = 0;
    private static int cnn = 0;
    //    private View rootView;
    private ImageView imageView_test;



    private Handler myHandler;
    private OnDialogClickListener listener;
    private Animation translate_animation;//出现时从右边出来
    private Animation alphha_animation;//出来时从透明到不透明
    private AnimationSet animationSet;//出来时的动画集合

    private AnimationSet disappearAnimationSet;//消失时进入右面
    private Animation disappear_translate_animation;//消失时位移
    private Animation disappear_alpha_animation;//消失
    private LinearLayout button_container,text_container;

    private View rootView;
    private int count, pos;

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.MenuFragmentStyle);


        animationSet = new AnimationSet(false);
        disappearAnimationSet = new AnimationSet(false);
        disappear_translate_animation = new TranslateAnimation(0, 130, 0, 0);
        disappear_translate_animation.setDuration(200);
        disappear_alpha_animation=new AlphaAnimation(1,0);
        disappear_alpha_animation.setDuration(200);
//        disappearAnimationSet.addAnimation(disappear_alpha_animation);
//        disappearAnimationSet.addAnimation(disappear_translate_animation);
//        disappearAnimationSet.setDuration(2000);
        /**
         * 监听消失动画，消失动画与出现动画不一样，效果为点击图片上下非点击项逐渐消失，
         * 消失后点击事件触发
         *
         * 所以必须监听每一次消失的那个，在那个结束后才开始轮到接下来的图标，
         * 这样才能观察到动画效果,
         *
         * 记得清理掉动画效果
         *
         *
         */

        disappear_translate_animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if (count % 2 == 0) {//有偶数个时
                    if (pos != cnn) {
                        clear(cnn);
                    }
                    if (pos != count - 1 - cnn) {
                        clear(count-1-cnn);
                    }

                    cnn += 1;
                    Log.e("my", cnn + "");
                    if (cnn < count / 2) {
                        if (pos != cnn){
                            itemDisappear(cnn);

                        }
                        if (pos != count - 1 - cnn){
                            itemDisappear(count-1-cnn);
                        }
                    } else {
                        cnn = 0;
                        listener.dialogClicked(pos);
                        dismiss();
                    }
                }
                /*
                * 有奇数个时
                * */
                else{
                    if (pos != cnn) {
                            clear(cnn);
                    }

                    if (pos != count - 1 - cnn) {
                        clear(count-1-cnn);
                    }
                    cnn += 1;
                    Log.e("my", cnn + "");
                    if (cnn < count / 2) {
                        if (pos != cnn){
                            itemDisappear(cnn);
                        }
                        if (pos != count - 1 - cnn){
                            itemDisappear(count-1-cnn);

                        }

                    }
                    else if(cnn==count/2){
                        if(pos!=cnn){
                            itemDisappear(cnn);
                            listener.dialogClicked(pos);
//                            dismiss();

                        }
                        else{
                            listener.dialogClicked(pos);
                            dismiss();
                             cnn=0;
                        }
                    }else{
                        dismiss();
                        cnn=0;
                    }
                }


            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        translate_animation = new TranslateAnimation(130, 0, 0, 0);
        translate_animation.setDuration(200);
        alphha_animation = new AlphaAnimation(0, 1);
        alphha_animation.setDuration(200);
        animationSet.addAnimation(translate_animation);
        animationSet.addAnimation(alphha_animation);


        LayoutInflater inflater = LayoutInflater.from(getActivity());
        rootView = inflater.inflate(R.layout.text, null);
        button_container = (LinearLayout) rootView.findViewById(R.id.button_container);
        text_container=(LinearLayout)rootView.findViewById(R.id.text_container);
        LayoutAnimationController controller = new LayoutAnimationController(animationSet);
        button_container.setLayoutAnimation(controller);


    }


    public void setListener(OnDialogClickListener listener) {
        this.listener = listener;
    }

    /*
    * 在将fragment去掉时，将cnn初始化为0
    * */
    @Override
    public void dismiss() {
        cnn=0;
        super.dismiss();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        cu = 0;

        imageView_test = new ImageView(getActivity());
        imageView_test.setImageResource(R.drawable.icn_1);

        /*
        设置蒙版的透明度为0.7
        * */
        WindowManager.LayoutParams lp = getDialog().getWindow().getAttributes();
        lp.dimAmount = 0.7f;
        getDialog().getWindow().setAttributes(lp);
        getDialog().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);



        /*目前只支持在Dialog类里面添加*/
        add("", R.drawable.icn_close);
        add("aaaa", R.drawable.icn_1);
        add("bbbb", R.drawable.icn_2);
        add("cccc", R.drawable.icn_3);
        add("dddd", R.drawable.icn_4);
        add("eeee", R.drawable.icn_5);


        return rootView;
    }

    public void add(String s, int pic) {
        addT(s);
        addV(pic);
    }

    /*
    * 添加图片
    * 这里的imageV是自己定义的imageView
    * 在imageView的基础上添加了一条横线
    * 每次默认添加图片时会自动用一条横线
    * 同时这样集合起来也可以方便后面动画的执行
    *
    *
    * */
    private void addV(int imageViewResource) {

        ImageV imageV=new ImageV(getActivity());
        imageV.setImageView(imageViewResource);
        imageV.setPadding(25,13,25,13);
        imageV.setBackgroundColor(Color.WHITE);
        imageV.setTag(cu);
        imageV.setOnClickListener(this);
        button_container.addView(imageV);
        cu++;
    }

    private void addT(String text) {

        TextView textView = new TextView(getActivity());
        textView.setTextColor(Color.WHITE);
        textView.setHeight(130);
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        LinearLayout text_container = (LinearLayout) rootView.findViewById(R.id.text_container);
        text_container.addView(textView);


    }


    /*
    * disappear（）是指点击后动画的生成，里面也实现了接口来确定点击每一个后
    * 将要执行的操作
    *
    * 用tag来记录点击的是哪一个
    *
    * */
    @Override
    public void onClick(View v) {
        pos = (int) v.getTag();
        disappear();
    }

    public interface OnDialogClickListener {
        void dialogClicked(int position);
    }


    /*
    * disappear中pos指的是点击的位置
    *
    * cnn是一个静态变量，用来记录当前消失动画进行到了哪一组，
    *
    * 记得判断将要消失的是不是点击的，如果是那么就不要消失
    * 等待最后再做处理
    *
    *
    * */
    private void disappear() {
        count = button_container.getChildCount();

        if (pos != cnn){
            itemDisappear(cnn);
        }

        if (pos != count - 1 - cnn){
            itemDisappear(count-1-cnn);
        }


    }

    private void itemDisappear(int item){
        button_container.getChildAt(item).startAnimation(disappear_translate_animation);
        text_container.getChildAt(item).startAnimation(disappear_alpha_animation);
    }
    private void clear(int item){
        button_container.getChildAt(item).clearAnimation();
        button_container.getChildAt(item).setAlpha(0);
        text_container.getChildAt(item).clearAnimation();
        text_container.getChildAt(item).setAlpha(0);
    }



}
