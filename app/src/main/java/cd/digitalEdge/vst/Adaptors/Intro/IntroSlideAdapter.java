package cd.digitalEdge.vst.Adaptors.Intro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewpager.widget.PagerAdapter;

import cd.digitalEdge.vst.R;


public class IntroSlideAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    public int[] list_color = {R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary};
    public String[] list_description = {
            " Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt",
            " Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt",
            " Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt",
            " Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt"
    };
    public int[] list_images = {R.drawable.png, R.drawable.png, R.drawable.png, R.drawable.png};
    public String[] list_title = {
            "Suprême Shop",
            "Suprême Shop",
            "Suprême Shop",
            "Suprême Shop",
    };

    public IntroSlideAdapter(Context context2) {
        this.context = context2;
    }

    public int getCount() {
        return this.list_title.length;
    }

    public boolean isViewFromObject(View view, Object obj) {
        return view == ((LinearLayout) obj);
    }

    public Object instantiateItem(ViewGroup container, int position) {
        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = this.inflater.inflate(R.layout.intro, container, false);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.slidelinearlayout);
        TextView txt1 = (TextView) view.findViewById(R.id.slidetitle);
        TextView txt2 = (TextView) view.findViewById(R.id.slidedescription);
        TextView textView = (TextView) view.findViewById(R.id.start);
        ((ImageView) view.findViewById(R.id.slideimg)).setImageResource(this.list_images[position]);
        txt1.setText(this.list_title[position]);
        txt2.setText(this.list_description[position]);
        linearLayout.setBackgroundColor(this.list_color[position]);
        container.addView(view);
        return view;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
