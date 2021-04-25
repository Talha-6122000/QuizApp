package com.trivia.quizapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.trivia.quizapp.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DifficultyLevelActivity extends AppCompatActivity implements View.OnClickListener {

    private Context activityContext;
    private Unbinder unbinder;
    private int categoryId = -1;


    @BindView(R.id.btn_mcq_question)
    Button btn_mcq_question;
    @BindView(R.id.btn_hard_question)
    Button btn_hard_question;
    @BindView(R.id.btn_medium_question)
    Button btn_medium_question;
    @BindView(R.id.btn_easy_question)
    Button btn_easy_question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chose_difficulity_level);
        activityContext = DifficultyLevelActivity.this;
        unbinder = ButterKnife.bind(this);
       // btn_true_false_question.setOnClickListener(this);
        btn_mcq_question.setOnClickListener(this);
        btn_hard_question.setOnClickListener(this);
        btn_medium_question.setOnClickListener(this);
        btn_easy_question.setOnClickListener(this);
        categoryId = getIntent().getIntExtra(Constants.EXTRA_CATEGORY_ID, -1);
        String str="";
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                return false;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activityContext, QuizActivity.class);
        switch (v.getId()) {
            case R.id.btn_easy_question:
                intent.putExtra("Category", categoryId);
                intent.putExtra(Constants.DIFFICULTY_LEVEL,"easy");
                startActivity(intent);
                break;
            case R.id.btn_medium_question:
                intent.putExtra("Category", categoryId);
                intent.putExtra(Constants.DIFFICULTY_LEVEL,"medium");
                startActivity(intent);
                break;
            case R.id.btn_hard_question:
                intent.putExtra("Category", categoryId);
                intent.putExtra(Constants.DIFFICULTY_LEVEL,"hard");
                startActivity(intent);
                break;
            case R.id.btn_mcq_question:
                intent.putExtra("Category", categoryId);
                intent.putExtra(Constants.DIFFICULTY_LEVEL,"mcq");
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
