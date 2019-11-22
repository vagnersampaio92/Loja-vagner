package br.edu.ifsul.loja.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.edu.ifsul.loja.R;
import br.edu.ifsul.loja.model.User;
import br.edu.ifsul.loja.setup.AppSetup;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "signup";
    private EditText etNome,etSobrenome,etCodigo,etFuncao,etEmail, etSenha;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //mapeia os componentes da UI
        etEmail = findViewById(R.id.textInputEmail);
        etSenha = findViewById(R.id.textInputSenha);
        etNome = findViewById(R.id.textInputNome);
        etSobrenome = findViewById(R.id.textInputSobrenome);
        etCodigo = findViewById(R.id.textInputCodigo);
        etFuncao = findViewById(R.id.textInputFuncao);
        //trata evento onclick do button
        findViewById(R.id.buttonsignup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String senha = etSenha.getText().toString();
                if(!email.isEmpty() && !senha.isEmpty()){
                    signUp(email, senha);
                }else{
                    if(email.isEmpty()){
                        etEmail.setError(getString(R.string.msg_invalido));
                    }
                    if(senha.isEmpty()){
                        etSenha.setError(getString(R.string.msg_invalido));
                    }
                  //  Snackbar.make(findViewById(R.id.R_id_container_activity_login), getString(R.string.toast_preencher_todos_campos), Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }
    private void signUp(String email, String senha) {
//
        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            //    Snackbar.make(findViewById(R.id.container_activity_signup), getString(R.string.toast_falha_autenticacao), Snackbar.LENGTH_LONG).show();
                            //   updateUI(null);
                        }

                        // ...
                    }
                });
    }
    private void updateUI() {
//        FirebaseDatabase.getInstance().getReference()
//                .child("vendas").child("users").child(mAuth.getCurrentUser().getUid())
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        //Log.d(TAG, "dataSnapshot=" + dataSnapshot + " id user = " + mAuth.getCurrentUser().getUid());
//                        AppSetup.user = dataSnapshot.getValue(User.class);
//                        AppSetup.user.setFirebaseUser(mAuth.getCurrentUser());
//                        startActivity(new Intent(SignupActivity.this, ProdutosActivity.class));
//                        finish();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                        //    Snackbar.make(findViewById(R.id.R_id_container_activity_login), getString(R.string.snack_problem_autenticacao), Snackbar.LENGTH_LONG).show();
//                    }
//                });
        //DatabaseReference ref;
        //DatabaseReference postsRef = ref.child("vendas").child("users");

        // DatabaseReference newPostRef = postsRef.push();
        //  newPostRef.setValueAsync(new );

    }



}