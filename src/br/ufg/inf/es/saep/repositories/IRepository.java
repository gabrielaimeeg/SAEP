package br.ufg.inf.es.saep.repositories;

public interface IRepository<T> {

    T GetById(int id);

    void update(T entity);

    void create(T entity);

    void delete(T entity);

}
