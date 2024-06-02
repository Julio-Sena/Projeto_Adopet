package com.example.projeto_adopet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class DeleteUsuario extends AppCompatActivity {

    private TextView nomeUsuario, emailUsuario;
    private Button bt_deslogar, bt_excluir_conta;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        bt_deslogar = findViewById(R.id.bt_deslogar);

        getSupportActionBar().hide();
        IniciarComponentes(); // Certifique-se de inicializar os componentes antes de definir os onClickListeners

        bt_deslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(DeleteUsuario.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        bt_excluir_conta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirConta();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String email = currentUser.getEmail();
            usuarioID = currentUser.getUid();

            DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                    if (documentSnapshot != null) {
                        nomeUsuario.setText(documentSnapshot.getString("nome"));
                        emailUsuario.setText(email);
                    }
                }
            });
        } else {
            // Faça algo aqui se o usuário não estiver autenticado
        }
    }


    private void excluirConta() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        usuarioID = user.getUid();

        // Excluir dados do Firestore
        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("db", "Conta excluída com sucesso do Firestore");

                // Excluir conta de autenticação
                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("auth", "Conta excluída com sucesso do FirebaseAuth");
                            Snackbar.make(findViewById(android.R.id.content), "Conta excluída com sucesso", Snackbar.LENGTH_LONG).show();
                            Intent intent = new Intent(DeleteUsuario.this, Login.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.e("auth_error", "Erro ao excluir conta do FirebaseAuth", task.getException());
                            Snackbar.make(findViewById(android.R.id.content), "Erro ao excluir conta", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("db_error", "Erro ao excluir conta do Firestore", e);
                Snackbar.make(findViewById(android.R.id.content), "Erro ao excluir conta", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void IniciarComponentes() {
        nomeUsuario = findViewById(R.id.textNomeUsuario);
        emailUsuario = findViewById(R.id.textEmailUsuario);
        bt_deslogar = findViewById(R.id.bt_deslogar);
        bt_excluir_conta = findViewById(R.id.bt_excluir_conta);
    }
}
