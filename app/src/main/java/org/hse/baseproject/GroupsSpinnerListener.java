package org.hse.baseproject;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

public class GroupsSpinnerListener implements AdapterView.OnItemSelectedListener {

    private Context context;

    public GroupsSpinnerListener(Context context){
        this.context = context;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Object item = parent.getSelectedItem();

        Toast toast = Toast.makeText(context, "selected item: "+ item, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
