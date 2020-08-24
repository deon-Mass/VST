package cd.digitalEdge.vst.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.multidex.MultiDex;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import cd.digitalEdge.vst.Controllers.Config;
import cd.digitalEdge.vst.Controllers.Config_preferences;
import cd.digitalEdge.vst.MainActivity;
import cd.digitalEdge.vst.Objects.Users;
import cd.digitalEdge.vst.R;
import cd.digitalEdge.vst.Tools.Constants;
import cd.digitalEdge.vst.Tools.FilePath;
import cd.digitalEdge.vst.Tools.Preferences;
import cd.digitalEdge.vst.Tools.Tool;
import cd.digitalEdge.vst.Views.Blanks.Add_product;
import cd.digitalEdge.vst.Views.Blanks.Add_vente;
import cd.digitalEdge.vst.Views.Lists.Biens;
import cd.digitalEdge.vst.Views.Lists.Commandes;
import cd.digitalEdge.vst.Views.Lists.Favoris;
import cd.digitalEdge.vst.Views.Lists.Messages;
import cd.digitalEdge.vst.Views.Lists.Wallet;
import cd.digitalEdge.vst.Views.Signin.Login;

public class Gerer extends AppCompatActivity {

    Context context = this;
    Users currentUser;

    DrawerLayout drawer;
    ImageView imageprofil,Pickpicture,edith_identities;
    ProgressBar progressbar;
    EditText nom,email;

    TextView logout,add_product,mesbiens,BTN_commande, BTN_favoris, message, BTN_wallet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MultiDex.install(this);
        setContentView(R.layout.activity_gerer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profil");

        INIT_COMPONENT();
        getUserInfo();

    }

    private void getUserInfo(){
        currentUser = Preferences.getCurrentUser(context);
        nom.setText(currentUser.getName() + "  "+currentUser.getId());
        email.setText(currentUser.getEmail());
        String path = Config.ROOT_img.concat(currentUser.getAvatar());
        //Loading Image
        imageprofil.setImageURI(Uri.parse(currentUser.getAvatar()));

    }

    private void INIT_COMPONENT(){
        logout = findViewById(R.id.logout);
        imageprofil = findViewById(R.id.imageprofil);
        Pickpicture = findViewById(R.id.Pickpicture);
        message = findViewById(R.id.message);
        nom = findViewById(R.id.nom);
        email = findViewById(R.id.email);
        drawer = findViewById(R.id.drawer);
        BTN_commande = findViewById(R.id.BTN_commande);
        mesbiens = findViewById(R.id.mesbiens);
        BTN_wallet = findViewById(R.id.BTN_wallet);
        BTN_favoris = findViewById(R.id.BTN_favoris);
        add_product = findViewById(R.id.add_product);
        progressbar = findViewById(R.id.progressbar);

        progressbar.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BTN_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(context, Wallet.class);
                startActivity(i);
                finish();
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(context, Messages.class);
                startActivity(i);
                finish();
            }
        });
        BTN_favoris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(context, Favoris.class);
                startActivity(i);
                finish();
            }
        });
        BTN_commande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(context, Commandes.class);
                startActivity(i);
                finish();
            }
        });
        mesbiens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(context, Biens.class);
                startActivity(i);
                finish();
            }
        });
        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(context, Add_product.class);
                startActivity(i);
                finish();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    Preferences.setUserPreferences(context, Config_preferences.CURRENT_USER, "null");
                }
                onBackPressed();
            }
        });
        Pickpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPicture();
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
                .start(Gerer.this);
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
                    //UploadFileMethod(fil, this.progressbar, new File(fil).getName());
                } catch (Exception e) {
                    Tool.Dialog(this.context, "URI Error", e.getMessage());
                }
            }
        }
    }





}
