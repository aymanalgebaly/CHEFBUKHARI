package com.compubase.chefbukhari.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.adapters.AgentDashboardAdapter;
import com.compubase.chefbukhari.data.API;
import com.compubase.chefbukhari.helpers.RetrofitClient;
import com.compubase.chefbukhari.models.OrdersAgentResponse;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
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

public class AgentDashboardActivity extends AppCompatActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.rel)
    RelativeLayout rel;
    @BindView(R.id.img_user)
    ImageView imgUser;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_city)
    TextView txtCity;
    @BindView(R.id.txt_userData)
    TextView txtUserData;
    @BindView(R.id.view_one)
    View viewOne;
    @BindView(R.id.txt_orders)
    TextView txtOrders;
    @BindView(R.id.view_two)
    View viewTwo;
    @BindView(R.id.rcv_dash_orers)
    RecyclerView rcvDashOrers;
    @BindView(R.id.lin_orders)
    LinearLayout linOrders;
    @BindView(R.id.ed_username)
    TextView edUsername;
    @BindView(R.id.ed_mail)
    TextView edMail;
    @BindView(R.id.ed_mobile)
    TextView edMobile;
    @BindView(R.id.lin_userData)
    LinearLayout linUserData;
    @BindView(R.id.img_back_ar)
    ImageView imgBackAr;
    private SharedPreferences preferences;
    private String string, id, name, email, city, area, phone, image, pass;
    private OrdersAgentResponse ordersResponse;
    private ArrayList<OrdersAgentResponse> ordersResponseArrayList = new ArrayList<>();
    private AgentDashboardAdapter ordersDashboardAdapter;

    private int GALLERY_REQUEST_CODE = 1;
    Uri filePath;

    private StorageReference storageReference;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_dashboard);
        ButterKnife.bind(this);

        FirebaseApp.initializeApp(this);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        preferences = getSharedPreferences("user", MODE_PRIVATE);

        string = preferences.getString("lan", "");

        id = preferences.getString("id", "");
        name = preferences.getString("name", "");
        email = preferences.getString("email", "");
        city = preferences.getString("city", "");
        area = preferences.getString("area", "");
        phone = preferences.getString("phone", "");
        image = preferences.getString("image", "");
        pass = preferences.getString("pass", "");


        if (string.equals("ar")) {
            imgBackAr.setVisibility(View.VISIBLE);
        } else {
            imgBack.setVisibility(View.VISIBLE);
        }

        imgBackAr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();

                preferences = getSharedPreferences("user", MODE_PRIVATE);

                editor.putBoolean("login", false);

                editor.apply();
                startActivity(new Intent(AgentDashboardActivity.this, MainActivity.class));
                finish();            }
        });

        txtOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtOrders.setBackgroundResource(R.color.error);
                viewTwo.setBackgroundResource(R.color.error);
                txtUserData.setBackgroundResource(R.color.gray_dark);
                viewTwo.setBackgroundResource(R.color.gray_dark);
                linUserData.setVisibility(View.GONE);
                linOrders.setVisibility(View.VISIBLE);
                setupRecycler();
                fetchData();
            }
        });

        txtUserData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtUserData.setBackgroundResource(R.color.error);
                txtOrders.setBackgroundResource(R.color.gray_dark);
                viewOne.setBackgroundResource(R.color.error);
                viewTwo.setBackgroundResource(R.color.gray_dark);
                linOrders.setVisibility(View.GONE);
                linUserData.setVisibility(View.VISIBLE);
            }
        });

        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickFromGallery();
            }
        });

        edMail.setText(email);
        edMobile.setText(phone);
        edUsername.setText(name);

    }


    private void fetchData() {

        ordersResponseArrayList.clear();

        RetrofitClient.getInstant().create(API.class).selecte_all_category().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                try {
                    assert response.body() != null;
                    List<OrdersAgentResponse> categoriesModels =
                            Arrays.asList(gson.fromJson(response.body().string(), OrdersAgentResponse[].class));

                    if (response.isSuccessful()) {

                        for (int i = 0; i < categoriesModels.size(); i++) {


                            ordersResponse = new OrdersAgentResponse();

                            ordersResponse.setDatee(categoriesModels.get(i).getDatee());
                            ordersResponse.setId(categoriesModels.get(i).getId());

                            ordersResponseArrayList.add(ordersResponse);

                        }
                    }

                    ordersDashboardAdapter = new AgentDashboardAdapter(AgentDashboardActivity.this);
                    rcvDashOrers.setAdapter(ordersDashboardAdapter);
                    ordersDashboardAdapter.setData(ordersResponseArrayList);
                    ordersDashboardAdapter.notifyDataSetChanged();


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AgentDashboardActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                Log.i("onFailure: ", t.getMessage());
            }
        });

    }

    private void setupRecycler() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvDashOrers.setLayoutManager(linearLayoutManager);

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

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                imgUser.setImageBitmap(bitmap);

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

                                    Toast.makeText(AgentDashboardActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();

                                    image = uri.toString();

//                                    txtPhoto.setText(imageURL);

                                    Log.i("onSuccess: ", image);

                                    SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();

                                    preferences = getSharedPreferences("user", MODE_PRIVATE);


                                    editor.putString("image", image);

                                    editor.apply();

                                    Glide.with(AgentDashboardActivity.this).load(image).into(imgUser);



                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AgentDashboardActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();

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

    @OnClick(R.id.img_back)
    public void onViewClicked() {

        SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();

        preferences = getSharedPreferences("user", MODE_PRIVATE);

        editor.putBoolean("login", false);

        editor.apply();
        startActivity(new Intent(AgentDashboardActivity.this, MainActivity.class));
        finish();
    }
}
