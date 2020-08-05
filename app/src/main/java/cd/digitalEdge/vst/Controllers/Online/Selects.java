package cd.digitalEdge.vst.Controllers.Online;

import android.util.Log;

import com.google.android.gms.actions.SearchIntents;
import com.koushikdutta.async.http.AsyncHttpRequest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import cd.digitalEdge.vst.Controllers.Config;


public class Selects {
    public final int TIME_OUT = AsyncHttpRequest.DEFAULT_TIMEOUT;
    public final String auth = Config.auth;
    HttpURLConnection httpURLConnection = null;
    InputStream in = null;
    public final String method = "1";
    OutputStream out = null;

    public String UPDATE_REQUEST(String query){
        String r = "-1";
        try {
            URL url = new URL(Config.GET_PRODUCTS);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");

            OutputStream out = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));

            String data =
                    URLEncoder.encode( "auth","UTF-8" ) +"="+ URLEncoder.encode(auth,"UTF-8" ) +"&"+
                    URLEncoder.encode( "method","UTF-8" ) +"="+ URLEncoder.encode(method,"UTF-8" ) +"&"+
                    URLEncoder.encode( "query","UTF-8" ) +"="+ URLEncoder.encode(query,"UTF-8" );

            //writer.write(data);
            writer.flush();
            writer.close();

            InputStream inputStream = connection.getInputStream();
            BufferedReader bw = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            // todo : Keeping data in a StringBuffer
            while ((line=bw.readLine()) !=null){
                buffer.append(line);
                Log.i("TAG_UPDATE", " LINE_DATAS "+ line);
            }

            r = buffer.toString();
            //Log.i("TAG_UPDATE","reponse du serveur "+ r);
            inputStream.close();
            connection.disconnect();
            r = "1";

        }catch (Exception e){
            e.printStackTrace();
            Log.e("TAG_UPDATE",e.getMessage());
            r = "-1";
        }
        return r;
    }
}
