package com.example.projeto_adopet;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ListaPetsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PetsAdapter petsAdapter;
    private List<Pet> petList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pets);

        recyclerView = findViewById(R.id.recycler_view_pets);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        petList = new ArrayList<>();
        petList.add(new Pet(R.drawable.ic_pet_dog_2, "Antonio", "2 anos", "SRD"));
        petList.add(new Pet(R.drawable.ic_pet_cat, "Luna", "1 ano", "Labrador"));
        petList.add(new Pet(R.drawable.ic_pet_dog_1, "Spike", "3 anos", "Persa"));

        petsAdapter = new PetsAdapter(petList, this);
        recyclerView.setAdapter(petsAdapter);
    }
}