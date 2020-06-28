package com.compubase.chefbukhari.ui.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.adapters.OrdersDashboardAdapter;
import com.compubase.chefbukhari.data.API;
import com.compubase.chefbukhari.helpers.RetrofitClient;
import com.compubase.chefbukhari.helpers.SharedPrefManager;
import com.compubase.chefbukhari.helpers.SpinnerUtils;
import com.compubase.chefbukhari.models.OrdersResponse;
import com.compubase.chefbukhari.ui.activities.HomeActivity;
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
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {


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
    EditText edUsername;
    @BindView(R.id.ed_mobile)
    EditText edMobile;
    @BindView(R.id.ed_mail)
    EditText edMail;
    @BindView(R.id.sp_city)
    Spinner spCity;
    @BindView(R.id.sp_neighborhood)
    Spinner spNeighborhood;
    @BindView(R.id.btn_prof)
    Button btnProf;
    @BindView(R.id.lin_userData)
    LinearLayout linUserData;
    @BindView(R.id.img_back_ar)
    ImageView imgBackAr;

    private Unbinder unbinder;
    private HomeActivity homeActivity;
    private SharedPreferences preferences;
    private String id, name, email, phone, city, area, image;
    private String pass;
    private OrdersResponse ordersResponse;
    private ArrayList<OrdersResponse> ordersResponseArrayList = new ArrayList<>();
    private OrdersDashboardAdapter ordersDashboardAdapter;

    private int GALLERY_REQUEST_CODE = 1;
    Uri filePath;

    private StorageReference storageReference;
    private FirebaseStorage storage;
    private String string;
    private List<String> cityList = new ArrayList<>();
    private List<String> districList = new ArrayList<>();
    private String item_city,item_distric;

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        unbinder = ButterKnife.bind(this, view);
        homeActivity = (HomeActivity) getActivity();

        assert homeActivity != null;
        preferences = homeActivity.getSharedPreferences("user", MODE_PRIVATE);

        image = preferences.getString("image", "");

        Glide.with(homeActivity).load(image).into(imgUser);


        FirebaseApp.initializeApp(Objects.requireNonNull(getActivity()));

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        id = preferences.getString("id", "");
        name = preferences.getString("name", "");
        email = preferences.getString("email", "");
        city = preferences.getString("city", "");
        area = preferences.getString("area", "");
        phone = preferences.getString("phone", "");
        image = preferences.getString("image", "");
        pass = preferences.getString("pass", "");

        string = preferences.getString("lan", "");


        if (string.equals("ar")) {
            imgBackAr.setVisibility(View.VISIBLE);
            edUsername.setTextDirection(View.TEXT_DIRECTION_RTL);
            edMobile.setTextDirection(View.TEXT_DIRECTION_RTL);
            edMail.setTextDirection(View.TEXT_DIRECTION_RTL);
        } else {
            imgBack.setVisibility(View.VISIBLE);
            edUsername.setTextDirection(View.TEXT_DIRECTION_LTR);
            edMobile.setTextDirection(View.TEXT_DIRECTION_LTR);
            edMail.setTextDirection(View.TEXT_DIRECTION_LTR);
        }

        imgBackAr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeActivity.onBackPressed();
            }
        });

        txtOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtOrders.setTextColor(homeActivity.getResources().getColor(R.color.error));
//                txtOrders.setBackgroundResource(R.color.error);
                viewTwo.setBackgroundResource(R.color.error);
                txtUserData.setTextColor(homeActivity.getResources().getColor(R.color.gray_dark));
                viewOne.setBackgroundResource(R.color.gray_dark);
                linUserData.setVisibility(View.GONE);
                linOrders.setVisibility(View.VISIBLE);
                setupRecycler();
                fetchData();
            }
        });

        txtUserData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtUserData.setTextColor(homeActivity.getResources().getColor(R.color.error));
//                txtUserData.setBackgroundResource(R.color.error);
                txtOrders.setTextColor(homeActivity.getResources().getColor(R.color.gray_dark));
                viewOne.setBackgroundResource(R.color.error);
                viewTwo.setBackgroundResource(R.color.gray_dark);
                linOrders.setVisibility(View.GONE);
                linUserData.setVisibility(View.VISIBLE);
            }
        });



        cityList.add("الرياض");


        districList.add("وادي البن");
        districList.add("الوادي");


        SpinnerUtils.SetSpinnerAdapter(homeActivity, spCity, cityList, android.R.layout.simple_spinner_item);

        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                item_city = cityList.get(position);

                SharedPrefManager.getInstance(homeActivity).saveArrivalTime(item_city);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        SpinnerUtils.SetSpinnerAdapter(homeActivity, spNeighborhood, districList, android.R.layout.simple_spinner_item);

        spNeighborhood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                item_distric = districList.get(position);
                SharedPrefManager.getInstance(homeActivity).saveLuggageType(item_distric);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        edMail.setText(email);
        edMobile.setText(phone);
        edUsername.setText(name);


        return view;
    }

    @OnClick({R.id.img_back, R.id.img_user, R.id.btn_prof})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                homeActivity.onBackPressed();
                break;
            case R.id.img_user:
                pickFromGallery();
                break;
            case R.id.btn_prof:
                updateData();
                break;
        }
    }

    private void updateData() {
        RetrofitClient.getInstant().create(API.class).updateProfile(name, email, pass, phone, image, city, area, id)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                assert response.body() != null;
                                String string = response.body().string();
                                if (string.equals("True")) {

                                    Toast.makeText(getActivity(), "Profile updated", Toast.LENGTH_SHORT).show();

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void fetchData() {

        ordersResponseArrayList.clear();

        RetrofitClient.getInstant().create(API.class).selecte_all_orders(id).enqueue(new Callback<List<OrdersResponse>>() {
            @Override
            public void onResponse(Call<List<OrdersResponse>> call, Response<List<OrdersResponse>> response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();


                    if (response.isSuccessful()) {

                        List<OrdersResponse> body = response.body();

                        for (int i = 0; i < body.size(); i++) {


                            ordersResponse = new OrdersResponse();

                            ordersResponse.setDatee(body.get(i).getDatee());
                            ordersResponse.setId(body.get(i).getId());
                            ordersResponse.setTimee(body.get(i).getTimee());
                            ordersResponse.setDeliverCode(body.get(i).getDeliverCode());
                            ordersResponse.setIdProduct(body.get(i).getIdProduct());
                            ordersResponse.setStatus(body.get(i).getStatus());

                            ordersResponseArrayList.add(ordersResponse);

                        }
                    }

                    ordersDashboardAdapter = new OrdersDashboardAdapter(getActivity());
                    rcvDashOrers.setAdapter(ordersDashboardAdapter);
                    ordersDashboardAdapter.setData(ordersResponseArrayList);
                    ordersDashboardAdapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<List<OrdersResponse>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();

                Log.i("onFailure: ", t.getMessage());
            }
        });

    }

    private void setupRecycler() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
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

                bitmap = MediaStore.Images.Media.getBitmap(homeActivity.getContentResolver(), filePath);

                imgUser.setImageBitmap(bitmap);

                uploadImage(filePath);


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void uploadImage(Uri customfilepath) {

        if (customfilepath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(homeActivity);
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

                                    Toast.makeText(homeActivity, "Image Uploaded", Toast.LENGTH_SHORT).show();

                                    image = uri.toString();

//                                    txtPhoto.setText(imageURL);

                                    Log.i("onSuccess: ", image);

                                    SharedPreferences.Editor editor = homeActivity.getSharedPreferences("user", MODE_PRIVATE).edit();

                                    preferences = homeActivity.getSharedPreferences("user", MODE_PRIVATE);


                                    editor.putString("image", image);

                                    editor.apply();

                                    Glide.with(homeActivity).load(image).into(imgUser);


                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Log.i( "onFailure: ",e.getMessage());
                            Toast.makeText(homeActivity, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();

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
