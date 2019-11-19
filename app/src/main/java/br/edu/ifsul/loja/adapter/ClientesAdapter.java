package br.edu.ifsul.loja.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ifsul.loja.R;
import br.edu.ifsul.loja.model.Cliente;

public class ClientesAdapter extends ArrayAdapter<Cliente> {

    private static final String TAG = "clientesAdapter";
    private Context context;


    public ClientesAdapter(@NonNull Context context, @NonNull List<Cliente> clientes) {
        super(context, 0, clientes);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //declara o objeto que irá segurar os objetos escaneados da view
        final ViewHolder holder;
        //infla a view
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.clientes_adapter, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder); //anexa à view o holder
        }else{
            holder = (ViewHolder) convertView.getTag(); //pega da view o holder
        }

        //vincula os dados do objeto de modelo à view
        final Cliente cliente = getItem(position); //devolve o objeto do modelo
        holder.tvNome.setText(cliente.getNome() + " " + cliente.getSobrenome());
        holder.tvDetalhes.setText("Código: " + cliente.getCodigoDeBarras() + "\nCPF.: " + cliente.getCpf());
        holder.pbFotoCliente.setVisibility(ProgressBar.VISIBLE);
        holder.imvFoto.setImageResource(R.drawable.img_cliente_icon_524x524);
        if(cliente.getUrl_foto().equals("")){
            holder.pbFotoCliente.setVisibility(ProgressBar.INVISIBLE);
        }else{
            //faz o download da foto do cliente aqui
        }

        return convertView;
    }

    /*
        A classe ViewHolder irá segurar os objetos escaneados da view (isso acelera o
        processamento dos cartões).
     */
    private class ViewHolder {

        final TextView tvNome;
        final TextView tvDetalhes;
        final ImageView imvFoto;
        final ProgressBar pbFotoCliente;

        public ViewHolder(View view) {
            //mapeia os componentes da UI para vincular os dados do objeto de modelo
            tvNome = view.findViewById(R.id.tvNomeClienteAdapter);
            tvDetalhes = view.findViewById(R.id.tvDetalhesDoClienteAdapater);
            imvFoto = view.findViewById(R.id.imvFotoDoClienteAdapter);
            pbFotoCliente = view.findViewById(R.id.pb_foto_cliente_adapter);
        }

    }
}
