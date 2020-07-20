package com.compubase.chefbukhari.ui.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.ui.activities.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_back_ar)
    ImageView imgBackAr;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.rel)
    RelativeLayout rel;
    @BindView(R.id.txt_des)
    TextView txtDes;
    @BindView(R.id.video)
    VideoView video;
    @BindView(R.id.txt_site)
    TextView txtSite;
    @BindView(R.id.txt_phone)
    TextView txtPhone;
    private HomeActivity homeActivity;
    private SharedPreferences preferences;
    private String string;

    private Unbinder unbinder;

    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        homeActivity = (HomeActivity) getActivity();
        unbinder = ButterKnife.bind(this, view);

        preferences = homeActivity.getSharedPreferences("user", Context.MODE_PRIVATE);

        string = preferences.getString("lan", "");

        if (string.equals("ar")) {
            imgBackAr.setVisibility(View.VISIBLE);

            Typeface typeface = ResourcesCompat.getFont(homeActivity, R.font.hacen_dalal_st_regular);

            txtDes.setTypeface(typeface);
            txtPhone.setTypeface(typeface);
            txtSite.setTypeface(typeface);
            txtTitle.setTypeface(typeface);

        } else {
            imgBack.setVisibility(View.VISIBLE);

            Typeface typeface = ResourcesCompat.getFont(homeActivity, R.font.century_gothic_400);

            txtDes.setTypeface(typeface);
            txtPhone.setTypeface(typeface);
            txtSite.setTypeface(typeface);
            txtTitle.setTypeface(typeface);
        }


        imgBackAr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeActivity.onBackPressed();
            }
        });


        TextView site = view.findViewById(R.id.txt_site);
        TextView phone = view.findViewById(R.id.txt_phone);
        ImageView back = view.findViewById(R.id.img_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeActivity.onBackPressed();
            }
        });
        String mobile = phone.getText().toString();
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + mobile));
                startActivity(intent);
            }
        });

        site.setMovementMethod(LinkMovementMethod.getInstance());
        site.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("http://www.google.com"));
                startActivity(browserIntent);
            }
        });

        VideoView videoView = (VideoView) view.findViewById(R.id.video);
        String link = "http://app.chefbukhari.com/images/CHEFINTRO.mp4";

        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    MediaController mediaController = new MediaController(getContext());
                    mediaController.setAnchorView(videoView);
                    Uri video = Uri.parse(link);
                    videoView.setMediaController(mediaController);
                    videoView.setVideoURI(video);
                    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            videoView.start();
                        }
                    });
//                    videoView.start();
                } catch (Exception e) {
                    // TODO: handle exception
                    Toast.makeText(getContext(), "Error connecting", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
