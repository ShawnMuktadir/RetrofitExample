package com.example.student.retrofitexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=(ListView)findViewById(R.id.listViewHeroes);
        getHeroes();
    }

    public void getHeroes(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api=retrofit.create(Api.class);

        Call<List<Hero>> call=api.getHeroes();

        call.enqueue(new Callback<List<Hero>>() {
            @Override
            public void onResponse(Call<List<Hero>> call, Response<List<Hero>> response) {
                List<Hero> heroList = response.body();
                String[] value=new String[heroList.size()];
                String[] heroes=new String[heroList.size()];
                String[] hero=new String[heroList.size()];
                for (int i=0;i<heroList.size();i++){
//                    heroes[i]=heroList.get(i).getName();
//                    hero[i]=heroList.get(i).getFirstappearance();
                    value[i]="Name: "+heroList.get(i).getName()+" FirstApperance :"+heroList.get(i).getFirstappearance();
                }

                listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,value));

//                listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, heroes));
//                String name, realName, firstappearance;
                for (Hero h:heroList){
                    Log.d("Hero","Name : "+h.getName()+"Real Name :"+h.getRealName()+"First Apperance : "+h.getFirstappearance());

                }


            }

            @Override
            public void onFailure(Call<List<Hero>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
