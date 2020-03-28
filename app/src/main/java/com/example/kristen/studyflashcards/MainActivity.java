package com.example.kristen.studyflashcards;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.plattysoft.leonids.ParticleSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    boolean isShowingAnswers = true;

    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 1;
    ArrayList<TextView> options = new ArrayList<TextView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardDatabase = new FlashcardDatabase(this);
        allFlashcards = flashcardDatabase.getAllCards();

        TextView tvFlashcardQuestion = ((TextView) findViewById(R.id.flashcard_question));
        TextView tvFlashcardAnswer =  ((TextView) findViewById(R.id.flashcard_answer));
        TextView tvFlashcardOption1 =  ((TextView) findViewById(R.id.option1));
        TextView tvFlashcardOption2 =  ((TextView) findViewById(R.id.option2));
        TextView tvFlashcardOption3 =  ((TextView) findViewById(R.id.option3));

        options.add(tvFlashcardOption1);
        options.add(tvFlashcardOption2);
        options.add(tvFlashcardOption3);

        Collections.shuffle(options);

        if (allFlashcards != null && allFlashcards.size() > 0) {
            tvFlashcardQuestion.setText(allFlashcards.get(0).getQuestion());
            tvFlashcardAnswer.setText(allFlashcards.get(0).getAnswer());
            if(options.get(0).getText().toString().equals(tvFlashcardAnswer.getText().toString())) {
                options.get(0).setText(allFlashcards.get(0).getAnswer());
                options.get(1).setText(allFlashcards.get(0).getWrongAnswer1());
                options.get(2).setText(allFlashcards.get(0).getWrongAnswer2());
            }
            else if(options.get(1).getText().toString().equals(tvFlashcardAnswer.getText().toString())) {
                options.get(1).setText(allFlashcards.get(0).getAnswer());
                options.get(0).setText(allFlashcards.get(0).getWrongAnswer1());
                options.get(2).setText(allFlashcards.get(0).getWrongAnswer2());
            }
            else{
                options.get(2).setText(allFlashcards.get(0).getAnswer());
                options.get(0).setText(allFlashcards.get(0).getWrongAnswer1());
                options.get(1).setText(allFlashcards.get(0).getWrongAnswer2());
            }
        }
        else {
            tvFlashcardQuestion.setText("Your study set is empty. Add a new card.");
            tvFlashcardAnswer.setText(" ");
            findViewById(R.id.option1).setVisibility(View.INVISIBLE);
            findViewById(R.id.option2).setVisibility(View.INVISIBLE);
            findViewById(R.id.option3).setVisibility(View.INVISIBLE);
            findViewById(R.id.toggle_choices_availability).setVisibility(View.INVISIBLE);
            findViewById(R.id.toggle_choices_invisible).setVisibility(View.INVISIBLE);
            findViewById(R.id.edit_icon).setVisibility(View.INVISIBLE);
            findViewById(R.id.trash_icon).setVisibility(View.INVISIBLE);
            findViewById(R.id.next_icon).setVisibility(View.INVISIBLE);
        }

        findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (((TextView) findViewById(R.id.flashcard_answer)).getText().toString() != " "){
                    View answerSideView = findViewById(R.id.flashcard_answer);

                    // get the center for the clipping circle
                    int cx = answerSideView.getWidth() / 2;
                    int cy = answerSideView.getHeight() / 2;

                    // get the final radius for the clipping circle
                    float finalRadius = (float) Math.hypot(cx, cy);

                    // create the animator for this view (the start radius is zero)
                    Animator anim = ViewAnimationUtils.createCircularReveal(answerSideView, cx, cy, 0f, finalRadius);

                    // hide the question and show the answer to prepare for playing the animation!
                    findViewById(R.id.flashcard_question).setVisibility(View.INVISIBLE);
                    answerSideView.setVisibility(View.VISIBLE);
                    findViewById(R.id.option1).setVisibility(View.INVISIBLE);
                    findViewById(R.id.option2).setVisibility(View.INVISIBLE);
                    findViewById(R.id.option3).setVisibility(View.INVISIBLE);
                    findViewById(R.id.toggle_choices_availability).setVisibility(View.INVISIBLE);
                    findViewById(R.id.toggle_choices_invisible).setVisibility(View.INVISIBLE);

                    anim.setDuration(1000);
                    anim.start();
                }
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
                if (((TextView) findViewById(R.id.option1)).getText().toString().equals(((TextView) findViewById(R.id.flashcard_answer)).getText().toString())){
                    findViewById(R.id.option1).setBackgroundColor(getResources().getColor(R.color.my_green_color, null));
                    new ParticleSystem(MainActivity.this, 100, R.drawable.confetti, 3000)
                            .setSpeedRange(0.2f, 0.5f)
                            .oneShot(findViewById(R.id.option1), 100);
                }
                else{
                    findViewById(R.id.option1).setBackgroundColor(getResources().getColor(R.color.my_red_color, null));
                }
            }
        });

        findViewById(R.id.option2).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (((TextView) findViewById(R.id.option2)).getText().toString().equals(((TextView) findViewById(R.id.flashcard_answer)).getText().toString())){
                    findViewById(R.id.option2).setBackgroundColor(getResources().getColor(R.color.my_green_color, null));
                    new ParticleSystem(MainActivity.this, 100, R.drawable.confetti, 3000)
                            .setSpeedRange(0.2f, 0.5f)
                            .oneShot(findViewById(R.id.option2), 100);
                }
                else{
                    findViewById(R.id.option2).setBackgroundColor(getResources().getColor(R.color.my_red_color, null));
                }
            }
        });

        findViewById(R.id.option3).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (((TextView) findViewById(R.id.option3)).getText().toString().equals(((TextView) findViewById(R.id.flashcard_answer)).getText().toString())){
                    findViewById(R.id.option3).setBackgroundColor(getResources().getColor(R.color.my_green_color, null));
                    new ParticleSystem(MainActivity.this, 100, R.drawable.confetti, 3000)
                            .setSpeedRange(0.2f, 0.5f)
                            .oneShot(findViewById(R.id.option3), 100);
                }
                else{
                    findViewById(R.id.option3).setBackgroundColor(getResources().getColor(R.color.my_red_color, null));
                }
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
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        findViewById(R.id.edit_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.shuffle(options);
                String question = ((TextView) findViewById(R.id.flashcard_question)).getText().toString();
                String answer = ((TextView) findViewById(R.id.flashcard_answer)).getText().toString();
                String option1 = options.get(0).getText().toString();
                String option2 = options.get(1).getText().toString();
                String option3 = options.get(2).getText().toString();
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                intent.putExtra("stringKey1", question);
                intent.putExtra("stringKey2", answer);
                if(option1.equals(answer)){
                    intent.putExtra("stringKey2", option1);
                    intent.putExtra("stringKey3", option2);
                    intent.putExtra("stringKey4", option3);
                }
                else if(option2.equals(answer)){
                    intent.putExtra("stringKey2", option2);
                    intent.putExtra("stringKey3", option1);
                    intent.putExtra("stringKey4", option3);
                }
                else{
                    intent.putExtra("stringKey2", option3);
                    intent.putExtra("stringKey3", option1);
                    intent.putExtra("stringKey4", option2);
                }
                MainActivity.this.startActivityForResult(intent, 200);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        findViewById(R.id.next_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Collections.shuffle(options);

                final Animation leftOutAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.left_out);
                final Animation rightInAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.right_in);

                if (findViewById(R.id.flashcard_answer).getVisibility() == (View.INVISIBLE)) {
                    findViewById(R.id.flashcard_question).startAnimation(leftOutAnim);
                    if (isShowingAnswers) {
                        findViewById(R.id.option1).startAnimation(leftOutAnim);
                        findViewById(R.id.option2).startAnimation(leftOutAnim);
                        findViewById(R.id.option3).startAnimation(leftOutAnim);
                    }
                }
                else{
                    findViewById(R.id.flashcard_answer).startAnimation(leftOutAnim);
                }

                currentCardDisplayedIndex++;

                if (currentCardDisplayedIndex > allFlashcards.size() - 1) {
                    currentCardDisplayedIndex = 0;
                }

                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                        ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                        if(options.get(0).getText().toString().equals(((TextView)findViewById(R.id.flashcard_answer)).getText().toString())){
                            options.get(0).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
                            options.get(1).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                            options.get(2).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());
                        }
                        else if(options.get(1).getText().toString().equals(((TextView)findViewById(R.id.flashcard_answer)).getText().toString())){
                            options.get(0).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
                            options.get(1).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                            options.get(2).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());
                        }
                        else{
                            options.get(0).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
                            options.get(2).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                            options.get(1).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());
                        }

                        findViewById(R.id.option1).setBackgroundColor(getResources().getColor(R.color.choices_color, null));
                        findViewById(R.id.option2).setBackgroundColor(getResources().getColor(R.color.choices_color, null));
                        findViewById(R.id.option3).setBackgroundColor(getResources().getColor(R.color.choices_color, null));

                        findViewById(R.id.flashcard_question).startAnimation(rightInAnim);
                        if (isShowingAnswers) {
                            findViewById(R.id.option1).startAnimation(rightInAnim);
                            findViewById(R.id.option2).startAnimation(rightInAnim);
                            findViewById(R.id.option3).startAnimation(rightInAnim);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                if (isShowingAnswers) {
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

        findViewById(R.id.previous_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Collections.shuffle(options);

                final Animation leftInAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.left_in);
                final Animation rightOutAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.right_out);

                findViewById(R.id.flashcard_question).startAnimation(rightOutAnim);
                if (isShowingAnswers) {
                    findViewById(R.id.option1).startAnimation(rightOutAnim);
                    findViewById(R.id.option2).startAnimation(rightOutAnim);
                    findViewById(R.id.option3).startAnimation(rightOutAnim);
                }
                currentCardDisplayedIndex--;

                if (currentCardDisplayedIndex > allFlashcards.size() - 1) {
                    currentCardDisplayedIndex = 0;
                }

                rightOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                        ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                        if(options.get(0).getText().toString().equals(((TextView)findViewById(R.id.flashcard_answer)).getText().toString())){
                            options.get(0).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
                            options.get(1).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                            options.get(2).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());
                        }
                        else if(options.get(1).getText().toString().equals(((TextView)findViewById(R.id.flashcard_answer)).getText().toString())){
                            options.get(0).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
                            options.get(1).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                            options.get(2).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());
                        }
                        else{
                            options.get(0).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
                            options.get(2).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                            options.get(1).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());
                        }

                        findViewById(R.id.option1).setBackgroundColor(getResources().getColor(R.color.choices_color, null));
                        findViewById(R.id.option2).setBackgroundColor(getResources().getColor(R.color.choices_color, null));
                        findViewById(R.id.option3).setBackgroundColor(getResources().getColor(R.color.choices_color, null));

                        findViewById(R.id.flashcard_question).startAnimation(leftInAnim);
                        if (isShowingAnswers) {
                            findViewById(R.id.option1).startAnimation(leftInAnim);
                            findViewById(R.id.option2).startAnimation(leftInAnim);
                            findViewById(R.id.option3).startAnimation(leftInAnim);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                if (isShowingAnswers) {
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

        findViewById(R.id.trash_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                flashcardDatabase.deleteCard(((TextView) findViewById(R.id.flashcard_question)).getText().toString());
                allFlashcards = flashcardDatabase.getAllCards();
                if (allFlashcards.size() == 0){
                    ((TextView) findViewById(R.id.flashcard_question)).setText("Your study set is empty. Add a new card.");
                    ((TextView) findViewById(R.id.flashcard_answer)).setText(null);
                    findViewById(R.id.option1).setVisibility(View.INVISIBLE);
                    findViewById(R.id.option2).setVisibility(View.INVISIBLE);
                    findViewById(R.id.option3).setVisibility(View.INVISIBLE);
                    findViewById(R.id.toggle_choices_availability).setVisibility(View.INVISIBLE);
                    findViewById(R.id.toggle_choices_invisible).setVisibility(View.INVISIBLE);
                    findViewById(R.id.edit_icon).setVisibility(View.INVISIBLE);
                    findViewById(R.id.trash_icon).setVisibility(View.INVISIBLE);
                    findViewById(R.id.next_icon).setVisibility(View.INVISIBLE);
                }
                else {
                    Collections.shuffle(options);
                    if (currentCardDisplayedIndex != 0) {
                        currentCardDisplayedIndex--;
                    }
                    ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                    ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                    if(options.get(0).getText().toString().equals(((TextView)findViewById(R.id.flashcard_answer)).getText().toString())){
                        options.get(0).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
                        options.get(1).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                        options.get(2).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());
                    }
                    else if(options.get(1).getText().toString().equals(((TextView)findViewById(R.id.flashcard_answer)).getText().toString())){
                        options.get(0).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
                        options.get(1).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                        options.get(2).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());
                    }
                    else{
                        options.get(0).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer1());
                        options.get(2).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                        options.get(1).setText(allFlashcards.get(currentCardDisplayedIndex).getWrongAnswer2());
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (((requestCode == 100) || (requestCode == 200)) && (data != null)) {
            Collections.shuffle(options);
            String string1 = data.getExtras().getString("string1");
            String string2 = data.getExtras().getString("string2");
            String string3 = data.getExtras().getString("string3");
            String string4 = data.getExtras().getString("string4");
            ((TextView) findViewById(R.id.flashcard_question)).setText(string1);
            ((TextView) findViewById(R.id.flashcard_answer)).setText(string2);
            if(options.get(0).getText().toString().equals(((TextView)findViewById(R.id.flashcard_answer)).getText().toString())){
                options.get(1).setText(string3);
                options.get(0).setText(string2);
                options.get(2).setText(string4);
            }
            else if(options.get(1).getText().toString().equals(((TextView)findViewById(R.id.flashcard_answer)).getText().toString())){
                options.get(0).setText(string3);
                options.get(1).setText(string2);
                options.get(2).setText(string4);
            }
            else {
                options.get(0).setText(string3);
                options.get(2).setText(string2);
                options.get(1).setText(string4);
            }
            findViewById(R.id.option1).setBackgroundColor(getResources().getColor(R.color.choices_color, null));
            findViewById(R.id.option2).setBackgroundColor(getResources().getColor(R.color.choices_color, null));
            findViewById(R.id.option3).setBackgroundColor(getResources().getColor(R.color.choices_color, null));
            if (isShowingAnswers){
                findViewById(R.id.option1).setVisibility(View.VISIBLE);
                findViewById(R.id.option2).setVisibility(View.VISIBLE);
                findViewById(R.id.option3).setVisibility(View.VISIBLE);
                findViewById(R.id.toggle_choices_availability).setVisibility(View.VISIBLE);
                findViewById(R.id.toggle_choices_invisible).setVisibility(View.INVISIBLE);
            }
            else{
                findViewById(R.id.option1).setVisibility(View.INVISIBLE);
                findViewById(R.id.option2).setVisibility(View.INVISIBLE);
                findViewById(R.id.option3).setVisibility(View.INVISIBLE);
                findViewById(R.id.toggle_choices_availability).setVisibility(View.INVISIBLE);
                findViewById(R.id.toggle_choices_invisible).setVisibility(View.VISIBLE);
            }
            findViewById(R.id.edit_icon).setVisibility(View.VISIBLE);
            findViewById(R.id.trash_icon).setVisibility(View.VISIBLE);
            findViewById(R.id.next_icon).setVisibility(View.VISIBLE);

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

    public int getRandomNumber(int minNumber, int maxNumber) {
        Random rand = new Random();
        return rand.nextInt((maxNumber - minNumber) + 1) + minNumber;
    }
}
