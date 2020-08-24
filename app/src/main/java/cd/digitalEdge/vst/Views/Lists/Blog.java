package cd.digitalEdge.vst.Views.Lists;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.kosalgeek.android.caching.FileCacher;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cd.digitalEdge.vst.Adaptors.Adaptor_blog_list;
import cd.digitalEdge.vst.Controllers.Config;
import cd.digitalEdge.vst.MainActivity;
import cd.digitalEdge.vst.Objects.Posts;
import cd.digitalEdge.vst.Objects.Posts;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Tools.Constants;
import cd.digitalEdge.vst.Tools.Preferences;
import cd.digitalEdge.vst.Tools.Tool;
import cd.digitalEdge.vst.Views.Blanks.Contacts;

public class Blog extends AppCompatActivity {

    Context context = this;
    ListView blog_list;
    LinearLayout progress_data,error404;

    Adaptor_blog_list adapter;

    ArrayList<Posts> POSTS = new ArrayList<>();
    FileCacher<ArrayList<Posts>> DATAS_CACHED = new FileCacher<>(context, Constants.FILE_POSTS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        getSupportActionBar().setTitle("Suprême Blog");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        INIT_COMPONENT();
        //getBlog();
        LoadPosts();
    }

    void INIT_COMPONENT(){
        blog_list = findViewById(R.id.blog_list);
        error404 = findViewById(R.id.error404);
        progress_data = findViewById(R.id.progress_data);

        progress_data.setVisibility(View.GONE);
        error404.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        blog_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (
                        scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE &&
                        (blog_list.getLastVisiblePosition() ) >= adapter.getCount()-2
                ){
                    Animation from_right = AnimationUtils.loadAnimation(context, R.anim.m_fromright);
                    Animation to_right = AnimationUtils.loadAnimation(context, R.anim.m_toleft);
                    progress_data.startAnimation(from_right);
                    progress_data.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progress_data.startAnimation(to_right);
                            progress_data.setVisibility(View.GONE);
                        }
                    }, 2000);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i  = new Intent(context, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.blog_menu, menu);
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
            case R.id.add_post:
                break;
            case R.id.categories:
                Tool.Dialog(context, "Liste de catégoris", "Liste de catégoris");
                break;
            case R.id.refresh:
                //getBlog();
                LoadPosts();
                break;
            case R.id.contact:
                Intent i  = new Intent(context, Contacts.class);
                startActivity(i);
                finish();
                break;
        }

        return true;
    }

    // TODO METHOD
    private void LoadPosts(){
        //Log.i("BLOG_RR ",Config.GET_POST);
        progress_data.setVisibility(View.VISIBLE);
        //error404.setVisibility(View.GONE);
        POSTS.clear();
        AndroidNetworking
                .get(Config.GET_POST)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray ar = response.getJSONArray("posts");
                            for (int i = 0; i < ar.length(); i++) {
                                JSONObject jsonObject = ar.getJSONObject(i);
                                Posts p = new Posts();
                                p.setId(jsonObject.getString(new Posts().id));
                                p.setAuthor_id(jsonObject.getString(new Posts().author_id));
                                p.setCategory_id(jsonObject.getString(new Posts().category_id));
                                p.setTitle(jsonObject.getString(new Posts().title));
                                p.setExcerpt(jsonObject.getString(new Posts().excerpt));
                                p.setBody(jsonObject.getString(new Posts().body));
                                p.setSlug(jsonObject.getString(new Posts().slug));
                                p.setStatus(jsonObject.getString(new Posts().status));
                                p.setCreated_at(jsonObject.getString(new Posts().created_at));
                                p.setImage(jsonObject.getString(new Posts().image));
                                p.setCategory(jsonObject.getString(new Posts().category));
                                p.setComments(jsonObject.getString(new Posts().comments));
                                POSTS.add(p);
                            }

                           DATAS_CACHED.writeCache(POSTS);

                            if (POSTS.size()<1){
                                Toast.makeText(context, "Empy", Toast.LENGTH_SHORT).show();
                                LoadCache();
                            }else{
                                adapter = new Adaptor_blog_list(context, POSTS);
                                blog_list.setAdapter(adapter);
                                error404.setVisibility(View.GONE);
                            }
                            progress_data.setVisibility(View.GONE);

                        } catch (Exception e) {
                            Log.e("BLOG_RR--XX ",e.getMessage());
                            e.printStackTrace();
                            LoadCache();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progress_data.setVisibility(View.GONE);
                        Log.e("BLOG_RR nnn",anError.getMessage());
                        //error404.setVisibility(View.VISIBLE);
                        LoadCache();
                    }
                });
    }

    private void LoadCache() {
        progress_data.setVisibility(View.VISIBLE);
        try {
            if (DATAS_CACHED.getSize()<1){
                Toast.makeText(context, "Empy from cache", Toast.LENGTH_SHORT).show();
                error404.setVisibility(View.VISIBLE);
            }else{
                adapter = new Adaptor_blog_list(context, DATAS_CACHED.readCache());
                blog_list.setAdapter(adapter);
                //error404.setVisibility(View.GONE);
            }
            progress_data.setVisibility(View.GONE);
            //Log.e("CACHED_DATA", String.valueOf(DATAS_CACHED.readCache().size()));
        } catch (Exception e) {
            e.printStackTrace();
            error404.setVisibility(View.VISIBLE);
        }
    }


}
