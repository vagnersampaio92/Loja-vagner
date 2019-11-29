package br.edu.ifsul.loja.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.ifsul.loja.R;
import br.edu.ifsul.loja.model.Pedido;
import br.edu.ifsul.loja.model.User;

public class ListaPedidosAdapter extends ArrayAdapter<Pedido> {

    private final Context context;

    public ListaPedidosAdapter(@NonNull Context context, @NonNull List<Pedido> pedidos){
        super(context, 0, pedidos);
        this.context = context;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent ){
        final ListaPedidosAdapter.ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.user_adapter, parent, false);
            holder = new  ListaPedidosAdapter.ViewHolder(convertView);
            convertView.setTag(holder);

        }else{
            holder = (ListaPedidosAdapter.ViewHolder) convertView.getTag();
        }



        return convertView;
    }



    private class ViewHolder {
        final TextView tvNome;
        final TextView tvSobrenome;
        // final TextView tvFuncao;
        final TextView tvCodigo;
        final TextView tvEmail;


        public ViewHolder(View view) {
            tvNome = view.findViewById(R.id.tvNomeUserAdapter);
            tvSobrenome = view.findViewById(R.id.tvSobrenomeUserAdapter);
            //  tvFuncao = view.findViewById(R.id.tvFuncaoUserAdapter2);
            tvEmail = view.findViewById(R.id.tvEmailUserAdapter);
            tvCodigo = view.findViewById(R.id.tvCodigoUserAdapter);
        }
    }
}
