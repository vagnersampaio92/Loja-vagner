
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
        import br.edu.ifsul.loja.model.ItemPedido;
        import br.edu.ifsul.loja.model.Pedido;
        import br.edu.ifsul.loja.model.User;

        public class CarrinhoAdapter extends ArrayAdapter<ItemPedido> {

            private final Context context;

            public CarrinhoAdapter(@NonNull Context context, @NonNull List<ItemPedido> itenspedido){
                super(context, 0, itenspedido);
                this.context = context;
            }



            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent ){
                final CarrinhoAdapter.ViewHolder holder;
                if(convertView == null){
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_carrinho_adapter, parent, false);
                    holder = new  CarrinhoAdapter.ViewHolder(convertView);
                    convertView.setTag(holder);

                }else{
                    holder = (CarrinhoAdapter.ViewHolder) convertView.getTag();
                }
//                Pedido pedido = getItem(position);



                return convertView;
            }



            private class ViewHolder {
                final TextView Nome;
//                imvFotoProdutoCarrinhoAdapter

                final TextView quantidade;
                final TextView Total;


                public ViewHolder(View view) {
                    quantidade = view.findViewById(R.id.tvQuantidadeDeProdutoCarrinhoAdapater);
                    Nome = view.findViewById(R.id.tvNomeProdutoCarrinhoAdapter);
                    Total = view.findViewById(R.id.tvTotalItemCarrinhoAdapter);
                }
            }
        }

