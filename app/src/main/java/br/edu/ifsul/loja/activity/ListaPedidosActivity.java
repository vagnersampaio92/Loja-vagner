package br.edu.ifsul.loja.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsul.loja.R;
import br.edu.ifsul.loja.adapter.ListaPedidosAdapter;
import br.edu.ifsul.loja.adapter.UserAdapter;
import br.edu.ifsul.loja.model.Cliente;
import br.edu.ifsul.loja.model.Pedido;
import br.edu.ifsul.loja.model.User;
import br.edu.ifsul.loja.setup.AppSetup;

public class ListaPedidosActivity extends AppCompatActivity {

    private static final String TAG = "ListaPedidosActivity";
    private ListView lv_pedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listpedidos);
        //ativa o botão home na actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //mapeia o componente da view
        lv_pedidos = findViewById(R.id.lv_pedidos);

        //buscar os dados no RealTimeDataBase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("vendas/cliente/"+ AppSetup.cliente.getKey());
        Log.d(TAG, "Myref=" + myRef);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "dataSnapshot=" + dataSnapshot);
                Cliente cliente = dataSnapshot.getValue(Cliente.class);

                final List<Pedido> pedidos = new ArrayList<>();
                for(final String keyp : cliente.getPedidos()){
                    Log.d(TAG, "keyp=" + keyp);
                    DatabaseReference myRef = database.getReference("vendas/pedidos/"+ keyp);
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "dataSnapshot2=" + dataSnapshot);
                            Pedido pedido = dataSnapshot.getValue(Pedido.class);
                            pedido.setKey(keyp);
                            pedidos.add(pedido);
                            lv_pedidos.setAdapter(new ListaPedidosAdapter(ListaPedidosActivity.this, pedidos));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                //faz o bindView

                //lvProdutos.setAdapter(new ProdutosAdapter(ProdutosActivity.this, listProdutos));

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
