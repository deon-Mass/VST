package cd.digitalEdge.vst.Views.Lists;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import cd.digitalEdge.vst.Adaptors.Adaptor_Articles_list;
import cd.digitalEdge.vst.Adaptors.Adaptor_recherche_list;
import cd.digitalEdge.vst.Adaptors.Slides.SlideAdapter_home;
import cd.digitalEdge.vst.Controllers.Offline.ExecuteUpdate;
import cd.digitalEdge.vst.Objects.Articles;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Tools.Constants;
import cd.digitalEdge.vst.Tools.Utils;

public class Details_Article extends AppCompatActivity {

    Context context = this;
    Adaptor_Articles_list adapter;
    SwipeRefreshLayout swiper;

    ViewPager viewPager;
    ViewPager viewpager;
    private LinearLayout liner;
    /* access modifiers changed from: private */
    public Button back;
    /* access modifiers changed from: private */
    public int mCureentPage;
    /* access modifiers changed from: private */
    public TextView[] mdots;
    /* access modifiers changed from: private */
    public Button next;
    int[] layouts;
    private SlideAdapter_home myadapter;

    TextView name,slut,description,price,availability,stock, qnt, addtocard, Noter;

    int turn = 0;

    Articles Article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_clients);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Détails article");
        //getSupportActionBar().hide();

        if (!getIntent().hasExtra("Article")) onBackPressed();
        else {
            Intent i = getIntent();
            Article = (Articles) i.getSerializableExtra("Article");
        }

        INIT_COMPONENT();
        INIT_COMPONENT_SLIDE();



    }


    private void INIT_COMPONENT() {
        name = findViewById(R.id.name);
        slut = findViewById(R.id.slut);
        price = findViewById(R.id.price);
        availability = findViewById(R.id.availability);
        description = findViewById(R.id.description);
        stock = findViewById(R.id.stock);
        qnt = findViewById(R.id.qnt);
        addtocard = findViewById(R.id.addtocard);
        Noter = findViewById(R.id.Noter);

        name.setText(Article.getName());
        slut.setText(Article.getSlug());
        description.setText(Article.getDescription());
        price.setText(Article.getPrice()+" USD");
        stock.setText(Article.getStock()+" en stock");
        if (Article.getAvailability().equals("available")) availability.setText("Disponible");
        else availability.setText("indisponible");
    }
    ViewPager.OnPageChangeListener viewlistener = new ViewPager.OnPageChangeListener() {
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
            adddots(position);
            mCureentPage = position;
            if (position == 0) {
                next.setEnabled(true);
                back.setEnabled(false);
                //back.setVisibility(View.INVISIBLE);
                back.setText("");
            } else if (position == mdots.length - 1) {
                next.setEnabled(true);
                back.setEnabled(true);
                //back.setVisibility(View.VISIBLE);
            } else {
                next.setEnabled(true);
                back.setEnabled(true);
                //back.setVisibility(View.VISIBLE);
            }
        }

        public void onPageScrollStateChanged(int state) {
        }
    };

    public void INIT_COMPONENT_SLIDE() {
        this.viewPager = (ViewPager) findViewById(R.id.viewpager);
        this.layouts = new int[]{R.layout.intro_slide, R.layout.intro_slide};
        this.viewpager = (ViewPager) findViewById(R.id.viewpager);
        this.liner = (LinearLayout) findViewById(R.id.dots);
        this.next = (Button) findViewById(R.id.nextBtn);
        this.back = (Button) findViewById(R.id.backBtn);
        this.myadapter = new SlideAdapter_home(context);
        this.viewpager.setAdapter(this.myadapter);
        adddots(0);
        this.viewpager.addOnPageChangeListener(this.viewlistener);
        this.next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                viewpager.setCurrentItem(mCureentPage + 1);
            }
        });
        this.back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                viewpager.setCurrentItem(mCureentPage - 1);
            }
        });
        next.setVisibility(View.GONE);
        back.setVisibility(View.GONE);
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
            textViewArr[x] = new TextView(context);
            if (Build.VERSION.SDK_INT >= 24) {
                this.mdots[x].setText(Html.fromHtml("&#8226", 0));
            }
            this.mdots[x].setTextSize(35.0f);
            if (Build.VERSION.SDK_INT >= 23) {
                this.mdots[x].setTextColor(context.getResources().getColor(R.color.gris_dark, context.getTheme()));
            }
            this.liner.addView(this.mdots[x]);
            x++;
        }
        if (textViewArr.length > 0 && Build.VERSION.SDK_INT >= 23) {
            this.mdots[i].setTextColor(context.getResources().getColor(R.color.colorAccent, context.getTheme()));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        qnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View convertView2 = LayoutInflater.from(context).inflate(R.layout.view_number_picker, null);
                NumberPicker qnt_picker =  convertView2.findViewById(R.id.qnt_picker);
                qnt_picker.setMaxValue(5);
                qnt_picker.setMinValue(1);
                qnt_picker.setDisplayedValues(Utils.QNT_PICKER());
                new AlertDialog.Builder(context)
                        .setView(convertView2)
                        .setPositiveButton("D'accord", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                qnt.setText(String.valueOf(qnt_picker.getValue()-1));
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

            }
        });
        Noter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Utils().Noter_article(context, Article.getId());
            }
        });
        addtocard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Adaptor_recherche_list.AddToCard(context, Article);
            }
        });
    }

    @Override
    public void onBackPressed() {
        String source;
        Intent i;
        if (getIntent().hasExtra("source")){
            source = getIntent().getExtras().getString("source");
            if (source.equals("P")) i = new Intent(context, Panier.class);
            else i = new Intent(context, Recherche.class);
        }else{
            i = new Intent(context, Recherche.class);
        }
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //getMenuInflater().inflate(R.menu.recherche_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {

            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;
        }
        return true;
    }

    // TODO METHODS

}
