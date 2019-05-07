package com.artichokecore.mikana.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.artichokecore.mikana.R;
import com.artichokecore.mikana.model.Kana;
import com.artichokecore.mikana.data.structures.KanaManager;

public final class KanaRowsAdapter extends BaseAdapter {

    private static final int KANA_COLUMN_NUMBER = 5;

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
        return kanaManager.getCurrentSyllabaryRows().size();
    }

    @Override
    public Object getItem(int i) {
        return kanaManager.getCurrentSyllabaryRows().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int rowIndex, View convertView, ViewGroup viewGroup) {

        convertView = inflater.inflate(R.layout.kana_row_select, null);

        CheckBox checks[] = new CheckBox[KANA_COLUMN_NUMBER];

        checks[0] = convertView.findViewById(R.id.check1);
        checks[1] = convertView.findViewById(R.id.check2);
        checks[2] = convertView.findViewById(R.id.check3);
        checks[3] = convertView.findViewById(R.id.check4);
        checks[4] = convertView.findViewById(R.id.check5);

        for(int columnIndex = 0; columnIndex < checks.length; columnIndex++) {
            if(kanaManager.exist(rowIndex, columnIndex)) {
                Kana kana = kanaManager.getKana(rowIndex, columnIndex);
                checks[columnIndex].setText(kana.getKanaChar());
                if(kanaManager.isSelected(kana))
                    checks[columnIndex].setChecked(true);
            } else {
                checks[columnIndex].setVisibility(View.INVISIBLE);
            }
        }

        return convertView;
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
