package dataManager;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


public class DbConnection {

	private Context c;
	private String databaseName;
	
	SQLiteDatabase db;
	
	public DbConnection(Context _c,String _dname)
	{
		c=_c;
		databaseName=_dname;
	}
	
	public void openDatabase()
	{
		try{
			db=SQLiteDatabase.openDatabase(databaseName, null, SQLiteDatabase.CREATE_IF_NECESSARY);
		}
		catch(Exception e)
		{
			Toast.makeText(c, e.toString(), Toast.LENGTH_LONG).show();
			//System.out.println("bamboocha1");
		}
	
	}
	
	public void closeDatabase()
	{
		db.close();
	}
	
	public void insertDatabase(String table,String[] fields,String[] argvs)
	{
		
		String fieldStr=fields[0];
		
		for(int i=1;i<fields.length;i++)
		{
			fieldStr+=","+fields[i];
		}
		
		String argvsStr=argvs[0];
		
		for(int i=1;i<argvs.length;i++)
		{
			argvsStr+=","+argvs[i];
		}
		
		String command="insert into "+table+"("+fieldStr+") values ("+argvsStr+")"; 
		
		
		db.beginTransaction();		
		
		try
		{
			db.execSQL(command);
			db.setTransactionSuccessful();
		}
		catch(Exception e)
		{
			Toast.makeText(c, e.toString(), Toast.LENGTH_LONG).show();
			
		}
		finally
		{
			db.endTransaction();
		}
		
	}
	
	
	public void updateDatabase(String table,String[] fields,String[] argvs,String whereClause)
	{
		
		String setClause=fields[0]+"="+argvs[0];
		
		for(int i=1;i<fields.length;i++)
		{
			setClause+=", "+fields[i]+"="+argvs[i];
		}
		
		String command="UPDATE "+table+" SET "+setClause+" WHERE "+whereClause; 
		
		
		try
		{
			db.execSQL(command);
		}
		catch(Exception e)
		{
			Toast.makeText(c, e.toString(), Toast.LENGTH_LONG).show();
			
		}
		
		
	}
	
	
	
	
	public ArrayList<String> selectDatabase(String[] fields,String table)
	{	
		return selectDatabase(fields,table,null);
	}
	
	public ArrayList<String> selectDatabase(String field,String table,String whereClause)
	{
		String[] fields=new String[1];
		fields[0]=field;
		return selectDatabase(fields,table,whereClause);
	}
	
	public ArrayList<String> selectDatabase(String[] fields,String table,String whereClause)
	{
		ArrayList<String> list=new ArrayList<String>();
		
		String command="";
		
		
		String fieldStr=fields[0];
		
		for(int i=1;i<fields.length;i++)
		{
			fieldStr+=","+fields[i];
		}
		
		//prepare the raw query and do it
		if(whereClause==null)
			command="select "+fieldStr+" from "+table+";";
		else
		{
			command="select "+fieldStr+" from "+table+" where "+whereClause;
			
		}
		
		
		Cursor curs=null;
		
		//prepare the cursor
		try{
			curs=db.rawQuery(command,null);
			int n=curs.getColumnCount();
			
			
			//iterate through the cursor
			while(curs.moveToNext())
			{
				String result="";
				
				for(int i=0;i<n;i++)
					result+=curs.getString(i)+";;";
				
				list.add(result); //add every field in the future
				
			}
			
				
		}
		catch(Exception e){
			Toast.makeText(c, e.toString(), Toast.LENGTH_LONG).show();
			System.out.println("bamb");
			
		}
		finally
		{
			
			curs.close();
			curs.deactivate();
		}
		
		
		return list;
	}
		
}
