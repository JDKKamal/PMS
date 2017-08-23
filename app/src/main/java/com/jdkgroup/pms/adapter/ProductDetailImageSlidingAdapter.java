package com.jdkgroup.pms.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jdkgroup.pms.R;

public class ProductDetailImageSlidingAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private Context mcontext;
    private int[] images;

    public ProductDetailImageSlidingAdapter(Context context, int[] imagesDetails) {
        // TODO Auto-generated constructor stub
        this.mcontext = context;
        this.images =imagesDetails;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView((LinearLayout) object);
    }

    @Override
    public void finishUpdate(View container) {
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        // TODO Auto-generated method stu
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        this.inflater = (LayoutInflater) mcontext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View imageLayout = inflater.inflate(R.layout.itemview_product_detail_image_sliding, container, false);
        ViewHolder viewholder = null;
        viewholder = new ViewHolder();
        //viewholder.iv_card_img = (ImageView) imageLayout.findViewById(R.id.iv_card_img);
        //viewholder.iv_card_img.setImageResource(images[position]);
        ((ViewPager) container).addView(imageLayout, 0);
        return imageLayout;
    }

    class ViewHolder{
        ImageView iv_card_img;
        TextView tv_card_name;
    }
}
