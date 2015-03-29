package com.personal.altik_0.teneleven.ui.real;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.personal.altik_0.teneleven.R;
import com.personal.altik_0.teneleven.logic.GameMode;

import java.util.ArrayList;

/**
 * Created by Altik_0 on 3/29/2015.
 */
public class OptionsDialogFragment extends DialogFragment {

    public interface OnSelectOptionListener {
        void optionSelected(GameMode mode);
    }

    private TextView descriptionText;
    private void setDescriptionText(GameMode mode) {
        descriptionText.setText(mode.getDescription());
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Game Mode");
        ViewGroup dialogLayout = (ViewGroup)inflater.inflate(R.layout.dialog_options, null);
        builder.setView(dialogLayout);

        final Spinner menuSpinner = (Spinner)dialogLayout.findViewById(R.id.menuSpinner);
        descriptionText = (TextView)dialogLayout.findViewById(R.id.menuDescription);
        final ArrayList<String> modeNames = new ArrayList<>();
        final ArrayList<GameMode> modes = new ArrayList<>();
        for (GameMode mode : GameMode.values()) {
            modeNames.add(mode.getName());
            modes.add(mode);
        }

        menuSpinner.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.spinner_menu_item, modeNames));
        menuSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setDescriptionText(modes.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int selectedIndex = menuSpinner.getSelectedItemPosition();
                ((OnSelectOptionListener) getActivity()).optionSelected(modes.get(selectedIndex));
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OptionsDialogFragment.this.getDialog().cancel();
            }
        });

        return builder.create();
    }
}
