package com.lvaleromsw.swcine.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.jdo.PersistenceManager;

import com.lvaleromsw.swcine.persistence.Movie;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.apphosting.api.DatastorePb.Query;

public class MovieDAO {

	private static MovieDAO instance = null;
	
	private MovieDAO(){}
	
	public static MovieDAO getInstance(){
		if(instance == null){
			instance = new MovieDAO();
		}
		return instance;
	}
	
	public boolean createMovie(Movie mov){
		
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		
		String query = "select from "+Movie.class.getName()
					+" where realMovieTitle == '"+mov.getRealMovieTitle()
					+"' && director == '"+mov.getDirector()
					+"' && date == '"+mov.getDate()+"'";
		try{
			
			@SuppressWarnings("unchecked")
			List<Movie> list = (List<Movie>) pm.newQuery(query).execute();
			if(list.isEmpty()){
				pm.makePersistent(mov);
				return true;
			}
			
		}finally{
			pm.close();
		}
		return false;
	}
	
	public List<Movie> getMovies(){
		
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		
		String query = "select from "+Movie.class.getName()+" order by realMovieTitle";
		
		try{
			
			@SuppressWarnings("unchecked")
			List<Movie> list = (List<Movie>) pm.newQuery(query).execute();
			
			if(list.isEmpty())
				return null;
			else
				return list;
			
		}finally{
			pm.close();
		}
	}

	public List<Movie> getMovies(String letter){
		
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		
		//String filter = "realMovieTitle >= '"+letter+"' && realMovieTitle < '"+letter+"' \uFFFD";
		
		String query = "select from "+Movie.class.getName()+
				//" where "+filter+
				" order by realMovieTitle" ;
		try{
			
			@SuppressWarnings("unchecked")
			List<Movie> list = (List<Movie>) pm.newQuery(query).execute();
			
			if(list.isEmpty())
				return null;
			else{
				List<Movie> result = new ArrayList<Movie>();
				for(int i = 0; i < list.size(); i++){
					if(list.get(i).getRealMovieTitle().startsWith(letter)){
						result.add(list.get(i));
					}
				}
				if(result.isEmpty()) return null;
				return result;
			}
			
		}finally{
			pm.close();
		}
	}
	
	public List<Movie> searchMovies(String s){
PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		
		//String filter = "realMovieTitle >= '"+letter+"' && realMovieTitle < '"+letter+"' \uFFFD";
		
		String query = "select from "+Movie.class.getName()+
				//" where "+filter+
				" order by realMovieTitle" ;
		try{
			
			@SuppressWarnings("unchecked")
			List<Movie> list = (List<Movie>) pm.newQuery(query).execute();
			
			if(list.isEmpty())
				return null;
			else{
				List<Movie> result = new ArrayList<Movie>();
				for(int i = 0; i < list.size(); i++){
					if(list.get(i).getRealMovieTitle().contains(s)){
						result.add(list.get(i));
					}
				}
				if(result.isEmpty()) return null;
				return result;
			}
			
		}finally{
			pm.close();
		}
	}
	
	public Movie getMovie(long key){
		
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		
		//String query = "select from "+Movie.class.getName()+" where key == '"+KeyFactory.createKey(Movie.class.getName(),key)+"'";
		
		Movie mov = null;
		
		try{
			
			Key k = KeyFactory.createKey(Movie.class.getSimpleName(), key);
			
			mov = pm.getObjectById(Movie.class,k);
			/*List<Movie> list = (List<Movie>) pm.newQuery(query).execute();
			
			System.out.println("despues de la query");
			
			if(list.size() == 1){
				return list.get(0);
			}else{
				return null;
			}*/
			
		}catch(Exception e){
			
		}finally{
			pm.close();
		}
		return mov;
	}
	
	public boolean addComment(String comment,String username,long key){
		
		PersistenceManager pm = PMF.getInstance().getPersistenceManager();
		
		Key k = KeyFactory.createKey(Movie.class.getSimpleName(),key);
		
		Movie mov = null;
		
		try{
			
			mov = pm.getObjectById(Movie.class,k);
			
			if(mov == null)
				return false;
			else{
				//pm.deletePersistent(mov);
				
				Vector<String> comments = mov.getComments();
				comments.add(comment);
				comments.add(username);
				mov.setComments(comments);
				
				//pm.makePersistent(mov);
				//pm.flush();
				
				return true;
			}
			
		}finally{
			pm.close();
		}
	}
}
