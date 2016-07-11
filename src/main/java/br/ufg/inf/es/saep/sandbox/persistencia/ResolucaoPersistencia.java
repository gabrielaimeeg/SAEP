package br.ufg.inf.es.saep.sandbox.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.dominio.ResolucaoRepository;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import com.google.gson.Gson;
import org.bson.Document;

import java.util.List;

public class ResolucaoPersistencia implements ResolucaoRepository {

    IPersistencia mongoPersistencia = MongoPersistencia.getInstance();
    static Gson gson = new Gson();
    String resolucaoCollection = "resolucaoCollection";
    String tipoCollection = "tipoCollection";

    private void limpaBase(String collection) {
        mongoPersistencia.limpaBase(collection);
    }

    @Override
    public Resolucao byId(String id) {
        Document document = mongoPersistencia.buscaJSON(resolucaoCollection, "id", id);
        return gson.fromJson(document.toJson(), Resolucao.class);
    }

    @Override
    public String persiste(Resolucao resolucao) {
        String json = gson.toJson(resolucao);
        mongoPersistencia.persisteJSON(resolucaoCollection, json);
        return resolucao.getId();
    }

    @Override
    public boolean remove(String identificador) {
        boolean result = false;
        try {
            mongoPersistencia.deletaJSON(tipoCollection, "id", identificador);
            result = true;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return result;
    }

    @Override
    public List<String> resolucoes() {
        return mongoPersistencia.pegaIdsCollection(resolucaoCollection, "id");
    }

    @Override
    public void persisteTipo(Tipo tipo) {
        String json = gson.toJson(tipo);
        mongoPersistencia.persisteJSON(tipoCollection, json);
    }

    @Override
    public void removeTipo(String codigo) {
        mongoPersistencia.deletaJSON(tipoCollection, "id", codigo);
    }

    @Override
    public Tipo tipoPeloCodigo(String codigo) {
        Document document = mongoPersistencia.buscaJSON(tipoCollection, "id", codigo);
        return gson.fromJson(document.toJson(), Tipo.class);
    }

    @Override
    public List<Tipo> tiposPeloNome(String nome) {
        return null;

    }
}
