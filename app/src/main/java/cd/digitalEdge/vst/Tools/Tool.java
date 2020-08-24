package cd.digitalEdge.vst.Tools;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.os.Environment;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.format.Formatter;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog.Builder;
import androidx.core.app.NotificationCompat;

import com.google.android.material.snackbar.Snackbar;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.body.StringBody;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.AnimateGifMode;
import com.koushikdutta.ion.builder.Builders.Any.B;
import com.koushikdutta.ion.builder.Builders.IV.F;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import cd.digitalEdge.vst.Controllers.Config_preferences;
import cd.digitalEdge.vst.MainActivity;
import cd.digitalEdge.vst.Objects.Users;
import cd.digitalEdge.vst.R;
import pl.droidsonroids.gif.GifDrawable;

public class Tool {
    public static boolean Snack_Connexion(Context context, View v) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info == null || !info.isConnected()) {
            NetworkInfo networkInfo = info;
            Snackbar.make(v, "Vous n'êtes pas connecté", 5000).show();
            return false;
        }else return true;

    }
    public static String NUMBER_FORMAT(String number) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        return new DecimalFormat("#,###", symbols).format((long) Integer.parseInt(number));
    }




    public static String[] Versions() {
        return new String[]{"PETIT FOUR", "CUPCAKE", "DONUT", "ECLAIR", "FROYO", "GINGERBREAD", "HONEYCOMB", "ICE CREAM SANDWICH", "JELLY BEAN", "KITKAT", "LOLLIPOP", "MARSHMALLOW", "NOUGAT", "OREO"};
    }

    public static int[] Image_details(String uri) {
        File f = new File(uri);
        long fileSizeInKB = f.length() / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(f.getAbsolutePath(), options);
        StringBuilder sb = new StringBuilder();
        sb.append(options.outWidth);
        sb.append("x");
        sb.append(options.outHeight);
        String sb2 = sb.toString();
        return new int[]{(int) fileSizeInKB, options.outWidth, options.outHeight};
    }

    public static void viewImage(Context context, Uri uri) {
        File file = new File(String.valueOf(uri));
        Uri path = Uri.fromFile(file);
        if (!file.exists()) {
            Toast.makeText(context, "No way to find Uri", Toast.LENGTH_LONG).show();
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setDataAndType(path, "image/*");
        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
        }
    }

    public static void Load_Image2(Context context, final ImageView imageView, String url) {
        try {
            new GifDrawable(context.getResources(), (int) R.drawable.gif4);
            imageView.setImageResource(R.drawable.avatar);
            ((B) Ion.with(context).load(url)).withBitmap().asBitmap().setCallback(new FutureCallback<Bitmap>() {
                public void onCompleted(Exception e, Bitmap bitmap) {
                    if (bitmap == null) {
                        imageView.setImageResource(R.drawable.unknow);
                        return;
                    }
                    Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
                    BitmapShader shader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
                    Paint paint = new Paint();
                    paint.setShader(shader);
                    new Canvas(circleBitmap).drawCircle((float) (bitmap.getWidth() / 2), (float) (bitmap.getHeight() / 2), (float) (bitmap.getWidth() / 2), paint);
                    imageView.setImageBitmap(circleBitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void Load_Image22(Context context, final ImageView imageView, String url) {
        try {
            //new GifDrawable(context.getResources(), (int) R.drawable.avatar);
            imageView.setImageResource(R.drawable.unknow);
            Ion.with(context)
                    .load(url)
                    .withBitmap()
                    .asBitmap()
                    .setCallback(new FutureCallback<Bitmap>() {
                public void onCompleted(Exception e, Bitmap bitmap) {
                    if (bitmap == null) {
                        imageView.setImageResource(R.drawable.unknow);
                        return;
                    }
                    Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
                    BitmapShader shader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
                    Paint paint = new Paint();
                    paint.setShader(shader);
                    new Canvas(circleBitmap).drawCircle((float) (bitmap.getWidth() / 2), (float) (bitmap.getHeight() / 2), (float) (bitmap.getWidth() / 2), paint);
                    imageView.setImageBitmap(circleBitmap);
                }
            });
        } catch (Exception e) {
            Log.e("PRODUCT_DATAS--XX ",e.getMessage());
        }
    }

    public static void Load_Image(Context context, ImageView imageView, String url) {
        try {
            Ion.with(imageView)
                    .placeholder((Drawable) new GifDrawable(context.getResources(), (int) R.drawable.gif5))
                    .error((int) R.drawable.unknow)
                    .animateGif(AnimateGifMode.ANIMATE)
                    .load(url);
        } catch (IOException e) {
            Log.e("PRODUCT_DATAS--XX ",e.getMessage());
        }
    }

    public static void Dialog(Context context, String title, String mesg) {
        new Builder(context).setTitle((CharSequence) title).setMessage((CharSequence) mesg)
                .setNegativeButton((CharSequence) "D'accord", (OnClickListener) new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    public static Bitmap createCircleBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        int halfWidth = bitmap.getWidth() / 2;
        int halfHeight = bitmap.getHeight() / 2;
        canvas.drawCircle((float) halfWidth, (float) halfHeight, (float) Math.max(halfWidth, halfHeight), paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public static SharedPreferences User_Preferences(Context context) {
        if (context == null) {
            context = new MainActivity().getApplicationContext();
        }
        return context.getSharedPreferences("User_Identities", 0);
    }

    public static void userPreferences_Init(Context context) {
        Users column = new Users();
        setUserPreferences(context, Config_preferences.PAIEMENT_MODE, "NO");
        String str = "NULL";
        setUserPreferences(context, Config_preferences.PRICE, str);
        setUserPreferences(context, Config_preferences.TAUX, str);
        setUserPreferences(context, Config_preferences.DEVISE, str);
        setUserPreferences(context, Config_preferences.LAT, str);
        setUserPreferences(context, Config_preferences.LONG, str);
        String str2 = "";
        setUserPreferences(context, column.id, str2);
        setUserPreferences(context, column.name, str2);
    }

    public static void userPreferences_Set(Context context, Users u) {
        Users column = new Users();
        setUserPreferences(context, "FirstUse", "No");
        setUserPreferences(context, column.id, u.getId());
        setUserPreferences(context, column.name, u.getName());
    }

    public String GetDeviceipMobileData() {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                Enumeration<InetAddress> enumIpAddr = ((NetworkInterface) en.nextElement()).getInetAddresses();
                while (true) {
                    if (enumIpAddr.hasMoreElements()) {
                        InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            return inetAddress.getHostAddress().toString();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("Current IP", ex.toString());
        }
        return null;
    }

    public static String GetDeviceipWiFiData(Context context) {
        return Formatter.formatIpAddress(((WifiManager) context.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo().getIpAddress());
    }

    public static void setUserPreferences(Context context, String key, String values) {
        try {
            Editor editor = User_Preferences(context).edit();
            editor.putString(key, values);
            editor.apply();
        } catch (Exception e) {
            Log.e("TAG_PREFERENCES", e.getMessage());
        }
    }

    public static String getUserPreferences(Context context, String key) {
        return User_Preferences(context).getString(key, "");
    }

    public static void setEntries(Context context, Spinner spinner, ArrayList<String> DATA) {
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, DATA);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter1);
    }

    public static String Today() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static String Today2() {
        return new SimpleDateFormat("yyyy/MM/dd").format(new Date());
    }


    /**
     * Méthode pour afficher le Date picker pour selectionner une datePublication
     * @param context
     * @param start_date
     * @return
     */
    public static String Date_Picker(final Context context, final EditText start_date){

        final Calendar c = Calendar.getInstance();
        final int mYear, mMonth, mDay;

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        final String[] Date = {""};

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String m = String.valueOf(monthOfYear + 1);
                if (m.length()<= 1){
                    m = "0"+m;
                }
                String d = String.valueOf(dayOfMonth);
                if (d.length()<= 1){
                    d = "0"+d;
                }
                String date = d+ "-" + m + "-" +year ;
                Date[0] = date;
                Log.i("TTTTTTTTTTDate", Date[0]);
                start_date.setText(date);

            }
        }, mYear, mMonth, mDay);

        datePickerDialog.show();
        Log.i("TTTTTTTTTT", Date[0]);

        return start_date.getText().toString();
    }

    public static final String md5(String s) {
        String str = "MD5";
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();
            StringBuilder hexString = new StringBuilder();
            /*for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(aMessageDigest & UnsignedBytes.MAX_VALUE);
                while (h.length() < 2) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("0");
                    sb.append(h);
                    h = sb.toString();
                }
                hexString.append(h);
            }*/
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "no";
        }
    }

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }
    public static final String getSha1Hex(String clearString) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(clearString.getBytes("UTF-8"));
            byte[] bytes = messageDigest.digest();
            StringBuilder buffer = new StringBuilder();
            for (byte b : bytes) {
                //buffer.append(Integer.toString((b & UnsignedBytes.MAX_VALUE) + Ascii.NUL, 16).substring(1));
            }
            Log.e("FFFFFFFFFFFFXXX", buffer.toString());
            return buffer.toString();
        } catch (Exception ignored) {
            ignored.printStackTrace();
            Log.e("FFFFFFFFFFFFERRR", ignored.getMessage());
            return ignored.toString();
        }
    }

    public static String Time_Picker(Context context, final EditText start_date) {
        Calendar c = Calendar.getInstance();
        final String[] Date = {""};
        Context context2 = context;
        TimePickerDialog timePickerDialog = new TimePickerDialog(context2, new OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String m = String.valueOf(minute);
                String str = "0";
                if (m.length() <= 1) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(m);
                    m = sb.toString();
                }
                String d = String.valueOf(hourOfDay);
                if (d.length() <= 1) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append(d);
                    d = sb2.toString();
                }
                StringBuilder sb3 = new StringBuilder();
                sb3.append(d);
                sb3.append(":");
                sb3.append(m);
                start_date.setText(sb3.toString());
                Log.i("TTTTTTTTTTime", Date[0]);
            }
        }, c.get(Calendar.DATE) + 1, c.get(Calendar.DATE) + 1, true);
        timePickerDialog.show();
        return start_date.getText().toString();
    }

    public static boolean Expired(String end) throws ParseException {
        String str = "yyyy-MM-dd";
        SimpleDateFormat sdf1 = new SimpleDateFormat(str);
        new SimpleDateFormat(str);
        Date d1 = new Date(sdf1.parse(end).getTime());
        Date d2 = new Date(sdf1.parse(sdf1.format(new Date())).getTime());
        Log.i("SSSSSS1", sdf1.format(d1));
        Log.i("SSSSSS2", sdf1.format(d2));
        return d1.before(d2);
    }

    public static long[] Time_stay(String end) throws ParseException {
        new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = new Date(sdf1.parse(end).getTime());
        Date d2 = new Date(sdf1.parse(sdf1.format(new Date())).getTime());
        StringBuilder sb = new StringBuilder();
        sb.append("PASSED_BEF ");
        sb.append(sdf1.format(d1));
        String str = "VERIFICATION22 ";
        Log.i(str, sb.toString());
        StringBuilder sb2 = new StringBuilder();
        String str2 = "PASSED ";
        sb2.append(str2);
        sb2.append(sdf1.format(d1));
        Log.i(str, sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str2);
        sb3.append(sdf1.format(d2));
        Log.i(str, sb3.toString());
        long intervalle = d1.getTime() - d2.getTime();
        return new long[]{TimeUnit.MILLISECONDS.toDays(intervalle), TimeUnit.MILLISECONDS.toHours(intervalle), TimeUnit.MILLISECONDS.toMinutes(intervalle) % 60};
    }

    public static String publication_Time(String start) throws ParseException {
        long intervalle;
        String dif;
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf3 = new SimpleDateFormat("dd-MM-yyyy");
        String today = sdf1.format(new Date());
        Date d1 = new Date(sdf1.parse(start).getTime());
        Date d2 = new Date(sdf1.parse(today).getTime());
        if (d2.after(d1)) {
            intervalle = d2.getTime() - d1.getTime();
        } else {
            intervalle = d1.getTime() - d2.getTime();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("START");
        sb.append(String.valueOf(start));
        String str = "PUBLISHING";
        Log.i(str, sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("TODAY");
        sb2.append(String.valueOf(today));
        Log.i(str, sb2.toString());
        long days = TimeUnit.MILLISECONDS.toDays(intervalle);
        long hours = TimeUnit.MILLISECONDS.toHours(intervalle);
        Date date = d2;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(intervalle) % 60;
        String str2 = "";
        SimpleDateFormat simpleDateFormat = sdf1;
        String str3 = today;
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
        String str4 = "Il y a ";
        if (hours >= 24) {
            long day2 = hours / 24;
            long hours2 = hours % 24;
            if (day2 == 1) {
                StringBuilder sb3 = new StringBuilder();
                long j = intervalle;
                sb3.append("Hier à ");
                sb3.append(sdf2.format(d1));
                dif = sb3.toString();
            } else {
                if (day2 > 30 && day2 < 35) {
                    dif = "Il y a 1 mois";
                } else if (day2 < 30) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(str4);
                    sb4.append(day2);
                    sb4.append(" Jours");
                    dif = sb4.toString();
                } else {
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append("Depuis le ");
                    sb5.append(sdf3.format(d1));
                    dif = sb5.toString();
                }
            }
        } else {
            if (hours >= 1) {
                StringBuilder sb6 = new StringBuilder();
                sb6.append(str4);
                sb6.append(hours);
                sb6.append(" h ");
                sb6.append(minutes);
                sb6.append(" min");
                dif = sb6.toString();
            } else if (minutes < 3) {
                dif = "A l'instant";
            } else {
                StringBuilder sb7 = new StringBuilder();
                sb7.append(str4);
                sb7.append(minutes);
                sb7.append(" min ");
                dif = sb7.toString();
            }
        }
        Log.i(str, String.valueOf(dif));
        return dif;
    }

    public static int get_Age(String start) throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
        new Date(sdf1.parse(start).getTime());
        return Integer.parseInt(sdf1.format(new Date(new Date().getTime()))) - Integer.parseInt(start.substring(0, 4));
    }

    public static long[] online_Time(String start) throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long intervalle = new Date(sdf1.parse(sdf1.format(new Date())).getTime()).getTime() - new Date(sdf1.parse(start).getTime()).getTime();
        Log.i("TRAINNING_TODAY", String.valueOf(intervalle));
        long days = TimeUnit.MILLISECONDS.toDays(intervalle);
        return new long[]{TimeUnit.MILLISECONDS.toHours(intervalle), TimeUnit.MILLISECONDS.toMinutes(intervalle) % 60};
    }

    public static String add_days(String start, int amount) throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = new Date(sdf1.parse(start).getTime());
        GregorianCalendar gc = new GregorianCalendar();
        Calendar c = Calendar.getInstance();
        gc.setTime(d1);
        c.setTime(sdf1.parse(start));
        gc.add(Calendar.DATE, amount);
        c.add(Calendar.DATE, amount);
        String v = sdf1.format(c.getTime());
        Date d12 = gc.getTime();
        return v;
    }

    public static String formatingDate(String origin_date) {
        String dif;
        String str = origin_date;
        String str2 = "";
        try {
            long[] date = online_Time(origin_date);
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
            Date d1 = new Date(sdf1.parse(str).getTime());
            String str3 = str2;
            long hours = date[0];
            long minutes = date[1];
            String str4 = "Il y a ";
            if (hours >= 24) {
                long day2 = hours / 24;
                long hours2 = hours % 24;
                if (day2 >= 30 && day2 < 45) {
                    dif = "Il y'a 1 mois";
                } else if (day2 < 30) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str4);
                    sb.append(day2);
                    sb.append(" Jours");
                    dif = sb.toString();
                } else if (day2 == 1) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Hier à ");
                    sb2.append(sdf2.format(d1));
                    dif = sb2.toString();
                } else {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Depuis ");
                    sb3.append(str);
                    dif = sb3.toString();
                }
            } else if (hours >= 1) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(str4);
                sb4.append(hours);
                sb4.append("h ");
                sb4.append(minutes);
                sb4.append("min");
                dif = sb4.toString();
            } else if (minutes < 3) {
                dif = "A l'instant";
            } else {
                StringBuilder sb5 = new StringBuilder();
                sb5.append(str4);
                sb5.append(minutes);
                sb5.append("min ");
                dif = sb5.toString();
            }
            return dif;
        } catch (ParseException e) {
            e.printStackTrace();
            Log.i("ERRRRRRRRR", e.getMessage());
            return str2;
        }
    }

    public static int KeyCodeGen() {
        return new Random().nextInt(100000);
    }

    public static void SHARE(Context context, String message) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.TEXT", message);
        intent.setType(StringBody.CONTENT_TYPE);
        context.startActivity(Intent.createChooser(intent, "Partager le lien de Clinnet via :"));
    }

    public static void CALL(Context context, String phone) {
        Intent intent = new Intent("android.intent.action.DIAL");
        StringBuilder sb = new StringBuilder();
        sb.append("tel:");
        sb.append(phone);
        intent.setData(Uri.parse(sb.toString()));
        context.startActivity(intent);
    }

    public static void LAUNCH_WHATAP(Context context, String number) {
        String str = "+00 9876543210";
        StringBuilder sb = new StringBuilder();
        sb.append("https://api.whatsapp.com/send?phone=+");
        sb.append(number);
        String url = sb.toString();
        try {
            context.getPackageManager().getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Intent i = new Intent("android.intent.action.VIEW");
            i.setData(Uri.parse(url));
            context.startActivity(i);
        } catch (NameNotFoundException e) {
            Toast.makeText(context, "Whatsapp app not installed in your phone", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public static void LAUNCH_WHATAPP(Context context, String number) {
        Intent sendIntent = new Intent();
        sendIntent.setAction("android.intent.action.SEND");
        sendIntent.putExtra("android.intent.extra.TEXT", "This is my text to send.");
        sendIntent.setType(StringBody.CONTENT_TYPE);
        sendIntent.setPackage("com.whatsapp");
        context.startActivity(Intent.createChooser(sendIntent, ""));
        context.startActivity(sendIntent);
        StringBuilder sb = new StringBuilder();
        sb.append("https://api.whatsapp.com/send?phone=");
        sb.append(number);
        String url = sb.toString();
        Intent i = new Intent("android.intent.action.VIEW");
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }

    public static void LAUNCH_WEB_SITE(Context context) {
        //context.startActivity(Intent.createChooser(new Intent("android.intent.action.VIEW", Uri.parse(ImagesContract.URL)), "Ouvrir via :"));
    }

    public static void Set_Alarm(Context context) {
        Intent intent = new Intent(context,null /*BroadCast.class*/);
        if (!(PendingIntent.getBroadcast(context, 0, intent, Intent.FILL_IN_ACTION) != null)) {
            Calendar calendar = Calendar.getInstance();
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, intent, 0);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 5000, pendingIntent);
        }
    }

    public static void Notify(Context context, Intent intent, String titre) {
        NotificationManager notifManager = null;
        String id = context.getString(R.string.app_name);
        String title = context.getString(R.string.app_name);
        if (0 == 0) {
            notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (VERSION.SDK_INT >= 26 && notifManager.getNotificationChannel(id) == null) {
            NotificationChannel mChannel = new NotificationChannel(id, title, NotificationManager.IMPORTANCE_HIGH);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notifManager.createNotificationChannel(mChannel);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, Intent.FILL_IN_ACTION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, id);
        //builder.setContentTitle(titre).setSmallIcon(R.drawable.ic_notify).setContentText(context.getString(R.string.app_name)).setDefaults(-1).setAutoCancel(true).setContentIntent(pendingIntent).setTicker(titre).setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        notifManager.notify(0, builder.build());
    }

    public static void Camera_Picker(int n) {
        Intent camera = new Intent("android.media.action.IMAGE_CAPTURE");
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getPath());
        sb.append("/MyAppFolder/MyApp");
        sb.append(n);
        sb.append(".png");
        camera.putExtra("output", Uri.fromFile(new File(sb.toString())));
        int n2 = n + 1;
    }

    public static int Savefile(String name, String path) {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().toString());
        String str = "/Xresolu/profil/";
        sb.append(str);
        File direct = new File(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Environment.getExternalStorageDirectory().toString());
        sb2.append(str);
        sb2.append(name);
        sb2.append(".jpg");
        File file = new File(sb2.toString());
        if (!direct.exists()) {
            direct.mkdir();
            Log.e("SAVE_IMAGE1", "Director created");
        }
        if (file.exists()) {
            return 0;
        }
        try {
            file.createNewFile();
            Log.e("SAVE_IMAGE2", "file created");
            FileChannel src = new FileInputStream(path).getChannel();
            FileChannel dst = new FileOutputStream(file).getChannel();
            dst.transferFrom(src, 0, src.size());
            src.close();
            dst.close();
            return 1;
        } catch (IOException e) {
            Log.e("SAVE_IMAGE_ERR", e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    public static String Blob_encode(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.JPEG, 100, baos);
        return Base64.encodeToString(baos.toByteArray(), 0);
    }
}
