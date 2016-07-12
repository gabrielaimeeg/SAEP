package test.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.*;
import br.ufg.inf.es.saep.sandbox.persistencia.IPersistencia;
import br.ufg.inf.es.saep.sandbox.persistencia.MongoPersistencia;
import br.ufg.inf.es.saep.sandbox.persistencia.ParecerPersistencia;
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
    String nomeCollectionParecer = "parecerCollection";
    String nomeCollectionRadoc = "radocCollection";

    public ParecerPersistenciaTest() {
        banco.iniciaConexaoBD();
    }

    @Before
    public void limpaBase() {
        banco.limpaBase(nomeCollectionParecer);
        banco.limpaBase(nomeCollectionRadoc);
    }


    @Test
    public void testaAdicionarNota() {
        Parecer parecer = criaParecer();
        parecerPersistencia.persisteParecer(parecer);
        int quantidadeAntigaDeNotas = parecerPersistencia.byId("1").getNotas().size();

        Nota nota = criaNota();
        parecerPersistencia.adicionaNota(parecer.getId(), nota);
        int quantidadeNovaDeNotas = parecerPersistencia.byId("1").getNotas().size();
        Assert.assertEquals(quantidadeAntigaDeNotas + 1, quantidadeNovaDeNotas);
    }

    @Test
    public void testaRemoveNota() {
        Parecer parecer = criaParecer();
        int quantidadeNotasAntes = parecer.getNotas().size();

        Nota nota = parecer.getNotas().get(0);
        parecerPersistencia.persisteParecer(parecer);
        parecerPersistencia.removeNota(parecer.getId(), nota.getItemOriginal());
        int quantidadeNotasDepois = parecerPersistencia.byId(parecer.getId()).getNotas().size();

        Assert.assertEquals(quantidadeNotasAntes - 1, quantidadeNotasDepois);

    }

    @Test
    public void testaPersisteParecer() {
        Parecer parecer = criaParecer();
        parecerPersistencia.persisteParecer(parecer);

        Assert.assertTrue(banco.busca(nomeCollectionParecer, "id", "1").get("id").equals("1"));
    }

    @Test
    public void testaById() {
        Parecer parecerDesejado = criaParecer();
        Parecer parecerBuscado;
        parecerPersistencia.persisteParecer(parecerDesejado);
        parecerBuscado = parecerPersistencia.byId("1");

        Assert.assertEquals(parecerDesejado.getId(), parecerBuscado.getId());

    }

    @Test
    public void testaRemoveParecer() {
        parecerPersistencia.persisteParecer(criaParecer());
        parecerPersistencia.removeParecer("1");

        Assert.assertNull(parecerPersistencia.byId("1"));
    }

    @Test
    public void testaRadocById() {
        Radoc radocDesejado = criaRadoc("1");
        Radoc radocBuscado;
        parecerPersistencia.persisteRadoc(radocDesejado);
        radocBuscado = parecerPersistencia.radocById("1");

        Assert.assertEquals(radocDesejado.getId(), radocBuscado.getId());

    }

    @Test
    public void testaPersisteRadoc() {
        parecerPersistencia.persisteRadoc(criaRadoc("1"));
        Assert.assertTrue(banco.busca(nomeCollectionRadoc, "id", "1").get("id").equals("1"));
    }

    @Test
    public void testaRemoveRadoc() {
        parecerPersistencia.persisteRadoc(criaRadoc("1"));
        parecerPersistencia.removeRadoc("1");

        Assert.assertNull(parecerPersistencia.radocById("1"));
    }

    private Parecer criaParecer() {
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

    private Radoc criaRadoc(String id) {
        List<Relato> listaRelato = new ArrayList<>();
        listaRelato.add(criaRelato("Relato1"));
        listaRelato.add(criaRelato("Relato2"));
        return new Radoc(id, 2016, listaRelato);
    }
}
