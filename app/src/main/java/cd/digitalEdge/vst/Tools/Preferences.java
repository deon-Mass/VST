package cd.digitalEdge.vst.Tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


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
        setUserPreferences(context,columns.role_id,"null");
        setUserPreferences(context,columns.username,"null");
        setUserPreferences(context,columns.email,"null");
        setUserPreferences(context,columns.phone,"null");
        setUserPreferences(context,columns.password,"null");
        setUserPreferences(context,columns.avatar,"null");
        setUserPreferences(context,columns.address,"null");
        setUserPreferences(context,columns.gender,"null");
        setUserPreferences(context,columns.office_id,"null");
        setUserPreferences(context,columns.project_ids,"null");
        setUserPreferences(context,columns.created_at,"null");
        setUserPreferences(context,columns.status,"null");
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
}
