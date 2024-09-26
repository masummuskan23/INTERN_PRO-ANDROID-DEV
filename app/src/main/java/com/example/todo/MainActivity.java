package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.todo.Adapter.ToDoAdapter;
import com.example.todo.Model.ToDoModel;
import com.example.todo.Utils.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListner{
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private DatabaseHelper myDb;
    private List<ToDoModel> mList;
    private ToDoAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView=findViewById(R.id.recyclerview);
        fab=findViewById(R.id.fab);
        myDb=new DatabaseHelper(MainActivity.this);
        mList=new ArrayList<>();
        adapter=new ToDoAdapter(myDb,MainActivity.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        mList=myDb.getAllTasks();
        Collections.reverse(mList);
        adapter.setTask(mList);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddnewTask.newInstance().show(getSupportFragmentManager(),AddnewTask.TAG);

            }
        });
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new RecyclerViewTouchHelper(adapter) );
        itemTouchHelper.attachToRecyclerView(mRecyclerView);


    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        mList=myDb.getAllTasks();
        Collections.reverse(mList);
        adapter.setTask(mList);
        //adapter.notifyDataSetChanged();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);

    }
}