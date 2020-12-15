package com.kaibalya.selfquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaibalya.selfquiz.model.Category;
import com.kaibalya.selfquiz.model.QuestionModel;

import java.util.ArrayList;
import java.util.List;

public class Question extends AppCompatActivity {
    Button btn, btn2,btn3, btn4, next;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Task<QuerySnapshot> doc;
    int no = 1;
    List<QuestionModel> questionModelList = new ArrayList<>();
    int getQuestion = 0;
    TextView tx ;
    String correctAnswer = "";
    String name = "";
    int score = 0;
    String btnNo = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        btn = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);
        next = findViewById(R.id.nextid);
        tx = findViewById(R.id.textView2);
        next.setEnabled(false);
        name = getIntent().getStringExtra("name");

        // retrive data from firestore
        // then assign data to the model
        retriveData(name);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.setEnabled(true);
                if(correctAnswer.equals(btn.getText().toString() )){
                    score +=1;
                   // btn.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
                    btn.setBackgroundColor(Color.RED);
                    btn.setTextColor(Color.RED);
                    btn2.setEnabled(false);
                    btn3.setEnabled(false);
                    btn4.setEnabled(false);
                }else{

                    if(correctAnswer.equals(btn2.getText().toString())){
                        btn2.setTextColor(Color.RED);
                        btn2.setEnabled(true);
                    }else if(correctAnswer.equals(btn3.getText().toString())){
                        btn3.setEnabled(true);
                        btn3.setTextColor(Color.RED);
                    }else if(correctAnswer.equals(btn4.getText().toString())){
                        btn4.setEnabled(true);
                        btn4.setTextColor(Color.RED);
                    }





                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.setEnabled(true);
                if(correctAnswer.equals(btn2.getText().toString())){
                    score +=1;
                    btn2.setTextColor(Color.BLUE);
                    btn2.setBackgroundColor(Color.BLUE);
                    btn.setEnabled(false);
                    btn2.setEnabled(true);
                    btn3.setEnabled(false);
                    btn4.setEnabled(false);
                }else{
                    btn.setEnabled(false);
                    btn2.setEnabled(false);
                    btn3.setEnabled(false);
                    btn4.setEnabled(false);
                    if(correctAnswer.equals(btn.getText().toString())){
                        btn.setTextColor(Color.RED);
                        btn.setEnabled(true);
                    }else if(correctAnswer.equals(btn3.getText().toString())){
                        btn3.setTextColor(Color.RED);
                        btn3.setEnabled(true);
                    }else if(correctAnswer.equals(btn4.getText().toString())){
                        btn4.setTextColor(Color.RED);
                        btn4.setEnabled(true);
                    }


                }
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.setEnabled(true);
                if(correctAnswer.equals(btn3.getText().toString())){
                    score +=1;
                    btn3.setBackgroundColor(Color.BLUE);
                    btn3.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
                    btn.setEnabled(false);
                    btn2.setEnabled(false);

                    btn4.setEnabled(false);
                }else{
                    btn.setEnabled(false);
                    btn2.setEnabled(false);
                    btn3.setEnabled(false);
                    btn4.setEnabled(false);
                    if(correctAnswer.equals(btn.getText().toString())){
                        btn.setTextColor(Color.RED);
                        btn.setEnabled(true);
                    }else if(correctAnswer.equals(btn2.getText().toString())){
                        btn2.setTextColor(Color.RED);
                        btn2.setEnabled(true);
                    }else if(correctAnswer.equals(btn4.getText().toString())){
                        btn4.setTextColor(Color.RED);
                        btn4.setEnabled(true);
                    }

                }
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.setEnabled(true);
                if(correctAnswer.equals(btn4.getText().toString())){
                    score +=1;
                    btn4.setBackgroundColor(Color.BLUE);
                    btn.setEnabled(false);
                    btn2.setEnabled(false);
                    btn3.setEnabled(false);

                }else{
                    btn.setEnabled(false);
                    btn2.setEnabled(false);
                    btn3.setEnabled(false);
                    btn4.setEnabled(false);
                    if(correctAnswer.equals(btn2.getText().toString())){
                        btn2.setTextColor(Color.RED);
                        btn2.setEnabled(true);
                    }else if(correctAnswer.equals(btn3.getText().toString())){
                        btn3.setTextColor(Color.RED);
                        btn3.setEnabled(true);
                    }else if(correctAnswer.equals(btn.getText().toString())){
                        btn.setTextColor(Color.RED);
                        btn.setEnabled(true);
                    }
                    btn.setEnabled(false);
                    btn2.setEnabled(false);
                    btn3.setEnabled(false);
                    btn4.setEnabled(false);
                }
            }
        });
    }


    public void retriveData(String name){
        try{
            doc = db.collection("Question").document(name.toLowerCase()).collection(String.valueOf(no)).get();
            doc.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                        Log.d("TAG", doc.getId() + " => " + doc.getData());
                        questionModelList.add(doc.toObject(QuestionModel.class));
                        no+=1;

                    }
                    assignQuestions();
                }
            });
        }catch (NullPointerException ex){
            Log.d("TAG", ex.toString());
        }

    }
    public void assignQuestions(){
        if(!questionModelList.isEmpty()){

                tx.setText(questionModelList.get(getQuestion).getQuestion());
                btn.setText(questionModelList.get(getQuestion).getOptionA());
                btn2.setText(questionModelList.get(getQuestion).getOptionB());
                btn3.setText(questionModelList.get(getQuestion).getOptionC());
                btn4.setText(questionModelList.get(getQuestion).getOptionD());
                correctAnswer = questionModelList.get(getQuestion).getCorrect();


        }
    }

    public void nextItem(View view) {
        getQuestion +=1;
        btn.setEnabled(true);
        btn.setTextColor(Color.BLACK);
        btn2.setEnabled(true);
        btn2.setTextColor(Color.BLACK);
        btn3.setEnabled(true);
        btn3.setTextColor(Color.BLACK);
        btn4.setEnabled(true);
        btn4.setTextColor(Color.BLACK);
        next.setEnabled(false);
        retriveData(name);


    }


}