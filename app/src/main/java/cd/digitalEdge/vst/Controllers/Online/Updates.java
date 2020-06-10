package cd.digitalEdge.vst.Controllers.Online;

import android.content.Context;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
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

public class Updates {
    public final String auth = Config.auth;
    HttpURLConnection httpURLConnection = null;
    InputStream in = null;
    public final String method = "2";
    OutputStream out = null;

    public String UPDATE_REQUEST(String query) {
        String str = "&";
        String str2 = "=";
        String str3 = "TAG_UPDATE";
        String str4 = "UTF-8";
        String str5 = "-1";
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(Config.SERVER_PATH).openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), str4));
            StringBuilder sb = new StringBuilder();
            sb.append(URLEncoder.encode("auth", str4));
            sb.append(str2);
            sb.append(URLEncoder.encode(Config.auth, str4));
            sb.append(str);
            sb.append(URLEncoder.encode("method", str4));
            sb.append(str2);
            sb.append(URLEncoder.encode("2", str4));
            sb.append(str);
            sb.append(URLEncoder.encode("query", str4));
            sb.append(str2);
            sb.append(URLEncoder.encode(query, str4));
            writer.write(sb.toString());
            writer.flush();
            writer.close();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bw = new BufferedReader(new InputStreamReader(inputStream, str4));
            StringBuffer buffer = new StringBuffer();
            String str6 = "";
            while (true) {
                String readLine = bw.readLine();
                String line = readLine;
                if (readLine != null) {
                    buffer.append(line);
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(" LINE_DATAS ");
                    sb2.append(line);
                    Log.i(str3, sb2.toString());
                } else {
                    String r = buffer.toString();
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("reponse du serveur ");
                    sb3.append(r);
                    Log.i(str3, sb3.toString());
                    inputStream.close();
                    connection.disconnect();
                    return r;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(str3, e.getMessage());
            return "-1";
        }
    }

    public String APPLY_UPDATE(Context context, String query, final ProgressBar p) {
        final boolean[] ret = new boolean[1];
        AsyncTask<String, Void, String> a = new AsyncTask<String, Void, String>() {
            /* access modifiers changed from: protected */
            public void onPreExecute() {
                super.onPreExecute();
                ProgressBar progressBar = p;
                if (progressBar != null) {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }

            /* access modifiers changed from: protected */
            public String doInBackground(String... strings) {
                return Updates.this.UPDATE_REQUEST(strings[0].toString());
            }

            /* access modifiers changed from: protected */
            public void onPostExecute(String o) {
                ProgressBar progressBar = p;
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                    if (o.length() > 5 || o.equals("-1") || o.contains("<")) {
                        ret[0] = false;
                    } else {
                        ret[0] = true;
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append(o.toString());
                    sb.append(" | Return : ");
                    sb.append(ret[0]);
                    Log.i("TAG_UPDATE2", sb.toString());
                    Updates.this.return_methode(ret[0]);
                }
            }
        };
        a.execute(new String[]{query});
        try {
            return (String) a.get();
        } catch (Exception e) {
            e.printStackTrace();
            return "<";
        }
    }

    public boolean return_methode(boolean value) {
        return value;
    }
}
