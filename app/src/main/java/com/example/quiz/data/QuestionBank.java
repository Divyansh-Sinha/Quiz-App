package com.example.quiz.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.quiz.controller.AppController;
import com.example.quiz.model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import static com.example.quiz.controller.AppController.TAG;

public class QuestionBank {
    ArrayList<Question> questionArrayList = new ArrayList<>();
    private String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
    
    public List<Question> getQuesion(final AnswerListAsyncResponse callBack)//getQuestion will have to confirm to the AnserList interface
            //It means that every time that we get the information from API we want to make sure the callBack is not NULL
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, (JSONArray) null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0;i < response.length();i++)
                {          //For getting JSON object
                    try {
                        Question question = new Question();

                        question.setAnswer(response.getJSONArray(i).get(0).toString());
                        question.setAnswerTrue(response.getJSONArray(i).getBoolean(1));


                        //Add Question Object to list
                        questionArrayList.add(question);
                        //Log.d("Helooo", "onResponse: "+question);

//                       Log.d("JSON", "onResponse: " + response.getJSONArray(i).get(0));
//                        Log.d("JSON2", "onResponse: "+response.getJSONArray(i).get(1));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(null!=callBack) callBack.processFinished(questionArrayList);
                //After passing the questionArrayList all the objects will be held in the interface arraylist.
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                
            }
        });
        AppController.getInstance().addToRequest(jsonArrayRequest);

        return questionArrayList;
    }
}
