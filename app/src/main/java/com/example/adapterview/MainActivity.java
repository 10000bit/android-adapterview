package com.example.adapterview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.adapterview.databinding.ActivityMainBinding;
import com.example.adapterview.databinding.ItemBinding;

import java.util.ArrayList;
import java.util.List;

class System {
    Drawable micon;
    CharSequence mlabel;
    String mpackageName;

    public System(Drawable icon, CharSequence label, String packageName) {
        micon = icon;
        mlabel = label;
        mpackageName = packageName;
    }
}

class ViewHolder extends RecyclerView.ViewHolder {
    ItemBinding mBinding;
    ViewHolder(ItemBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }
}

class MyAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<System> mSystems;

    MyAdapter(List<System> systems) {
        mSystems = systems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemBinding binding = ItemBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        System email = mSystems.get(position);
        holder.mBinding.icon.setImageDrawable(email.micon);
        holder.mBinding.label.setText(email.mlabel);
        holder.mBinding.packageName.setText(email.mpackageName);
    }

    @Override
    public int getItemCount() {
        return mSystems.size();
    }
}


public class MainActivity extends AppCompatActivity {
    ArrayList mSystems = new ArrayList<System>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for(int i=0;i<packages.size();i++) {
            ApplicationInfo appInfo = packages.get(i);
            CharSequence label = appInfo.loadLabel(pm);
            Drawable icon = appInfo.loadIcon(pm);
            String packageName = appInfo.packageName;

            mSystems.add(new System(icon, label, packageName));
        }

        binding.reCyclerview.setAdapter(new MyAdapter(mSystems));
        binding.reCyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }
}