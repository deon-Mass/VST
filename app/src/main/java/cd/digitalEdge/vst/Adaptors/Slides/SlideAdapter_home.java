package cd.digitalEdge.vst.Adaptors.Slides;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.viewpager.widget.PagerAdapter;

import cd.digitalEdge.vst.Adaptors.Adaptor_recherche_list;
import cd.digitalEdge.vst.Controllers.Config;
import cd.digitalEdge.vst.R;


public class SlideAdapter_home extends PagerAdapter {
    public int[] list_color = {R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary};
    public String[] list_description = {
            "",
            "",
            "",
            ""};
    //public int[] list_images = {R.drawable.png, R.drawable.png, R.drawable.png, R.drawable.png};

    public String[] list_title = {"", "", "", ""};
    public String[] list_images = {"", "", "", ""};
    Context context;
    LayoutInflater inflater;

    public SlideAdapter_home(Context context2, String[] imgs) {
        this.context = context2;
        list_images = imgs;

        //String url = Config.ROOT_img.concat(imgs[0].substring(1,imgs[0].length()-1));
    }

    public int getCount() {
        return this.list_title.length;
    }

    public boolean isViewFromObject(View view, Object obj) {
        return view == ((RelativeLayout) obj);
    }

    public Object instantiateItem(ViewGroup container, int position) {
        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = this.inflater.inflate(R.layout.intro_slide, container, false);
        RelativeLayout linearLayout = (RelativeLayout) view.findViewById(R.id.slidelinearlayout);
        TextView txt1 = (TextView) view.findViewById(R.id.slidetitle);
        TextView txt2 = (TextView) view.findViewById(R.id.slidedescription);
        TextView textView = (TextView) view.findViewById(R.id.start);
        ImageView img = (ImageView) view.findViewById(R.id.slideimg);
        ProgressBar progress = (ProgressBar) view.findViewById(R.id.progress);

        progress.setVisibility(View.GONE);

        String url = Config.ROOT_img.concat(list_images[position].substring(1,list_images[position].length()-1)); //list_images[position]

        Adaptor_recherche_list.loadImageBitmap(img, url, progress);
        //((ImageView) view.findViewById(R.id.slideimg)).setImageResource(this.);

        txt1.setText(this.list_title[position]);
        txt2.setText(this.list_description[position]);
        linearLayout.setBackgroundColor(this.list_color[position]);
        container.addView(view);
        return view;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
