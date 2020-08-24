package cd.digitalEdge.vst.Views.Blanks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;
import com.koushikdutta.async.http.body.MultipartFormDataBody;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cd.digitalEdge.vst.Controllers.Config;
import cd.digitalEdge.vst.Controllers.Offline.SQLite.Sqlite_selects_methods;
import cd.digitalEdge.vst.Objects.Articles;
import cd.digitalEdge.vst.Objects.Categories;
import cd.digitalEdge.vst.Objects.Colors;
import cd.digitalEdge.vst.Objects.Etats;
import cd.digitalEdge.vst.Objects.Users;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Tools.FilePath;
import cd.digitalEdge.vst.Tools.Preferences;
import cd.digitalEdge.vst.Tools.Tool;
import cd.digitalEdge.vst.Views.Gerer;
import cd.digitalEdge.vst.Views.Lists.Details_Article;
import okhttp3.internal.http.HttpHeaders;

public class Add_product extends AppCompatActivity {

    Context context = this;
    ArrayList<Categories> CAT;

    ArrayList<String> pictureChoosen = new ArrayList<>();
    ArrayList<String> pictureChoosen64 = new ArrayList<>();
    ArrayList<String> Colors = new ArrayList<>();
    ArrayList<String> Colors_id = new ArrayList<>();
    ArrayList<String> Categorie_id = new ArrayList<>();
    ArrayList<Colors> DATA_CHOOSEN = new ArrayList<>();

    Spinner couleur,etat,categories;
    TextView BTN_pickpicture,BTN_addColor, BTN_save;
    EditText nom, motcle,qnt,descripion,phone,siteweb,email,prix;
    ImageView img1,img2,img3,img4,img5;
    LinearLayout sous_color;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Nouveau bien");
        INIT_COMPONENT();
    }

    private void INIT_COMPONENT(){
        categories = findViewById(R.id.categories);
        couleur = findViewById(R.id.couleur);
        etat = findViewById(R.id.etat);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        img5 = findViewById(R.id.img5);
        prix = findViewById(R.id.prix);
        email = findViewById(R.id.email);
        siteweb = findViewById(R.id.siteweb);
        phone = findViewById(R.id.phone);
        qnt = findViewById(R.id.qnt);
        nom = findViewById(R.id.nom);
        motcle = findViewById(R.id.motcle);
        descripion = findViewById(R.id.descripion);
        sous_color = findViewById(R.id.sous_color);
        BTN_save = findViewById(R.id.BTN_save);
        BTN_addColor = findViewById(R.id.BTN_addColor);
        BTN_pickpicture = findViewById(R.id.BTN_pickpicture);
        progress = findViewById(R.id.progress);

        progress.setVisibility(View.GONE);
        img1.setVisibility(View.GONE);
        img2.setVisibility(View.GONE);
        img3.setVisibility(View.GONE);
        img4.setVisibility(View.GONE);
        img5.setVisibility(View.GONE);

        setEntries();
    }


    boolean CheckZone(){
        if (TextUtils.isEmpty(nom.getText().toString())){
            nom.setError("Champ obligatoire");
            return false;
        }else if (TextUtils.isEmpty(motcle.getText().toString())){
            motcle.setError("Champ obligatoire");
            return false;
        }else if (TextUtils.isEmpty(qnt.getText().toString())){
            qnt.setError("Champ obligatoire");
            return false;
        }else if (TextUtils.isEmpty(descripion.getText().toString())){
            descripion.setError("Champ obligatoire");
            return false;
        }else if (TextUtils.isEmpty(prix.getText().toString())){
            prix.setError("Champ obligatoire");
            return false;
        }else return true;
    }

    private void setEntries(){

        CAT = Sqlite_selects_methods.getall_Categorie(context);
        if (null == CAT || CAT.isEmpty()) CAT = new ArrayList<>();
        ArrayList<String> DATA0 = new ArrayList<>();
        for (Categories c: CAT) {
            DATA0.add(c.getName());
        }
        Tool.setEntries(context,categories,DATA0);

        ArrayList<Colors> COLORS = Sqlite_selects_methods.getall_Colors(context);
        if (null == COLORS || COLORS.isEmpty()) COLORS = new ArrayList<>();
        ArrayList<String> DATA = new ArrayList<>();
        for (Colors c: COLORS) {
            DATA.add(c.getName());
        }
        Tool.setEntries(context,couleur,DATA);

        ArrayList<Etats> ETAT = Sqlite_selects_methods.getall_Etat(context);
        if (null == ETAT || ETAT.isEmpty()) {
            ETAT = new ArrayList<>();
            Toast.makeText(context, "ETAT vide ou null", Toast.LENGTH_SHORT).show();
        }
        ArrayList<String> DATA2 = new ArrayList<>();
        for (Etats etats: ETAT) {
            DATA2.add(etats.getName());
        }
        Tool.setEntries(context,etat,DATA2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BTN_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //if (CheckZone()  ==  false) return;

                    for (Colors c :DATA_CHOOSEN) {
                        Colors_id.add(c.getId());
                    }

                    for (Categories c :CAT) {
                        if (c.getName().equals(categories.getSelectedItem().toString())){
                            Categorie_id.add(c.getId());
                            break;
                        }
                    }

                    JSONObject object = new JSONObject();
                    object.put("user_id", Preferences.getCurrentUser(context).getId());
                    object.put("name",nom.getText().toString());
                    object.put("keyword",motcle.getText().toString());
                    object.put("quantity",qnt.getText().toString());
                    object.put("phone",phone.getText().toString());
                    object.put("description",descripion.getText().toString());
                    object.put("website",siteweb.getText().toString());
                    object.put("email",email.getText().toString());
                    object.put("price",prix.getText().toString());
                    object.put("img",pictureChoosen64);
                    object.put("colors",Colors_id);
                    object.put("state",etat.getSelectedItem().toString());
                    object.put("category",Categorie_id);
                    Log.e("responce_Object",object.toString());

                    progress.setVisibility(View.VISIBLE);
                    AndroidNetworking
                            .post(Config.SEND_IMG)
                            .addBodyParameter("product", object.toString())
                            .setPriority(Priority.HIGH)
                            .build()
                            .getAsString(new StringRequestListener() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("responce_app ", response.toString());
                                    progress.setVisibility(View.GONE);
                                }
                                @Override
                                public void onError(ANError anError) {
                                    progress.setVisibility(View.GONE);
                                    Log.e("responce_app",anError.getMessage());
                                }
                            });


                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("responce_app--SSSSSSXX ",e.getMessage());
                }

            }
        });
        BTN_pickpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPicture();
            }
        });
        BTN_addColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addColor_to_Panel(couleur.getSelectedItem().toString());
            }
        });
    }

    void upload(String path){
        String uploadUrl = Config.SEND_IMG;
        Log.d("responce_app",path);
        URL url = null;
        File file  = null;
        try {
            url = new URL(uploadUrl);
            URI u = new URI(path);
            file = new File(path);
            if(file.createNewFile()){
                file.createNewFile();
            }else{
                Log.e("responce_app","errrrrrrrr");
            }
            AndroidNetworking.upload(uploadUrl)
                    .addMultipartFile("image",  file)
                    .addMultipartParameter("key","value")
                    .setTag("uploadTest")
                    .setPriority(Priority.HIGH)
                    .build()
                    .setUploadProgressListener(new UploadProgressListener() {
                        @Override
                        public void onProgress(long bytesUploaded, long totalBytes) {
                            // do anything with progress
                            //Log.d("responce_app",String.valueOf(bytesUploaded));
                        }
                    })
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // do anything with response
                            Log.d("responce_app",String.valueOf(response));
                        }
                        @Override
                        public void onError(ANError error) {
                            // handle error
                            Log.e("responce_app",String.valueOf(error.getMessage()));
                            Log.e("responce_app","errorrrr");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("responce_app",e.getMessage());
        }


    }

    @Override
    public void onBackPressed() {
        Intent i  = new Intent(context, Gerer.class);
        startActivity(i);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.add_client_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.add_client_done :

                break;
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                break;
        }
        return true;
    }

    private void addColor_to_Panel(String color){
        ArrayList<Colors> DATA = Sqlite_selects_methods.getall_Colors(context);

        View convertView = LayoutInflater.from(context).inflate(R.layout.view_color_in_form, null);
        TextView counter_badge = convertView.findViewById(R.id.counter_badge);


        for (Colors data : DATA) {

            if (data.getName().equals(color)){
                counter_badge.setText(data.getName());
                counter_badge.setBackgroundColor(Color.parseColor(data.getCodage_rvb()));

                DATA_CHOOSEN.add(data);
                sous_color.addView(convertView, 0);

            }
        }

    }

    private void setimage(){
        if (pictureChoosen.get(0) != null){
            img1.setVisibility(View.VISIBLE);
            img1.setImageURI(Uri.parse(pictureChoosen.get(0)));
        }
        if (pictureChoosen.get(1) != null){
            img2.setVisibility(View.VISIBLE);
            img2.setImageURI(Uri.parse(pictureChoosen.get(1)));
        }
        if (pictureChoosen.get(2) != null){
            img3.setVisibility(View.VISIBLE);
            img3.setImageURI(Uri.parse(pictureChoosen.get(2)));
        }
        if (pictureChoosen.get(3) != null){
            img4.setVisibility(View.VISIBLE);
            img4.setImageURI(Uri.parse(pictureChoosen.get(3)));
        }
        if (pictureChoosen.get(4) != null){
            img5.setVisibility(View.VISIBLE);
            img5.setImageURI(Uri.parse(pictureChoosen.get(4)));
        }
    }

    // TODO ;  PICTURES PICKER
    private void pickPicture() {
        CropImage.startPickImageActivity((Activity) context);
    }

    /* access modifiers changed from: 0000 */
    public void cropRequest(Uri imageuri) {
        CropImage.activity(imageuri)
                .setMultiTouchEnabled(false)
                .setAspectRatio(1, 1)
                .setFixAspectRatio(true)
                .setInitialCropWindowPaddingRatio(0.0f)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .setActivityTitle("Suprême Crop")
                .start(Add_product.this);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == -1) {
            cropRequest(CropImage.getPickImageResultUri(this.context, data));
        }
        if (requestCode == 203) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == -1) {
                try {
                    String fil = FilePath.getPath(this, result.getUri());
                    FileInputStream fs = new FileInputStream(fil);
                    byte[] imgbyte = new byte[fs.available()];
                    int i = fs.read(imgbyte);
                    // TODO ; Conversion en base64
                    String encodedImage = Base64.encodeToString(imgbyte, Base64.DEFAULT);
                    //Log.e("responce_app",encodedImage);
                    pictureChoosen64.add(encodedImage);

                    pictureChoosen.add(fil);
                    setimage();


                    //UploadFileMethod(fil, this.progressbar, new File(fil).getName());

                } catch (Exception e) {

                    //Log.e("responce_app",e.getMessage());
                    //Tool.Dialog(this.context, "URI Error", e.getMessage());
                }
            }
        }
    }

    public void UploadFileMethod(String path, final ProgressBar p, final String filname) {
        AsyncTask execute = new AsyncTask() {
            /* access modifiers changed from: protected */
            public void onPreExecute() {
                p.setVisibility(View.VISIBLE);
                super.onPreExecute();
            }

            /* access modifiers changed from: protected */
            public Object doInBackground(Object[] params) {
                return Integer.valueOf(uploadFile(params[0].toString()));
            }

            /* access modifiers changed from: protected */
            public void onPostExecute(Object oO) {
                if (((Integer) oO).intValue() >= 0) {
                    Users u = new Users();
                    u.setId(Tool.getUserPreferences(context, new Users().id));
                    u.setAvatar(filname);
                    /*String query = Updates_queries.update_avatar_query(u);
                    Log.i("TAG_UPDATE_QUERY", query);
                    String o = new Updates().APPLY_UPDATE(context, query, progressbar);
                    if (o.length() > 5 || o.equals("-1") || o.contains("<")) {
                        Tool.Dialog(context, "Info", "Erreur lors de l'enregistrement.\nVeuillez vérifier les informations saisies ou soit votre connexion internet");
                    } else {
                        Tool.setUserPreferences(context, new Users().avatar, u.getAvatar());
                        settingUp_profil();
                        Toast.makeText(context, "Le photo de profil a été modifié avec succes", Toast.LENGTH_LONG).show();
                    }*/
                    Toast.makeText(context, "Effectué", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Echec du chargement du upload", Toast.LENGTH_LONG).show();
                }
                p.setVisibility(View.GONE);
                super.onPostExecute(oO);
            }
        }.execute(new Object[]{path});
    }

    public int uploadFile(String selectedFilePath) {
        int maxBufferSize;
        DataOutputStream dataOutputStream;
        byte[] buffer;
        String serverResponseMessage;
        StringBuilder sb;
        int bytesAvailable;
        String str = selectedFilePath;
        String str2 = "UPLOAD";
        String str3 = "DATA not passed";
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int maxBufferSize2 = 1048576;
        File selectedFile = new File(str);
        String[] parts = str.split("/");
        String str4 = parts[parts.length - 1];
        if (!selectedFile.isFile()) {
            runOnUiThread(new Runnable() {
                public void run() {
                }
            });
            return 0;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(selectedFile);
            URL url = new URL(Config.SEND_IMG);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("ENCTYPE", MultipartFormDataBody.CONTENT_TYPE);
            String str5 = "Content-Type";
            StringBuilder sb3 = new StringBuilder();
            File file = selectedFile;
            try {
                sb3.append("multipart/form-data;boundary=");
                sb3.append(boundary);
                connection.setRequestProperty(str5, sb3.toString());
                connection.setRequestProperty("uploaded_file", str);
                dataOutputStream = new DataOutputStream(connection.getOutputStream());
                StringBuilder sb4 = new StringBuilder();
                sb4.append(twoHyphens);
                sb4.append(boundary);
                sb4.append(lineEnd);
                dataOutputStream.writeBytes(sb4.toString());
                StringBuilder sb5 = new StringBuilder();
                sb5.append("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"");
                sb5.append(str);
                sb5.append("\"");
                sb5.append(lineEnd);
                dataOutputStream.writeBytes(sb5.toString());
                dataOutputStream.writeBytes(lineEnd);
                InputStream inn = connection.getInputStream();

                Log.e("responce_app", inn.toString());


                Log.e(str2, "DATA passed ON DATAOUTPUTSTREAM");
                int bytesAvailable2 = fileInputStream.available();
                int bufferSize = Math.min(bytesAvailable2, 1048576);
                buffer = new byte[bufferSize];
                int i = bytesAvailable2;
                int bufferSize2 = bufferSize;
                int bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                while (bytesRead > 0) {
                    String[] parts2 = parts;
                    try {
                        dataOutputStream.write(buffer, 0, bufferSize2);
                        bytesAvailable = fileInputStream.available();
                        bufferSize2 = Math.min(bytesAvailable, maxBufferSize2);
                    } catch (FileNotFoundException e) {
                        e = e;
                        int i2 = maxBufferSize2;
                        String str6 = lineEnd;
                        maxBufferSize = 0;
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "File Not Found", Toast.LENGTH_LONG).show();
                            }
                        });
                        Log.e("UPLOAD1", str3);
                        return maxBufferSize;
                    } catch (Exception e) {
                        int i3 = maxBufferSize2;
                        String str7 = lineEnd;
                        maxBufferSize = 0;
                        e.printStackTrace();
                        Log.e("UPLOAD2", str3);
                        return maxBufferSize;
                    }
                    try {
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize2);
                        int i5 = bytesAvailable;
                        parts = parts2;
                        maxBufferSize2 = maxBufferSize2;
                    } catch (FileNotFoundException e4) {
                        String str9 = lineEnd;
                        maxBufferSize = 0;
                        e4.printStackTrace();
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "File Not Found", Toast.LENGTH_LONG).show();
                            }
                        });
                        Log.e("UPLOAD1", str3);
                        return maxBufferSize;
                    } catch (MalformedURLException e5) {
                        String str10 = lineEnd;
                        maxBufferSize = 0;
                        e5.printStackTrace();
                        Log.e("UPLOAD2", str3);
                        return maxBufferSize;
                    } catch (IOException e6) {
                        String str11 = lineEnd;
                        maxBufferSize = 0;
                        e6.printStackTrace();
                        Log.e("UPLOAD3", str3);
                        return maxBufferSize;
                    }
                }
                String[] strArr = parts;
            } catch (FileNotFoundException e7) {
                String str12 = lineEnd;
                String[] strArr2 = parts;
                maxBufferSize = 0;
                e7.printStackTrace();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "File Not Found", Toast.LENGTH_LONG).show();
                    }
                });
                Log.e("UPLOAD1", str3);
                return maxBufferSize;
            } catch (MalformedURLException e8) {
                String str13 = lineEnd;
                String[] strArr3 = parts;
                maxBufferSize = 0;
                e8.printStackTrace();
                Log.e("UPLOAD2", str3);
                return maxBufferSize;
            } catch (IOException e9) {
                String str14 = lineEnd;
                String[] strArr4 = parts;
                maxBufferSize = 0;
                e9.printStackTrace();
                Log.e("UPLOAD3", str3);
                return maxBufferSize;
            }
            try {
                dataOutputStream.writeBytes(lineEnd);
                StringBuilder sb6 = new StringBuilder();
                sb6.append(twoHyphens);
                sb6.append(boundary);
                sb6.append(twoHyphens);
                sb6.append(lineEnd);
                dataOutputStream.writeBytes(sb6.toString());
                maxBufferSize = connection.getResponseCode();
            } catch (FileNotFoundException e10) {
                String str15 = lineEnd;
                maxBufferSize = 0;
                e10.printStackTrace();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "File Not Found", Toast.LENGTH_LONG).show();
                    }
                });
                Log.e("UPLOAD1", str3);
                return maxBufferSize;
            } catch (MalformedURLException e11) {
                String str16 = lineEnd;
                maxBufferSize = 0;
                e11.printStackTrace();
                Log.e("UPLOAD2", str3);
                return maxBufferSize;
            } catch (IOException e12) {
                String str17 = lineEnd;
                maxBufferSize = 0;
                e12.printStackTrace();
                Log.e("UPLOAD3", str3);
                return maxBufferSize;
            }
            try {
                serverResponseMessage = connection.getResponseMessage();
                byte[] bArr = buffer;
                sb = new StringBuilder();
                String str18 = lineEnd;
            } catch (FileNotFoundException e13) {
                String str19 = lineEnd;
                e13.printStackTrace();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "File Not Found", Toast.LENGTH_LONG).show();
                    }
                });
                Log.e("UPLOAD1", str3);
                return maxBufferSize;
            } catch (MalformedURLException e14) {
                String str20 = lineEnd;
                e14.printStackTrace();
                Log.e("UPLOAD2", str3);
                return maxBufferSize;
            } catch (IOException e15) {
                String str21 = lineEnd;
                e15.printStackTrace();
                Log.e("UPLOAD3", str3);
                return maxBufferSize;
            }
            try {
                sb.append("Server Response is: ");
                sb.append(serverResponseMessage);
                sb.append(": ");
                sb.append(maxBufferSize);
                Log.e(str2, sb.toString());
                if (maxBufferSize == 200) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                        }
                    });
                }
                fileInputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();
            } catch (FileNotFoundException e16) {
                e16.printStackTrace();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "File Not Found", Toast.LENGTH_LONG).show();
                    }
                });
                Log.e("UPLOAD1", str3);
                return maxBufferSize;
            } catch (MalformedURLException e17) {
                e17.printStackTrace();
                Log.e("UPLOAD2", str3);
                return maxBufferSize;
            } catch (IOException e18) {
                e18.printStackTrace();
                Log.e("UPLOAD3", str3);
                return maxBufferSize;
            }
        } catch (FileNotFoundException e22) {
            String str25 = lineEnd;
            File file5 = selectedFile;
            String[] strArr8 = parts;
            maxBufferSize = 0;
            e22.printStackTrace();
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(), "File Not Found", Toast.LENGTH_LONG).show();
                }
            });
            Log.e("UPLOAD1", str3);
            return maxBufferSize;
        } catch (MalformedURLException e23) {
            String str26 = lineEnd;
            File file6 = selectedFile;
            String[] strArr9 = parts;
            maxBufferSize = 0;
            e23.printStackTrace();
            Log.e("UPLOAD2", str3);
            return maxBufferSize;
        } catch (IOException e24) {
            String str27 = lineEnd;
            File file7 = selectedFile;
            String[] strArr10 = parts;
            maxBufferSize = 0;
            e24.printStackTrace();
            Log.e("UPLOAD3", str3);
            return maxBufferSize;
        }
        return maxBufferSize;
    }




    public class Color_product{

        public String id  ="id";
        public String name  ="name";

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


}
