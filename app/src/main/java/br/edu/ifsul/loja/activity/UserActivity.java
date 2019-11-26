package br.edu.ifsul.loja.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsul.loja.R;
import br.edu.ifsul.loja.adapter.ClientesAdapter;
import br.edu.ifsul.loja.adapter.UserAdapter;
import br.edu.ifsul.loja.barcode.BarcodeCaptureActivity;
import br.edu.ifsul.loja.model.Cliente;
import br.edu.ifsul.loja.model.User;

public class UserActivity extends AppCompatActivity {

    private static final String TAG = "UserActivity";
    private ListView lvUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        //ativa o botão home na actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //mapeia o componente da view
        lvUsers = findViewById(R.id.lv_users);

        //buscar os dados no RealTimeDataBase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("vendas/users");

        myRef.orderByChild("nome").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "dataSnapshot=" + dataSnapshot);
                List<User> listUser = new ArrayList<>();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Log.d(TAG, "dataSnapshot=" + ds);
                    User user = ds.getValue(User.class);
                    //====                  //user.setKey(ds.getKey());
                    //user.setIndex(listUser.size());
                    listUser.add(user);

                }

                //faz o bindView

                //lvProdutos.setAdapter(new ProdutosAdapter(ProdutosActivity.this, listProdutos));

                lvUsers.setAdapter(new UserAdapter(UserActivity.this, listUser));




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;


        }

        return true;
    }


}