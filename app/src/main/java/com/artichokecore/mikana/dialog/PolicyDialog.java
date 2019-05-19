package com.artichokecore.mikana.dialog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import com.artichokecore.mikana.R;
import com.artichokecore.mikana.activities.MainActivity;

public class PolicyDialog extends DialogFragment {

    TextView hyperLinkPolicy;
    TextView acceptButton, declineButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.policy_info, container, false);
        hyperLinkPolicy = view.findViewById(R.id.policyLink);
        acceptButton = view.findViewById(R.id.acceptPolicyButton);
        declineButton = view.findViewById(R.id.declinePolicyButton);


        hyperLinkPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://artichokecore.github.io/projects/mikana/privacy-policy");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("policy_check", true);
                editor.commit();

                Intent intent = new Intent(getContext().getApplicationContext(), MainActivity.class);
                startActivity(intent);
                getContext().setTheme(R.style.AppTheme_NoActionBar);
                getActivity().finish();
            }
        });

        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });



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

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().finish();
    }
}
