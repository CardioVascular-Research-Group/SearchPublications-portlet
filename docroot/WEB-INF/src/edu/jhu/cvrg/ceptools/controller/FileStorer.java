package edu.jhu.cvrg.ceptools.controller;

import java.io.Serializable;

public class FileStorer implements Serializable{
	
	String filename;
	String filelocation;
	String filetype;
	String localfilestore;
	private String panel;
	private String figure;
	private String description;
	private long filesize;
	private String figpandisplay;
	private static final long serialVersionUID = 1L;
	
	private int index;
	
	public FileStorer()
	{
		index = 0;
		filename = "";
		filetype = "";
		localfilestore = "";
		panel = description = figure = "";
		filesize = 0;
		figpandisplay="";
	}
	
	
	public void setFigpandisplay(String c)
	{
		figpandisplay = c;
	}
	
	public String getFigpandisplay()
	{
		return figpandisplay;
	}
	
	public long getFilesize()
	{
		return filesize;
	}
	
	public void setFilesize(long f)
	{
		filesize = f;
	}
	
    public String getPanel()
    {
    	return panel;
    }
    
    public String getFigure()
    {
    	return figure;
    }
    
    public String getDescription()
    {
    	return description;
    }
    
    public void setPanel(String p)
    {
    	panel = p;
    }
    
    public void setFigure(String f)
    {
    	figure = f;
    }
    
    public void setDescription(String d)
    {
    	description = d;
    }
	
	public int getIndex()
    {
      	return index;
    }
      
    public void setIndex(int i)
    {
      	this.index = i;
    }
      
    public String getLocalfilestore()
    {
    	return localfilestore;	  
    }
      
    public void setLocalfilestore(String lfs)
    {
    	this.localfilestore = lfs;
    }
      
	public void setFilename(String name)
	{
		this.filename = name;
	}
	
	public void setFilelocation(String location)
	{
		this.filelocation = location;
	}
	
	public String getFilename()
	{
		return filename;
	}
	
	public String getFilelocation()
	{
		return filelocation;
	}
	
	
	public String getFiletype()
	{
		return filetype;
	}
	
	public void setFiletype (String filetype)
	{
		this.filetype = filetype;
	}
	

}
