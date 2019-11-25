package br.edu.ifsul.loja.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;

import br.edu.ifsul.loja.R;
import br.edu.ifsul.loja.model.ItemPedido;
import br.edu.ifsul.loja.model.Pedido;
import br.edu.ifsul.loja.model.Produto;
import br.edu.ifsul.loja.setup.AppSetup;

public class CarrinhoActivity extends AppCompatActivity {

    private static final String TAG = "carrinhoActivity";
    private ListView lvCarrinho;
    private TextView valor;
    private TextView nome;

    private ArrayAdapter<Produto> adapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("vendas/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        Log.d(TAG, "Carrinho=" + AppSetup.carrinho);

        Intent intent = getIntent();
        String val = intent.getStringExtra("value");
        String name_cli = intent.getStringExtra("name_cli");

        //ativa o botão home na actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        valor = (TextView) findViewById(R.id.tvTotalPedidoCarrinho);
        nome = (TextView) findViewById(R.id.tvClienteCarrinho);



        valor.setText(val);
        nome.setText(name_cli);
        //tratamento de eventos
        lvCarrinho = findViewById(R.id.lv_carrinho);

        lvCarrinho.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CarrinhoActivity.this, "Clique curto.", Toast.LENGTH_SHORT).show();
                //pegar os itens e passar para venda, logo após passar o valor desse objeto para tvTotalPedidoCarrinho
                venda();
            }
        });

        lvCarrinho.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CarrinhoActivity.this, "Clique longo.", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    //inflar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_carrinho, menu);
        return true; //chamada do método termina aqui
    }

    //tratar eventos do menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuitem_salvar_pedido:
                Toast.makeText(this, "Salvar", Toast.LENGTH_SHORT).show();
                salvapedido();
                finish();
                break;
            case R.id.menuitem_cancelar_pedido:
                Toast.makeText(this, "Cancelar", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true; //chamada do método termina aqui
    }


    //excluir um item

    //editar um item

    //cancelar pedido

    //salvar pedido




    //rollback no estoque

    //limpar setup
    private void salvapedido (){
        Pedido p = new Pedido(AppSetup.carrinho);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        Log.d(TAG, "uid: " + user.getFirebaseUser());
//        p.setKey("1");
//        DatabaseReference myRef = database.getReference().child("vendas").child("pedidos").child(p.getKey());
        DatabaseReference myRef = database.getReference("vendas/pedidos");
        p.setKey(myRef.push().getKey()); //cria o nó e devolve a key

        p.setCliente(AppSetup.cliente);
        p.setSituacao(true);
        
        myRef.child(p.getKey()).setValue(p); //salva o produto no database
    }
    private void atualizaEstoque(int position){
        final  DatabaseReference myRef =database.getReference("vendas/produtos");
        ItemPedido item = AppSetup.carrinho.get(position);
        myRef.child(item.getProduto().getKey()).child("quantidade").setValue(item.getQuantidade()+item.getProduto().getQuantidade());
    }

    private void venda(){
        salvapedido();

    }

    private void atualizarview(){
//        lvCarrinho.setAdapter(new CarrinhoActivity(CarrinhoActivity.this, AppSetup.carrinho));
//        totalPedido= od;
//        for(ItemPedido item : AppSetup.carrinho){
//            totalPedido += item.getTotalItem();
//        }
//        tvTotalPedido.setText(NumberFormat.getCurrencyInstance().format(totalPedido));
    }
}
