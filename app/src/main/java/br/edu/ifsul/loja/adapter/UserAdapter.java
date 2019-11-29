
        package br.edu.ifsul.loja.adapter;

        import android.content.Context;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.ProgressBar;
        import android.widget.TextView;

        import java.util.List;

        import br.edu.ifsul.loja.R;
        import br.edu.ifsul.loja.model.Cliente;
        import br.edu.ifsul.loja.model.User;

public class UserAdapter extends ArrayAdapter<User> {

    private final Context context;

    public UserAdapter(@NonNull Context context, @NonNull List<User> users){
        super(context, 0, users);
        this.context = context;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent ){
        final UserAdapter.ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.user_adapter, parent, false);
            holder = new  UserAdapter.ViewHolder(convertView);
            convertView.setTag(holder);

        }else{
            holder = (UserAdapter.ViewHolder) convertView.getTag();
        }
        User user = getItem(position);
        holder.tvNome.setText(user.getNome());
        holder.tvSobrenome.setText(user.getSobrenome());
       // holder.tvFuncao.setText(user.getFuncao());
        holder.tvCodigo.setText(user.getCodigoDeBarras().toString());
        holder.tvEmail.setText(user.getEmail());


        return convertView;
    }



    class ViewHolder {
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

