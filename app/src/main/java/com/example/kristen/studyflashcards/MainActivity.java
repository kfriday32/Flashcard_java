package com.example.kristen.studyflashcards;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    boolean isShowingAnswers = true;

    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;

    TextView tvFlashcardQuestion = ((TextView) findViewById(R.id.flashcard_question));
    TextView tvFlashcardAnswer =  ((TextView) findViewById(R.id.flashcard_answer));
    TextView tvFlashcardOption1 =  ((TextView) findViewById(R.id.option1));
    TextView tvFlashcardOption2 =  ((TextView) findViewById(R.id.option2));
    TextView tvFlashcardOption3 =  ((TextView) findViewById(R.id.option3));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardDatabase = new FlashcardDatabase(this);
        allFlashcards = flashcardDatabase.getAllCards();

        if (allFlashcards != null && allFlashcards.size() > 0) {
            tvFlashcardQuestion.setText(allFlashcards.get(0).getQuestion());
            tvFlashcardAnswer.setText(allFlashcards.get(0).getAnswer());
            tvFlashcardOption1.setText(allFlashcards.get(0).getWrongAnswer1());
            tvFlashcardOption2.setText(allFlashcards.get(0).getAnswer());
            tvFlashcardOption3.setText(allFlashcards.get(0).getWrongAnswer2());
        }

        findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                findViewById(R.id.flashcard_question).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
                findViewById(R.id.option1).setVisibility(View.INVISIBLE);
                findViewById(R.id.option2).setVisibility(View.INVISIBLE);
                findViewById(R.id.option3).setVisibility(View.INVISIBLE);
                findViewById(R.id.toggle_choices_availability).setVisibility(View.INVISIBLE);
                findViewById(R.id.toggle_choices_invisible).setVisibility(View.INVISIBLE);
            }
        });

        findViewById(R.id.flashcard_answer).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                if (isShowingAnswers == true) {
                    findViewById(R.id.option1).setVisibility(View.VISIBLE);
                    findViewById(R.id.option2).setVisibility(View.VISIBLE);
                    findViewById(R.id.option3).setVisibility(View.VISIBLE);
                    findViewById(R.id.toggle_choices_availability).setVisibility(View.VISIBLE);
                }
                else{
                    findViewById(R.id.toggle_choices_invisible).setVisibility(View.VISIBLE);
                }
            }
        });

        findViewById(R.id.option1).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                findViewById(R.id.option1).setBackgroundColor(getResources().getColor(R.color.my_red_color, null));
                findViewById(R.id.option2).setBackgroundColor(getResources().getColor(R.color.my_green_color, null));
            }
        });

        findViewById(R.id.option3).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                findViewById(R.id.option3).setBackgroundColor(getResources().getColor(R.color.my_red_color, null));
                findViewById(R.id.option2).setBackgroundColor(getResources().getColor(R.color.my_green_color, null));
            }
        });

        findViewById(R.id.option2).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                findViewById(R.id.option2).setBackgroundColor(getResources().getColor(R.color.my_green_color, null));
            }
        });

        findViewById(R.id.toggle_choices_availability).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                findViewById(R.id.option1).setVisibility(View.INVISIBLE);
                findViewById(R.id.option2).setVisibility(View.INVISIBLE);
                findViewById(R.id.option3).setVisibility(View.INVISIBLE);
                findViewById(R.id.toggle_choices_availability).setVisibility(View.INVISIBLE);
                findViewById(R.id.toggle_choices_invisible).setVisibility(View.VISIBLE);
                isShowingAnswers = false;
            }
        });

        findViewById(R.id.toggle_choices_invisible).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                findViewById(R.id.option1).setVisibility(View.VISIBLE);
                findViewById(R.id.option2).setVisibility(View.VISIBLE);
                findViewById(R.id.option3).setVisibility(View.VISIBLE);
                findViewById(R.id.toggle_choices_availability).setVisibility(View.VISIBLE);
                findViewById(R.id.toggle_choices_invisible).setVisibility(View.INVISIBLE);
                isShowingAnswers = true;
            }
        });

        findViewById(R.id.addition_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });

        findViewById(R.id.edit_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = tvFlashcardQuestion.getText().toString();
                String answer = tvFlashcardAnswer.getText().toString();
                String option1 = tvFlashcardOption1.getText().toString();
                String option3 = tvFlashcardOption3.getText().toString();
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                intent.putExtra("stringKey1", question);
                intent.putExtra("stringKey2", answer);
                intent.putExtra("stringKey3", option1);
                intent.putExtra("stringKey4", option3);
                MainActivity.this.startActivityForResult(intent, 200);
            }
        });

        findViewById(R.id.next_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                currentCardDisplayedIndex++;

                if (currentCardDisplayedIndex > allFlashcards.size() - 1) {
                    currentCardDisplayedIndex = 0;
                }

                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                if (isShowingAnswers) {
                    findViewById(R.id.option1).setVisibility(View.VISIBLE);
                    findViewById(R.id.option2).setVisibility(View.VISIBLE);
                    findViewById(R.id.option3).setVisibility(View.VISIBLE);
                }

                tvFlashcardQuestion.setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                tvFlashcardAnswer.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                tvFlashcardOption1.setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
                tvFlashcardOption2.setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                tvFlashcardOption3.setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (((requestCode == 100) || (requestCode == 200)) && (data != null)) {
            String string1 = data.getExtras().getString("string1");
            String string2 = data.getExtras().getString("string2");
            String string3 = data.getExtras().getString("string3");
            String string4 = data.getExtras().getString("string4");
            tvFlashcardQuestion.setText(string1);
            tvFlashcardAnswer.setText(string2);
            tvFlashcardOption1.setText(string3);
            tvFlashcardOption2.setText(string2);
            tvFlashcardOption3.setText(string4);
            findViewById(R.id.option1).setBackgroundColor(getResources().getColor(R.color.choices_color, null));
            findViewById(R.id.option2).setBackgroundColor(getResources().getColor(R.color.choices_color, null));
            findViewById(R.id.option3).setBackgroundColor(getResources().getColor(R.color.choices_color, null));

            flashcardDatabase.insertCard(new Flashcard(string1, string2, string3, string4));
        }
        if ((requestCode == 100) && (data != null)) {
            Snackbar.make(findViewById(R.id.flashcard_question), "Card successfully created", Snackbar.LENGTH_SHORT).show();
        }
        else if ((requestCode == 200) && (data != null)) {
            Snackbar.make(findViewById(R.id.flashcard_question), "Card successfully updated", Snackbar.LENGTH_SHORT).show();
        }
        allFlashcards = flashcardDatabase.getAllCards();
    }
}
