package dream.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import Entidad.Hashtag;
import dream.util.HibernateUtil;


public class HashtagDAO {

    private Session sesion; 
    private Transaction tx;  
    
    Hashtag hashtag;
    List<Hashtag> listas;
    
    public HashtagDAO() {
        super();
        listas = new ArrayList<Hashtag>();
    }
    
    public void insertar(Hashtag hashtag) {
	    iniciarOperacion(); 
	    sesion.save(hashtag); 
	    tx.commit(); 
	    sesion.close();
    }

    @SuppressWarnings("unchecked")
	public List<Hashtag> consultarTodo() {
        listas.clear();
        iniciarOperacion();
        listas = sesion.createQuery("from Hashtag").list();
        sesion.close();

        return listas;
    }

    public Hashtag consultarId(int id) {
        hashtag = new Hashtag();
        iniciarOperacion();
        hashtag = (Hashtag)sesion.get(Hashtag.class, id);
        sesion.close();
        return hashtag;  //NO funciona   
    }

    public void actualizar(Hashtag hashtag) { 
        iniciarOperacion();
        sesion.update(hashtag);
        tx.commit();
        sesion.close();
    }

    public void eliminar(Hashtag hashtag) {
        iniciarOperacion();
        sesion.delete(hashtag);
        tx.commit();
        sesion.close();
    }
    
    @SuppressWarnings("unchecked")
    public List<Hashtag> consultarPorIdea(int idea_id) {
           listas.clear();
           iniciarOperacion();
           listas = sesion.createQuery("from Hashtag where idea.id = "+idea_id).list();
           sesion.close();

           return listas;
       }
    
    private void iniciarOperacion() throws HibernateException { 
        sesion = HibernateUtil.getSessionFactory().openSession(); 
        tx = sesion.beginTransaction(); 
    }
    
    private void manejaException(HibernateException he){
    	tx.rollback();
    	throw new HibernateException("Error de hibernate");
    }
}