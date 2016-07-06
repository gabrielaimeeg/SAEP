package test.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.*;
import br.ufg.inf.es.saep.sandbox.persistencia.IPersistencia;
import br.ufg.inf.es.saep.sandbox.persistencia.MongoPersistencia;
import br.ufg.inf.es.saep.sandbox.persistencia.ParecerPersistencia;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParecerPersistenciaTest {
    IPersistencia banco = new MongoPersistencia();
    ParecerPersistencia parecerPersistencia = new ParecerPersistencia();
    static Gson gson = new Gson();

    String json = "{\"id\":\"1\",\"resolucao\":\"2\",\"radocs\":[\"1\",\"2\",\"3\"],\"pontuacoes\":[{\"atributo\":\"nomePontuacao1\",\"valor\":{\"real\":0.0,\"logico\":false,\"string\":\"valor1\"}},{\"atributo\":\"nomePontuacao2\",\"valor\":{\"real\":0.0,\"logico\":false,\"string\":\"valor2\"}}],\"fundamentacao\":\"fundamentação aqui\",\"notas\":[{\"original\":{\"tipo\":\"tipoRelato1\",\"valores\":{\"tipoRelato1\":{\"real\":0.0,\"logico\":false,\"string\":\"teste2\"}}},\"novo\":{\"tipo\":\"tipoRelato2\",\"valores\":{\"tipoRelato2\":{\"real\":0.0,\"logico\":false,\"string\":\"teste2\"}}},\"justificativa\":\"Porque eu quis.\"}]}";

    public ParecerPersistenciaTest() {
        banco.iniciaConexaoBD();
    }

    @After
    @Before
    public void limpaBase() {
        banco.limpaBase("parecerCollectionTeste");
    }


    @Test
    public void testaAdicionarNota() {

    }

    @Test
    public void testaRemoveNota() {

    }

    @Test
    public void testaPersisteParecer() {
        Parecer parecer = criaParecer();
        parecerPersistencia.persisteParecer(parecer);

        banco.persisteJSON("parecerCollectionTeste", gson.toJson(parecer));
        Assert.assertEquals(json, banco.buscaJSON("parecerCollectionTeste", "1").toJson());

    }

    @Test
    public void testaById() {
        Parecer parecerDesejado = criaParecer();
        Parecer parecerBuscado;
        parecerPersistencia.persisteParecer(parecerDesejado);
        parecerBuscado = parecerPersistencia.byId("1");

        Assert.assertEquals(parecerDesejado.getId(), parecerBuscado.getId());
        Assert.assertEquals(parecerDesejado.getRadocs(), parecerBuscado.getRadocs());
        Assert.assertEquals(parecerDesejado.getPontuacoes(), parecerBuscado.getPontuacoes());
        Assert.assertEquals(parecerDesejado.getFundamentacao(), parecerBuscado.getFundamentacao());
        Assert.assertEquals(parecerDesejado.getResolucao(), parecerBuscado.getResolucao());
        Assert.assertEquals(parecerDesejado.getNotas(), parecerBuscado.getNotas());

    }

    @Test
    public void testaRemoveParecer() {

    }

    @Test
    public void testaRadocById() {

    }

    @Test
    public void testaPersisteRadoc() {

    }

    @Test
    public void testaRemoveRadoc() {

    }

    public Parecer criaParecer() {
        return new Parecer(
                "1",
                "2",
                criaListaRadocID("1", "2", "3"),
                criaListaPontuacao("nomePontuacao1", "valor1", "nomePontuacao2", "valor2"),
                "fundamentação aqui",
                criaListaNota()
        );

    }

    private List<String> criaListaRadocID(String... valores) {
        List<String> listaRadocId = new ArrayList<>();

        for (String i : valores) {
            listaRadocId.add(i);
        }

        return listaRadocId;
    }

    private List<Pontuacao> criaListaPontuacao(String... valores) {

        if (valores.length % 2 == 0) {
            List<Pontuacao> listaPontuacoes = new ArrayList<>();

            for (int i = 0; i < valores.length - 1; i++) {
                listaPontuacoes.add(new Pontuacao(valores[i], new Valor(valores[i + 1])));
                i++;
            }

            return listaPontuacoes;
        } else {
            return null;
        }
    }

    private Nota criaNota() {
        return new Nota(criaRelato("tipoRelato1"), criaRelato("tipoRelato2"), "Porque eu quis.");
    }


    private List<Nota> criaListaNota() {
        List<Nota> listaNota = new ArrayList<>();
        listaNota.add(criaNota());
        return listaNota;
    }

    private Relato criaRelato(String tipoRelato) {
        Map<String, Valor> valores = new HashMap<>();
        Valor valorTeste1 = new Valor("teste1");
        Valor valorTeste2 = new Valor("teste2");

        valores.put(tipoRelato, valorTeste1);
        valores.put(tipoRelato, valorTeste2);

        return new Relato(tipoRelato, valores);

    }

}
