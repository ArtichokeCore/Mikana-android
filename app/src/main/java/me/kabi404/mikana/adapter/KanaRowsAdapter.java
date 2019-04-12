package me.kabi404.mikana.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import java.util.List;

import me.kabi404.mikana.R;
import me.kabi404.mikana.model.Kana;
import me.kabi404.mikana.model.KanaManager;

public final class KanaRowsAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;

    private Context context;
    private KanaManager kanaManager;

    public KanaRowsAdapter(Context context) {
        setContext(context);
        setKanaManager(KanaManager.getInstance());
        setInflater((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    }

    @Override
    public int getCount() {
        return kanaManager.getKanaRows().size();
    }

    @Override
    public Object getItem(int i) {
        return kanaManager.getSelectedKanas().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View v = convertView;
        v = inflater.inflate(R.layout.kana_row_select, null);

        List dir = kanaManager.getKanaRows().get(i);

        CheckBox check1 = v.findViewById(R.id.check1);
        check1.setText("hola");

        return v;
    }

    public static LayoutInflater getInflater() {
        return inflater;
    }

    public static void setInflater(LayoutInflater inflater) {
        KanaRowsAdapter.inflater = inflater;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public KanaManager getKanaManager() {
        return kanaManager;
    }

    public void setKanaManager(KanaManager kanaManager) {
        this.kanaManager = kanaManager;
    }
}
