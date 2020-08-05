package cd.digitalEdge.vst.Tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.anychart.editor.Editor;
import com.google.gson.Gson;

import cd.digitalEdge.vst.Controllers.Config_preferences;
import cd.digitalEdge.vst.Objects.Users;

import static android.content.Context.MODE_PRIVATE;

public class Preferences {

    public static SharedPreferences User_Preferences(Context context){
        return context.getSharedPreferences("User_Identities", MODE_PRIVATE);
    }

    public static void userPreferences_Init(Context context){
        Users columns = new Users();
        setUserPreferences(context,"FirstUse","null");
        setUserPreferences(context,columns.id,"null");
    }

    public static void setUserPreferences(Context context, String key, String values){
        try {
            SharedPreferences.Editor editor = User_Preferences(context).edit();
            editor.putString(key, values);
            editor.apply();
            //Toast.makeText(context, key+" = "+values, Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.e("TAG_PREFERENCES", e.getMessage());
            //Toast.makeText(context, key+" error", Toast.LENGTH_SHORT).show();
        }
    }

    public static String getUserPreferences(Context context, String key ){
        SharedPreferences mshPreferences = User_Preferences(context);
        return mshPreferences.getString(key,"");
    }

    public static Users getCurrentUser(Context context){
        Gson gson = new Gson();
        String json = getUserPreferences(context,Config_preferences.CURRENT_USER);
        Users user = gson.fromJson(json, Users.class);
        return user;
    }
    public static void SaveCurrentUser(Context context ,Users user){
        Gson gson = new Gson();
        String json = gson.toJson(user);
        setUserPreferences(context, Config_preferences.CURRENT_USER,json);
    }


}
