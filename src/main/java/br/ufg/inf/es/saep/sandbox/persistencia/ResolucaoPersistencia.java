package br.ufg.inf.es.saep.sandbox.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.google.gson.Gson;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ResolucaoPersistencia implements ResolucaoRepository {

    IPersistencia mongoPersistencia = MongoPersistencia.getInstance();
    static Gson gson;
    String resolucaoCollection = "resolucaoCollection";
    String tipoCollection = "tipoCollection";

    public ResolucaoPersistencia() {
        mongoPersistencia.iniciaConexaoBD();
        gson = new Gson();
    }

    @Override
    public Resolucao byId(String id) {
        Document document = mongoPersistencia.busca(resolucaoCollection, "id", id);

        if (document == null)
            return null;

        document = mongoPersistencia.busca(resolucaoCollection, "id", id);
        return gson.fromJson(gson.toJson(document), Resolucao.class);
    }

    @Override
    public String persiste(Resolucao resolucao) {

        if (resolucao.getId() == null || resolucao.getId().equals("")) {
            throw new CampoExigidoNaoFornecido("id");
        }

        Document document = mongoPersistencia.busca(resolucaoCollection, "id", resolucao.getId());

        if (document != null)
            throw new IdentificadorExistente("Já existe uma resolucao com esse identificador!");


        String json = gson.toJson(resolucao);
        mongoPersistencia.persiste(resolucaoCollection, json);

        String resolucaoPersistida = gson.toJson(mongoPersistencia.busca(resolucaoCollection, "id", resolucao.getId()));
        if (resolucaoPersistida == null) {
            return null;
        }

        return resolucao.getId();
    }

    @Override
    public boolean remove(String identificador) {
        boolean result = false;
        try {
            mongoPersistencia.deleta(resolucaoCollection, "id", identificador);
            result = true;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return result;
    }

    @Override
    public List<String> resolucoes() {
        return mongoPersistencia.listaValoresDeCollection(resolucaoCollection, "id");
    }

    @Override
    public void persisteTipo(Tipo tipo) {

        Document document = mongoPersistencia.busca(tipoCollection, "id", tipo.getId());

        if (document != null)
            throw new IdentificadorExistente("Tipo com identificador " + tipo.getId() + " já existente.");

        String json = gson.toJson(tipo);
        mongoPersistencia.persiste(tipoCollection, json);
    }

    @Override
    public void removeTipo(String codigo) {

        if (mongoPersistencia.busca(resolucaoCollection, "regras.tipoRelato", codigo) != null)
            throw new ResolucaoUsaTipoException("Tipo usado em uma resolucao!");

        mongoPersistencia.deleta(tipoCollection, "id", codigo);
    }

    @Override
    public Tipo tipoPeloCodigo(String codigo) {
        Document document = mongoPersistencia.busca(tipoCollection, "id", codigo);
        return gson.fromJson(gson.toJson(document), Tipo.class);
    }

    @Override
    public List<Tipo> tiposPeloNome(String nome) {
        ArrayList<Tipo> listaTipo = new ArrayList<>();

        for (Document document : mongoPersistencia.encontraTodosDocumentsPorFiltro(tipoCollection, "nome", nome)) {
            String tipoJson = document.toJson();
            listaTipo.add(gson.fromJson(tipoJson, Tipo.class));
        }

        return listaTipo;


    }
}
