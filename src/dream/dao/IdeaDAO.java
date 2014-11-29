package dream.dao;


	import java.util.ArrayList;
	import java.util.List;

	import org.hibernate.HibernateException;
	import org.hibernate.Session;
	import org.hibernate.Transaction;

	import Entidad.Idea;
	import dream.util.HibernateUtil;


	public class IdeaDAO {

	    private Session sesion; 
	    private Transaction tx;  
	    
	    Idea idea;
	    List<Idea> listas;
	    
	    public IdeaDAO() {
	        super();
	        listas = new ArrayList<Idea>();
	    }
	    
	    public void insertar(Idea idea) {
		    iniciarOperacion(); 
		    sesion.save(idea); 
		    tx.commit(); 
		    sesion.close();
	    }

	    @SuppressWarnings("unchecked")
		public List<Idea> consultarTodo() {
	        listas.clear();
	        iniciarOperacion();
	        listas = sesion.createQuery("from Idea").list();
	        sesion.close();

	        return listas;
	    }

	    public Idea consultarId(int id) {
	        idea = new Idea();
	        iniciarOperacion();
	        idea = (Idea)sesion.get(Idea.class, id);
	        sesion.close();
	        return idea;  //NO funciona   
	    }

	    public void actualizar(Idea idea) { 
	        iniciarOperacion();
	        sesion.update(idea);
	        tx.commit();
	        sesion.close();
	    }

	    public void eliminar(Idea idea) {
	        iniciarOperacion();
	        sesion.delete(idea);
	        tx.commit();
	        sesion.close();
	    }
	    
	    public List<Idea> getIdesPorHashtag(String nombre) {
	        listas.clear();
	        iniciarOperacion();
	        listas = sesion.createQuery("select h.idea from Hashtag h where h.nombre = "+nombre.toLowerCase()).list();
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
