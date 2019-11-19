package br.edu.ifsul.loja.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;

import br.edu.ifsul.loja.R;
import br.edu.ifsul.loja.model.Cliente;
import br.edu.ifsul.loja.model.ItemPedido;
import br.edu.ifsul.loja.model.Produto;
import br.edu.ifsul.loja.setup.AppSetup;

public class ProdutoDetalheActivity extends AppCompatActivity {

    private static final String TAG = "produtoDetalheActivity";
    private TextView tvNome, tvValor, tvEstoque, tvDescricao, tvVendedor;
    private ImageView imvFoto;
    private ProgressBar pbFoto;
    private EditText etQuantidade;
    private Produto produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_detalhe);

        //ativa o botão home na actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //obtém o position do objeto produto a a partir da intent
        int position = getIntent().getExtras().getInt("position");
        produto = AppSetup.listProdutos.get(position);

        //mapeia os componentes da view
        tvNome = findViewById(R.id.tvNomeProduto);
        imvFoto = findViewById(R.id.imvFoto);
        pbFoto = findViewById(R.id.pb_foto_produto_detalhe);
        tvValor = findViewById(R.id.tvValorProduto);
        tvEstoque = findViewById(R.id.tvQuantidadeProduto);
        tvDescricao = findViewById(R.id.tvDerscricaoProduto);
        etQuantidade = findViewById(R.id.etQuantidade);
        tvVendedor = findViewById(R.id.tvVendedor);

        //obtém a instância do banco de dados
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("vendas/produtos/" + produto.getKey() + "/quantidade");

        //trata evento onClick do botão
        findViewById(R.id.btComprarProduto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //realiza a venda
                if(AppSetup.cliente == null){
                    startActivity(new Intent(ProdutoDetalheActivity.this, ClientesActivity.class));
                }else{
                    if(!etQuantidade.getText().toString().isEmpty()){
                        if(Integer.parseInt(etQuantidade.getText().toString()) > produto.getQuantidade()
                                || Integer.parseInt(etQuantidade.getText().toString()) <= 0){
                            Snackbar.make(findViewById(R.id.container_activity_detalhe_produto),
                                    R.string.snack_qde_insuficiente,
                                    Snackbar.LENGTH_LONG).show();
                        }else{
                            //faz a venda
                            ItemPedido item = new ItemPedido(produto);
                            item.setQuantidade(Integer.parseInt(etQuantidade.getText().toString()));
                            item.setTotalItem(Integer.parseInt(etQuantidade.getText().toString()) * produto.getValor());
                            item.setSituacao(true);
                            AppSetup.carrinho.add(item);
                            //baixa do estoque
                            myRef.setValue(produto.getQuantidade() - Integer.parseInt(etQuantidade.getText().toString()));
                            //vai para o carrinho
                            startActivity(new Intent(ProdutoDetalheActivity.this, CarrinhoActivity.class));
                            finish();
                        }
                    }else{
                        Snackbar.make(findViewById(R.id.container_activity_detalhe_produto),
                                R.string.snack_insira_quantidade,
                                Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });

        //bind nos componentes da view
        tvNome.setText(produto.getNome());
        tvValor.setText(NumberFormat.getCurrencyInstance().format(produto.getValor()));
        tvEstoque.setText(produto.getQuantidade().toString());
        tvDescricao.setText(produto.getDescricao());
        //ativar quando tiver a navegabilidade definida
        //tvVendedor.setText(AppSetup.user.getEmail());
        if(produto.getUrl_foto().equals("")){
            pbFoto.setVisibility(View.INVISIBLE);
        }else{
            //carrega a foto aqui
        }

        //escuta o banco para atualizar estoque na view
        myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String url = "vendas/produtos/" + produto.getKey() + "/quantidade";
                        Log.d(TAG, "url=" + url);
                        tvEstoque.setText(dataSnapshot.getValue(Integer.class).toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }

        return true;
    }
}
