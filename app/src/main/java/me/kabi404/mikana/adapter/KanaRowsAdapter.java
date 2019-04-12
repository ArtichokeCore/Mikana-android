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
import me.kabi404.mikana.model.Syllabary;

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
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        List<Kana> kanaRow = kanaManager.getCurrentSyllabaryRows().get(i);

        if(kanaRow.get(0).getSyllabary() == kanaManager.getCurrentSyllabary()) {
            convertView = inflater.inflate(R.layout.kana_row_select, null);

            CheckBox checks[] = new CheckBox[KANA_COLUMN_NUMBER];

            checks[0] = convertView.findViewById(R.id.check1);
            checks[1] = convertView.findViewById(R.id.check2);
            checks[2] = convertView.findViewById(R.id.check3);
            checks[3] = convertView.findViewById(R.id.check4);
            checks[4] = convertView.findViewById(R.id.check5);

            for(int column = 0; column < checks.length; column++) {
                if(kanaRow.size() > column) {
                    checks[column].setText(kanaRow.get(column).getKanaChar());
                    if(kanaRow.get(column).getSyllabary() == kanaManager.getCurrentSyllabary() &&
                            kanaManager.getSelectedKanas().contains(kanaRow.get(column)))
                        checks[column].setChecked(true);
                } else {
                    checks[column].setVisibility(View.INVISIBLE);
                }
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
