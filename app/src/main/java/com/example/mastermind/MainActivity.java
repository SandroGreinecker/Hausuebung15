package com.example.mastermind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = ">";
    public List<String> items = new ArrayList<>();
    public ListView mListView;
    public ArrayAdapter<String> adapter;
    public String alphabet;
    public int codeLength;
    public boolean doubleAllowed;
    public int guessRounds;
    public String correctPositionSign;
    public String correctCodeElementSign;
    public String res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = findViewById(R.id.listview);
        bindAdapterToListView(mListView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 12) {
                    deleteList(mListView);
                }
            }
        });
        readsettings();
        res=createcode();
        System.out.println(res);
    }

    public String createcode() {
        Random r = new Random();
        String code="";
        for(int i=0;i<codeLength;i++) {
            code = code + alphabet.charAt(r.nextInt(alphabet.length()));
        }
        System.out.println(code);
        return code;
    }

    public void bindAdapterToListView(ListView lv) {
        adapter = new ArrayAdapter<>(
                this,android.R.layout.simple_list_item_1,items
        );
        lv.setAdapter(adapter);
    }

    public void addItem(View view,String adder) {
        items.add(adder);
        adapter.notifyDataSetChanged();
    }

    public void deleteList(View view) {
        items.clear();
        adapter.notifyDataSetChanged();
    }

    public void submit(View view) {
        EditText text = findViewById(R.id.textfeld);
        String eingabe = text.getText().toString();
        addItem(mListView,eingabe);
    }

    public void settings(View view) {
        System.out.println(readsettings());
        List<String> set = readsettings();
        alphabet = set.get(0).substring(9);
        codeLength = Integer.parseInt(set.get(1).substring(11));
        doubleAllowed = Boolean.parseBoolean(set.get(2).substring(14));
        guessRounds = Integer.parseInt(set.get(3).substring(12));
        correctPositionSign = set.get(4).substring(20);
        correctCodeElementSign = set.get(5).substring(23);
        deleteList(mListView);
        addItem(mListView,"alphabet");
        addItem(mListView,alphabet);
        addItem(mListView,"codeLength");
        addItem(mListView, String.valueOf(codeLength));
        addItem(mListView,"doubleAllowed");
        addItem(mListView, String.valueOf(doubleAllowed));
        addItem(mListView,"guessRounds");
        addItem(mListView, String.valueOf(guessRounds));
        addItem(mListView,"CorrectPositionSign");
        addItem(mListView,correctPositionSign);
        addItem(mListView,"CorrectCodeElementSign");
        addItem(mListView,correctCodeElementSign);
        addItem(mListView,"START NEW GAME");
        addItem(mListView,"New Game");
    }

    public List<String> readsettings() {
        InputStream in = getInputStreamForAsset("config.conf");
        BufferedReader b = new BufferedReader(new InputStreamReader(in));
        String line;
        List<String> list = new ArrayList<>();
        try {
            while((line = b.readLine()) != null) {
                Log.d(TAG,"line: "+line);
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public InputStream getInputStreamForAsset(String filename) {
        Log.d(TAG,"getInputStreamForAsset: "+filename);
        AssetManager assets = getAssets();
        try{
            return assets.open(filename);
        } catch (IOException e) {
            Log.e(TAG,e.toString());
            e.printStackTrace();
            return null;
        }

    }
}