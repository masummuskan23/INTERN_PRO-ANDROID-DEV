package com.example.todo;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todo.Model.ToDoModel;
import com.example.todo.Utils.DatabaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddnewTask extends BottomSheetDialogFragment {
    public static final String TAG="AddNewTask";
    private EditText mEditText;
    private Button msaveButton;
    private DatabaseHelper myDb;
    public static AddnewTask newInstance(){
        return new AddnewTask();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.add_newtask,container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditText=view.findViewById(R.id.edittext);
        msaveButton =view.findViewById(R.id.button_save);
        myDb=new DatabaseHelper(getActivity());
        boolean isUpdate=false;
        Bundle bundle=getArguments();
        if(bundle!=null){
            isUpdate=true;
            String task=bundle.getString("task");
            mEditText.setText(task);
            if(task.length()>0){
                msaveButton.setEnabled(false);
            }
        }mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    msaveButton.setEnabled(false);
                    msaveButton.setBackgroundColor(Color.GRAY);
                }else{
                    msaveButton.setEnabled(true);
                    msaveButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        final boolean finalIsUpdate = isUpdate;
        msaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=mEditText.getText().toString();
                if(finalIsUpdate){
                    myDb.updateTask(bundle.getInt("id"),text);


                }else{
                    ToDoModel item=new ToDoModel();
                    item.setTask(text);
                    item.setStatus(0);
                    myDb.insertTask(item);
                }dismiss();

            }
        });



    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity=getActivity();
        if(activity instanceof OnDialogCloseListner){
            ((OnDialogCloseListner)activity).onDialogClose(dialog);

        }
    }
}
