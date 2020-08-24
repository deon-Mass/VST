package cd.digitalEdge.vst.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cd.digitalEdge.vst.R;

public class Adaptor_blog_comments extends BaseAdapter {

    Context context;
    JSONArray jsonArr;

    public Adaptor_blog_comments(Context context, JSONArray jsonArr) {
        this.context = context;
        this.jsonArr = jsonArr;
    }

    public int getCount() {
        return jsonArr.length();
    }

    public Object getItem(int position) {
        try {
            return jsonArr.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.model_wallet1_list, null);

        ImageView image = view.findViewById(R.id.image);
        TextView name = view.findViewById(R.id.name);
        TextView mail = view.findViewById(R.id.mail);
        TextView comment = view.findViewById(R.id.comment);

        try {
            JSONObject jsonObj = jsonArr.getJSONObject(0);
            comment.setText(jsonObj.getString("comment"));

            JSONObject byObj = new JSONObject(jsonObj.getString("commenter"));
            name.setText(byObj.getString("name"));
            mail.setText(byObj.getString("email"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return view;
    }

}
