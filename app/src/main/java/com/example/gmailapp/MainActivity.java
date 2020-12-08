package com.example.gmailapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.gmailapp.adapter.EmailItemAdapter;
import com.example.gmailapp.model.EmailItemModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<EmailItemModel> items;
    EmailItemAdapter adapter;
    boolean isShowingFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new ArrayList<>();
        items.add(new EmailItemModel("Edurila.com", "$19 Only (First 10 spots)", "Are you looking to Learn Web", "12:34PM"));
        items.add(new EmailItemModel("Chris Abad", "Help make campaign monitor better", "Let us know your thought", "11:22AM"));
        items.add(new EmailItemModel("Tuto.com", "8h de formation gratuite", "Photoshop, SEO, Blender", "11:04AM"));
        items.add(new EmailItemModel("Support", "Societe Ovh", "SAS OVH", "10:26AM"));
        items.add(new EmailItemModel("Matt from ionic", "The New Ionic Creator Is Here!", "Announcing all-new Creator", "12:34PM"));
        items.add(new EmailItemModel("Microsoft", "Welcome to IT student training", "Click this link to proceed", "9:24PM"));
        items.add(new EmailItemModel("Pro. Ng", "Assignment Due!", "You was late for deadline", "03:34AM"));

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final EmailItemAdapter adapter = new EmailItemAdapter(items);
        recyclerView.setAdapter(adapter);
        isShowingFavorite = false;

        findViewById(R.id.btn_favorite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShowingFavorite = !isShowingFavorite;
                if(isShowingFavorite){
                    adapter.showFavorites();;
                } else{
                    adapter.showAll();
                }

            }
        });

        EditText editKeyword = findViewById(R.id.txt_search);
        editKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                    adapter.search(keyword);

            }
        });
    }
}
