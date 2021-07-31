package Wazzuf_Pack;

import java.io.IOException;
import java.net.URISyntaxException;
import org.apache.commons.csv.CSVFormat;
import smile.data.DataFrame;
import smile.io.Read;

public class MinMaxProv {
    private DataFrame minmax;
    public DataFrame readCSV(String path) {
        CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader ();
        DataFrame df = null;
        try {
            df = Read.csv (path, format);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace ();
        }
        minmax = df;
        return minmax;
    }
}
