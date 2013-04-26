package cs213.photoAlbum.simpleview;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import cs213.photoAlbum.control.Control;
import cs213.photoAlbum.control.Controller;
import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Backend;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.User;

/**
 * 
 * The text-based interface. CmdView will parse the user's text inputs and call the
 * appropriate methods accordingly. There are two modes of the text based interface: command line, and interactive mode. 
 * The two modes define which actions the users can perform. (list users, list albums, add/remove tags users or photos,
 *  get photos by date or tag, etc. 
 * 
 * @author Gal
 *
 */
public class CmdView {
	
	public static Backend backend = new Backend();
	public static Control control = new Controller();
	
	/**
	 * Runs the text interface.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		//check the command line arguments
		String command = null;
		String userID = null; //int userID = 0;
		String userName = null;
		
		try {
			CmdView.backend.setUserList(CmdView.backend.load());
			//System.out.println("LOADED: " + CmdView.backend.getUserList().get("dennisr").getFullName().toString());
		} catch (IOException e1) {
			//e1.printStackTrace();
			
		} catch (ClassNotFoundException e1) {
			//e1.printStackTrace();
		}
	
		if (args.length != 0){
			command = args[0];
			//command = command.toLowerCase();
			//System.out.println(argument);
	
			
			if (command.equalsIgnoreCase("listusers")){  //   ----------  LIST USERS
			
				ArrayList<User> users = new ArrayList<User>(control.listUsers());
				
				if (users.size() == 0){
					System.out.println("no users exist");
				}else{
					for(int y = 0; y < users.size(); y++){
						System.out.println(users.get(y).getID());
					}
				}
				
			}else if (command.equalsIgnoreCase("adduser")){ // ---------- ADD USERS
				
				if (args.length == 3){
					
					userID = args[1];
					userName = args[2];
					if ( control.addUser(userID, userName) == true){
						try {
							//System.out.println("ATTEMPTING TO SAVE: " + CmdView.backend.getUserList().get("dennisr").getID().toString());
							backend.save(CmdView.backend.getUserList());
						} catch (IOException e) {
							//e.printStackTrace();
							System.out.println("Error: Could not save data.");
						}
						
						System.out.println("created user "+ userID +" with name " + userName);
					}else{
						System.out.println("user "+userID+" already exists with name " +userName);
					}
	
				}else{
					System.out.println("Error: Invalid number of arguments.");
				}
				
			}else if (command.equalsIgnoreCase("deleteuser")){ // ------- DELETE USERS
				if (args.length == 2){
			
					userID = args[1];
					if ( control.deleteUser(userID) == true){
						try {
							backend.save(CmdView.backend.getUserList());
						} catch (IOException e) {
							e.printStackTrace();
							System.out.println("Error: Could not load data.");
						}
						
						System.out.println("deleted user "+ userID );
					}else{
						System.out.println("user " + userID +" does not exist");
					}
							
				}else{
					System.out.println("Error: Invalid number of arguments.");
				}
				
			}else if(command.equalsIgnoreCase("login")){  // ------------- LOGIN
				if (args.length == 2){
					
					userID = args[1];
					if (control.login(userID) == true){
						//SWITCH TO INTERACTIVE MODE  
						interactiveMode();
					}else{
						System.out.println("Error: The user "+ userID + " does not exist");
					}
	
				}else{
					System.out.println("Error: Invalid number of arguments.");
				}
			
			}else{
				System.out.println("Error: invalid command.");
			}
		}
		
	}
	
	
	/**
	 * This method controls of "interactive mode". This is where the users will be able to
	 * give different text commands and get responses from the program accordingly.
	 */
	public static void interactiveMode(){
		
        BufferedReader reader; 
        reader = new BufferedReader(new InputStreamReader(System.in));
        String input = null;
        
        
        Boolean loggedin = true;
        
        while (loggedin == true){
        	 try {
     			 input = reader.readLine();
     			
     			 String[] argumentsArray = new String[100];
     			 StringTokenizer st = new StringTokenizer(input);
     		     //int i = 1;
     		     
     		     argumentsArray[0] = st.nextToken();
     		     
     		/*	 while (st.hasMoreTokens()) {
     		    	 argumentsArray[i] = st.nextToken("\"");
     		    	System.out.println(argumentsArray[i]);
     		    	 i++;
     		     }
			*/
     			 if (argumentsArray[0] != null){
     				 //argumentsArray[0] = argumentsArray[0].toLowerCase();
     				
     				 if (argumentsArray[0].equalsIgnoreCase("createalbum")){    //  ------------ interactive - CREATE ALBUM
     					
     					 try{
     						 //System.out.println(argumentsArray[0]);
         					 argumentsArray[1] = st.nextToken("\"");
         					 argumentsArray[1] = st.nextToken("\"");
         					// argumentsArray[1] = removeQuotes(argumentsArray[1]);
         					 //System.out.println(argumentsArray[1]);
         					 
         					 if (argumentsArray[1] != null){
         						//argumentsArray[1] = argumentsArray[1].toLowerCase();
             					if (control.createAlbum(argumentsArray[1]) == true){
             						System.out.println("created album for user " + control.getCurrentUser().getID().toString() +":");
             						System.out.println(argumentsArray[1]);
             					}else{
             						System.out.println("album exists for user" + control.getCurrentUser().getID().toString()+":");
             						System.out.println(argumentsArray[1]);
             					}
         								
         					 }else{
         						 System.out.println("Error: No album name provided.");
         					 }
     					 }catch (NoSuchElementException e){
     						System.out.println("Error: No album name provided.");
     					 }
     					 
   	
     				 }else if (argumentsArray[0].equalsIgnoreCase("deletealbum")){  // ---------- interactive - DELETE ALBUM
     					
     					 try{
     						argumentsArray[1] = st.nextToken("\"");
        					 argumentsArray[1] = st.nextToken("\""); 
        					 
        					if (argumentsArray[1] != null){ 
        						//argumentsArray[1] = argumentsArray[1].toLowerCase();
            					if (control.deleteAlbum(argumentsArray[1]) == true){
            						System.out.println("deleted album from user " + control.getCurrentUser().getID().toString() +":");
            						System.out.println(argumentsArray[1]);
            					}else{
            						System.out.println("album does not exist for user " + control.getCurrentUser().getID().toString()+":");
            						System.out.println(argumentsArray[1]);
            					}
            					
        					}else{
       						 System.out.println("Error: No album name provided");
       					 }
     					 }catch (NoSuchElementException e){
     						System.out.println("Error: No album name provided");
     					 }
     					
     				 }else if (argumentsArray[0].equalsIgnoreCase("listalbums")){ // ------------ interactive - LIST ALBUMS
     					 
     					ArrayList<Album> albumslist = control.listAlbums();
     					if (albumslist != null){
     						
     						System.out.println("Albums for user "+control.getCurrentUser().getID().toString() +":");
     						
     						for (int y = 0; y < albumslist.size(); y++){
     							if (albumslist.get(y).getPhotoList().size() > 0){
     								System.out.println(albumslist.get(y).getName() + " number of photos: " +
         									albumslist.get(y).getPhotoList().size()+", "+ control.getStartDate(albumslist.get(y)) +" - "+ control.getEndDate(albumslist.get(y)));
     							}else{
     								System.out.println(albumslist.get(y).getName() + " number of photos:0"+", "+ "MM/DD/YYYY-HH:MM:SS" + " - " +"MM/DD/YYYY-HH:MM:SS");
     							}
     							
     						}
  
     					}else{
     						System.out.println("No albums exist for user " + control.getCurrentUser().getID().toString());
     					}
     					 
     				 }else if (argumentsArray[0].equalsIgnoreCase("listphotos")){ // ------------- interactive - LIST PHOTOS
     					
     					 try {
     						argumentsArray[1] = st.nextToken("\"");
     						argumentsArray[1] = st.nextToken("\"");
     						
     						 if (argumentsArray[1] != null){
          						//argumentsArray[1] = argumentsArray[1].toLowerCase();
          						ArrayList<Photo> photos = new ArrayList<Photo>();
          						photos = control.listPhotos(argumentsArray[1]);
          						
          						if (photos != null){
          							System.out.println("Photos for album "+ argumentsArray[1]+":");
          							for (int x = 0; x < photos.size(); x++){
          								System.out.println(photos.get(x).getName() + " - " + photos.get(x).getDateString());
          							}
          						}else{
          							System.out.println("Album "+argumentsArray[1] + " does not exist.");
          						}
          					
          					 }else{
         						 System.out.println("Error: No album name provided");
         					 }
     					 } catch (NoSuchElementException e){
     						System.out.println("Error: No album name provided");
     					 }
     					
     				 }else if (argumentsArray[0].equalsIgnoreCase("addphoto")){  // -------------- interactive - ADD PHOTO
     					 
     					 try{
     						 argumentsArray[1] = st.nextToken("\"");
         					 argumentsArray[1] = st.nextToken("\"");
         					// System.out.println(argumentsArray[1]);
         					 
         					 argumentsArray[2] = st.nextToken("\"");
        					 argumentsArray[2] = st.nextToken("\"");
        					// System.out.println(argumentsArray[2]);
        					 
        					 argumentsArray[3] = st.nextToken("\"");
        					 argumentsArray[3] = st.nextToken("\"");
         				//	 System.out.println(argumentsArray[3]);
        					 
        					 
         					 
         					 if (argumentsArray[1] != null && argumentsArray[2] != null && argumentsArray[3] != null){
         						//argumentsArray[1] = argumentsArray[1].toLowerCase();
         						//argumentsArray[3] = argumentsArray[3].toLowerCase();
         						 
         						 File p = new File("test");
         						
         						 int retval = control.addPhoto(argumentsArray[1], argumentsArray[2], argumentsArray[3], p);
         						 
         						 if (retval == 1){
         							System.out.println("Added photo "+argumentsArray[1]);
         							System.out.println(argumentsArray[2] + " - Album:"+argumentsArray[3]);
         						 }else if (retval == 2){
         							 System.out.println("Photo "+argumentsArray[1] + " already exists in album " + argumentsArray[2]);
         						 }else if (retval == 3){
         							 System.out.println("File "+ argumentsArray[1] + " does not exist");
         						 }
         						 
         					 }else{
        						 System.out.println("Error: invalid number of arguments.");
        					 }
     					 } catch (NoSuchElementException e){
     						System.out.println("Error: Error: invalid number of arguments.");
     					 }
     					
   
     				 }else if (argumentsArray[0].equalsIgnoreCase("movephoto")) {  // ------------- interactive - MOVE PHOTO
     					 
     					 try {
     						 argumentsArray[1] = st.nextToken("\"");
							 argumentsArray[1] = st.nextToken("\"");
							// System.out.println(argumentsArray[1]);
							 
							 argumentsArray[2] = st.nextToken("\"");
							 argumentsArray[2] = st.nextToken("\"");
							// System.out.println(argumentsArray[2]);
							 
							 argumentsArray[3] = st.nextToken("\"");
							 argumentsArray[3] = st.nextToken("\"");
						//	 System.out.println(argumentsArray[3]);
        					 
        					 if (argumentsArray[1] != null && argumentsArray[2] != null && argumentsArray[3] != null){
         					//	argumentsArray[1] = argumentsArray[1].toLowerCase();
         					//	argumentsArray[2] = argumentsArray[2].toLowerCase();
         					//	argumentsArray[3] = argumentsArray[3].toLowerCase();
         						
         						int result = control.movePhoto(argumentsArray[1], argumentsArray[2], argumentsArray[3]);
         						
         						if (result == 1){
         							System.out.println("Moved photo "+ argumentsArray[1]+ ":");
         							System.out.println(argumentsArray[1] + " - From album " + argumentsArray[2] + " to album "+ argumentsArray[3]);
         						}else if (result == -3){
         							System.out.println("Photo "+argumentsArray[1]+" does not exist in "+ argumentsArray[2]);
         						}else if (result  == -1){
         							System.out.println("Error: album "+ argumentsArray[2] + " does not exist.");
         						}else if (result == -2){
         							System.out.println("Error: album "+ argumentsArray[3] + " does not exist.");
         						}else if (result == -4){
         							System.out.println("Error: photo already exists in album: " + argumentsArray[3]);
         						}
         						
         						
         					 }else{
        						 System.out.println("Error: invalid number of arguments.");
        					 }
     					 } catch (NoSuchElementException e){
     						System.out.println("Error: invalid number of arguments.");
     					 }
     					 
     					 
     				 }else if (argumentsArray[0].equalsIgnoreCase("removephoto")){  // ------------ interactive - REMOVE PHOTO
     					 
     					 try {
     						 argumentsArray[1] = st.nextToken("\"");
							 argumentsArray[1] = st.nextToken("\"");
						//	 System.out.println(argumentsArray[1]);
							 
							 argumentsArray[2] = st.nextToken("\"");
							 argumentsArray[2] = st.nextToken("\"");
						//	 System.out.println(argumentsArray[2]);
							 
							 if (argumentsArray[1] != null && argumentsArray[2] != null){
		     					//	argumentsArray[1] = argumentsArray[1].toLowerCase();
		      					//	argumentsArray[2] = argumentsArray[2].toLowerCase();
		      						
		      						if (control.removePhoto(argumentsArray[1], argumentsArray[2]) == true){
		      							System.out.println("Removed photo:");
		      							System.out.println(argumentsArray[1] + " - From album " + argumentsArray[2]);
		      						}else{
		      							System.out.println("Photo " + argumentsArray[1] + " is not in album " + argumentsArray[2]);
		      						}
		      						
		     					 }else{
		     						 System.out.println("Invalid input. invalid number of arguments.");
		     					 }
     					 }catch (NoSuchElementException e){
     						 System.out.println("Invalid input. invalid number of arguments.");
     					 }
     					 
     					 
     				 }else if (argumentsArray[0].equalsIgnoreCase("addtag")){ // -------------- interactive - ADD TAG
     					
     					 try {
     						 argumentsArray[1] = st.nextToken("\"");
							 argumentsArray[1] = st.nextToken("\"");
							 //System.out.println(argumentsArray[1]);
							 
							 argumentsArray[2] = st.nextToken(":");
							 argumentsArray[2] = argumentsArray[2].replace("\"", " ");
							 argumentsArray[2] =  argumentsArray[2].trim();
							 //System.out.println(argumentsArray[2]);
							 
							 argumentsArray[3] = st.nextToken("\"");
							 argumentsArray[3] = st.nextToken("\"");
							 argumentsArray[3] = argumentsArray[3].trim();
							 //System.out.println(argumentsArray[3]);
							 
							//String tester = argumentsArray[2] + ":" + argumentsArray[3];
							 
							if (argumentsArray[1] != null && argumentsArray[2] != null && argumentsArray[3] !=null){
	     						//argumentsArray[1] = argumentsArray[1].toLowerCase();
	      						//argumentsArray[2] = argumentsArray[2].toLowerCase();
	      					
	      						//if (checkTagInput(tester) == true){
	      							
	      							int result = control.addTag(argumentsArray[1], argumentsArray[2], argumentsArray[3] );
	      							
	      							if (result == 1){
	      								System.out.println(argumentsArray[1] + " " + argumentsArray[2] + ":" + argumentsArray[3]);
	      							}else if (result == -1){
	      								System.out.println("Tag already exists for " + argumentsArray[1] + " " + argumentsArray[2] + ":"+ argumentsArray[3] );
	      							}else if (result == -2){
	      							}
	      							
	      						//}else{
	      						//	System.out.println("Error: Tags are not in the correct format.");
	      						//}
	      						
	     					 }else{
	     						 System.out.println("Invalid input. Tags not in the correct format.");
	     					 }
     					 }catch (NoSuchElementException e){
     						System.out.println("Invalid input. Tags not in the correct format.");
     					 }
     					
     					 
     				 }else if (argumentsArray[0].equalsIgnoreCase("deletetag")){ // ----------- interactive - DELETE TAG
     					 
     					try {
    						 argumentsArray[1] = st.nextToken("\"");
							 argumentsArray[1] = st.nextToken("\"");
							 //System.out.println(argumentsArray[1]);
							 
							 argumentsArray[2] = st.nextToken(":");
							 argumentsArray[2] = argumentsArray[2].replace("\"", " ");
							 argumentsArray[2] =  argumentsArray[2].trim();
							 //System.out.println(argumentsArray[2]);
							 
							 argumentsArray[3] = st.nextToken("\"");
							 argumentsArray[3] = st.nextToken("\"");
							 argumentsArray[3] = argumentsArray[3].trim();
							 //System.out.println(argumentsArray[3]);
							 
							 if (argumentsArray[1] != null && argumentsArray[2] != null){
		     						//argumentsArray[1] = argumentsArray[1].toLowerCase();
		      						//argumentsArray[2] = argumentsArray[2].toLowerCase();
		      						
		      						//if (checkTagInput(argumentsArray[2]) == true){
		          						
		      							int result = control.deleteTag(argumentsArray[1], argumentsArray[2], argumentsArray[3]);
		      							if (result == 1){
		      								System.out.println("Deleted tag:");
		      								System.out.println(argumentsArray[1] + " " + argumentsArray[2] +":" + argumentsArray[3]);
		      							}else if (result == -1){
		      								//Tag does not exist for <fileName> <tagType>:<tagValue>
		      								System.out.println("Tag does not exist for "+argumentsArray[1] + " " + argumentsArray[2] +":" + argumentsArray[3]);
		      							}else if (result == -2){
		      								
		      							}
		      						//}else{
		      						//	System.out.println("Error: Tags are not in the correct format.");
		      						//}
		      						
		     					 }else{
		     						 System.out.println("Invalid input. Tags not in the correct format.");
		     					 }
     					}catch (NoSuchElementException e){
     						System.out.println("Invalid input. Tags not in the correct format.");
     					}
     					 
     				 }else if (argumentsArray[0].equalsIgnoreCase("listphotoinfo")){ // -------- interactive - LIST PHOTO INFO
     					 
     					try {
     						argumentsArray[1] = st.nextToken("\"");
 							argumentsArray[1] = st.nextToken("\"");
 							//System.out.println(argumentsArray[1]);
						 
 							if (argumentsArray[1] != null){ 
	     						//argumentsArray[1] = argumentsArray[1].toLowerCase();
	     						
	     						Photo photo;
	     						photo = control.listPhotoInfo(argumentsArray[1]);
	     						/*
	     						System.out.println(control.getCurrentUser().getID());
	     						Iterator it = control.getCurrentUser().getAlbumList().entrySet().iterator();
	     						while (it.hasNext()) {
	     							Map.Entry<String, Album> pairs = (Map.Entry<String, Album>) it.next();
	     							if (pairs.getValue().getPhotoList().containsKey(photo.getName())) {
	     								for (int x = 0; x < pairs.getValue().getPhotoList().get(photo.getName()).getAlbums().size(); x++) {
	     									System.out.println("x = " + x + ", " + photo.getName() + " is in album " + pairs.getValue().getPhotoList().get(photo.getName()).getAlbums().get(x));
	     								}
	     							}
	     						}
	     						*/
	     						if (photo != null){
	     							System.out.println("Photo file name:"+photo.getName());
	     							
	     							System.out.print("Album:");
	     							//System.out.println("SIZE OF ALBUMS IN MAIN: " + photo.getAlbums().size());
	     							for (int x = 0; x < photo.getAlbums().size(); x++){
	     								System.out.print(photo.getAlbums().get(x));
	     								
	     								if (x != photo.getAlbums().size()-1){ System.out.print(", ");}
	     								
	     							}
	     							System.out.println();
	     							
	     							System.out.println("Date: "+ photo.getDateString());
	     							System.out.println("Tags:");
	     							for (int x = 0; x < photo.getSortedTags().size(); x++){
	     								System.out.println(photo.getSortedTags().get(x).getType() + ":" + photo.getSortedTags().get(x).getValue());
	     							}
	     						}else{
	     							System.out.println("got here");
	     							System.out.println("Photo "+argumentsArray[1]+" does not exist");
	     						}
	     						
	     					}else{
	    						 System.out.println("Error: No album name provided");
	    					 }
     					}catch (NoSuchElementException e){
     						System.out.println("Error: No album name provided");
     					}
     					 
     				 }else if (argumentsArray[0].equalsIgnoreCase("getphotosbydate")){ // ------ interactive - GET PHOTOS BY DATE
     					 
     					try {
      						 argumentsArray[1] = st.nextToken();
      						 argumentsArray[2] = st.nextToken();
      						 //System.out.println(argumentsArray[1]);
      						 
      						 if (argumentsArray[1] != null && argumentsArray[2] != null){
          						if ( checkDate(argumentsArray[1]) == true && checkDate(argumentsArray[2]) == true){
          							
          							ArrayList<Photo> photos = new ArrayList<Photo>();
          							try {
     									photos = control.getPhotosByDate(argumentsArray[1], argumentsArray[2]);
     									System.out.println("Photos for user "+control.getCurrentUser().getID().toString()+ " in range "+
     											argumentsArray[1] + " to " + argumentsArray[2]);
     									for (int x = 0; x < photos.size(); x++){
     										System.out.print(photos.get(x).getCaption()+"- Album:");
     										for (int y = 0; y < photos.get(x).getAlbums().size(); y++){
     											System.out.print(photos.get(x).getAlbums().get(y));
     											if (y != photos.get(x).getAlbums().size()-1){ System.out.print(", "); }
     										}
     										System.out.println(" - Date:"+photos.get(x).getDateString());
     									}
     								} catch (ParseException e) {
     									//e.printStackTrace();
     									System.out.println("Error: could not read date.");
     								}
          						
          						}else{
          							System.out.println("Error: The dates were not entered in the correct format.");
          						}
          					 }else{
         						 System.out.println("Error: Start date and end date must both be entered.");
         					 }
     					} catch (NoSuchElementException e){
     						System.out.println("Error: Start date and end date must both be entered.");
     					}
     				 }else if (argumentsArray[0].equalsIgnoreCase("getphotosbytag")){ // ---------- interactive - GET PHOTOS BY TAG
     					 ArrayList<String> tagArray = new ArrayList<String>();
     					 String searchString = input;
     					 searchString = searchString.substring(14);
     					 
     					try {
 							//int j = 1;
 							
 							//if (checkIfTagTypesExist(input)){
 								while (st.hasMoreTokens()){
 									tagArray.add(st.nextToken(","));
 									
 									// getphotosbytag type:value, type:value, value, type:value 
 									// argumentsArray[j] = location:"value"
 							/*		
 	   								argumentsArray[j] = st.nextToken(":");
 	   								argumentsArray[j] = argumentsArray[j].replace("\"", " ");
 	   								argumentsArray[j] =  argumentsArray[j].trim();
 	   								System.out.println(argumentsArray[j]);
 	   								searchString = searchString +argumentsArray[j] + ":";
 	   								j++; 
 	   								
 	   								argumentsArray[j] = st.nextToken("\"");
 	   								argumentsArray[j] = st.nextToken("\"");
 	   								argumentsArray[j] = argumentsArray[j].trim();
 	   								System.out.println(argumentsArray[j]);
 	   								searchString = searchString +"\""+argumentsArray[j]+"\"" + " ";
 	   								
 	   								// argumentsArray[j] = value
 	   								tags.add(argumentsArray[j]);
 	   								j++;
 	   							*/
 	 							}
 								
							ArrayList<String> tags = new ArrayList<String>();
							for (int x = 0; x < tagArray.size(); x++) {
								if (tagArray.get(x).contains(":")) {
									String s = tagArray.get(x).substring(tagArray.get(x).indexOf(":") + 2, tagArray.get(x).length() - 1);
									tags.add(s);
								}
								else {
									//System.out.println("got here: " + tagArray.get(x) + " size of word = " + tagArray.get(x).length());
									String s = tagArray.get(x).trim().substring(1, tagArray.get(x).trim().length() - 1);
									tags.add(s);
								}
							}				
							 
 							ArrayList<Photo> photos = new ArrayList<Photo>();
 							photos = control.getPhotosByTag(tags);
 							if (photos != null){
 								//Photos for user <user id> with tags <search string>: 
 								//	<caption> - Album: <albumName>[,<albumName>]... - Date: <date> 
 								System.out.println("Photos for user "+control.getCurrentUser().getID().toString()+ " with tags "+searchString+":");
 								System.out.println("photos size = " + photos.size());
 								for (int x = 0; x < photos.size(); x++){
 									System.out.print(photos.get(x).getCaption()+"- Album:");
										for (int y = 0; y < photos.get(x).getAlbums().size(); y++){
											System.out.print(photos.get(x).getAlbums().get(y));
											if (y != photos.get(x).getAlbums().size()-1){ System.out.print(", "); }
										}
										System.out.println(" - Date:"+photos.get(x).getDateString());
 								}
 							}else{
 								System.out.println("test");
 							}
							 
 						}catch (NoSuchElementException e){
 							System.out.println("Invalid input. Tags not in the correct format.");
 						} 
     					 
     					/*if (argumentsArray[1] != null){
     						ArrayList<String> tags = new ArrayList<String>();
     						
     						ArrayList<String> tags = new ArrayList<String>();
         					int x = 1;
         					String type = null;
         					String value = null;
         					
         					while (argumentsArray[x] != null){
         						argumentsArray[x] = removeComma(argumentsArray[x]);
         						
         						type = getTagType(argumentsArray[x]);
         						value = getTagValue(argumentsArray[x]);
         						
         						tags.add(value);
         						x++;
         					}
         					
         					control.getPhotosByTag(tags);
         				
     					}else{
     						 System.out.println("Error: Not tags specified.");
     					}
     					*/ 					 
     				 }else if (argumentsArray[0].equalsIgnoreCase("logout")){ // ------------------ interactive - LOGOUT
     					 
     					 control.logout();
     					 
     				 }else{
     					 System.out.println("Error: Invalid command. Try again."); 
     				 }
     			 }else{
     				 System.out.println("Error: Invalid command. Try again.");
     			 }
     			
     			
     		} catch (IOException e) {
     			//e.printStackTrace();
     			System.out.println("Error: Not a valid input.");
     			//System.exit(0);
     		} catch (NoSuchElementException e){
     			System.out.println("Error: Not a valid input.");
     		}
        }
       		
	}
	
	/**
	 * This method takes a string and checks whether it follows the specified format for a date. 
	 * @param String of the date date
	 * @return true if the syntax of the date is correct, false otherwise.
	 */
	public static boolean checkDate(String date){
		//  M M / D D / Y Y Y Y -  H  H  :  M  M  :  S  S 
		//  0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18
		
		if (date.length() != 19){
			return false;
		}
		
		if (date.charAt(2) != '/'){
			return false;
		}
		
		if (date.charAt(5) != '/'){
			return false;
		}
		
		if (date.charAt(10) != '-'){
			return false;
		}
		
		if (date.charAt(13) != ':'){
			return false;
		}
		
		if (date.charAt(16) != ':'){
			return false;
		}
	
		
		String month = date.substring(0, 2);
		String day = date.substring(3, 5);
		String year = date.substring(6, 10);
		String hour = date.substring(11, 13);
		String minute = date.substring(14, 16);
		String second = date.substring(17, 19);
		
		int number = 0;
		
		try {
			number = Integer.parseInt(month);
			number = Integer.parseInt(day);
			number = Integer.parseInt(year);
			number = Integer.parseInt(hour);
			number = Integer.parseInt(minute);
			number = Integer.parseInt(second);
		} catch (NumberFormatException e){
			//e.printStackTrace();
			return false;
		}
		
		
		//System.out.println(month +" "+ day +" "+ year +" "+ hour +" "+ minute +" "+ second);
	
		return true;
	}
	
	/**
	 * Check if the input string is in the correct format to parse for a tag type and value.
	 * 
	 * @param String tag
	 * @return true if format is type:value
	 */
	public static boolean checkTagInput(String tag){
		
		for (int i = 0; i < tag.length(); i++){
			if (tag.charAt(i) == ':') {
				return true;
			}
		}
		
		return false;
	}
	

	
	/**
	 * Parse the string input and return the tagType.
	 * @return String of the tagType
	 */
	public static String getTagType(String tag){
		String result = null;
		int i;
		
		for (i = 0; i < tag.length(); i++){
			if (tag.charAt(i) == ':'){
				break;
			}
		}
		
		result = tag.substring(0, i);
		
		return result;
	}
	
	
	/**
	 * Parse the String input and return the tagValue.
	 * @return String of the tagValue
	 */
	public static String getTagValue(String tag){
		String result = null;
		int i;
		
		for (i = 0; i < tag.length(); i++){
			if (tag.charAt(i) == ':'){
				break;
			}
		}
		
		result = tag.substring(i+1, tag.length());
		
		return result;
	}
	
	
	/**
	 * This method will remove the commas from a token string.
	 * @param input
	 * @return input without the comma
	 */
	public static String removeComma(String input){
		//int i = 0;
		
	/*	for (i = 0; i < input.length(); i++){
			if (input.charAt(i) == ','){
				break;
			}
		}
		
		input = input.substring(0, i);
	*/
		
		input.replace(",", "");
		
		return input;
	}
	
	
	/**
	 * Checks if the format of the query for the getphotosbytag method has the tagtypes or just tag values.
	 * @param input
	 * @return
	 */
	public static boolean checkIfTagTypesExist(String input){
		if (input.contains(":")){
			return true;
		}
		return false;
	}
}
