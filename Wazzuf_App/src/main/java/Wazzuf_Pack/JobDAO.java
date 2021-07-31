/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Wazzuf_Pack;

import java.io.IOException;
import java.net.URISyntaxException;
import org.apache.commons.csv.CSVFormat;
import smile.data.DataFrame;
import smile.io.Read;

public class JobDAO {
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
