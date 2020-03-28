package com.example.kristen.studyflashcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        findViewById(R.id.cancel_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });

        findViewById(R.id.save_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String question = ((EditText) findViewById(R.id.editQuestion)).getText().toString();
                String answer = ((EditText) findViewById(R.id.editAnswer)).getText().toString();
                String wrong1 = ((EditText) findViewById(R.id.wrongOption1)).getText().toString();
                String wrong2 = ((EditText) findViewById(R.id.wrongOption2)).getText().toString();
                if ((question.isEmpty()) || (answer.isEmpty()) || (wrong1.isEmpty()) || (wrong2.isEmpty())) {
                    Toast.makeText(getApplicationContext(), "Must enter a question and answers!", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent data = new Intent();
                    data.putExtra("string1", question);
                    data.putExtra("string2", answer);
                    data.putExtra("string3", wrong1);
                    data.putExtra("string4", wrong2);
                    setResult(RESULT_OK, data);
                    finish();
                }
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });

        String s1 = getIntent().getStringExtra("stringKey1");
        String s2 = getIntent().getStringExtra("stringKey2");
        String s3 = getIntent().getStringExtra("stringKey3");
        String s4 = getIntent().getStringExtra("stringKey4");
        ((EditText) findViewById(R.id.editQuestion)).setText(s1);
        ((EditText) findViewById(R.id.editAnswer)).setText(s2);
        ((EditText) findViewById(R.id.wrongOption1)).setText(s3);
        ((EditText) findViewById(R.id.wrongOption2)).setText(s4);
    }
}
