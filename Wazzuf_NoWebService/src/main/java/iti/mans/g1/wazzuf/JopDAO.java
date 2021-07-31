package iti.mans.g1.wazzuf;


	import java.io.File;
	import java.nio.file.Files;
	import java.util.ArrayList;
	import java.util.List;

public class JopDAO {
	    private List<Jop> jopList = new ArrayList<>();

	    public ArrayList<Jop> readCitiesCSV(String filePath){

	        File file = new File(filePath);

	        List<String>  lines = new ArrayList<>();
	        try {
	            lines = Files.readAllLines(file.toPath());
	        }catch (Exception e){
	            System.out.println(e.getStackTrace());
	        }

	        for(int ind=1 ;ind < lines.size(); ind++){
	            String[] attributes = lines.get(ind).split(",");

//	            }

	           jopList.add(new Jop(attributes[0].trim().replace("\"", ""),
	                    attributes[1].trim().replace("\"", ""),
	                    attributes[2].trim().replace("\"", ""),
	                    attributes[3].trim().replace("\"", ""),
	                    attributes[4].trim().replace("\"", ""),
	                    attributes[5].trim().replace("\"", ""),
	                    attributes[6].trim().replace("\"", ""),
	                    attributes[7].trim().replace("\"", "")));

	        }


	        System.out.println("Data is loaded and consist of "+ jopList.size()+ " records");
	        return (ArrayList<Jop>) jopList;

	    }

	}

