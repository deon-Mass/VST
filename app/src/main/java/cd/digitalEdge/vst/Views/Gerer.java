package cd.digitalEdge.vst.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

import cd.digitalEdge.vst.Controllers.Config;
import cd.digitalEdge.vst.Controllers.Config_preferences;
import cd.digitalEdge.vst.MainActivity;
import cd.digitalEdge.vst.Objects.Users;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Tools.Constants;
import cd.digitalEdge.vst.Tools.Preferences;
import cd.digitalEdge.vst.Tools.Tool;
import cd.digitalEdge.vst.Views.Blanks.Add_vente;
import cd.digitalEdge.vst.Views.Signin.Login;

public class Gerer extends AppCompatActivity {

    Context context = this;
    Users currentUser;

    DrawerLayout drawer;
    ImageView imageprofil,Pickpicture,edith_identities;
    ProgressBar progressbar;
    TextView nom,email;

    TextView logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profil");

        INIT_COMPONENT();
        getUserInfo();

    }

    private void getUserInfo(){
        currentUser = Preferences.getCurrentUser(context);
        nom.setText(currentUser.getName());
        email.setText(currentUser.getEmail());
        String path = Config.ROOT_img.concat(currentUser.getAvatar());
        //Tool.Load_Image2(context,imageprofil,path );
        new AsyncTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressbar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Bitmap doInBackground(Object[] objects) {
                Bitmap bmp = null;
                try {
                    URL url = new URL("https://lesupreme.shop/storage/users/November2019/MgMgAthaK3NIDNomVAxM.jpg");
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bmp;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                imageprofil.setImageBitmap((Bitmap) o);
                progressbar.setVisibility(View.GONE);
            }
        }.execute();



        //Tool.Load_Image(context, userProfil, path);


    }

    private void INIT_COMPONENT(){
        logout = findViewById(R.id.logout);
        imageprofil = findViewById(R.id.imageprofil);
        Pickpicture = findViewById(R.id.Pickpicture);
        progressbar = findViewById(R.id.progressbar);
        nom = findViewById(R.id.nom);
        email = findViewById(R.id.email);
        edith_identities = findViewById(R.id.edith_identities);
        drawer = findViewById(R.id.drawer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    Preferences.setUserPreferences(context, Config_preferences.CURRENT_USER, "null");
                }
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i  = new Intent(context, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.empty_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.accueil:
                if (drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START);
                else drawer.openDrawer(GravityCompat.START );
                return true;
        }

        return true;
    }


}
