package br.ufg.inf.es.saep.sandbox.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bson.Document;

import java.util.List;

public class ParecerPersistencia implements ParecerRepository {
    IPersistencia mongoPersistencia = MongoPersistencia.getInstance();
    GsonBuilder gsonBuilder;
    static Gson gson;
    String parecerCollection = "parecerCollection";
    String radocCollection = "radocCollection";

    public ParecerPersistencia() {
        mongoPersistencia.iniciaConexaoBD();
        gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Nota.class, new SerializadorNota());
        gson = gsonBuilder.create();
    }

    @Override
    public void adicionaNota(String parecer, Nota nota) {
        Document document = mongoPersistencia.busca(parecerCollection, "id", parecer);

        if (document == null)
            throw new IdentificadorDesconhecido("Objeto Nota não encontrado no parecer " + parecer);

        Parecer parecerAntigaInstacia = gson.fromJson(document.toJson(), Parecer.class);

        List<Nota> listaNotas = parecerAntigaInstacia.getNotas();
        listaNotas.add(nota);

        Parecer parecerNovaInstancia = new Parecer(
                parecerAntigaInstacia.getId(),
                parecerAntigaInstacia.getResolucao(),
                parecerAntigaInstacia.getRadocs(),
                parecerAntigaInstacia.getPontuacoes(),
                parecerAntigaInstacia.getFundamentacao(),
                listaNotas
        );

        String json = gson.toJson(parecerNovaInstancia);

        mongoPersistencia.atualiza(parecerCollection, "id", parecer, json);

    }

    @Override
    public void removeNota(String id, Avaliavel original) {
        Document parecer = mongoPersistencia.busca(parecerCollection, "id", id);
        Document filtro = new Document("$pull", new Document("notas", new Document("original", Document.parse(gson.toJson(original)))));

        mongoPersistencia.atualizaDocumentUsandoFiltro(parecerCollection, parecer, filtro);
    }

    @Override
    public void persisteParecer(Parecer parecer) {
        Document document = mongoPersistencia.busca(parecerCollection, "id", parecer.getId());

        if (document != null)
            throw new IdentificadorExistente("Já existe um parecer com esse identificador!");


        String json = gson.toJson(parecer);
        mongoPersistencia.persiste(parecerCollection, json);
    }

    @Override
    public void atualizaFundamentacao(String parecer, String fundamentacao) {
        Document document = mongoPersistencia.busca(parecerCollection, "id", parecer);

        if (document == null)
            throw new IdentificadorDesconhecido("Não existe um parecer com este identificador.");


        Parecer parecerAntigaInstacia = gson.fromJson(document.toJson(), Parecer.class);

        Parecer parecerNovaInstancia = new Parecer(
                parecerAntigaInstacia.getId(),
                parecerAntigaInstacia.getResolucao(),
                parecerAntigaInstacia.getRadocs(),
                parecerAntigaInstacia.getPontuacoes(),
                fundamentacao,
                parecerAntigaInstacia.getNotas()
        );

        String json = gson.toJson(parecerNovaInstancia);

        mongoPersistencia.atualiza(parecerCollection, "id", parecer, json);

    }

    @Override
    public Parecer byId(String id) {
        Document document = mongoPersistencia.busca(parecerCollection, "id", id);

        if (document == null)
            return null;

        document = mongoPersistencia.busca(parecerCollection, "id", id);
        return gson.fromJson(gson.toJson(document), Parecer.class);
    }

    @Override
    public void removeParecer(String id) {
        mongoPersistencia.deleta(parecerCollection, "id", id);
    }

    @Override
    public Radoc radocById(String identificador) {
        Document document = mongoPersistencia.busca(radocCollection, "id", identificador);

        if (document == null)
            return null;

        document = mongoPersistencia.busca(radocCollection, "id", identificador);
        return gson.fromJson(gson.toJson(document), Radoc.class);

    }

    @Override
    public String persisteRadoc(Radoc radoc) {
        Document document;

        document = mongoPersistencia.busca(radocCollection, "id", radoc.getId());

        if (document != null)
            throw new IdentificadorExistente("Radoc de id " + radoc.getId() + " já existe!");

        String json = gson.toJson(radoc);
        mongoPersistencia.persiste(radocCollection, json);

        document = mongoPersistencia.busca(radocCollection, "id", radoc.getId());
        if (document != null) {
            return gson.fromJson(gson.toJson(document), Radoc.class).getId();
        }
        return null;

    }

    @Override
    public void removeRadoc(String identificador) {
        if (!validaSeExisteParecerReferenciaRadoc(identificador)) {
            mongoPersistencia.deleta(radocCollection, "id", identificador);
        } else {
            throw new ExisteParecerReferenciandoRadoc("Existe um parecer referenciando o radoc " + identificador);
        }
    }

    private boolean validaSeExisteParecerReferenciaRadoc(String identificador) {

        if (mongoPersistencia.busca(parecerCollection, "id", identificador) == null)
            return false;
        return true;
    }
}
