package br.edu.ifsul.loja.setup;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsul.loja.model.Cliente;
import br.edu.ifsul.loja.model.ItemPedido;
import br.edu.ifsul.loja.model.Produto;
import br.edu.ifsul.loja.model.User;

public class AppSetup {
    public static User user = null;
    public static List<Produto> listProdutos = new ArrayList<>();
    public static Cliente cliente = null;
    public static ArrayList<ItemPedido> carrinho = new ArrayList<>();
}
