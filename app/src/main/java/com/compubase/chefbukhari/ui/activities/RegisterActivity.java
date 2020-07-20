package com.compubase.chefbukhari.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.data.API;
import com.compubase.chefbukhari.helpers.RetrofitClient;
import com.compubase.chefbukhari.helpers.SharedPrefManager;
import com.compubase.chefbukhari.helpers.ShowErrorMsgDialog;
import com.compubase.chefbukhari.helpers.SpinnerUtils;
import com.compubase.chefbukhari.models.RegisterResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.ed_username)
    EditText edUsername;
    @BindView(R.id.ed_mobile)
    EditText edMobile;
    @BindView(R.id.ed_mail)
    EditText edMail;
    @BindView(R.id.ed_pass)
    EditText edPass;
    @BindView(R.id.sp_city)
    Spinner spCity;
    @BindView(R.id.sp_neighborhood)
    Spinner spNeighborhood;
    @BindView(R.id.txt_photo)
    TextView txtPhoto;
    @BindView(R.id.btn_signUp)
    Button btnSignUp;


    Uri filePath;
    @BindView(R.id.img_back_ar)
    ImageView imgBackAr;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_namee)
    TextView txtNamee;
    @BindView(R.id.txt_numee)
    TextView txtNumee;
    @BindView(R.id.txt_address)
    TextView txtAddress;
    @BindView(R.id.txt_passss)
    TextView txtPassss;
    @BindView(R.id.txt_cityyy)
    TextView txtCityyy;
    @BindView(R.id.txt_disssss)
    TextView txtDisssss;
    @BindView(R.id.txt_image)
    TextView txtImage;
    private int GALLERY_REQUEST_CODE = 1;
    private String imageURL;
    private StorageReference storageReference;
    private FirebaseStorage storage;
    private String username, email, phone, pass;
    private SharedPreferences preferences;
    private String m_Text;
    private String area, city, phoneNumber, name, mail, img;
    private int id;
    private String type;

    private ShowErrorMsgDialog showErrorMsgDialog;
    private String string;
    private List<String> cityList = new ArrayList<>();
    private List<String> cityListEn = new ArrayList<>();
    private String item_city;
    private List<String> districList = new ArrayList<>();
    private List<String> districListEn = new ArrayList<>();
    private String item_distric;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        preferences = getSharedPreferences("user", MODE_PRIVATE);

        string = preferences.getString("lan", "");


        FirebaseApp.initializeApp(Objects.requireNonNull(this));

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        if (this.string.equals("ar")) {
            imgBackAr.setVisibility(View.VISIBLE);
            edPass.setTextDirection(View.TEXT_DIRECTION_RTL);
            edMobile.setTextDirection(View.TEXT_DIRECTION_RTL);
            edMail.setTextDirection(View.TEXT_DIRECTION_RTL);
            edUsername.setTextDirection(View.TEXT_DIRECTION_RTL);

            Typeface typeface = ResourcesCompat.getFont(this, R.font.hacen_dalal_st_regular);

            txtAddress.setTypeface(typeface);
            txtCityyy.setTypeface(typeface);
            txtDisssss.setTypeface(typeface);
            txtImage.setTypeface(typeface);
            txtNamee.setTypeface(typeface);
            txtNumee.setTypeface(typeface);
            txtPassss.setTypeface(typeface);
            txtTitle.setTypeface(typeface);
            btnSignUp.setTypeface(typeface);


        } else {
            imgBack.setVisibility(View.VISIBLE);
            edPass.setTextDirection(View.TEXT_DIRECTION_LTR);
            edMobile.setTextDirection(View.TEXT_DIRECTION_LTR);
            edMail.setTextDirection(View.TEXT_DIRECTION_LTR);
            edUsername.setTextDirection(View.TEXT_DIRECTION_LTR);

            Typeface typeface = ResourcesCompat.getFont(this, R.font.century_gothic_400);

            txtAddress.setTypeface(typeface);
            txtCityyy.setTypeface(typeface);
            txtDisssss.setTypeface(typeface);
            txtImage.setTypeface(typeface);
            txtNamee.setTypeface(typeface);
            txtNumee.setTypeface(typeface);
            txtPassss.setTypeface(typeface);
            txtTitle.setTypeface(typeface);
            btnSignUp.setTypeface(typeface);
        }

        imgBackAr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        showErrorMsgDialog = new ShowErrorMsgDialog(this);

        cityList.add("الرياض");

        cityListEn.add("Riyadh");


        districList.add("الوادي");
        districList.add("نداء");
        districList.add("جامعة الإمام");
        districList.add("الفلاح");
        districList.add("النفل");
        districList.add("التعاون");
        districList.add("الازدهار");
        districList.add("الیاسمین");
        districList.add("الغدیر");
        districList.add("النخيل");
        districList.add("المرسلات");
        districList.add("النهضة");
        districList.add("جامعة الاميره نوره");
        districList.add("قرطبة");
        districList.add("المروج");
        districList.add("الواحة");
        districList.add("النرجس");
        districList.add("منطقه ظهرة لبن");

        districListEn.add("Alwadi");
        districListEn.add("Neda’a");
        districListEn.add("Alimam University");
        districListEn.add("Alfalah");
        districListEn.add("Alnafel");
        districListEn.add("Attaawoun");
        districListEn.add("Alezdihar");
        districListEn.add("Alyasmin");
        districListEn.add("Alghadir");
        districListEn.add("Alnakheel");
        districListEn.add("Almursalat");
        districListEn.add("Alnahda");
        districListEn.add("Princess Noura University");
        districListEn.add("Qurtoba");
        districListEn.add("Almorouj");
        districListEn.add("Alwaha");
        districListEn.add("Alnarjes");
        districListEn.add("Dahrat Laban Area");


        if (string.equals("ar")) {

            SpinnerUtils.SetSpinnerAdapter(this, spCity, cityList, android.R.layout.simple_spinner_item);
            SpinnerUtils.SetSpinnerAdapter(this, spNeighborhood, districList, android.R.layout.simple_spinner_item);


        } else {

            SpinnerUtils.SetSpinnerAdapter(this, spCity, cityListEn, android.R.layout.simple_spinner_item);
            SpinnerUtils.SetSpinnerAdapter(this, spNeighborhood, districListEn, android.R.layout.simple_spinner_item);
        }


        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                item_city = cityList.get(position);

                SharedPrefManager.getInstance(RegisterActivity.this).saveArrivalTime(item_city);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spNeighborhood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                item_distric = districList.get(position);
                SharedPrefManager.getInstance(RegisterActivity.this).saveLuggageType(item_distric);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @OnClick({R.id.img_back, R.id.txt_photo, R.id.btn_signUp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.txt_photo:
                pickFromGallery();
                break;
            case R.id.btn_signUp:
                validate();
                break;
        }
    }

    private void validate() {

        username = edUsername.getText().toString();
        email = edMail.getText().toString();
        phone = edMobile.getText().toString();
        pass = edPass.getText().toString();

        if (TextUtils.isEmpty(username)) {
            edUsername.setError("اسم المستخدم مطلوب");
        } else if (TextUtils.isEmpty(email)) {
            edMail.setError("البريد الالكتروني مطلوب");
        } else if (TextUtils.isEmpty(phone)) {
            edMobile.setError("رقم الهاتف مطلوب");
        } else if (TextUtils.isEmpty(pass)) {
            edPass.setError("كلمة المرور مطلوبه");
        } else {


            Call<ResponseBody> call2 = RetrofitClient.getInstant()
                    .create(API.class)
                    .register(username, email, pass, phone, "img", item_city, item_distric);

            call2.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                    try {

                        if (response.isSuccessful()) {

                            assert response.body() != null;
                            List<RegisterResponse> registerModels =
                                    Arrays.asList(gson.fromJson(response.body().string(), RegisterResponse[].class));

                            area = registerModels.get(0).getArea();
                            city = registerModels.get(0).getCity();
                            mail = registerModels.get(0).getEmail();
                            id = registerModels.get(0).getId();
                            img = registerModels.get(0).getImg();
                            name = registerModels.get(0).getName();
                            phoneNumber = registerModels.get(0).getPhone();
                            type = registerModels.get(0).getType();


                            startActivity(new Intent(RegisterActivity.this, HomeActivity.class));

                            finish();

                            sharedLogin();


                        }

                    } catch (Exception e) {
                        showErrorMsgDialog.createDilog(RegisterActivity.this, "Email address already exisit");
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    showErrorMsgDialog.createDilog(RegisterActivity.this, "Email address already exisit");
                }

            });
        }

    }

    private void sharedLogin() {
        SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();

        preferences = getSharedPreferences("user", MODE_PRIVATE);

        editor.putBoolean("login", true);

        editor.putString("id", String.valueOf(id));
        editor.putString("name", name);
        editor.putString("email", mail);
        editor.putString("phone", phoneNumber);
        editor.putString("type", type);
        editor.putString("city", city);
        editor.putString("area", area);
        editor.putString("pass", pass);

        editor.apply();
    }

    private void pickFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            Bitmap bitmap;

            try {

                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);

//                imgAddPhotoCar.setImageBitmap(bitmap);

                uploadImage(filePath);


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    private void uploadImage(Uri customfilepath) {

        if (customfilepath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);

            final StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(customfilepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                                @Override
                                public void onSuccess(Uri uri) {

                                    progressDialog.dismiss();

                                    Toast.makeText(RegisterActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();

                                    imageURL = uri.toString();

//                                    txtPhoto.setText(imageURL);

                                    Log.i("onSuccess: ", imageURL);

                                    SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();

                                    preferences = getSharedPreferences("user", MODE_PRIVATE);


                                    editor.putString("image", imageURL);

                                    editor.apply();


                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }


}



