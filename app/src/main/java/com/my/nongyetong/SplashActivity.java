package com.my.nongyetong;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.my.nongyetong.utils.PrefUtils;
import com.my.nongyetong.views.GuideActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 首页显示一段动画
 */
@ContentView(R.layout.activity_splash)
public class SplashActivity extends Activity {
    @ViewInject(R.id.splash_layout)
    private RelativeLayout splash_layout  ;

    private AnimationSet as ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        //setContentView(R.layout.activity_splash);
        //设置动画
        setAnimation();
        //设置事件
        setAnimationListener();
    }

    private void setAnimation(){
        //旋转   基于自身中心点旋转360度
        RotateAnimation rt = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f);
        rt.setDuration(1000); //动画时间
        rt.setFillAfter(true);//保持住动画结束的状态

        //缩放
        ScaleAnimation st = new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        st.setDuration(1000);
        st.setFillAfter(true);

        //渐变
        AlphaAnimation at = new AlphaAnimation(0,1);
        at.setDuration(2000);
        at.setFillAfter(true);

        //动画集合 同时运行
        as = new AnimationSet(false);
        as.addAnimation(rt);
        as.addAnimation(st);
        as.addAnimation(at);

        splash_layout.setAnimation(as);

    }
    private void setAnimationListener(){
        if(as==null) return ;
        //判断动画是否结束  结束就跳转到主页面 还是向导页
        as.setAnimationListener(new Animation.AnimationListener(){

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                boolean isShow = PrefUtils.getBoolean(SplashActivity.this,"is_guide_show",false);
                if(isShow){
                    //已经展示过了 就跳转到 首页
                }else{
                    //没有展示过  跳转到展示页
                    Intent it = new Intent(SplashActivity.this, GuideActivity.class);
                    SplashActivity.this.startActivity(it);
                    finish();
                }
                SplashActivity.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
