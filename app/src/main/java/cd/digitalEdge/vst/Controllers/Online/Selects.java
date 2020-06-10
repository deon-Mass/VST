package cd.digitalEdge.vst.Controllers.Online;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class Selects {
    /*public final int TIME_OUT = AsyncHttpRequest.DEFAULT_TIMEOUT;
    public final String auth = Config.auth;
    HttpURLConnection httpURLConnection = null;
    InputStream in = null;
    public final String method = "1";
    OutputStream out = null;

    public StringBuffer Get_stringBuffered(String query) {
        String str = "&";
        String str2 = "=";
        String str3 = "UTF-8";
        StringBuffer stringBuffer = new StringBuffer();
        try {
            URL url = new URL(Config.SERVER_PATH);
            StringBuilder sb = new StringBuilder();
            sb.append("I was Here !!! ");
            sb.append(Config.SERVER_PATH);
            Log.i("TAG", sb.toString());
            this.httpURLConnection = (HttpURLConnection) url.openConnection();
            this.httpURLConnection.setRequestMethod("POST");
            this.httpURLConnection.setDoInput(true);
            this.httpURLConnection.setDoOutput(true);
            this.httpURLConnection.setConnectTimeout(AsyncHttpRequest.DEFAULT_TIMEOUT);
            this.httpURLConnection.connect();
            OutputStream out2 = this.httpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out2, str3));
            StringBuilder sb2 = new StringBuilder();
            sb2.append(URLEncoder.encode("auth", str3));
            sb2.append(str2);
            sb2.append(URLEncoder.encode(Config.auth, str3));
            sb2.append(str);
            sb2.append(URLEncoder.encode("method", str3));
            sb2.append(str2);
            sb2.append(URLEncoder.encode("1", str3));
            sb2.append(str);
            sb2.append(URLEncoder.encode(SearchIntents.EXTRA_QUERY, str3));
            sb2.append(str2);
            sb2.append(URLEncoder.encode(query, str3));
            writer.write(sb2.toString());
            writer.flush();
            writer.close();
            out2.close();
            this.in = this.httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.in));
            String str4 = "";
            while (true) {
                String readLine = bufferedReader.readLine();
                String line = readLine;
                if (readLine == null) {
                    break;
                }
                stringBuffer.append(line);
                StringBuilder sb3 = new StringBuilder();
                sb3.append(" LINE_DATAS ");
                sb3.append(line);
                Log.i("ROADS_TAG", sb3.toString());
            }
        } catch (Exception e) {
            stringBuffer.append("No data");
            StringBuilder sb4 = new StringBuilder();
            sb4.append("NO data ");
            sb4.append(e.getMessage());
            Log.i("ROADS_TAG_ERROR", sb4.toString());
        }
        return stringBuffer;
    }*/
}
