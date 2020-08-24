package cd.digitalEdge.vst.Views.Diapo;

import android.os.Bundle;

import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;

import androidx.appcompat.app.AppCompatActivity;

import cd.digitalEdge.vst.Adaptors.Intro.IntroSlideAdapter;
import cd.digitalEdge.vst.MainActivity;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Views.About_app;

public class IntroActivity extends AppCompatActivity {
    public Button back;
    Context context = this;
    int[] layouts;
    private LinearLayout liner;
    /* access modifiers changed from: private */
    public int mCureentPage;
    /* access modifiers changed from: private */
    public TextView[] mdots;
    private IntroSlideAdapter myadapter;
    /* access modifiers changed from: private */
    public Button next;
    ViewPager viewPager;
    OnPageChangeListener viewlistener = new OnPageChangeListener() {
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
            adddots(position);
            mCureentPage = position;
            String str = "SUIVANT";
            if (position == 0) {
                next.setEnabled(true);
                back.setEnabled(false);
                back.setVisibility(View.INVISIBLE);
                next.setText(str);
                back.setText("");
                return;
            }
            String str2 = "ARRIERE";
            if (position == mdots.length - 1) {
                next.setEnabled(true);
                back.setEnabled(true);
                back.setVisibility(View.VISIBLE);
                next.setText("COMMENCER");
                back.setText(str2);
                return;
            }
            next.setEnabled(true);
            back.setEnabled(true);
            back.setVisibility(View.VISIBLE);
            next.setText(str);
            back.setText(str2);
        }

        public void onPageScrollStateChanged(int state) {
        }
    };
    /* access modifiers changed from: private */
    public ViewPager viewpager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        this.viewPager = (ViewPager) findViewById(R.id.viewpager);
        this.layouts = new int[]{R.layout.intro, R.layout.intro};
        this.viewpager = (ViewPager) findViewById(R.id.viewpager);
        this.liner = (LinearLayout) findViewById(R.id.dots);
        this.next = (Button) findViewById(R.id.nextBtn);
        this.back = (Button) findViewById(R.id.backBtn);
        this.myadapter = new IntroSlideAdapter(this);
        this.viewpager.setAdapter(this.myadapter);
        adddots(0);
        this.viewpager.addOnPageChangeListener(this.viewlistener);
        this.next.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (next.getText().equals("COMMENCER")) {
                    startActivity(new Intent(context, About_app.class));
                    finish();
                }
                viewpager.setCurrentItem(mCureentPage + 1);
            }
        });
        this.back.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                viewpager.setCurrentItem(mCureentPage - 1);
            }
        });



    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(context, About_app.class));
        finish();
    }

    public void adddots(int i) {
        TextView[] textViewArr;
        this.mdots = new TextView[4];
        this.liner.removeAllViews();
        int x = 0;
        while (true) {
            textViewArr = this.mdots;
            if (x >= textViewArr.length) {
                break;
            }
            textViewArr[x] = new TextView(this);
            if (VERSION.SDK_INT >= 24) {
                this.mdots[x].setText(Html.fromHtml("&#8226", 0));
            }
            this.mdots[x].setTextSize(35.0f);
            if (VERSION.SDK_INT >= 23) {
                this.mdots[x].setTextColor(this.context.getResources().getColor(R.color.gris, this.context.getTheme()));
            }
            this.liner.addView(this.mdots[x]);
            x++;
        }
        if (textViewArr.length > 0 && VERSION.SDK_INT >= 23) {
            this.mdots[i].setTextColor(this.context.getResources().getColor(R.color.white, this.context.getTheme()));
        }
    }
}

