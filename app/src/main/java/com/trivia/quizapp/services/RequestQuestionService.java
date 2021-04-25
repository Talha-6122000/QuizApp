package com.trivia.quizapp.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import com.trivia.quizapp.model.QuizService;
import com.trivia.quizapp.model.Question;
import com.trivia.quizapp.model.QuestionResponse;
import com.trivia.quizapp.utils.Constants;
import com.trivia.quizapp.utils.NetworkHelper;

public class RequestQuestionService extends IntentService {

    private final String TAG = "RequestQuestionService";
    private static final String EXTRA_CATEGORY_ID = "categoryId";


    public RequestQuestionService() {
        super("RequestQuestionService");
    }


    /**
     * Starts this service to perform action RequestQuestion with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @param context    Context of activity that will invoke the service
     * @param categoryId The integer id of current category
     */
    public static void startActionRequestQuestions(Context context, int categoryId) {
        Intent intent = new Intent(context, RequestQuestionService.class);
        intent.putExtra(EXTRA_CATEGORY_ID, categoryId);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final int categoryId = intent.getIntExtra(EXTRA_CATEGORY_ID, -1);
            handleActionRequestQuestion(categoryId);
        }
    }

    /**
     * Invokes webservice to fetch questions from webservice and parses response. After receiving response,
     * result is returned to caller activity.
     *
     * @param categoryId The integer id of current category
     */
    private void handleActionRequestQuestion(int categoryId) {
        if (NetworkHelper.hasNetworkAccess(getApplicationContext())) {
            String str = Constants.DIFFICULTY_LEVEL;
            QuizService quizService = QuizService.retrofit.create(QuizService.class);
            Call<QuestionResponse> call = quizService.loadQuestionsOfAnyDifficulty(
                    Constants.VALUE_AMOUNT, categoryId, Constants.VALUE_TYPE);
            try {
                QuestionResponse response = call.execute().body();
                if (response.getResponse_code() == Constants.RESPONSE_SUCCESS) {
                    ArrayList<Question> questions = response.getResults();
                    ArrayList<Question> difficultyLevel = new ArrayList<>();
                    Intent responseIntent = new Intent(Constants.RESPONSE_QUESTION_SERVICE);
                    for(int index=0;index<questions.size();index++) {
                        if(Constants.DIFFICULTY_EASY==questions.get(index).getDifficulty()) {
                            difficultyLevel.add(questions.get(index));
                        }
                        if(Constants.DIFFICULTY_MEDIUM==questions.get(index).getDifficulty())
                        {
                            difficultyLevel.add(questions.get(index));
                        }
                        if(Constants.DIFFICULTY_HARD==questions.get(index).getDifficulty())
                        {
                            difficultyLevel.add(questions.get(index));
                        }
                    }
                    responseIntent.putParcelableArrayListExtra(Constants.PAYLOAD_QUESTION_SERVICE, questions);
                    LocalBroadcastManager broadcastManager = LocalBroadcastManager
                            .getInstance(getApplicationContext());
                    broadcastManager.sendBroadcast(responseIntent);
                } else {
                    //TODO : Handle other response codes
                    Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "handleActionRequestQuestion: " + e.getMessage());
                return;
            }

        }
    }
}
