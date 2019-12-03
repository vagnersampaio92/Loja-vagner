package br.edu.ifsul.loja.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

import br.edu.ifsul.loja.R;
import br.edu.ifsul.loja.model.Pedido;
import br.edu.ifsul.loja.model.User;

import static android.support.constraint.Constraints.TAG;

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
            convertView = LayoutInflater.from(context).inflate(R.layout.listapedidos_adapter, parent, false);
            holder = new  ListaPedidosAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ListaPedidosAdapter.ViewHolder) convertView.getTag();
        }

        Pedido pedido = getItem(position);
        Log.d(TAG, "pedido"+ pedido);
        holder.tvValor.setText(NumberFormat.getCurrencyInstance().format(pedido.getTotalPedido()));
        holder.tvData.setText(pedido.getDataCriacao());
        holder.tvkey.setText(pedido.getKey());

        return convertView;
    }



    private class ViewHolder {
        final TextView tvData;
        final TextView tvkey;
        final TextView tvValor;



        public ViewHolder(View view) {
            tvData = view.findViewById(R.id.tvData);
            tvkey = view.findViewById(R.id.tvKey);
            tvValor = view.findViewById(R.id.tvValor);
        }
    }
}
