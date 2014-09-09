package edu.jhu.cvrg.ceptools.controller;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Publication implements Serializable{
	
	    private static final long serialVersionUID = 2L;
        private List<String> fauthors;
	    private List<String> lauthors;
	    private List<String> fullnames;
	    private String journalname;
	    private String journalvolume;
	    private String journalissue;
	    private String journalyear;
	    private String journalday;
	    private String journalmonth;
	    private String journalstartpg;
	    private String epubday;
	    private String epubmonth;
	    private String epubyear;
	    private String epubsum, epubsum2;
	    private String doi;
	    private String title;
	    private String theabstract;
	    private String year;
	    private boolean completed;  
	    private int pmid;
	    private int index;
	    private List<File> files;
	    private List<FileStorer> pubfiles;
	    private List<String> filenames;
	    private String authorfull;
	    private String userid;
	   

        
        public Publication()
        {
        	
        	fauthors = new ArrayList<String>();
        	lauthors = new ArrayList<String>();
        	fullnames = new ArrayList<String>();
        	files = new ArrayList<File>();
        	pubfiles = new ArrayList<FileStorer>();
        	filenames = new ArrayList<String>();
        	title = "";
        	theabstract = "";
        	year = "";
        	index = -1;
        	pmid = 0;
        	completed = false;
        	userid = "";
        	epubmonth = epubyear = epubday = doi = epubsum = epubsum2 = authorfull = "";
        	journalvolume = journalname = journalissue = journalyear = journalday = journalmonth = journalstartpg="";
        	
        }

        
        Publication(String t, String a, String y, List<String> fa, List<String> la, List<String> fn, String jv, String jn, String jy, String jm, String jd, String jsp, String ji, String epday, String epmonth, String epyear, String doi,String epubsum, String epubsum2, String authorfull, int pmid, int in)
        {
        	this.theabstract = a;
        	this.title = t;
        	this.year = y;
        	this.fauthors = fa;
        	this.lauthors = la;
        	this.fullnames = fn;
        	this.index = in;
        	this.pmid = pmid;
          	this.journalvolume = jv;
        	this.journalday = jd;
        	this.journalissue = ji;
        	this.journalmonth = jm;
        	this.journalname = jn;
        	this.journalstartpg = jsp;
        	this.journalyear = jy;
        	this.epubday = epday;
        	this.epubmonth = epmonth;
        	this.epubyear = epyear;
        	this.doi = doi;
        	this.epubsum = epubsum;
        	this.epubsum2 = epubsum2;
        	this.authorfull = authorfull;
        }
        
        
      public String getUserid()
      {
    	return userid;  
      }
      
      public void setUserid(String s)
      {
    	  userid = s;
      }
      public String getSubstring()
      {
    	  
    	  String currnames = "";
          int commacount = 0;
    	  boolean isfull = false;
    	  
    	  for(int i=0; i<authorfull.length() && isfull == false; i++)
    	  {
    		  
    		  if(authorfull.charAt(i) == ',')
    		  {
    		    commacount++;
	    		currnames += authorfull.charAt(i);
    		  
    		  }
    		  else
    		  { 
    			  currnames += authorfull.charAt(i);
    		  }
    		  
    		  if (commacount > 5)
    		  {
    			  isfull = true;
    		  }
    		 
    	  }
    	  
    	  if(isfull == true)
    	  {
    		  currnames += "...";
    	  }
    	  
    	  
    	  return currnames;
      }
      
      public void setCompleted(Boolean c)
      {
    	  completed = c;
      }
      public Boolean getCompleted()
      {
    	  return completed;
      }
        
        public int getIndex()
        {
        	return index;
        }
        
        public void setIndex(int i)
        {
        	this.index = i;
        }
        
        public void addFile(FileStorer curr)
        {
        	pubfiles.add(curr);
        }
        
        public List<FileStorer> getPubfiles()
        {
        	return pubfiles;
        }
        
        public void setPubfiles(List<FileStorer> c)
        {
        	pubfiles = c;
        }
        
      public void setFullnames(List<String> fullnames)
      {
    	  this.fullnames = fullnames;
      }
      
      public List<String> getFullnames()
      {
    	  return fullnames;
      }
        
      public void setAuthorfull(String f)
      {
    	  authorfull = f;
      }
	    public String getAuthorfull()
	    {
	    	return authorfull;
	    }
	   
	   public void setYear(String year )
	   {
		   this.year = year;
	   }
	   
	   public void setTitle(String title)
	   {
		   this.title = title;
	   }
	   
	   public void setAbstract(String theabstract )
	   {
		   this.theabstract = theabstract;
	   }
	   
	   public void setLauthors(List<String> lauthors )
	   {
		   this.lauthors = lauthors;
	   }
	   
	   public void setFauthors (List<String> fauthors )
	   {
		   this.fauthors  = fauthors;
	   }
	   
	   
	   public String getAbstract()
	   {
		  return theabstract; 
	   }
	   
	   public int getPmid()
	   {
		   return pmid;
	   }
	   
	   public void setPmid(int pmid)
	   {
		   this.pmid = pmid;
	   }
	
	   
	   public String getTitle()
	   {
		   return title;
	   }
	   
	   public String getYear()
	   {
		   return year;
	   }
	   
	   public List<String> getLauthors ()
	   {
		  return lauthors; 
	   }
    
      public List<String> getFauthors ()
	   {
		  return fauthors; 
	   }
	   

      public void setFilenames(List<String> filenames )
	   {
		   this.filenames = filenames;
	   }
      
      public void setFiles(List<File> files)
	   {
		   this.files = files;
	   }
      public List<String> getFilenames( )
	   {
		   return filenames;
	   }
      public List<File> getFiles( )
	   {
		   return files;
	   }
      public String getJournalname ()
      {
    	  return journalname;
      }
      
      public String getJournalissue ()
      {
    	return journalissue;  
      }
      public String getJournalday ()
      {
    	  return journalday;
      }
      public String getJournalmonth ()
      {
    	  return journalmonth;
      }
      public String getJournalyear ()
      {
    	  return journalyear;
      }
      public String getJournalstartpg ()
      {
    	  return journalstartpg;
      }
      public String getJournalvolume ()
      {
    	  return journalvolume;
      }
      
      public void setJournalname (String jn)
      {
    	  this.journalname = jn;
      }
      
      public void setJournalvolume (String jv)
      {
    	  this.journalvolume = jv;
      }
      
      public void setJournalissue (String ji)
      {
    	  this.journalissue = ji;
      }
      
      public void setJournalyear (String jy)
      {
    	  this.journalyear = jy;
      }
      
      public void setJournalmonth (String jm)
      {
    	  this.journalmonth = jm;
      }
      
      public void setJournalday (String jd)
      {
    	  this.journalday = jd;
      }
      
      
      public void setJournalstartpg (String jp)
      {
    	  this.journalstartpg = jp;
      }
	   
	   public void setEpubmonth (String epmonth)
	   {
		   this.epubmonth = epmonth;
	   }
	   
	   public void setEpubyear (String epyear)
	   {
		   this.epubyear = epyear;
	   }
	   
	   
	   public void setEpubday (String epday)
	   {
		  this.epubday = epday; 
	   }
	   
	   
	   public void setDoi (String doi)
	   {
		   this.doi = doi;
	   }
	   
	   
	   public String getEpubday ()
	   {
		 return epubday;  
	   }
	   
	   public String getEpubmonth ()
	   {
		  return epubmonth; 
	   }
	   
	   public String getEpubyear ()
	   {
		   return epubyear;
	   }
	   
	   public String getDoi ()
	   {
		 return doi;  
	   }
	   
	   public String getEpubsum ()
	   {
		   return epubsum;
	   }
	   
	   public void setEpubsum (String ep)
	   {
		   this.epubsum = ep;
	   }
	   
	   public String getEpubsum2 ()
	   {
		   return epubsum2;
	   }
	   
	   public void setEpubsum2 (String ep)
	   {
		   this.epubsum2 = ep;
	   }
	   
     

}