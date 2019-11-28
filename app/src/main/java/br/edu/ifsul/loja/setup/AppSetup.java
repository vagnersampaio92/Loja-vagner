package br.edu.ifsul.loja.setup;

import android.graphics.Bitmap;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ifsul.loja.model.Cliente;
import br.edu.ifsul.loja.model.ItemPedido;
import br.edu.ifsul.loja.model.Produto;
import br.edu.ifsul.loja.model.User;

public class AppSetup {
    public static List<Produto> listProdutos = new ArrayList<>();
    public static Cliente cliente = null;
    public static List<ItemPedido> carrinho = new ArrayList<>();
    public static User user = null;
    public static Cliente clienteAdm = null;
    public static Map<String, Bitmap> cacheProdutos =  new HashMap<>();
    public static Map<String, Bitmap> cacheClientes =  new HashMap<>();
    public static FirebaseAuth mAuth;
    public static ArrayList<ItemPedido> carrinholist = new ArrayList<>();
}
