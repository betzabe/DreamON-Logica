package dream.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import Entidad.IdeaSeguidor;
import dream.util.HibernateUtil;


public class IdeaSeguidorDAO {

    private Session sesion; 
    private Transaction tx;  
    
    IdeaSeguidor ideaSeguidor;
    List<IdeaSeguidor> listas;
    
    public IdeaSeguidorDAO() {
        super();
        listas = new ArrayList<IdeaSeguidor>();
    }
    
    public void insertar(IdeaSeguidor ideaSeguidor) {
	    iniciarOperacion(); 
	    sesion.save(ideaSeguidor); 
	    tx.commit(); 
	    sesion.close();
    }

    @SuppressWarnings("unchecked")
	public List<IdeaSeguidor> consultarTodo() {
        listas.clear();
        iniciarOperacion();
        listas = sesion.createQuery("from IdeaSeguidor").list();
        sesion.close();

        return listas;
    }

    public IdeaSeguidor consultarId(int id) {
        ideaSeguidor = new IdeaSeguidor();
        iniciarOperacion();
        ideaSeguidor = (IdeaSeguidor)sesion.get(IdeaSeguidor.class, id);
        sesion.close();
        return ideaSeguidor;  //NO funciona   
    }

    public void actualizar(IdeaSeguidor ideaSeguidor) { 
        iniciarOperacion();
        sesion.update(ideaSeguidor);
        tx.commit();
        sesion.close();
    }

    public void eliminar(IdeaSeguidor ideaSeguidor) {
        iniciarOperacion();
        sesion.delete(ideaSeguidor);
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