package br.ufg.inf.es.saep.sandbox.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.dominio.ResolucaoRepository;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import com.google.gson.Gson;

import java.util.List;

public class ResolucaoPersistencia implements ResolucaoRepository {
    IPersistencia iPersistencia = new MongoPersistencia();
    static Gson gson = new Gson();
    String parecerCollection = "resolucaoCollection";
    String radocCollection = "tipoCollection";

    @Override
    public Resolucao byId(String s) {
        return null;
    }

    @Override
    public String persiste(Resolucao resolucao) {
        return null;
    }

    @Override
    public boolean remove(String identificador) {
        return false;
    }

    @Override
    public List<String> resolucoes() {
        return null;
    }

    @Override
    public void persisteTipo(Tipo tipo) {

    }

    @Override
    public void removeTipo(String codigo) {

    }

    @Override
    public Tipo tipoPeloCodigo(String codigo) {
        return null;
    }

    @Override
    public List<Tipo> tiposPeloNome(String nome) {
        return null;
    }
}
