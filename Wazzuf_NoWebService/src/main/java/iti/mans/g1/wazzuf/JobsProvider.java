package iti.mans.g1.wazzuf;

import java.io.IOException;
import java.net.URISyntaxException;
import org.apache.commons.csv.CSVFormat;
import smile.data.DataFrame;
import smile.io.Read;


public class JobsProvider {
    private DataFrame jobsDataFrame;
    public DataFrame readCSV(String path) {
        CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader ();
        DataFrame df = null;
        try {
            df = Read.csv (path, format);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace ();
        }
        jobsDataFrame = df;
        return jobsDataFrame;
    } 
}
