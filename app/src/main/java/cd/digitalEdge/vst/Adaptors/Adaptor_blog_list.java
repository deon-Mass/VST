package cd.digitalEdge.vst.Adaptors;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.function.Consumer;

import cd.digitalEdge.vst.Objects.Post_Category;
import cd.digitalEdge.vst.Objects.Post_Comments;
import cd.digitalEdge.vst.Objects.Posts;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Tools.Tool;

public class Adaptor_blog_list extends BaseAdapter {

    Context context;
    ArrayList<Posts> POSTS;

    public Adaptor_blog_list(Context context, ArrayList<Posts> POSTS) {
        this.context = context;
        this.POSTS = POSTS;
    }

    public int getCount() {
        return POSTS.size();
    }

    public Object getItem(int position) {
        return POSTS.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View getView(int position, View convertView, ViewGroup parent) {
        View convertView2 = LayoutInflater.from(this.context).inflate(R.layout.model_blog_item, null);
        CardView CARD = convertView2.findViewById(R.id.CARD);
        TextView title = convertView2.findViewById(R.id.title);
        TextView detail = convertView2.findViewById(R.id.detail);
        TextView time = convertView2.findViewById(R.id.time);
        TextView categorie = convertView2.findViewById(R.id.categorie);
        TextView commentaires = convertView2.findViewById(R.id.commentaires);

        Posts data = POSTS.get(position);

        title.setText(data.title);
        detail.setText(data.excerpt);

        Gson g = new Gson();
        Post_Category  cat = g.fromJson(data.getCategory(), Post_Category.class);
        categorie.setText(cat.getName());
        time.setText(data.created_at);


        try {
            JSONArray jsonArr = null;
            jsonArr = new JSONArray(data.getComments());
            commentaires.setText(jsonArr.length()+" Commentaire(s)");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonArray arr = new JsonArray();
        arr.add(data.getComments());


        CARD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetailsBlog(context, data);

            }
        });
        commentaires.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tool.Dialog(context, "Les Commentaires", "Liste de commentaires");
            }
        });
        return convertView2;
    }

    public static void showDetailsBlog(Context context, Posts data) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_blog_details, null);
        TextView title = view.findViewById(R.id.title);
        TextView detail = view.findViewById(R.id.detail);
        TextView time = view.findViewById(R.id.time);
        TextView categorie = view.findViewById(R.id.categorie);
        ListView blog_comments_list = view.findViewById(R.id.blog_comments_list);



        title.setText(data.title);
        detail.setText(data.body);

        Gson g = new Gson();
        Post_Category  cat = g.fromJson(data.getCategory(), Post_Category.class);
        categorie.setText(cat.getName());
        time.setText(data.created_at);


        try {
            JSONArray jsonArr = null;
            jsonArr = new JSONArray(data.getComments());
            Adaptor_blog_comments ad = new Adaptor_blog_comments(context, jsonArr);
            blog_comments_list.setAdapter(ad);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(false)
                .setPositiveButton("Fermer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

}
