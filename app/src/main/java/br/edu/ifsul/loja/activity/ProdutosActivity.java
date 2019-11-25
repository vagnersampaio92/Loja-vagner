package br.edu.ifsul.loja.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsul.loja.R;
import br.edu.ifsul.loja.adapter.ProdutosAdapter;
import br.edu.ifsul.loja.barcode.BarcodeCaptureActivity;
import br.edu.ifsul.loja.model.Cliente;
import br.edu.ifsul.loja.model.Produto;
import br.edu.ifsul.loja.setup.AppSetup;

public class ProdutosActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "produtosActivity";
    private ListView lvProdutos;
    private List<Produto> produtosTemp = new ArrayList<>();
    private static final int RC_BARCODE_CAPTURE = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //a navegação de alto nível
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        //personaliza o menu da navigationview de acordo com o perfil do usuário logado na sessão
        if(AppSetup.user.getFuncao().equals("admin")){
            navigationView.getMenu().findItem(R.id.nav_group_admin).setVisible(true);
        }

        //mapeia o componente da view
        lvProdutos = findViewById(R.id.lv_produtos);

        lvProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(produtosTemp.isEmpty()){
                    Intent intent = new Intent(ProdutosActivity.this, ProdutoDetalheActivity.class);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(ProdutosActivity.this, ProdutoDetalheActivity.class);
                    intent.putExtra("position", produtosTemp.get(position).getIndex());
                    startActivity(intent);
                }

            }
        });

        //buscar os dados no RealTimeDataBase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("vendas/produtos");
        myRef.orderByChild("nome").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "dataSnapshot=" + dataSnapshot);
                AppSetup.listProdutos = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Log.d(TAG, "dataSnapshot=" + ds);
                    Produto produto = ds.getValue(Produto.class);
                    produto.setKey(ds.getKey());
                    produto.setKey(ds.getKey());
                    produto.setIndex(AppSetup.listProdutos.size());
                    AppSetup.listProdutos.add(produto);
                }

                //faz o bindView
                lvProdutos.setAdapter(new ProdutosAdapter(ProdutosActivity.this, AppSetup.listProdutos));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(!AppSetup.carrinho.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //adiciona um título e uma mensagem
            builder.setTitle(getString(R.string.title_modal_atencao));
            builder.setMessage(getString(R.string.message_modal_sair));
            //adiciona os botões
            builder.setPositiveButton(R.string.alert_sim, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton(R.string.alert_nao, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Snackbar.make(findViewById(R.id.container_activity_clientes), getString(R.string.snack_operacao_cancelada), Snackbar.LENGTH_LONG).show();
                }
            });

            builder.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_produtos, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.menuitem_pesquisar).getActionView();
        searchView.setQueryHint(getString(R.string.text_nome));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                produtosTemp.clear();
                for(Produto p : AppSetup.listProdutos){
                    if(p.getNome().contains(newText)){
                        produtosTemp.add(p);
                    }
                }

                //faz o bindView
                lvProdutos.setAdapter(new ProdutosAdapter(ProdutosActivity.this, produtosTemp));

                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuitem_barcode:
                Intent intent = new Intent(ProdutosActivity.this, BarcodeCaptureActivity.class);
                intent.putExtra(BarcodeCaptureActivity.AutoFocus, true); //true liga a funcionalidade autofoco
                intent.putExtra(BarcodeCaptureActivity.UseFlash, false); //true liga a lanterna (fash)
                startActivityForResult(intent, RC_BARCODE_CAPTURE);
                break;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    //Toast.makeText(this, barcode.displayValue, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                    //localiza o produto na lista (ou não)
                    boolean flag = true;
                    for (Produto produto : AppSetup.listProdutos) {
                        if (String.valueOf(produto.getCodigoDeBarras()).equals(barcode.displayValue)) {
                            flag = false;
                            Intent intent = new Intent(ProdutosActivity.this, ProdutoDetalheActivity.class);
                            intent.putExtra("position", produto.getIndex());
                            startActivity(intent);
                            break;
                        }
                    }
                    if (flag) {
                        Snackbar.make(findViewById(R.id.container_activity_produtos_), getString(R.string.snack_codigo_barras_nao_cadastrado), Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, R.string.barcode_failure, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                Toast.makeText(this, String.format(getString(R.string.barcode_error),
                        CommonStatusCodes.getStatusCodeString(resultCode)), Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


            Log.d(TAG, "entrou no onNavigationItemSelected");

            switch (menuItem.getItemId()){
                case R.id.nav_carrinho:
                    Log.d(TAG, "entrou no case nav carrinho");

                    Toast.makeText(this, "Clicou no navhome", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ProdutosActivity.this, CarrinhoActivity.class));
                    break;

                case R.id.nav_add_user:

                    Toast.makeText(this, "Clicou no add user", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ProdutosActivity.this, SignupActivity.class));
                    break;
            }

            return true;
    }



}
