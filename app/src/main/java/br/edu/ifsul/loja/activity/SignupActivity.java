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

        //ativa o botão home na actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                User user = new User();
                user.setEmail(etEmail.getText().toString());
                user.setSenha(etSenha.getText().toString());
                user.setNome(etNome.getText().toString());
                user.setSobrenome(etSobrenome.getText().toString());
                user.setFuncao(etFuncao.getText().toString());
                user.setCodigoDeBarras(Long.parseLong(etCodigo.getText().toString()));
                if(!user.getEmail().isEmpty() && !user.getSenha().isEmpty()){

                    signUp(user);
                }else{
                    if(user.getEmail().isEmpty()){
                        etEmail.setError(getString(R.string.msg_invalido));
                    }
                    if(user.getSenha().isEmpty()){
                        etSenha.setError(getString(R.string.msg_invalido));
                    }
                  //  Snackbar.make(findViewById(R.id.R_id_container_activity_login), getString(R.string.toast_preencher_todos_campos), Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }
    private void signUp(final User user) {

        mAuth.createUserWithEmailAndPassword(user.getEmail(),user.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            user.setFirebaseUser( task.getResult().getUser());
                            Log.d(TAG, "uid: " + user.getFirebaseUser());
                            saveUI(user); //por que aqui não pode?
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
    private void saveUI(final User user) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

//        Log.d(TAG, "uid: " + user.getFirebaseUser());


        DatabaseReference myRef = database.getReference().child("vendas").child("users").child(user.getFirebaseUser().getUid());

        myRef.setValue(user);


        user.getFirebaseUser().sendEmailVerification()
               .addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {

                   }
               });
        startActivity(new Intent(SignupActivity.this, ProdutosActivity.class));

    }



}