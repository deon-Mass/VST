package cd.digitalEdge.vst.Tools;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.view.Menu;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.androidnetworking.model.Progress;
import com.anychart.core.Text;
import com.google.android.material.snackbar.Snackbar;
import com.koushikdutta.async.http.body.MultipartFormDataBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import cd.digitalEdge.vst.Controllers.Config;
import cd.digitalEdge.vst.Controllers.Config_preferences;
import cd.digitalEdge.vst.Controllers.Offline.SQLite.Sqlite_selects_methods;
import cd.digitalEdge.vst.Controllers.Online.Updates;
import cd.digitalEdge.vst.MainActivity;
import cd.digitalEdge.vst.Objects.Articles;
import cd.digitalEdge.vst.Objects.Users;
import cd.digitalEdge.vst.R;
import it.sephiroth.android.library.bottomnavigation.MenuParser;
import okhttp3.internal.http.HttpHeaders;


public class Utils {

    Timer mTimer = null;
    public static Handler mHandler = new Handler();

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

    public static String[] QNT_PICKER() {

            String[] qntVal;
            qntVal = new String[10];
            for (int i = 0; i < qntVal.length; i++) {
                qntVal[i] = String.valueOf(i);
            }
            return qntVal;

    }

    public void Noter_article(Context context, String property_id) {
        View convertView = LayoutInflater.from(context).inflate(R.layout.view_note, null);
        RatingBar note = convertView.findViewById(R.id.note);
        TextView note_prod = convertView.findViewById(R.id.note_prod);
        EditText rating_body = convertView.findViewById(R.id.rating_body);
        CheckBox recommand = convertView.findViewById(R.id.recommand);
        final String[] cote = {"0.0"};
        note.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBar.setRating(rating);
                note_prod.setText(String.valueOf(rating)+" /5.0");
            }
        });

        new AlertDialog.Builder(context)
                .setView(convertView)
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Votre note est de : "+note.getRating(), Toast.LENGTH_SHORT).show();
                        String recommaded = "";
                        if (recommand.isChecked()== true) recommaded = "Yes";
                        else recommaded = "No";
                        sendNote(
                                context,
                                property_id,
                                rating_body.getText().toString().replace("'","''").trim(),
                                recommaded,
                                String.valueOf(note.getRating())
                        );
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

    }
    public static void Setting_badge(String count2, TextView textCartItemCount) {
        if (count2.equals("0") || count2 == null){
            textCartItemCount.setVisibility(View.GONE);
        }else{
            textCartItemCount.setVisibility(View.VISIBLE);
            textCartItemCount.setText(count2);
        }
    }

    public static class TimeDisplay extends TimerTask {
        Context context;
        int turn = 0;
        TextView textCartItemCount;
        MenuItem menuItem;

        public TimeDisplay(Context context, int turn, TextView textCartItemCount, MenuItem menuItem) {
            this.context = context;
            this.turn = turn;
            this.textCartItemCount = textCartItemCount;
            this.menuItem = menuItem;
        }

        public void run() {
            mHandler.post(new Runnable() {
                public void run() {
                    Log.e("MYSERVICE---XX", "je tourne bien");
                    int count = Check_Panier(context);
                    View actionView = menuItem.getActionView();
                    textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);
                    if (Preferences.getUserPreferences(context, Constants.PANIER_COUNT) == null || Preferences.getUserPreferences(context, Constants.PANIER_COUNT).equals("")){
                        Utils.Setting_badge("0",textCartItemCount);
                    }else Utils.Setting_badge(String.valueOf(count),textCartItemCount);

                }
            });
        }
    }

    public static int Check_Panier(final Context context) {
        ArrayList<Articles> PANIER = new ArrayList<>();
        PANIER = Sqlite_selects_methods.getall_Articles(context);
        if ( null == PANIER || PANIER.isEmpty() ){
            PANIER = new ArrayList<>();
            //Log.e("DATA", "DATAS "+PANIER.size());
        }
        int count = PANIER.size();
        Preferences.setUserPreferences(context, Constants.PANIER_COUNT, String.valueOf(count));
        return count;
    }

    public void sendNote(Context context,String property_id,String body,String recommanded,String note ) {
        //Log.e("RATING_PARAMS ", property_id+" "+ body+" "+ recommanded+" "+ note );
        AndroidNetworking
                .post(Config.GET_PRODUCT_NOTE)
                .addBodyParameter("body", body)
                .addBodyParameter("user_id", Preferences.getCurrentUser(context).getId())
                .addBodyParameter("property_id", property_id)
                .addBodyParameter("recommend", recommanded)
                .addBodyParameter("note", note)
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                        //Log.e("RATING_ERR ",response);
                    }
                    @Override
                    public void onError(ANError anError) {
                        //Log.e("RATING_ERR ",anError.getMessage());
                    }
                });
    }


    public void SaveToFavoris(Context context,String bienId ) {
        ProgressDialog p = new ProgressDialog(context);
        p.setTitle("");
        p.setMessage("Encours...");
        p.show();
        Log.e("FAVORISXXXX ", Config.SET_FAVORI.concat(Preferences.getCurrentUser(context).getId() +"/"+bienId));
        AndroidNetworking
                .post(Config.SET_FAVORI.concat(Preferences.getCurrentUser(context).getId() +"/"+bienId))
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                        Log.e("RATING_ERR ",response);
                        p.dismiss();
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.e("RATING_ERR ",anError.getMessage());
                        p.dismiss();
                    }

                });
    }

}
