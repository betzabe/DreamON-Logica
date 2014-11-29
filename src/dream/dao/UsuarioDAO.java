package dream.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import Entidad.Usuario;
import Enum.Categoria;
import dream.util.HibernateUtil;


public class UsuarioDAO {

	private Session sesion; 
    private Transaction tx;  
    
    Usuario usuario;
    List<Usuario> listas;
    
    public UsuarioDAO() {
        super();
        listas = new ArrayList<Usuario>();
    }
    
    public void insertar(Usuario usuario) {
	    iniciarOperacion(); 
	    sesion.save(usuario); 
	    tx.commit(); 
	    sesion.close();
    }

    @SuppressWarnings("unchecked")
	public List<Usuario> consultarTodo() {
        listas.clear();
        iniciarOperacion();
        listas = sesion.createQuery("from Usuario").list();
        sesion.close();

        return listas;
    }

    public Usuario consultarId(int id) {
        usuario = new Usuario();
        iniciarOperacion();
        usuario = (Usuario)sesion.get(Usuario.class, id);
        sesion.close();
        return usuario;  //NO funciona   
    }

    public void actualizar(Usuario usuario) { 
        iniciarOperacion();
        sesion.update(usuario);
        tx.commit();
        sesion.close();
    }

    public void eliminar(Usuario usuario) {
        iniciarOperacion();
        sesion.delete(usuario);
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