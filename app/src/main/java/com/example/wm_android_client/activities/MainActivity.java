package com.example.wm_android_client.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;

import android.content.Intent;
import android.graphics.Color;

import android.graphics.drawable.Drawable;

import android.os.Build;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.wm_android_client.Components.ProgressBarDialog;
import com.example.wm_android_client.R;
import com.example.wm_android_client.adapters.MeasuringPointsAdapter;
import com.example.wm_android_client.databinding.ActivityMainBinding;
import com.example.wm_android_client.listeners.MPListener;
import com.example.wm_android_client.models.MeasuringPoint;
import com.example.wm_android_client.utilities.FilterHelper;
import com.example.wm_android_client.utilities.VariousTools;
import com.example.wm_android_client.viewmodels.MeasuringPointViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements MPListener {

    //check if file exists
    //create new file

    private MeasuringPointViewModel viewModel;
    private ActivityMainBinding mainBinding;

    private List<MeasuringPoint> measuringPoints;
    private MeasuringPointsAdapter mpAdapter;

    private ProgressBarDialog dialog;

    private boolean rotate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initToolbar();
        initComponents();
    }

    private void initComponents() {
        measuringPoints = new ArrayList<>();
        dialog = new ProgressBarDialog(this);
        viewModel = new ViewModelProvider(this).get(MeasuringPointViewModel.class);

        //initial
        mainBinding.mask.setVisibility(View.GONE);
        VariousTools.initShowOut(mainBinding.download);
        VariousTools.initShowOut(mainBinding.upload);

        //FAB
        setFabIconTint(this);
        mainBinding.fab.setOnClickListener(this::toggleFabOptions);
        mainBinding.mask.setOnClickListener(v -> toggleFabOptions(mainBinding.fab));

        mainBinding.fabDownload.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Download clicked", Toast.LENGTH_SHORT).show();

            getMeasuringPointsFromWeb();
            //display via other method
        });

        mainBinding.fabUpload.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Upload clicked", Toast.LENGTH_SHORT).show();
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mainBinding.recyclerView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                if (scrollY < oldScrollY) { // up

                    mainBinding.fab.setVisibility(View.VISIBLE);
                }
                if (scrollY > oldScrollY) { // down

                    mainBinding.fab.setVisibility(View.INVISIBLE);
                }
            });
        }

        //recycler view
        mainBinding.recyclerView.setHasFixedSize(true);
        mpAdapter = new MeasuringPointsAdapter(measuringPoints, this);
        //mainBinding.recyclerView.setAdapter(mpAdapter);
        mainBinding.recyclerView.setVisibility(View.VISIBLE);


        //scroll up to load from db
        //if comes out empty it will attempt to download from web
        //TEST
        mainBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!mainBinding.recyclerView.canScrollVertically(1)) {

                    loadMeasuringPoints();
                }
            }
        });


        loadMeasuringPoints();
    }



    private void initToolbar() {

        setSupportActionBar(mainBinding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
    }

    private void toggleFabOptions(View v) {
        rotate = VariousTools.rotateFab(v, !rotate);
        if (rotate) {
            VariousTools.showIn(mainBinding.download);
            VariousTools.showIn(mainBinding.upload);
            mainBinding.mask.setVisibility(View.VISIBLE);
        } else {
            VariousTools.showOut(mainBinding.download);
            VariousTools.showOut(mainBinding.upload);
            mainBinding.mask.setVisibility(View.GONE);
        }
    }

    private void setFabIconTint(Context context) {

        Drawable myFabSrc = ResourcesCompat.getDrawable(context.getResources(), R.drawable.vector_download_24, context.getTheme());
        Drawable willBeWhite = myFabSrc.getConstantState().newDrawable();

        FilterHelper.setColorFilter(willBeWhite, Color.WHITE, FilterHelper.Mode.SRC_ATOP);

        mainBinding.fab.setImageDrawable(willBeWhite);
    }

    private void getMeasuringPointsFromWeb() {
        dialog.show();

        if (VariousTools.isNetworkAvailable(getApplication())) {
            viewModel.getMeasuringPointsFromWeb().observe(this, s -> {
                switch (s) {
                    case "200":
                        //accepted
                        dialog.dismiss();

                        if(rotate){ //if rotate is true
                            toggleFabOptions(mainBinding.fab);
                        }

                        mpAdapter.notifyDataSetChanged();

                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                        break;
                    case "404":
                        //bad request
                        dialog.dismiss();
                        Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
                        break;
                    case "500":
                        //something went wrong on server while trying
                        dialog.dismiss();
                        Toast.makeText(this, "Problem on server", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        dialog.dismiss();
                        Toast.makeText(this, "error" + " " + s, Toast.LENGTH_SHORT).show();
                }
            }); // observe when it ends??
        } else {
            dialog.dismiss();
            Toast.makeText(this, "No Internet connection try again later", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadMeasuringPoints() {

        dialog.show();

        //https://stackoverflow.com/questions/49522619/the-result-of-subscribe-is-not-used

        viewModel.getMeasuringPointsFromDb().observe(this, new Observer<List<MeasuringPoint>>() {
            @Override
            public void onChanged(List<MeasuringPoint> mps) {
                dialog.dismiss();

                if (measuringPoints.size() > 0) {
                    measuringPoints.clear();
                }

                if (mps ==null || mps.isEmpty())
                {
                    getMeasuringPointsFromWeb();
                }else
                {
                    measuringPoints.addAll(mps);
                    mpAdapter.setMeasuringPoints(mps);
                    mainBinding.recyclerView.setAdapter(mpAdapter);
                    mainBinding.recyclerView.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    @Override
    public void onMeasuringPointClicked(MeasuringPoint data) {
        Intent intent = new Intent(getApplicationContext(), WaterMeterActivity.class);
        intent.putExtra("MEASURING_POINT", data);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            Toast.makeText(this, "Home clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dialog.dismiss();
    }
}