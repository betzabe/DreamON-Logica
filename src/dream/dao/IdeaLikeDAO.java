package dream.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import Entidad.IdeaLike;
import dream.util.HibernateUtil;


public class IdeaLikeDAO {

    private Session sesion; 
    private Transaction tx;  
    
    IdeaLike ideaLike;
    List<IdeaLike> listas;
    
    public IdeaLikeDAO() {
        super();
        listas = new ArrayList<IdeaLike>();
    }
    
    public void insertar(IdeaLike ideaLike) {
	    iniciarOperacion(); 
	    sesion.save(ideaLike); 
	    tx.commit(); 
	    sesion.close();
    }

    @SuppressWarnings("unchecked")
	public List<IdeaLike> consultarTodo() {
        listas.clear();
        iniciarOperacion();
        listas = sesion.createQuery("from IdeaLike").list();
        sesion.close();

        return listas;
    }

    public IdeaLike consultarId(int id) {
        ideaLike = new IdeaLike();
        iniciarOperacion();
        ideaLike = (IdeaLike)sesion.get(IdeaLike.class, id);
        sesion.close();
        return ideaLike;  //NO funciona   
    }

    public void actualizar(IdeaLike ideaLike) { 
        iniciarOperacion();
        sesion.update(ideaLike);
        tx.commit();
        sesion.close();
    }

    public void eliminar(IdeaLike ideaLike) {
        iniciarOperacion();
        sesion.delete(ideaLike);
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