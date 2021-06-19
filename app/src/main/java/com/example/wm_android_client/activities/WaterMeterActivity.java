package com.example.wm_android_client.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.wm_android_client.Components.ProgressBarDialog;
import com.example.wm_android_client.R;
import com.example.wm_android_client.adapters.MeasuringPointsAdapter;
import com.example.wm_android_client.adapters.WaterMetersAdapter;
import com.example.wm_android_client.databinding.ActivityWaterMeterBinding;
import com.example.wm_android_client.listeners.WMListener;
import com.example.wm_android_client.models.MeasuringPoint;
import com.example.wm_android_client.models.WaterMeter;
import com.example.wm_android_client.utilities.VariousTools;
import com.example.wm_android_client.viewmodels.WaterMeterViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WaterMeterActivity extends AppCompatActivity implements WMListener {

    private ActivityWaterMeterBinding binding;
    private WaterMeterViewModel viewModel;
    private WaterMetersAdapter wmAdapter;

    private MeasuringPoint measuringPoint;
    private List<WaterMeter> waterMeters;

    private ProgressBarDialog dialog;

    //toolbar has return to other side


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_water_meter);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_water_meter);

        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initToolbar();
        initComponents();
    }

    private void initComponents() {
        measuringPoint = (MeasuringPoint) getIntent().getSerializableExtra("MEASURING_POINT");

        waterMeters = new ArrayList<>();
        dialog = new ProgressBarDialog(this);
        viewModel = new ViewModelProvider(this).get(WaterMeterViewModel.class);


        //recycler view
        binding.recyclerView.setHasFixedSize(true);
        wmAdapter = new WaterMetersAdapter(waterMeters, this);
        //mainBinding.recyclerView.setAdapter(mpAdapter);
        binding.recyclerView.setVisibility(View.VISIBLE);

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!binding.recyclerView.canScrollVertically(1)) {

                    loadWaterMeters(measuringPoint.id);
                }
            }
        });

        loadWaterMeters(measuringPoint.id);
    }

    private void loadWaterMeters(int id) {
        dialog.show();

        viewModel.getWaterMetersForMeasuringPointID(id).observe(this, new Observer<List<WaterMeter>>() {
            @Override
            public void onChanged(List<WaterMeter> wm) {
                dialog.dismiss();

                if (waterMeters.size() > 0) {
                    waterMeters.clear();
                }

                if (wm ==null || wm.isEmpty())
                {
                    Toast.makeText(WaterMeterActivity.this, "No Water Meters in DB", Toast.LENGTH_SHORT).show();
                }else
                {
                    waterMeters.addAll(wm);
                    wmAdapter.setWaterMeters(wm);
                    binding.recyclerView.setAdapter(wmAdapter);
                    binding.recyclerView.setVisibility(View.VISIBLE);
                }

            }
        });
    }


    private void initToolbar() {

        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        /*
        //toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        * */
    }

    /*
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/


    @Override
    public void onWaterMeterClicked(WaterMeter waterMeter) {
       // Intent intent = new Intent(getApplicationContext(), WaterMeterActivity.class);
        //intent.putExtra("WATER_METER", waterMeter);
        //startActivity(intent);
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
    }
}