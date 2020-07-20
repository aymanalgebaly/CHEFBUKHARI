package com.compubase.chefbukhari.ui.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.compubase.chefbukhari.R;
import com.compubase.chefbukhari.helpers.RangeTimePickerDialog;
import com.compubase.chefbukhari.helpers.SharedPrefManager;
import com.compubase.chefbukhari.helpers.SpinnerUtils;
import com.compubase.chefbukhari.ui.activities.HomeActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PickUpFragment extends Fragment {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.rel)
    RelativeLayout rel;
    @BindView(R.id.sp_branch)
    Spinner spBranch;
    @BindView(R.id.txt_time)
    TextView txtTime;
    @BindView(R.id.btn_checkOut)
    Button btnCheckOut;
    @BindView(R.id.img_back_ar)
    ImageView imgBackAr;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_pick)
    TextView txtPick;

    private Calendar myCalendar;
    private HomeActivity homeActivity;
    private Unbinder unbinder;
    private String aTime;
    private SharedPrefManager sharedPrefManager;
    private String string;
    private List<String> districList = new ArrayList<>();
    private List<String> districListEn = new ArrayList<>();

    private String item_distric;


    public PickUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pick_up, container, false);
        homeActivity = (HomeActivity) getActivity();
        unbinder = ButterKnife.bind(this, view);

        SharedPreferences preferences = homeActivity.getSharedPreferences("user", Context.MODE_PRIVATE);

        string = preferences.getString("lan", "");
        myCalendar = Calendar.getInstance();


        if (string.equals("ar")) {
            imgBackAr.setVisibility(View.VISIBLE);

            Typeface typeface = ResourcesCompat.getFont(homeActivity, R.font.hacen_dalal_st_regular);

            btnCheckOut.setTypeface(typeface);
            txtTime.setTypeface(typeface);
            txtPick.setTypeface(typeface);
            txtTitle.setTypeface(typeface);

        } else {
            imgBack.setVisibility(View.VISIBLE);

            Typeface typeface = ResourcesCompat.getFont(homeActivity, R.font.century_gothic_400);

            btnCheckOut.setTypeface(typeface);
            txtTime.setTypeface(typeface);
            txtPick.setTypeface(typeface);
            txtTitle.setTypeface(typeface);
        }

        imgBackAr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeActivity.onBackPressed();
            }
        });

        sharedPrefManager = SharedPrefManager.getInstance(homeActivity);


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

            SpinnerUtils.SetSpinnerAdapter(homeActivity, spBranch, districList, android.R.layout.simple_spinner_item);


        } else {

            SpinnerUtils.SetSpinnerAdapter(homeActivity, spBranch, districListEn, android.R.layout.simple_spinner_item);
        }


        spBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                item_distric = districList.get(position);
                SharedPrefManager.getInstance(homeActivity).saveLuggageType(item_distric);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.img_back, R.id.txt_time, R.id.btn_checkOut})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                homeActivity.onBackPressed();
                break;
            case R.id.txt_time:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    getTime();
                }
                break;
            case R.id.btn_checkOut:
                homeActivity.displaySelectedFragmentWithBack(new CouponFragment());
                break;
        }
    }

    private void getTime() {


        final int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
        final int minute = myCalendar.get(Calendar.MINUTE);

        final RangeTimePickerDialog timePickerDialog =
                new RangeTimePickerDialog(homeActivity, (view, hourOfDay, minute1) -> {


                    String am_pm = "";
                    String mins = String.valueOf(minute1);

                    Calendar datetime = Calendar.getInstance();
                    datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    datetime.set(Calendar.MINUTE, minute);


                    if (datetime.get(Calendar.AM_PM) == Calendar.AM)
                        am_pm = "am";
                    else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
                        am_pm = "pm";

                    String strHrsToShow = (datetime.get(Calendar.HOUR) == 0) ? "12" : datetime.get(Calendar.HOUR) + "";

                    if (mins.length() == 1)
                        mins = "0" + mins;

                    if (strHrsToShow.length() == 1)
                        strHrsToShow = "0" + strHrsToShow;


                    aTime = new StringBuilder().append(strHrsToShow).append(':')
                            .append(mins).append(" ").append(am_pm).toString();

                    Log.i("getTime: ", strHrsToShow);

                    txtTime.setText(aTime);

                    Log.i("getTime: ", aTime);

                    sharedPrefManager.saveTime(aTime);


                }, hour, minute, false);
        timePickerDialog.show();
    }
}
