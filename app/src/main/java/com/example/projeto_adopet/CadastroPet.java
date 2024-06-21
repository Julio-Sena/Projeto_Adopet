package com.example.projeto_adopet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.HashMap;
import java.util.Map;

/** @noinspection ALL */
public class CadastroPet extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText edit_nome_pet, edit_idade_pet, edit_raca_pet;
    private Button bt_cadastrar_pet, button_choose_image;
    private ImageView image_preview;
    private Uri imageUri;
    private StorageReference storageReference;
    String[] mensagens = {"Preencha todos os campos", "Cadastro de pet realizado com sucesso"};
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pet);

        getSupportActionBar().hide();
        IniciarComponentes();

        storageReference = FirebaseStorage.getInstance().getReference("Pets");

        button_choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        bt_cadastrar_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomePet = edit_nome_pet.getText().toString();
                String idadePet = edit_idade_pet.getText().toString();
                String racaPet = edit_raca_pet.getText().toString();

                if (nomePet.isEmpty() || idadePet.isEmpty() || racaPet.isEmpty() || imageUri == null) {
                    Snackbar snackbar = Snackbar.make(v, mensagens[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                } else {
                    uploadImage();
                }
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            image_preview.setImageURI(imageUri);
        }
    }

    private void uploadImage() {
        if (imageUri != null) {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            fileReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();
                    CadastrarPet(imageUrl);
                });
            }).addOnFailureListener(e -> {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Erro ao fazer upload da imagem", Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(Color.BLACK);
                snackbar.setTextColor(Color.WHITE);
                snackbar.show();
            });
        }
    }

    private String getFileExtension(Uri uri) {
        String extension;
        // Use content resolver to get the file extension
        extension = getContentResolver().getType(uri).split("/")[1];
        return extension;
    }

    private void CadastrarPet(String imageUrl) {
        String nomePet = edit_nome_pet.getText().toString();
        String idadePet = edit_idade_pet.getText().toString();
        String racaPet = edit_raca_pet.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> pet = new HashMap<>();
        pet.put("nome", nomePet);
        pet.put("idade", idadePet);
        pet.put("raca", racaPet);
        pet.put("imageUrl", imageUrl);

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID).collection("Pets").document();
        documentReference.set(pet).addOnSuccessListener(aVoid -> {
            Log.d("db", "Sucesso ao salvar os dados do pet");
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), mensagens[1], Snackbar.LENGTH_SHORT);
            snackbar.setBackgroundTint(Color.WHITE);
            snackbar.setTextColor(Color.BLACK);
            snackbar.show();
        }).addOnFailureListener(e -> {
            Log.d("db_error", "Erro ao salvar os dados do pet" + e.toString());
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Erro ao cadastrar pet", Snackbar.LENGTH_SHORT);
            snackbar.setBackgroundTint(Color.BLACK);
            snackbar.setTextColor(Color.WHITE);
            snackbar.show();
        });
    }

    private void IniciarComponentes() {
        edit_nome_pet = findViewById(R.id.edit_nome_pet);
        edit_idade_pet = findViewById(R.id.edit_idade_pet);
        edit_raca_pet = findViewById(R.id.edit_raca_pet);
        bt_cadastrar_pet = findViewById(R.id.bt_cadastrar_pet);
        button_choose_image = findViewById(R.id.button_choose_image);
        image_preview = findViewById(R.id.image_preview);
    }
}
