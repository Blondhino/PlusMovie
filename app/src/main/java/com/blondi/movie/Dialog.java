package com.blondi.movie;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by Enio on 1/25/2019.
 */

public class Dialog extends AppCompatDialogFragment {

    private EditText Ettitle;
    private String title;
    private String type;
    private RadioGroup rbGroup;
    private DialogListenr listener;
    RadioButton radioButtonMovie;
    RadioButton radioButtonGame;
    RadioButton radioButtonSeries;





    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout,null);

        Ettitle= view.findViewById(R.id.etDialogSearchTitle);


        rbGroup= view.findViewById(R.id.rbGroup);
        radioButtonMovie=view.findViewById(R.id.RbMovies);
        radioButtonGame=view.findViewById(R.id.RbGames);
        radioButtonSeries=view.findViewById(R.id.RbSeries);




        builder.setView(view)
                .setTitle("Search")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        type = getType();
                        title = Ettitle.getText().toString();
                        if(title.isEmpty()){
                            Toast.makeText(getContext(), "Enter some title first", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        listener.applyData(title,type);
                    }
                });










return builder.create(); }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListenr) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString()+ "must implement DialogListener");
        }
    }

    public interface  DialogListenr{

        void applyData(String Title,String Type);

    }

    public String getType(){

        if(radioButtonMovie.isChecked()){
            type="sdsdfsd";
            Log.e("Radio","Movie");
            return "movie";
        }

        if(radioButtonGame.isChecked()){
            type="sdsdfsd";
            Log.e("Radio","Game");
            return "game";
        }

        if(radioButtonSeries.isChecked()){
            type="sdsdfsd";
            Log.e("Radio","Series");
            return "series";
        }
        return "movie";
    }


}
