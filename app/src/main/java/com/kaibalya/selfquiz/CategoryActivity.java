package com.kaibalya.selfquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaibalya.selfquiz.adapter.ViewAdapter;
import com.kaibalya.selfquiz.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference doc;
    List<Category> lst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        lst = new ArrayList<>();

        // initialize GRID view
        GridView gridView = findViewById(R.id.gridview);
        ViewAdapter viewAdapter = new ViewAdapter(lst);
        gridView.setAdapter(viewAdapter);

        /// retrive data from firestore
        doc = db.collection("category");
        doc.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        Log.d("TAG", documentSnapshot.getId() + " => " + documentSnapshot.getData());

                        lst.add(documentSnapshot.toObject(Category.class));
                    }
                    viewAdapter.notifyDataSetChanged();
                }
            }
        });

    }
}