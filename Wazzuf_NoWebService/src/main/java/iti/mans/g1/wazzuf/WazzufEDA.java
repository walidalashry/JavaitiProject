package iti.mans.g1.wazzuf;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.awt.Paint;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.stream.BaseStream;
import java.util.stream.Stream;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;


import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.PieStyler.AnnotationType;
import org.knowm.xchart.style.Styler;

import smile.data.DataFrame;
import smile.data.type.DataType;
import smile.data.vector.BaseVector;
import smile.data.vector.IntVector;


public class WazzufEDA {
    
    public static void main(String[] args) throws IOException{
        
//	• Build all java needed classes (POJO , DAO, web service
//	and a tester client for the web service)
//	• Make a web service to get the following from the data set:
//	1. Read data set and convert it to dataframe or Spark RDD
//	and display some from it.
        String path = "src/main/resources/Wuzzuf_Jobs.csv";
        
//        WazzufEDA wd = new WazzufEDA ();
        JobsProvider jProvider = new JobsProvider();
        DataFrame data = jProvider.readCSV(path);
        System.out.println(data);
        System.out.println("***************************************************************");
        
//	2. Display structure and summary of the data.
        System.out.println(data.summary());
        System.out.println(data.structure());
        System.out.println("***************************************************************");
        
//	3. Clean the data (null, duplications)
        data = data.omitNullRows ();

       
//	4. Count the jobs for each company and display that in order
//	(What are the most demanding companies for jobs?)
        Map<String,Long> companiesCount = new HashMap<>();
        companiesCount = data.stream ()
                .collect (Collectors.groupingBy (t -> String.valueOf (t.getString("Company")), Collectors.counting ()));
        companiesCount.entrySet().stream()
                    .sorted(Map.Entry.<String,Long>comparingByValue().reversed())
                    .limit(10)
                    .forEach(System.out::println)
                    ;
        System.out.println("***************************************************************");     
        Map<String,Long> toPie = companiesCount.entrySet().stream()
                .sorted(Map.Entry.<String,Long>comparingByValue().reversed())
                .limit(11)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue))
                ;

//	5. Show step 4 in a pie chart
        WazzufEDA.drawPie(toPie);
        
        
//	6. Find out What are it the most popular job titles?
        Map<String,Long> jopTitleCount = data.stream ()
                .collect (Collectors.groupingBy (t -> String.valueOf (t.getString("Title")), Collectors.counting ()));
        Map<String,Long> toBar =jopTitleCount.entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed()) 
                    .limit(10)
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue))
//                    .forEach(System.out::println)  
                    ;
        jopTitleCount.entrySet().stream()
        .sorted(Map.Entry.<String, Long>comparingByValue().reversed()) 
        .limit(10)
        .forEach(System.out::println)
        ;
        System.out.println("***************************************************************");
        
        
//	7. Show step 6 in bar chart
        WazzufEDA.drawBar(toBar);     
        
        
        
        
        
//	8. Find out the most popular areas?
        Map<String,Long> areaCount = data.stream ()
                .collect (Collectors.groupingBy (t -> String.valueOf (t.getString("Location")), Collectors.counting ()));
        Map<String,Long> toBar2=areaCount.entrySet().stream()
        .sorted(Map.Entry.<String,Long>comparingByValue().reversed()) 
        .limit(10)
        .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue))
        ;
        areaCount.entrySet().stream()
                    .sorted(Map.Entry.<String,Long>comparingByValue().reversed()) 
                    .limit(10)
                    .forEach(System.out::println)
                    ;
        
        
        System.out.println("***************************************************************");
        
        
//	9. Show step 8 in bar chart
        
        WazzufEDA.drawBar(toBar2);
        
        


//	10. Print skills one by one and how many each repeated and
//	order the output to find out the most important skills
//	required?

        
        List<String> SkillsList = new ArrayList<>();
        List<String> Skills = new ArrayList<>();
        data.stream()
            .forEach(t -> SkillsList.add(t.getString("Skills")));
        for(String skill:SkillsList){
            Arrays.stream(skill.split( "," )).forEach(t -> Skills.add(t.trim().toLowerCase()));
        }
        Map<String,Long> skillsCount = Skills.stream() 
                                  .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        
        skillsCount.entrySet().stream()
                    .sorted(Map.Entry.<String,Long>comparingByValue().reversed()) 
                    .limit(10)
                    .forEach(System.out::println);
        System.out.println("***************************************************************");
        
        
//	11. Factorize the YearsExp feature and convert it to numbers
//	in new col. (Bounce )
        Map<String,Long> yearsExpCount = data.stream ()
                .collect (Collectors.groupingBy (t -> String.valueOf (t.getString("YearsExp")), Collectors.counting ()));
//        yearsExpCount.entrySet().stream()
//                    .sorted(Map.Entry.<String,Long>comparingByValue().reversed()) 
//                    .forEach(System.out::println);
        System.out.println("***************************************************************");
        
        List<String> yearsExp = new ArrayList<>();
        List<String> maxmin = new ArrayList<>();
        List<String> maxExp = new ArrayList<>();
        List<String> minExp = new ArrayList<>();
        
        data.stream()
            .forEach(t -> yearsExp.add(t.getString("YearsExp")));
        for(String field:yearsExp){
            maxmin.add(field.split(" ")[0]);
        }
        for(String field:maxmin){
            String val1;  
            String val2;
            try{
               val1 = field.split("\\+|\\-")[0];
               
               try{
                   val2 = field.split("\\+|\\-")[1];
               }catch(Exception e){
                   val2 = "null";
               }
            minExp.add(val1);
            maxExp.add(val2);
            }catch(Exception e){
                System.out.println("ERRROR");
//                minExp.add("null");
//                maxExp.add("null");
            }
            
        }
      
        
       // convert to csv:
       BufferedWriter br = new BufferedWriter(new FileWriter("myfile.csv"));
       StringBuilder sb = new StringBuilder();

       // Append strings from array+
       sb.append("minExp,maxExp\n");
       
       for (int i=0; i<maxmin.size() ; i++) {
        sb.append(minExp.get(i));
        sb.append(",");
        sb.append(maxExp.get(i));
        sb.append("\n");
        }

        br.write(sb.toString());
        br.close();
        
        //read csv for new data
        
        
        JobsProvider m = new JobsProvider();
        String epath = "myfile.csv";
        DataFrame minmax = m.readCSV(epath);
        System.out.println(minmax);
       
                
                
        
        
        
//	12. Apply K-means for job title and companies (Bounce )  
        


        
    }
    
    public static void drawBar(Map<String,Long> jopTitleCount) throws IOException {
    	
        List<String> Titles = jopTitleCount.keySet().stream ().collect (Collectors.toList ());
        List<Long> Count = jopTitleCount.values().stream ().collect (Collectors.toList ());     

        
        // Create Chart
        CategoryChart chart = new CategoryChartBuilder ().width (1024).height (768).title ("Bar Chart").xAxisTitle ("Names").yAxisTitle ("Count").build ();
        // Customize Chart
        chart.getStyler ().setLegendPosition (Styler.LegendPosition.InsideNW);
        chart.getStyler ().setHasAnnotations (true);
        chart.getStyler ().setXAxisLabelRotation(45);
        // Series
        chart.addSeries ("Count", Titles, Count);
        // Show it
        chart.getStyler().setAxisTickMarksColor(Color.red);
        Color[] sliceColors =
        	    new Color[] {

        	     new Color(0,111, 255)
        	    };
        chart.getStyler().setSeriesColors(sliceColors);
        new SwingWrapper (chart).displayChart ();
        
        BitmapEncoder.saveBitmap(chart, "popularAreas", BitmapFormat.JPG);
    }
    public static void drawPie(Map<String,Long> jopTitleCount) throws IOException {
        
        PieChart chart = new PieChartBuilder ().width (800).height (600).title ("Pie Char").build ();
        // Customize Chart
//        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setAnnotationType(AnnotationType.LabelAndValue);
        chart.getStyler().setAnnotationDistance(1.15);
        chart.getStyler().setPlotContentSize(.7);
        chart.getStyler().setStartAngleInDegrees(90);
        // Series
        jopTitleCount.remove("Confidential");
        for (Map.Entry<String,Long> entry : jopTitleCount.entrySet())
          chart.addSeries (entry.getKey(), entry.getValue());
    
        // Show it
        new SwingWrapper (chart).displayChart ();
        
        BitmapEncoder.saveBitmap(chart, "popularJobs", BitmapFormat.JPG);
    }
}
