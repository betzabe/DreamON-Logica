package dream.dao;

package dream.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import Entidad.Categoria;
import dream.util.HibernateUtil;


public class CategoriaDAO {

    private Session sesion; 
    private Transaction tx;  
    
    Categoria categoria;
    List<Categoria> listas;
    
    public CategoriaDAO() {
        super();
        listas = new ArrayList<Categoria>();
    }
    
    public void insertar(Categoria categoria) {
	    iniciarOperacion(); 
	    sesion.save(categoria); 
	    tx.commit(); 
	    sesion.close();
    }

    @SuppressWarnings("unchecked")
	public List<Categoria> consultarTodo() {
        listas.clear();
        iniciarOperacion();
        listas = sesion.createQuery("from Categoria").list();
        sesion.close();

        return listas;
    }

    public Categoria consultarId(int id) {
        categoria = new Categoria();
        iniciarOperacion();
        categoria = (Categoria)sesion.get(Categoria.class, id);
        sesion.close();
        return categoria;  //NO funciona   
    }

    public void actualizar(Categoria categoria) { 
        iniciarOperacion();
        sesion.update(categoria);
        tx.commit();
        sesion.close();
    }

    public void eliminar(Categoria categoria) {
        iniciarOperacion();
        sesion.delete(categoria);
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