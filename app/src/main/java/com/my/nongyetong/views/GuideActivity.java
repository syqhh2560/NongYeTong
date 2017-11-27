package com.my.nongyetong.views;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.my.nongyetong.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

@ContentView(R.layout.activity_guide)
public class GuideActivity extends Activity {
    @ViewInject(R.id.guide_view_pager)
    private ViewPager pager ;
    @ViewInject(R.id.guide_btn)
    private Button guide_btn ;
    @ViewInject(R.id.yuan_layout)
    private LinearLayout yuan_layout ;
    @ViewInject(R.id.red_img)
    private ImageView red_img ;

    private int images[] = new int[]{R.mipmap.guide_1,R.mipmap.guide_2,R.mipmap.guide_3};
    private ArrayList<ImageView> imgList;
    private int redPoint = 0 ;//红点需要移动的位置
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        //使用xutils3
        x.view().inject(this);

        //设置值
        setData();
        //设置事件
        setClick();
    }
    private void setClick(){
        /**
         * //监听ViewPager的滑动事件  小红点移动
         * 翻页  开始体验的button 显示与隐藏
         */

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //获取红点的位置
                int leftMargin = (int)(redPoint * positionOffset + position * redPoint + 0.5f);
                //设置 layout的左边距
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)red_img.getLayoutParams();
                params.leftMargin = leftMargin;

                red_img.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                if(position == images.length-1){
                    guide_btn.setVisibility(View.VISIBLE);
                }else{
                    guide_btn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        /**
         * 布局绘制的三个流程  measure 测量   layout设置位置   draw 画
         * 必须在onCreate()方法结束后 才会走三个方法
         * 所以 必须监听 layout树 是否绘制结束
         * 结束再获取left位置
         */
        red_img.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
            //绘制结束  自动回调
            @Override
            public void onGlobalLayout() {

                //第二个圆减掉第一个圆的 左边距 就是红圆需要移动的位置
               redPoint = yuan_layout.getChildAt(1).getLeft()-yuan_layout.getChildAt(0).getLeft();
                //删除掉观察者 监听  要不会执行多次
                red_img.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    private void setData(){
        //三张图片
        imgList = new ArrayList();
        for(int i=0;i<images.length;i++){
            ImageView iv = new ImageView(this);
            iv.setBackgroundResource(images[i]);
            imgList.add(iv);

            //创建三个灰色的圆
            ImageView shapView = new ImageView(this);
            shapView.setBackgroundResource(R.drawable.shape_point_normal);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams .WRAP_CONTENT);
            //如果是第二章图片 那么离第一张10dp距离
            if(i>0){
                params.leftMargin = 10 ;
            }
            shapView.setLayoutParams(params);

            this.yuan_layout.addView(shapView);
        }

        pager.setAdapter(new MyPagerAdapter());
    }
    //viewpager 的 adapter
    class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        //初始化
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = (ImageView)imgList.get(position);
            container.addView(view);
            return view;
        }
        //销毁
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }
}
