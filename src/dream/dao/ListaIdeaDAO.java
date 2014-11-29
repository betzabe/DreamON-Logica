package dream.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import Entidad.ListaIdea;
import dream.util.HibernateUtil;


public class ListaIdeaDAO {

    private Session sesion; 
    private Transaction tx;  
    
    ListaIdea listaIdea;
    List<ListaIdea> listas;
    
    public ListaIdeaDAO() {
        super();
        listas = new ArrayList<ListaIdea>();
    }
    
    public void insertar(ListaIdea listaIdea) {
	    iniciarOperacion(); 
	    sesion.save(listaIdea); 
	    tx.commit(); 
	    sesion.close();
    }

    @SuppressWarnings("unchecked")
	public List<ListaIdea> consultarTodo() {
        listas.clear();
        iniciarOperacion();
        listas = sesion.createQuery("from ListaIdea").list();
        sesion.close();

        return listas;
    }

    public ListaIdea consultarId(int id) {
        listaIdea = new ListaIdea();
        iniciarOperacion();
        listaIdea = (ListaIdea)sesion.get(ListaIdea.class, id);
        sesion.close();
        return listaIdea;  //NO funciona   
    }

    public void actualizar(ListaIdea listaIdea) { 
        iniciarOperacion();
        sesion.update(listaIdea);
        tx.commit();
        sesion.close();
    }

    public void eliminar(ListaIdea listaIdea) {
        iniciarOperacion();
        sesion.delete(listaIdea);
        tx.commit();
        sesion.close();
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