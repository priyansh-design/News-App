package com.example.newsfresh;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<News> newsList;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private Button filterBtn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.newsRecycle);
        filterBtn=findViewById(R.id.filterBtn);
        newsList=new ArrayList<>();
        getNews();
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterBottomSheet();
            }
        });



    }
    public String curCategory="general",curCountry="in";
    public int categorypos=0,countrypos=0;

    public void filterBottomSheet(){
        View view= LayoutInflater.from(this).inflate(R.layout.filterbottomsheet,null);
        Spinner countrySpinner=view.findViewById(R.id.contrySpinner);
        Spinner categorySpinner=view.findViewById(R.id.categorySpinner);
        Button apply=view.findViewById(R.id.applyBtn);

        ArrayAdapter<String> categoryAdapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,Constants.category);
        ArrayAdapter<String> countryAdapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,Constants.country);
        categoryAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        countryAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        categorySpinner.setAdapter((categoryAdapter));
        countrySpinner.setAdapter((countryAdapter));
        categorySpinner.setSelection(categorypos);
        countrySpinner.setSelection(countrypos);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                curCategory=Constants.category[position];
                categorypos=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String a=Constants.country[position];
                switch (a){
                    case "India":
                        curCountry="in";
                        break;
                    case "Usa":
                        curCountry="us";
                        break;
                    case "Russia":
                        curCountry="ru";
                        break;
                    case "France":
                        curCountry="fr";
                        break;
                    case "Australia":
                        curCountry="au";
                        break;
                    case "Uk":
                        curCountry="gb";
                        break;
                    default:
                        curCountry="in";
                        break;
                }
                countrypos=position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                getNews();
            }
        });




    }

    public void setAdapter() {

        Adapter adapter=new Adapter(this,newsList);


        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
//        adapter.updateNews(newsList);

        recyclerView.setAdapter(adapter);
        Log.d("onrun", String.valueOf(newsList.size()));

    }


    public void getNews() {
        if(newsList.size()!=0){
            newsList.clear();
        }
        Log.d("FILTER-TAG","COUNTRY="+curCountry);
        Log.d("FILTER-TAG","CATEGORY="+curCategory);
        String mainUrl ="https://saurav.tech/NewsAPI/top-headlines/category/"+curCategory+"/"+curCountry+".json";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest//here we have send the request to the api to get the jsonobject,url-it showsfrom where we have to send the request,response.listener-it helps to listen the response from the request we have sent
                (Request.Method.GET,
                        mainUrl,
                        null,
                        new Response.Listener<JSONObject>() {


                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray jsonArray=response.getJSONArray("articles");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject art=jsonArray.getJSONObject(i);
                                        String a=art.getString("author");
                                        String t=art.getString("title");
                                        String u=art.getString("url");
                                        String iu=art.getString("urlToImage");

                                        newsList.add(new News(a,t,u,iu));
                                        Log.d("onrun", String.valueOf(newsList.size()));
                                        Log.d("onrun",art.getString("author"));
                                        Log.d("onrun",art.getString("title"));
                                        Log.d("onrun",art.getString("urlToImage"));


                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                setAdapter();



                            }
                        },
                        new Response.ErrorListener() {


                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                Log.d("onNotRun","oops");

                            }
                        });
        queue.add(jsonObjectRequest);

    }
}