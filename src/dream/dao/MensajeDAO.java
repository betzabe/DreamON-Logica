package dream.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import Entidad.Mensaje;
import dream.util.HibernateUtil;


public class MensajeDAO {

    private Session sesion; 
    private Transaction tx;  
    
    Mensaje mensaje;
    List<Mensaje> listas;
    
    public MensajeDAO() {
        super();
        listas = new ArrayList<Mensaje>();
    }
    
    public void insertar(Mensaje mensaje) {
	    iniciarOperacion(); 
	    sesion.save(mensaje); 
	    tx.commit(); 
	    sesion.close();
    }

    @SuppressWarnings("unchecked")
	public List<Mensaje> consultarTodo() {
        listas.clear();
        iniciarOperacion();
        listas = sesion.createQuery("from Mensaje").list();
        sesion.close();

        return listas;
    }

    public Mensaje consultarId(int id) {
        mensaje = new Mensaje();
        iniciarOperacion();
        mensaje = (Mensaje)sesion.get(Mensaje.class, id);
        sesion.close();
        return mensaje;  //NO funciona   
    }

    public void actualizar(Mensaje mensaje) { 
        iniciarOperacion();
        sesion.update(mensaje);
        tx.commit();
        sesion.close();
    }

    public void eliminar(Mensaje mensaje) {
        iniciarOperacion();
        sesion.delete(mensaje);
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