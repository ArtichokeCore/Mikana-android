package com.artichokecore.mikana.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.artichokecore.mikana.BuildConfig;
import com.artichokecore.mikana.R;

public class InfoDialog extends DialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_info, container, false);
        TextView idVersion = view.findViewById(R.id.version);
        idVersion.setText("Version "+ BuildConfig.VERSION_NAME);
        return view;
    }

    @Override
    public void onResume() {
        super .onResume();
        setWindows();
    }

    private void setWindows(){
        Window window = getDialog().getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        int width = dm.widthPixels;
        window.setLayout((int) (width * 0.8), (int) (height * 0.6));
        window.setGravity(Gravity.CENTER);
    }
}
