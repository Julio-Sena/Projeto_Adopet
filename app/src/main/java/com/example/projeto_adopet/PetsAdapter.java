package com.example.projeto_adopet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PetsAdapter extends RecyclerView.Adapter<PetsAdapter.PetViewHolder> {

    private List<Pet> petList;
    private Context context;

    public PetsAdapter(List<Pet> petList, Context context) {
        this.petList = petList;
        this.context = context;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_pets, parent, false);
        return new PetViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        Pet pet = petList.get(position);
        holder.imagePet.setImageResource(pet.getImageResId());
        holder.namePet.setText(pet.getName());
        holder.agePet.setText(pet.getAge());
        holder.breedPet.setText(pet.getBreed());
        holder.colorPet.setText(pet.getColor());
        holder.btnViewPetDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ListaPetsActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    public static class PetViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePet;
        TextView namePet;
        TextView agePet;
        TextView breedPet;
        TextView colorPet;
        Button btnViewPetDetails;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePet = itemView.findViewById(R.id.image_pet);
            namePet = itemView.findViewById(R.id.name_pet);
            agePet = itemView.findViewById(R.id.age_pet);
            breedPet = itemView.findViewById(R.id.breed_pet);
            colorPet = itemView.findViewById(R.id.color_pet);
            btnViewPetDetails = itemView.findViewById(R.id.btn_view_pet_details);
        }
    }
}