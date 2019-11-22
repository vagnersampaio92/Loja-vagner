package br.edu.ifsul.loja.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
import br.edu.ifsul.loja.adapter.ClientesAdapter;
import br.edu.ifsul.loja.barcode.BarcodeCaptureActivity;
import br.edu.ifsul.loja.model.Cliente;
import br.edu.ifsul.loja.model.Produto;
import br.edu.ifsul.loja.setup.AppSetup;

public class ClientesActivity extends AppCompatActivity {

    private ListView lvClientes;
    private static final String TAG = "clientesActivity";
    private List<Cliente> clientes;
    private static final int RC_BARCODE_CAPTURE = 9001;
    private List<Cliente> clientesTemp = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        //ativa o botão home na actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvClientes = findViewById(R.id.lv_clientes);
        lvClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                confirmaSelecaoCliente(position);
            }
        });

        //obtém a instância do database e a referência para o nó
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("vendas/cliente");

        //Lê os dados na referência
        myRef.orderByChild("nome").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //imprime os dados originais no LogCat (veja que eles chegam na ordem de criação dos nós)
                Log.d(TAG, "Value is: " + dataSnapshot.getValue());

                clientes = new ArrayList<>();
//                AppSetup.listProdutos = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Cliente cliente = ds.getValue(Cliente.class);
                    cliente.setKey(ds.getKey());
                    cliente.setIndex(clientes.size());
                    clientes.add(cliente);
                }

                //carrega os dados na View
                lvClientes.setAdapter(new ClientesAdapter(ClientesActivity.this, clientes));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.menuitem_barcode:
                Intent intent = new Intent(ClientesActivity.this, BarcodeCaptureActivity.class);
                intent.putExtra(BarcodeCaptureActivity.AutoFocus, true); //true liga a funcionalidade autofoco
                intent.putExtra(BarcodeCaptureActivity.UseFlash, false); //true liga a lanterna (fash)
                startActivityForResult(intent, RC_BARCODE_CAPTURE);
                break;
        }

        return true;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    //Toast.makeText(this, barcode.displayValue, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);

                    boolean flag = true;
                    for (Cliente cliente : clientes) {
                        if (String.valueOf(cliente.getCodigoDeBarras()).equals(barcode.displayValue)) {
                            flag = false;
//                            Intent intent = new Intent(ClientesActivity.this, ProdutoDetalheActivity.class);
//                            intent.putExtra("position", cliente.getIndex());
//                            startActivity(intent);
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

    private void confirmaSelecaoCliente(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //adiciona um título e uma mensagem
        builder.setTitle(R.string.titulo_cliente_selecionado);
        final Cliente cliente;
        cliente = clientes.get((clientes.get(position).getIndex()));
        builder.setMessage(getString(R.string.hint_nome) + ": " + cliente.getNome() + " " + cliente.getSobrenome()
                + "\nCPF: " + cliente.getCpf());
        //adiciona os botões
        builder.setPositiveButton(R.string.alert_sim, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppSetup.cliente = cliente;
                Toast.makeText(ClientesActivity.this, getString(R.string.toast_cliente_selecionado), Toast.LENGTH_SHORT).show();
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
