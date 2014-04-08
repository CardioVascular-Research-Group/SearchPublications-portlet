/*Copyright 2013 Johns Hopkins University Institute for Computational Medicine

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
/**
* @author Shallon Brown 2014
* 
*/


package edu.jhu.cvrg.ceptools.main;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.primefaces.event.FlowEvent;


import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.client.solrj.response.CoreAdminResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.CoreAdminParams.CoreAdminAction;
import org.apache.solr.common.util.NamedList;





import com.liferay.faces.portal.context.LiferayFacesContext;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.util.PortalUtil;
import com.sun.jna.Native;

import edu.jhu.cvrg.ceptools.controller.FileStorer;
import edu.jhu.cvrg.ceptools.controller.Publication;










@ManagedBean(name="searchPubs")
@SessionScoped


public class SearchPubs {

	/**
	 * @param args
	 */
	private static Logger logger = Logger.getLogger(SearchPubs.class.getName());  
	
	List<Publication> results;
	 private List<FileStorer> allfiles;

	 private FileStorer selectedfile;
	 private boolean redostep2;
	 private String redostep2msg;
	  private FileStorer selecteddownloadfile;
	Publication searchresultpub;
	String searchentry;
	 private List<File> files;
	    private List<String> filenames;
	    private List<FileStorer> filesfromsolr;
	    private String pmid;
	  
	    private String selecteddownloadfiletype;
	    private String selecteddownloadfilename;
	    int step;
	    int solrindex;
	

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public SearchPubs()
	{
		allfiles = new ArrayList<FileStorer>();
		redostep2 = false;
		filesfromsolr = new ArrayList<FileStorer>();
		searchentry = "";
		selecteddownloadfile = null;
		searchresultpub = null;
		selectedfile = null;
		results = new ArrayList<Publication>();
		 files = new ArrayList<File>();
    	 filenames = new ArrayList<String>();
    	 
    	 solrindex = 0;
    	 pmid = "";
    	 
    	 
    	 step = 1;
    	 selecteddownloadfiletype = selecteddownloadfilename="";
    	 redostep2= false;
    	 redostep2msg = "";
	}
	
	
	  public void setRedostep2msg(String m)
	    {
	    	redostep2msg = m;
	    }
	    
	    public String getRedostep2msg()
	    {
	    	return redostep2msg;
	    }
	    
	    public void setRedostep2(boolean m)
	    {
	    	redostep2 = m;
	    }
	    
	    public boolean getRedostep2()
	    {
	    	return redostep2;
	    }
	
	public void convertStore(String fileinfo, Publication currlist)
	{
		int fname, fsize, ffigure, fpanel, fdescription = -1;
		String sname , ssize, sfigure, spanel, sdescription;
		sname = ssize= sfigure= spanel= sdescription = "";
		
		
		fsize = fileinfo.indexOf("filesize:");
		fdescription = fileinfo.indexOf(",filedescription:");
		ffigure = fileinfo.indexOf(",filefigure:");
		fpanel = fileinfo.indexOf(",filepanel:");
		fname = fileinfo.indexOf(",filename:");
		
		if(fsize != -1 && fdescription != -1)
		{
			ssize = (String) fileinfo.subSequence(fsize, fdescription);
		}
		if(ffigure!= -1 && fdescription != -1)
		{
			sdescription = (String) fileinfo.subSequence(fdescription, ffigure);
		}
		if(ffigure!= -1 && fpanel != -1)
		{
			sfigure = (String) fileinfo.subSequence(ffigure, fpanel);
		}
		if(fname != -1 && fpanel != -1)
		{
			spanel = (String) fileinfo.subSequence(fpanel, fname);
		}
		if(fname != -1)
		{
			sname = (String) fileinfo.subSequence(fname,fileinfo.length());
		}
		
		ssize = ssize.replace("filesize:","" );
		sname = sname.replace(",filename:", "");
		sdescription = sdescription.replace(",filedescription:", "");
		sfigure = sfigure.replace(",filefigure:", "");
		spanel = spanel.replace(",filepanel:", "");
		
		
		
		String fileloc = PropsUtil.get("data_store") + pmid + "/";
		FileStorer currfile = new FileStorer();
		currfile.setDescription(sdescription);
		currfile.setFigure(sfigure);
		currfile.setFilesize(Long.valueOf(ssize));
		currfile.setFilename(sname);
		currfile.setPanel(spanel);
		currfile.setIndex(solrindex);
		currfile.setFilelocation(fileloc);
		currfile.setLocalfilestore(fileloc);
		
	
		currlist.addFile(currfile);
		//filesfromsolr.add(currfile);
		
		
		solrindex++;
		
	}
	
	
	public int getSolrindex()
	{
		return solrindex;
	}
	
	public void setSolrIndex(int s)
	{
		solrindex = s;
	}
	
	public void setFilesfromsolr(List<FileStorer> f)
	{
		filesfromsolr = f;
	}
	
	public List<FileStorer> getFilesfromsolr()
	{
		return filesfromsolr;
	}
	
	public void setAllfiles()
	{
		
	}
	public List<FileStorer> getAllfiles()
	{
		return allfiles;
	}
	

	public void moveStep(int nextstep)
	{
		int previousstep = step;
		
		step = nextstep;
		
		if(step ==1)
		{
			Clean();
		}
		if(step == 2)
		{
			if(previousstep == 1)
			{
			Clean2();
			}
			if(redostep2 == true)
			{
				if(searchresultpub != null)
				{
					step = 3;
				}
				else
				{
					step = 2;
					redostep2msg = "Please choose a single citation from the listing.";
				}
				
				
			}
			else
			{
			
			SearchSolr();
			redostep2 = true;
			}
		}
		if(step == 3)
		{
			RetrieveFiles();
			setDisplay();
		}
		
		try{
			LiferayFacesContext portletFacesContext = LiferayFacesContext.getInstance();
			portletFacesContext.getExternalContext().redirect("search");
			return;
		}
		catch (Exception ex)
		{
			logger.info(ex);
		}
		
		
	}
	
	public void downloadRawFiles(FileStorer currfile){


		
		selecteddownloadfile = currfile;
		
		
	    if(selecteddownloadfile != null){
	    	
	           downloadInit();
	    }
	    //return "success";
	}
	
	public void downloadZipOnly()
	{
		
		
		selecteddownloadfile = new FileStorer();
		
		String fileloc = PropsUtil.get("data_store") + pmid + "/";
		String filen = pmid + ".zip";
		
		selecteddownloadfile.setFilelocation(fileloc);
		selecteddownloadfile.setFilename(filen);
		selecteddownloadfile.setIndex(0);
		selecteddownloadfile.setLocalfilestore(fileloc);
		selecteddownloadfile.setFiletype("zip");
		
		
		downloadFile(filen,"zip");
	}
	
	public void downloadInit()
	{
		
		
		//Gather the content type and store
		selecteddownloadfilename = selecteddownloadfile.getFilename();
		
		
		
		String currtype = FilenameUtils.getExtension(selecteddownloadfile.getFilename());
		
		
	
		
	     selecteddownloadfile.setFiletype(currtype);
		
		downloadFile(selecteddownloadfilename, selecteddownloadfiletype);
		
		//Begin the download process
	}
	
	public void setSelecteddownloadfile(FileStorer afiles)
	{
		selecteddownloadfile = afiles;
	}
	
	public FileStorer getSelecteddownloadfile()
	{
		return selecteddownloadfile;
	}
	
	public void setSelectedfile(FileStorer thefile)
	{
		selectedfile = thefile;
	}
	
	public FileStorer getSelectedfile()
	{
		return selectedfile;
	}
	
	public void setResults(List<Publication> re)
	{
		this.results = re;
	}
	
	public List<Publication> getResults()
	{
		return results;
	}
	
	public void setSearchresultpub (Publication pub)
	{
		this.searchresultpub = pub;
	}
	
	public Publication getSearchresultpub()
	{
		return searchresultpub;
	}
	
	public String getSearchentry ()
	{
		return searchentry;
	}
	
	public void setSearchentry(String se)
	{
		this.searchentry = se;
	}
	
	
	 public String onFlowProcess(FlowEvent event) {  
		 if(event.getOldStep().equalsIgnoreCase("searchpubs") )
	       {
			 
			 SearchSolr();
			 return "searchresultsofpubs";
	       }
		 else if(event.getOldStep().equalsIgnoreCase("searchresultsofpubs") )
	       {
			  RetrieveFiles();
			 
	          return "searchresultdisplay";
	       }	
		 else
		 {
			 Clean();
			 return "searchpubs";
		 }
		 
	 }
	 
	 private void RetrieveFiles() {
		 
		int currpmid =  searchresultpub.getPmid();
		pmid = String.valueOf(currpmid);
		
		
    	
    	String currlocation = PropsUtil.get("data_store") + this.searchresultpub.getPmid() + "/";
    	String zipfilelocation = currlocation+ this.searchresultpub.getPmid()+".zip";
    	File foldertozip = new File(currlocation);
    	 
    	
    	
    	File folder = new File(currlocation);
    	
    	
    
    	
    	for(File currfile: folder.listFiles())
    	{
    		
           String absolutePath = currfile.getAbsolutePath();
    		FileStorer currfilestore = new FileStorer();
    		currfilestore.setFilename(currfile.getName());
    		currfilestore.setFilelocation(currlocation);
    		currfilestore.setFiletype(FilenameUtils.getExtension(currfile.getName()));
    		currfilestore.setLocalfilestore( absolutePath.substring(0,absolutePath.lastIndexOf(File.separator)));
    		allfiles.add(currfilestore);
    		files.add(currfile);
    		filenames.add(currfile.getName());
    	}
    	
    	searchresultpub.setFiles(files);
    	searchresultpub.setFilenames(filenames);


		
	}
	 
public void downloadFile(String filename, String filetype){
         
         String contentType = "application/zip";
 
         if(filetype.equals("abf")){
                 contentType = "text/abf";
         }
     
       
    
         
  FacesContext facesContext = (FacesContext) FacesContext.getCurrentInstance();
  ExternalContext externalContext = facesContext.getExternalContext();
  PortletResponse portletResponse = (PortletResponse) externalContext.getResponse();
  HttpServletResponse response = PortalUtil.getHttpServletResponse(portletResponse);


  File file = new File(selecteddownloadfile.getFilelocation(), filename);
  BufferedInputStream input = null;
  BufferedOutputStream output = null;

 
  try {
  input = new BufferedInputStream(new FileInputStream(file), 10240);
 
  response.reset();
  response.setHeader("Content-Type", contentType);
  response.setHeader("Content-Length", String.valueOf(file.length()));
  response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
  response.flushBuffer();
  output = new BufferedOutputStream(response.getOutputStream(), 10240);
  
  byte[] buffer = new byte[10240];
  int length;
  while ((length = input.read(buffer)) > 0) {
  output.write(buffer, 0, length);
  }
 
  output.flush();
  } catch (FileNotFoundException e) {
                 e.printStackTrace();
         } catch (IOException e) {
                 e.printStackTrace();
         } finally {
  try {
                         output.close();
  input.close();
                 } catch (IOException e) {
                         e.printStackTrace();
                 }
  }
 
  facesContext.responseComplete();

 
}


	public void Clean()
	 {
		 searchentry = "";
			
			searchresultpub = null;
			results = new ArrayList<Publication>();
			allfiles = new ArrayList<FileStorer>();
			searchentry = "";
			selecteddownloadfile = null;
			searchresultpub = null;
			selectedfile = null;
			results = new ArrayList<Publication>();
			 files = new ArrayList<File>();
	    	 filenames = new ArrayList<String>();
	    	 solrindex = 0;
	    	
	    	 redostep2 = false;
	    	 redostep2msg = "";
	    	 pmid ="";
	    	 step = 1;
	    	 selecteddownloadfiletype = selecteddownloadfilename="";
	 }
	
	public void Clean2()
	{
		 
			
			searchresultpub = null;
			results = new ArrayList<Publication>();
			allfiles = new ArrayList<FileStorer>();
		
			selecteddownloadfile = null;
			searchresultpub = null;
			selectedfile = null;
			results = new ArrayList<Publication>();
			 files = new ArrayList<File>();
	    	 filenames = new ArrayList<String>();
	    	 solrindex = 0;
	    	
	    	 redostep2 = false;
	    	 redostep2msg = "";
	    	 pmid ="";
	    
	    	 selecteddownloadfiletype = selecteddownloadfilename="";
	}
	
	   public void setStep(int currstep)
	   {
		   step = currstep;
	   }
	   
	   public int getStep()
	   {
		   return step;
	   }
	   
	 
	 public void SearchSolr()
	 {
		
		 
		 
		 try
		 {
			
			 CoreAdminRequest adminRequest = new CoreAdminRequest();
			 adminRequest.setAction(CoreAdminAction.RELOAD);
			 CoreAdminResponse adminResponse = adminRequest.process(new HttpSolrServer("http://localhost:8983/solr"));
			 NamedList<NamedList<Object>> coreStatus = adminResponse.getCoreStatus();
			 
			 
			 SolrServer solr = new HttpSolrServer ("http://localhost:8983/solr");
			 
			 
			 String query;
			
			 //query = "pid:*";
			 query = "collector:" + searchentry;
			 
			
			 SolrQuery theq = new SolrQuery();
			 theq.setQuery(query);
			 
			
			 QueryResponse response = new QueryResponse();
			 
			
			 response = solr.query(theq);
			 
			 
			 SolrDocumentList list = response.getResults();
		
			 
			
			 
			 int docnum = 1;
			
			 for(SolrDocument doc: list)
			 {
				Publication currlist = new Publication();
				 List<String> fnames = new ArrayList<String> ();
				 List<String> lnames = new ArrayList<String> ();
				 List<String> fullnames =  new ArrayList<String> ();
				 String currepubsum1 = "", currepubsum2 = "";
				
				
				
				currlist.setTitle(doc.getFieldValue("ptitle").toString());
				
				currlist.setAbstract(doc.getFieldValue("abstract").toString());
				currlist.setPmid(Integer.valueOf(doc.getFieldValue("pmid").toString()));
				pmid = String.valueOf(currlist.getPmid());
				
				if(doc.getFieldValue("journalname")!=null)
				{
				currlist.setJournalname(doc.getFieldValue("journalname").toString());
				}
				
				if(doc.getFieldValue("journalyear")!=null)
				{
				currlist.setJournalyear(doc.getFieldValue("journalyear").toString());
				}
				if(doc.getFieldValue("journalday")!=null)
				{
				currlist.setJournalday(doc.getFieldValue("journalday").toString());
				}
				if(doc.getFieldValue("journalmonth")!=null)
				{
				currlist.setJournalmonth(doc.getFieldValue("journalmonth").toString());
				}
				if(doc.getFieldValue("journalpage")!=null)
				{
				currlist.setJournalstartpg(doc.getFieldValue("journalpage").toString());
				}
				if(doc.getFieldValue("journalissue")!=null)
				{
				currlist.setJournalissue(doc.getFieldValue("journalissue").toString());
				}
				if(doc.getFieldValue("journalvolume")!=null)
				{
				currlist.setJournalvolume(doc.getFieldValue("journalvolume").toString());
				}
				if(doc.getFieldValue("publication_year")!=null)
				{
				currlist.setYear(doc.getFieldValue("publicationdate_year").toString());
				}
				if(doc.getFieldValue("doi") != null)
				{
				currlist.setDoi(doc.getFieldValue("doi").toString());
				}
				
				if(doc.getFieldValues("pfileinfo") != null)
				{
				
					Collection<Object> currcoll = doc.getFieldValues("pfileinfo");
					
					for(Object currobj: currcoll)
					{
						convertStore(String.valueOf(currobj), currlist);
					}
					
					
				}
			
				currlist.setFauthors((List<String>) doc.getFieldValue("author_firstname"));
				currlist.setLauthors((List<String>) doc.getFieldValue("author_lastname"));
				
				
			
				
				if(doc.getFieldValue("epubmonth") != null)
				{
				currlist.setEpubmonth(doc.getFieldValue("epubmonth").toString());
				}
				
				if(doc.getFieldValue("epubyear") != null)
				{
				currlist.setEpubyear(doc.getFieldValue("epubyear").toString());
				}
				if(doc.getFieldValue("epubday") !=null)
				{
				currlist.setEpubday(doc.getFieldValue("epubday").toString());
				}
				if(doc.getFieldValue("author_fullname_list") !=null)
				{
					
					currlist.setAuthorfull(doc.getFieldValue("author_fullname_list").toString());
				}
				
				int counter = 0;
				

				
				for(String currstring: currlist.getFauthors())
				{
				    currstring += " " + currlist.getLauthors().get(counter); 
				    fullnames.add(currstring);
					counter++;
				}
				
				currlist.setFullnames(fullnames);
				
				if(currlist.getJournalvolume().length()>0)
  	        	{
  	        		currepubsum2 +=  currlist.getJournalvolume();
  	        	}
  	        	
  	        	if(currlist.getJournalissue().length()>0)
  	        	{
  	        		currepubsum2 += "("+ currlist.getJournalissue() + ")"+ ":";
  	        	}
  	        	
  	        	if(currlist.getJournalstartpg().length()>0)
  	        	{
  	        		currepubsum2 += currlist.getJournalstartpg() + ".";
  	        	}
  	        	
  	        	
	              
	              
	              if( currlist.getEpubday().length()<1 && currlist.getEpubmonth().length()<1  && currlist.getEpubyear().length()<1)
  	              {
  	            	currepubsum1 = "[Epub ahead of print]"; 
  	              }
	              else if(currlist.getEpubyear().length()>0)
	              {
	            	  currepubsum1= "Epub "  + currlist.getEpubyear() + " " + currlist.getEpubmonth() + " " + currlist.getEpubday();
	              }
	              else
	              {
	            	  currepubsum1 = "";
	              }
				
	              currlist.setEpubsum(currepubsum1);
	              currlist.setEpubsum2(currepubsum2);
	              currlist.setIndex(docnum);
				
				
				
				//currlist.setFullnames(doc.getFieldValue("ptitle").toString());
				
				
				
			
				results.add(currlist);
				docnum++;
			 }
			 
			 
			 
		
		  
		 }
		 catch (Exception ex)
		 {
			 logger.info(ex);
			 StringWriter stack = new StringWriter();
			 ex.printStackTrace(new PrintWriter(stack));
			 
			
		 }
	 }
	 
	 public void setDisplay()
	 {
		String tmpdis = "";
		 
		 for(FileStorer currfile: searchresultpub.getPubfiles())
		 {
			   if(currfile.getFigure().length() > 0 && currfile.getPanel().length()>0)
			   {
				   tmpdis = "Figure " + currfile.getFigure() + ", Panel " + currfile.getPanel();
				   currfile.setFigpandisplay(tmpdis); 
			   }
			   else if (currfile.getFigure().length() > 0 && currfile.getPanel().length()<1)
			   {
				   tmpdis = "Figure " + currfile.getFigure(); 
				   currfile.setFigpandisplay(tmpdis);  
			   }
			   else 
			   {
				   currfile.setFigpandisplay("");  
			   }
		 }
	 }
	 
	 public void setSolrResult()
	 {
		 
	 }

}
