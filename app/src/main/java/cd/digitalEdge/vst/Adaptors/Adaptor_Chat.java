package cd.digitalEdge.vst.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import android.widget.BaseAdapter;

import java.util.ArrayList;

import cd.digitalEdge.vst.Objects.Chats;
import cd.digitalEdge.vst.R;

public class Adaptor_Chat extends Adapter<Adaptor_Chat.holder>{
    ArrayList<Chats> DATAS;
    Context context;

    public class holder extends ViewHolder {
        LinearLayout IP;
        LinearLayout OP;
        TextView input;
        TextView inputdate;
        TextView output;
        TextView outputdate;

        public holder(View convertView) {
            super(convertView);
            this.output = (TextView) convertView.findViewById(R.id.output);
            this.inputdate = (TextView) convertView.findViewById(R.id.inputdate);
            this.input = (TextView) convertView.findViewById(R.id.input);
            this.outputdate = (TextView) convertView.findViewById(R.id.outputdate);
            this.IP = (LinearLayout) convertView.findViewById(R.id.IP);
            this.OP = (LinearLayout) convertView.findViewById(R.id.OP);
        }
    }

    public Adaptor_Chat(Context context2) {
        this.context = context2;
    }

    public holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.model_chat_item, parent, false));
    }

    public void onBindViewHolder(holder holder2, int position) {

    }

    public int getItemCount() {
        return 5;
    }
}
