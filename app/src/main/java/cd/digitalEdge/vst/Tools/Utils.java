package cd.digitalEdge.vst.Tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cd.digitalEdge.vst.R;


public class Utils {
    public static void MonToast(Context context, String titres, String message, String type) {
        View view = LayoutInflater.from(context).inflate(R.layout.toast_auth_info, null);
        LinearLayout back = (LinearLayout) view.findViewById(R.id.back);
        TextView sous_titre = (TextView) view.findViewById(R.id.sous);
        ((TextView) view.findViewById(R.id.titre)).setText(titres);
        sous_titre.setText(message);
        if (type.equals("Danger")) {
            back.setBackgroundColor(context.getResources().getColor(R.color.red));
        } else if (type.equals("Success")) {
            back.setBackgroundColor(context.getResources().getColor(R.color.green));
        } else if (type.equals("Info")) {
            back.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        }
        Toast toast = Toast.makeText(context, "", Toast.LENGTH_LONG);
        toast.setGravity(48, 0, 0);
        toast.setView(view);
        toast.show();
    }
}
